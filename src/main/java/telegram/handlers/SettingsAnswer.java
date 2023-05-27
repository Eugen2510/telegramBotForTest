package telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.button_utils.KeyboardCreator;
import telegram.button_utils.MessageCreator;

import java.util.List;

public class SettingsAnswer {
    public static SendMessage generateAnswer(long userId){
        List<InlineKeyboardButton> bankButton = KeyboardCreator.createButton("Банк", "bank");
        List<InlineKeyboardButton> currencyButton = KeyboardCreator.createButton("Валюта", "currency");
        List<InlineKeyboardButton> decimalButton = KeyboardCreator.createButton("Кількість знаків після коми", "numOfDecimal");
        List<InlineKeyboardButton> timeButton = KeyboardCreator.createButton("Час оповіщень", "notificationTime");

        InlineKeyboardMarkup keyboard =
                KeyboardCreator.createKeyboard(bankButton,
                        currencyButton,
                        decimalButton,
                        timeButton);

        SendMessage settingsMessage = MessageCreator.createMessageWithButton(keyboard, "Налаштування");
        settingsMessage.setChatId(userId);
        return settingsMessage;
    }
}
