package telegram;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import telegram.commands.GetCurrencyInfo;
import telegram.commands.InfoCommand;
import telegram.handlers.*;
import telegram.commands.StartCommand;
import telegram.customer.User;
import telegram.customer.UserStateSaver;
import telegram.schedule.ScheduledMessagesSender;
import telegram.user_data.UsersDataGetter;
import telegram.user_data.UsersDataSaver;

import java.util.Map;



public class MyCurrencyTelegramBot extends TelegramLongPollingCommandBot {
    private final static Map<Long, User> users = UsersDataGetter.getUsers("Users.json");

    User user;

    public MyCurrencyTelegramBot() {

        register(new StartCommand());
        register(new InfoCommand());
        register(new GetCurrencyInfo());

        Thread sendMessage = new Thread(() -> ScheduledMessagesSender.runSending(users,this));
        sendMessage.start();
        //не работает
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveData));


    }
    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }

    @Override
    public String getBotToken() {

        return BotConstants.BOT_TOKEN;
    }

    public static Map<Long, User> getUsers() {
        return users;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        long userId =0;
        if (update.hasCallbackQuery() || update.hasMessage()){
            userId = update.hasCallbackQuery() ?
                    update.getCallbackQuery().getFrom().getId()
                    : update.getMessage().getFrom().getId();
            if(users.get(userId) == null){
                user = new User();
                user.setId(userId);
                users.put(userId, user);
            }
            user = users.get(userId);
            UsersDataSaver.saveUsers("Users.json", users);
        }

        if (update.hasCallbackQuery()){

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

    private void saveData(){
        UsersDataSaver.saveUsers("Users.json", users);
    }

}
