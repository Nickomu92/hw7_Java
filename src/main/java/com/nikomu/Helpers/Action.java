package com.nikomu.Helpers;

import com.nikomu.Entities.*;
import org.hibernate.Session;
import javax.persistence.Query;
import java.util.*;

public class Action {

    // Метод, который выполняется при чтении всех контактов из БД
    public static void readAllContacts() {
        Session session = Connection.getSessionFactory();
        session.beginTransaction();

        Query query = session.createQuery("FROM User ORDER BY surname");
        System.out.printf("\n%s\n", showContacts(query.getResultList()));

        session.getTransaction().commit();
    }

    // Метод, который выполняется при создании контакта в БД
    public static void createContact() {
        User newUser = new User();
        Scanner scanner = new Scanner(System.in);

        Message.primaryMsg("\nВведите фамилию: ");
        newUser.setSurname(scanner.next());

        Message.primaryMsg("\nВведите имя: ");
        newUser.setFirstname(scanner.next());

        Message.primaryMsg("\nВведите отчество: ");
        newUser.setLastname(scanner.next());

        boolean isContinue = false;

        do {
            String fullPhoneNumber = inputPhoneNumber();
            newUser.addPhoneNumber(new PhoneNumber(getPhone(fullPhoneNumber), getOperatorCode(fullPhoneNumber)));
            isContinue = Menu.showIsContinueMenu("Добавить еще номер телефона?");
        } while(isContinue);

        Address newUserAddress = new Address();

        newUserAddress.setCity(inputCity(true, null));
        scanner.nextLine();
        Message.primaryMsg("\nВведите название улицы: ");
        newUserAddress.setStreet(scanner.nextLine());

        Message.primaryMsg("\nВведите номер дома: ");
        newUserAddress.setHouse(scanner.nextLine());

        Message.primaryMsg("\nВведите номер квартиры: ");
        newUserAddress.setApartment(scanner.nextLine());

        newUser.setAddress(newUserAddress);

        Session session = Connection.getSessionFactory();
        session.beginTransaction();
        session.save(newUser);
        session.getTransaction().commit();
    }

    // Метод, который выполняется при обновлении контакта в БД
    public static void updateContact() {
        int number = Menu.showFindMenu();
        User updateContact = null;
        Scanner scanner = new Scanner(System.in);
        Session session = Connection.getSessionFactory();

        session.beginTransaction();

        if(number == 0) {
            return;
        } else if(number == 1) {
            updateContact = session.get(User.class, inputId());
        } else if(number == 2) {
           updateContact = getContactByFullName();
        } else if(number == 3) {
           updateContact = getFindPhoneNumber().getUser();
        }

        if(updateContact == null) {
            Message.errorMsg("\n\t[СООБЩЕНИЕ] - Ошибка изменения! Указанный контакт не существует.\n");
        } else {
            Message.primaryMsg("\nИзменение фамилии (" + updateContact.getSurname() + "), [ENTER] - пропустить: ");
            String surname = scanner.nextLine();
            if(surname != null && surname.trim() != "") {
                updateContact.setSurname(surname);
            }

            Message.primaryMsg("\nИзменение имени (" + updateContact.getFirstname() + "), [ENTER] - пропустить: ");
            String firstname = scanner.nextLine();
            if(firstname != null && firstname.trim() != "") {
                updateContact.setFirstname(firstname);
            }

            Message.primaryMsg("\nИзменение отчества (" + updateContact.getLastname() + "), [ENTER] - пропустить: ");
            String lastname = scanner.nextLine();
            if(lastname != null && lastname.trim() != "") {
                updateContact.setLastname(lastname);
            }

            for (PhoneNumber item : updateContact.getPhoneNumbers()) {
                String countryCode = updateContact.getAddress().getCity().getCountry().getCountryCode();
                String operatorCode = item.getOperatorCode();
                String cityCode = updateContact.getAddress().getCity().getCityCode();
                String phoneNumber = item.getPhoneNumber();
                String fullPhoneNumber = countryCode + "(" + (operatorCode != null ? operatorCode : cityCode) +
                        ")" + phoneNumber;

                Message.primaryMsg("\nИзменение номера телефона (" + fullPhoneNumber + "), [ENTER] - пропустить: ");

                fullPhoneNumber = scanner.nextLine();

                phoneNumber = getPhone(fullPhoneNumber);

                if (phoneNumber != null && phoneNumber.trim() != "") {
                    item.setPhoneNumber(phoneNumber);
                }

                operatorCode = getOperatorCode(fullPhoneNumber);
                if (operatorCode != null && operatorCode.trim() != "") {
                    item.setOperatorCode(operatorCode);
                }
            }

            boolean isContinue = Menu.showIsContinueMenu("Добавить еще номер телефона?");

            while(isContinue) {
                String fullPhoneNumber = inputPhoneNumber();
                updateContact.addPhoneNumber(new PhoneNumber(getPhone(fullPhoneNumber),
                        getOperatorCode(fullPhoneNumber)));
                isContinue = Menu.showIsContinueMenu("Добавить еще номер телефона?");
            };

            updateContact.getAddress().setCity(inputCity(false, updateContact.getAddress().getCity()));

            Message.primaryMsg("\nИзменение названия улицы (" + updateContact.getAddress().getStreet() + "), " +
                    "[ENTER] - пропустить: ");
            String street = scanner.nextLine();
            if(street != null && street.trim() != "") {
                updateContact.getAddress().setStreet(street);
            }

            Message.primaryMsg("\nИзменение номера дома (" + updateContact.getAddress().getHouse() + "), " +
                    "[ENTER] - пропустить: ");
            String house = scanner.nextLine();
            if(house != null && house.trim() != "") {
                updateContact.getAddress().setHouse(house);
            }

            Message.primaryMsg("\nИзменение номера квартиры (" + updateContact.getAddress().getApartment() + "), " +
                    "[ENTER] - пропустить: ");
            String apartment = scanner.nextLine();
            if(apartment != null && apartment.trim() != "") {
                updateContact.getAddress().setApartment(apartment);
            }
        }

        session.saveOrUpdate(updateContact);
        session.getTransaction().commit();
    }

    // Метод, который выполняется при удалении контакта из БД
    public static void deleteContact() {
        int number = Menu.showFindMenu();

        User deleteContact = null;

        Session session = Connection.getSessionFactory();
        session.beginTransaction();

        if(number == 0) {
            return;
        } else if(number == 1) {
            deleteContact = session.get(User.class, inputId());
        } else if(number == 2) {
            deleteContact = getContactByFullName();
        } else if(number == 3) {
            deleteContact = getFindPhoneNumber().getUser();
        }

        if(deleteContact == null && number != 0) {
            Message.errorMsg("\n\t[СООБЩЕНИЕ] - Ошибка удаления! Указанный контакт не существует.\n");
        } else {
            session.delete(deleteContact);
        }

        session.getTransaction().commit();
    }

    // Метод для отображения списка контактов в виде таблички
    public static String showContacts(List<User> users) {
        String contacts = Message.returnPrimaryMsg(String.format("%-3s | %-15s | %-15s | %-15s | %-50s | %s\n",
                "№", "Фамилия", "Имя", "Отчество", "Адрес", "Номер телефона"));

        for(User userItem : users) {

            contacts += String.format("%-3s | %-15s | %-15s | %-15s | %-50s | ",
                    userItem.getId(),
                    userItem.getSurname(),
                    userItem.getFirstname(),
                    userItem.getLastname(),
                    userItem.getAddress() != null ? "г. " + userItem.getAddress().getCity().getCityName() + ", " +
                            userItem.getAddress().getStreet() + ", " +
                            "дом №" + userItem.getAddress().getHouse() + ", " +
                            "кв. " + userItem.getAddress().getApartment() : "");


            for(PhoneNumber phoneNumberItem : userItem.getPhoneNumbers()) {

                String countryCode = phoneNumberItem.getUser().getAddress().getCity().getCountry().getCountryCode();
                String cityOrOperatorCode;

                if(phoneNumberItem.getOperatorCode() == null) {
                    cityOrOperatorCode = phoneNumberItem.getUser().getAddress().getCity().getCityCode();
                } else {
                    cityOrOperatorCode = phoneNumberItem.getOperatorCode();
                }

                String phoneNumber = phoneNumberItem.getPhoneNumber();
                contacts += String.format(" +%14s ", countryCode + "(" + cityOrOperatorCode + ")" + phoneNumber);
            }

           contacts += "\n";
        }

        return contacts;
    }

    // Метод для проверки является ли полученная строка числом
    public static boolean checkIsDigit(String number) {
        try {
            Long.parseLong(number);
            return true;
        } catch(Exception ex) {
            return false;
        }
    }

    // Метод для получения идентификатора контакта от пользователя
    public static int inputId() {
        Scanner scanner = new Scanner(System.in);
        String symbol;

        do {
            Message.primaryMsg("\nВведите Id: ");
            symbol = scanner.next();

            if(!Action.checkIsDigit(symbol) && Integer.parseInt(symbol) <= 0) {
                Message.errorMsg("\n\t[СООБЩЕНИЕ] - Id должен быть больше 0.\n");
            }

        } while(!Action.checkIsDigit(symbol) && Integer.parseInt(symbol) <= 0);

        return Integer.parseInt(symbol);
    }

    // Метод для получения полного номера телефона от пользователя
    public static String inputPhoneNumber() {
        String inputPhoneNumber;
        String phoneNumber = null;
        Scanner scanner = new Scanner(System.in);

        do {
            Message.primaryMsg("\nВведите номер телефона: ");
            inputPhoneNumber = scanner.next();

            if(checkIsDigit(inputPhoneNumber)) {
                if(inputPhoneNumber.length() >= 7 && inputPhoneNumber.length() <= 13) {
                    phoneNumber = inputPhoneNumber;
                }
            }

            if(phoneNumber == null) {
                Message.errorMsg("\n\t[СООБЩЕНИЕ] - Неправильный формат номера телефона\n");
            }

        } while(phoneNumber == null);

        System.out.println(phoneNumber);
        return phoneNumber;
    }

    // Метод для получения названия страны от пользователя
    public static Country inputCountry() {
        Scanner scanner = new Scanner(System.in);
        Session session = Connection.getSessionFactory();
        List<Country> userCountry;

        do {
            Message.primaryMsg("\nВведите название страны: ");
            String country = scanner.next();

            session.beginTransaction();

            Query query = session.createQuery("FROM Country WHERE countryName = :param1");
            query.setParameter("param1", country);

            userCountry = query.getResultList();

            session.getTransaction().commit();

            if(userCountry.size() < 1) {
                Message.errorMsg("\n\t[СООБЩЕНИЕ] - В базе данных нет такой страны\n");
            }
        } while(userCountry.size() < 1);

        return userCountry.get(0);
    }

    // Метод для получения названия города от пользователя
    public static City inputCity(boolean isLoop, City contactCity) {
        Scanner scanner = new Scanner(System.in);

        List<City> userCity;
        String city;

        do {
            if(contactCity == null) {
                Message.primaryMsg("\nВведите название города: ");
                city = scanner.nextLine();
            } else {
                Message.primaryMsg("\nИзменение названия города (" + contactCity.getCityName() + "), " +
                        "[ENTER] - пропустить: ");
                city = scanner.nextLine();
                if(city.trim() == "") {
                    city = contactCity.getCityName();
                }
            }

            Session session = Connection.getSessionFactory();
            session.beginTransaction();

            Query query = session.createQuery("FROM City WHERE cityName = :param1");
            query.setParameter("param1", city);
            userCity = query.getResultList();

            session.getTransaction().commit();

            if(userCity.size() < 1) {
                Message.errorMsg("\n\t[СООБЩЕНИЕ] - В базе данных нет такого города\n");
                isLoop = true;
            }
        } while(isLoop && userCity.size() < 1);

        return userCity.get(0);
    }

    // Метод для получения номера телефона без кода города/оператора и кода страны
    public static String getPhone(String fullPhoneNumber) {
        if(fullPhoneNumber.length() >= 7) {
            return fullPhoneNumber.substring(fullPhoneNumber.length() - 7, fullPhoneNumber.length());
        }

        return null;
    }

    // Метод для получения кода оператора
    public static String getOperatorCode(String fullPhoneNumber) {
        if(fullPhoneNumber.length() == 8) {
            return fullPhoneNumber.substring(fullPhoneNumber.length() - 8, fullPhoneNumber.length() - 7);
        } else if(fullPhoneNumber.length() == 9) {
            return fullPhoneNumber.substring(fullPhoneNumber.length() - 9, fullPhoneNumber.length() - 7);
        } else if(fullPhoneNumber.length() >= 10) {
            return fullPhoneNumber.substring(fullPhoneNumber.length() - 10, fullPhoneNumber.length() - 7);
        }

        return null;
    }

    // Метод для получения User из БД по указанному полному имени
    public static User getContactByFullName() {
        Scanner scanner = new Scanner(System.in);
        Session session = Connection.getSessionFactory();

        Message.primaryMsg("\nВведите фамилию: ");
        String surname = scanner.next();

        Message.primaryMsg("\nВведите имя: ");
        String firstname = scanner.next();

        Message.primaryMsg("\nВведите отчество: ");
        String lastname = scanner.next();

        session.beginTransaction();

        Query query = session.createQuery("FROM User WHERE surname = :param1 AND firstname = :param2 AND lastname = :param3");
        query.setParameter("param1", surname);
        query.setParameter("param2", firstname);
        query.setParameter("param3", lastname);

        List<User> contacts = query.getResultList();
        session.getTransaction().commit();

        if(contacts.size() > 0) {
            return contacts.get(0);
        } else {
            return null;
        }
    }

    // Метод для получения PhoneNumber из БД по указанному полному номеру телефона
    public static PhoneNumber getFindPhoneNumber() {
        Scanner scanner = new Scanner(System.in);
        String fullPhoneNumber;

        do {
            Message.primaryMsg("\nВведите номер телефона: ");
            fullPhoneNumber = scanner.next();

        } while(!checkIsDigit(fullPhoneNumber) && fullPhoneNumber.length() > 7);

        String phoneNumber = getPhone(fullPhoneNumber);

        Session session = Connection.getSessionFactory();
        session.beginTransaction();

        Query query = session.createQuery("FROM PhoneNumber WHERE phoneNumber = :param1");
        query.setParameter("param1", phoneNumber);

        List<PhoneNumber> contacts = query.getResultList();
        session.getTransaction().commit();

        if(contacts.size() > 0) {
            return contacts.get(0);
        } else {
            return null;
        }
    }

    // Метод для получения всех номеров телефонов из БД указанного контакта
    public static List<PhoneNumber> getUserPhoneNumbers(int id) {
        Session session = Connection.getSessionFactory();
        session.beginTransaction();

        Query query = session.createQuery("FROM PhoneNumber WHERE user.id = :param1");
        query.setParameter("param1", id);

        List<PhoneNumber> contacts = query.getResultList();
        session.getTransaction().commit();

        return contacts;
    }
}
