package telegram;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import telegram.commands.GetCurrencyInfo;
import telegram.commands.InfoCommand;
import telegram.handlers.*;
import telegram.commands.StartCommand;
import telegram.customer.User;
import telegram.customer.UserStateSaver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MyCurrencyTelegramBot extends TelegramLongPollingCommandBot {
    private Map<Long, User> users = new ConcurrentHashMap<>();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    User user = new User();
    FileReader fileReader;
    public MyCurrencyTelegramBot() {

        register(new StartCommand());
        register(new InfoCommand());
        register(new GetCurrencyInfo());

        StringBuilder json = new StringBuilder();
        try {
            fileReader = new FileReader("Users.json");
            int i;
            while ((i = fileReader.read()) != -1)
                json.append((char) i);
            fileReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String usersString = json.toString();
        Type type = TypeToken.getParameterized(List.class, telegram.customer.User.class).getType();
        List<telegram.customer.User> users = new Gson().fromJson(usersString,type);
        for (int i = 0; i < users.size(); i++){
            this.users.put(users.get(i).getId(), users.get(i));
        }

    }
    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }

    @Override
    public String getBotToken() {

        return BotConstants.BOT_TOKEN;
    }

    public Map<Long, User> getUsers() {
        return users;
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
            users.put(userId, user);
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

            try {
                FileWriter fw = new FileWriter("Users.json");
                Collection<User> values = users.values();
                String json = gson.toJson(values);
                fw.write(json);
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        if(update.hasMessage()){
            sendApiMethodAsync(TimeSettingsAndMessageAnswer.handleMessage(update, update.getMessage().getFrom().getId(), user));
        }



    }

}
