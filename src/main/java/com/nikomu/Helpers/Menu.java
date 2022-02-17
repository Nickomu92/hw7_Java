package com.nikomu.Helpers;

import java.util.Scanner;

public class Menu {

    // Метод, представляющий главное меню приложения
    public static void makeMainMenu() {
        String newMenu = Message.returnHeaderMsg("\n\tДомашнее задание №7\n") +
                Message.returnErrorMsg("\tМулина Николая\n") +
                Message.returnWarningMsg("[1]") + " - Посмотреть все контакты;\n" +
                Message.returnWarningMsg("[2]") + " - Добавить контакт;\n" +
                Message.returnWarningMsg("[3]") + " - Изменить контакт;\n" +
                Message.returnWarningMsg("[4]") + " - Удалить контакт;\n" +
                Message.returnWarningMsg("[q]") + " - Выйти из приложения.\n" +
                Message.returnPrimaryMsg("Ваш выбор: ");
        System.out.print(newMenu);
    }

    // Метод, представляющий меню поиска контакта в БД
    public static void makeFindMenu() {
        String newMenu = Message.returnHeaderMsg("\n\tПоиск контакта по:\n") +
                Message.returnWarningMsg("[1]") + " - Идентификатору (Id);\n" +
                Message.returnWarningMsg("[2]") + " - Ф.И.О.;\n" +
                Message.returnWarningMsg("[3]") + " - Номеру телефона;\n" +
                Message.returnWarningMsg("[q]") + " - Выйти.\n" +
                Message.returnPrimaryMsg("Ваш выбор: ");
        System.out.print(newMenu);
    }

    // Метод, представляющий диалоговое меню с вариантами выбора - да или нет
    public static void makeIsContinueMenu(String menuHeader) {
        String newMenu = Message.returnHeaderMsg(String.format("\n\t%s\n", menuHeader)) +
                Message.returnWarningMsg("[y]") + " - Да;\n" +
                Message.returnWarningMsg("[n]") + " - Нет;\n" +
                Message.returnPrimaryMsg("Baш выбор: ");
        System.out.print(newMenu);
    }

    // Метод для отображения главного меню
    public static void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        String symbol;

        do {
            makeMainMenu();
            symbol = scanner.next();

            switch(symbol) {
                case "1":
                    Action.readAllContacts();
                    break;
                case "2":
                    Action.createContact();
                    break;
                case "3":
                    Action.updateContact();
                    break;
                case "4":
                    Action.deleteContact();
                    break;
                case "q": case "Q": case "й": case "Й":
                    Message.warningMsg("\n\t[СООБЩЕНИЕ] - Выходим из приложения...\n");
                    break;
                default:
                    Message.errorMsg("\n\t[СООБЩЕНИЕ] - Нет такого пункта меню!\n");
            }

        } while(!symbol.equals("q") && !symbol.equals("Q") && !symbol.equals("й") && !symbol.equals("Й"));
    }

    // Медод для отображения меню поиска контакта
    public static int showFindMenu() {
        Scanner scanner = new Scanner(System.in);
        String symbol;

        do {
            makeFindMenu();
            symbol = scanner.next();

            switch(symbol) {
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    return 3;
                case "q": case "Q": case "й": case "Й":
                    Message.warningMsg("\n\t[СООБЩЕНИЕ] - Выходим...\n");
                    break;
                default:
                    Message.errorMsg("\n\t[СООБЩЕНИЕ] - Нет такого пункта меню!\n");
            }

        } while(!symbol.equals("q") && !symbol.equals("Q") && !symbol.equals("й") && !symbol.equals("Й"));

        return 0;
    }

    // Метод для отображения диалогового окна
    public static boolean showIsContinueMenu(String menuHeader) {
        Scanner scanner = new Scanner(System.in);
        String symbol;
        boolean result;

        do {
            makeIsContinueMenu(menuHeader);
            symbol = scanner.next();

            if (symbol.equals("y") || symbol.equals("Y") || symbol.equals("н") || symbol.equals("Н")) {
                return true;
            } else if (symbol.equals("n") || symbol.equals("N") || symbol.equals("т") || symbol.equals("Т")) {
                return false;
            } else {
                Message.errorMsg("\n\t[СООБЩЕНИЕ] - Нет такого пункта меню...\n");
            }
        } while(true);
    }
}
