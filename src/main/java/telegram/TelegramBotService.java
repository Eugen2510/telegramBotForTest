package telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Scanner;

public class TelegramBotService {
    MyCurrencyTelegramBot  currencyTelegramBot;
    public TelegramBotService() {
        currencyTelegramBot = new MyCurrencyTelegramBot();
       try {
           TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
           telegramBotsApi.registerBot(currencyTelegramBot);
       } catch (TelegramApiException e) {
           e.printStackTrace();
       }

        Scanner scanner = new Scanner(System.in);
            if(scanner.next().equals("e")){
                currencyTelegramBot.saveData();
                System.exit(0);
            }


    }
}
