package telegram.button_utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class KeyboardCreator {

    public static List<InlineKeyboardButton> createButton(String text, String callBackData){
        InlineKeyboardButton button = InlineKeyboardButton
                .builder()
                .text(new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8))
                .callbackData(new String(callBackData.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8))
                .build();

        return List.of(button);
    }

    @SafeVarargs
    public static InlineKeyboardMarkup createKeyboard(List<InlineKeyboardButton> ... buttons){
        List<List<InlineKeyboardButton>> list = List.of(buttons);
        return InlineKeyboardMarkup
                .builder()
                .keyboard(list)
                .build();
    }

    public static InlineKeyboardMarkup createKeyboard(List<List<InlineKeyboardButton>> keyboard){
        return InlineKeyboardMarkup
                .builder()
                .keyboard(keyboard)
                .build();
    }
}
