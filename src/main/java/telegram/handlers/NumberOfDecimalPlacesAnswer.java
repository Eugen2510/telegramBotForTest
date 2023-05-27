package telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.button_utils.KeyboardCreator;
import telegram.button_utils.MessageCreator;
import telegram.customer.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class NumberOfDecimalPlacesAnswer {
    public static SendMessage generateAnswer(User user, String input){
        List<List<InlineKeyboardButton>> collect = new ArrayList<>();

        try{
            int i = Integer.parseInt(input);
            user.setNumOfDecimal(i);
        }catch(Exception e){
            System.out.println(e.getMessage());

        }

        IntStream.range(2, 5)
                .mapToObj(i -> i == user.getNumOfDecimal()
                        ? ">> " + i + " <<"
                        : String.valueOf(i))
                .map(st -> KeyboardCreator.createButton(st, st.replaceAll("[< >]", "")))
                .forEach(collect::add);

        List<InlineKeyboardButton> backToSettingsButton = KeyboardCreator.createButton("Повернутися до налаштувань", "backToSettings");
        collect.add(backToSettingsButton);


        InlineKeyboardMarkup keyboard = KeyboardCreator.createKeyboard(collect);

        SendMessage message = MessageCreator.createMessageWithButton(keyboard, "Оберіть кількість знаків після коми");
        message.setChatId(user.getId());
        return message;
    }

}
