package service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class Menu {
    public static int requestIntegerInput(Scanner scanner, String message) {
        System.out.println(message);
        while (!scanner.hasNextInt()) {
            System.out.println("Неверный ввод. Пожалуйста, введите целое число:");
            scanner.next(); // Пропускаем неверный ввод
        }
        return scanner.nextInt();
    }
    public static void menu() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("Добро пожаловать в программу управления!!!");
        System.out.println("                /\\_/\\");
        System.out.println("               ( o.o )");
        System.out.println("                > ^ <");
        System.out.println();

        Scanner scanner = new Scanner(System.in);

        ControlPanel controlPanel = context.getBean(ControlPanel.class);
        ControlPanelProxyImpl proxy = context.getBean(ControlPanelProxyImpl.class, controlPanel);

        System.out.println("Сгенерирована панель управления:");
        proxy.visualize();

        RequestHandlerChain handlerChain = context.getBean(RequestHandlerChain.class);
        handlerChain.addHandler(new ButtonPressHandler());
        handlerChain.addHandler(new LampBindingHandler());
        handlerChain.addHandler(new LampUnlinkHandler());

        while (true) {
            System.out.println("          Меню:");
            System.out.println("1. Нажать на кнопку");
            System.out.println("2. Привязать кнопку к лампе");
            System.out.println("3. Отвязать кнопку от лампы");
            System.out.println("4. Выйти");

            int choice = requestIntegerInput(scanner, "Ваше действие:");
            handlerChain.handleRequest(choice, proxy, scanner);

            if (choice == 4) {
                System.out.println("До свидания!");
                proxy.shutdown();
                break;
            }
        }

        context.close();
    }
}

