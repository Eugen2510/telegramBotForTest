package telegram.commands;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.handlers.GetInfoAnswer;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class GetCurrencyInfo extends BotCommand {
    FileReader fileReader;
    public  GetCurrencyInfo(){
        super("get", "Get currency info");
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        telegram.customer.User user1 = null;
        StringBuilder json = new StringBuilder();
        try {
            fileReader = new FileReader("Users.json");
            int i;
            while ((i = fileReader.read()) != -1){
                json.append((char) i);
            }

            fileReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String usersString = json.toString();
        Type type = TypeToken.getParameterized(List.class, telegram.customer.User.class).getType();
        List<telegram.customer.User> users = new Gson().fromJson(usersString,type);
        for (telegram.customer.User value : users) {
            if (value.getId() == user.getId()) {
                user1 = value;
            }
        }

        SendMessage message = GetInfoAnswer.generateAnswer(user1);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
