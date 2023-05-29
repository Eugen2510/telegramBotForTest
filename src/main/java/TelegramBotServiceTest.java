import telegram.MyCurrencyTelegramBot;
import telegram.TelegramBotService;
import telegram.user_data.UsersDataSaver;

public class TelegramBotServiceTest {
    public static void main(String[] args) {
        TelegramBotService ts = new TelegramBotService();
//        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
//            UsersDataSaver.saveUsers("Users.json", MyCurrencyTelegramBot.getUsers());
//        }));
    }

}
