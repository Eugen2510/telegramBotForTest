package telegram;


import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import telegram.commands.InfoCommand;
import telegram.handlers.*;
import telegram.commands.StartCommand;
import telegram.customer.User;
import telegram.customer.UserStateSaver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MyCurrencyTelegramBot extends TelegramLongPollingCommandBot {
    Map<Long, User> users = new ConcurrentHashMap<>();
    User user = new User();
    MyCurrencyTelegramBot() {

        register(new StartCommand());
        register(new InfoCommand());
    }
    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }

    @Override
    public String getBotToken() {

        return BotConstants.BOT_TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        System.out.println("user.getId() = " + user.getId());
        if (update.hasCallbackQuery()){
            long userId = update.getCallbackQuery().getFrom().getId();
            if(users.get(userId) == null){
                users.put(userId, new User());
            }
            user = users.get(userId);
            String input = update.getCallbackQuery().getData();
            user.setId(userId);
            System.out.println(update.getCallbackQuery().getData());
            System.out.println(userId);
            if (input.equals("Settings") || input.equals("backToSettings")){

                sendApiMethodAsync(SettingsAnswer.generateAnswer(userId));
            }

            if (input.equals("bank")){
                sendApiMethodAsync(BankAnswer.generateAnswer(user));
            }

            if (input.equals("NBU") || input.equals("MonoBank") || input.equals("PrivatBank")){
                UserStateSaver.saveBank(user, input);
                sendApiMethodAsync(BankAnswer.generateAnswer(user));
            }

            if (input.equals("currency") || input.equals("USD") || input.equals("EUR")){
                sendApiMethodAsync(CurrencyAnswer.generateAnswer(user, input));
            }

            if (input.equals("numOfDecimal")
                    || input.equals("1")
                    || input.equals("2")
                    ||  input.equals("3")
                    ||  input.equals("4"))
            {
                sendApiMethodAsync(NumberOfDecimalPlacesAnswer.generateAnswer(user, input));
            }

            if(input.equals("notificationTime")){
                SendMessage message = AlertTimesSettings.settingsAlertTimeAlertTimesMessage(userId);
                sendApiMethodAsync(message);

            }
            if(input.equals("Get info")){

                SendMessage message = GetInfoAnswer.generateAnswer(user);
                sendApiMethodAsync(message);
            }



        }

        if(update.hasMessage()){
            sendApiMethodAsync(TimeSettingsAndMessageAnswer.handleMessage(update, update.getMessage().getFrom().getId(), user));
        }



    }

}
