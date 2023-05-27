package telegram.button_utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.nio.charset.StandardCharsets;

public class MessageCreator {
    public static SendMessage createMessageWithButton(InlineKeyboardMarkup buttons, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        sendMessage.setReplyMarkup(buttons);
        return sendMessage;
    }

    public static SendMessage createTextMessage(String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        return sendMessage;
    }
}
