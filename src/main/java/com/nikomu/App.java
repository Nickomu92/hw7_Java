/*******************************************************************************************************************
 Создать консольную телефонную книгу
 Отошение один ко одному(10 баллов максимум)
 1.Посмотреть все контакты
 2.Добавить контакт
 3.Изменить контакт
 4.Удалить контакт

 Отошение один ко многим(12 баллов)
 У Ивана может быть несколько номеров
 *******************************************************************************************************************/

package com.nikomu;

import com.nikomu.Entities.*;
import com.nikomu.Helpers.Menu;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class App 
{
    public static void main(String[] args) {
        Menu.showMainMenu();
    }
}
