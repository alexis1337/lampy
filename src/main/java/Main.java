import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.Menu;

public class Main {
    @Autowired
    public static void main(String[] args) {
        // Загрузка конфигурации из XML-файла
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Получение бина menu из контекста
        Menu menu = context.getBean(Menu.class);

        // Вызов метода menu() для запуска программы
        menu.menu();

        // Закрытие контекста приложения
        context.close();
    }
}