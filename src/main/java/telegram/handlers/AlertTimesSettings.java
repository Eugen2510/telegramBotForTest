package telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AlertTimesSettings {
    public static SendMessage settingsAlertTimeAlertTimesMessage(Long chatId){
        List<KeyboardRow> keyboards = new ArrayList<>();

        for (int i = 9; ; ) {
            int end = ((i + 4) > 19) ? (i + (19 - i)) : (i + 4);
            keyboards.add(createKeyboard(i, end));
            i = end;
            if(end == 19) {
                break;
            }
        }

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(true);
        keyboard.setKeyboard(keyboards);

        SendMessage message = new  SendMessage();
        message.setChatId(chatId);
        message.setText("Оберіть час: ");
        message.setReplyMarkup(keyboard);

        return message;
    }

    private static KeyboardRow createKeyboard(int startOfRange, int endOfRange){
        KeyboardRow keyboard = new KeyboardRow();
        IntStream.range(startOfRange, endOfRange)
                .mapToObj(String::valueOf)
                .map(s -> KeyboardButton
                        .builder()
                        .text(s)
                        .build())
                .forEach(keyboard::add);
        return keyboard;
    }

}
