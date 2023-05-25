package telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.button_utils.KeyboardCreator;
import telegram.button_utils.MessageCreator;

import java.util.List;

public class SettingsAnswer {
    public static SendMessage generateAnswer(long userId){
        List<InlineKeyboardButton> bankButton = KeyboardCreator.createButton("����", "bank");
        List<InlineKeyboardButton> currencyButton = KeyboardCreator.createButton("������", "currency");
        List<InlineKeyboardButton> decimalButton = KeyboardCreator.createButton("ʳ������ ����� ���� ����", "numOfDecimal");
        List<InlineKeyboardButton> timeButton = KeyboardCreator.createButton("��� ��������", "notificationTime");

        InlineKeyboardMarkup keyboard =
                KeyboardCreator.createKeyboard(bankButton,
                        currencyButton,
                        decimalButton,
                        timeButton);

        SendMessage settingsMessage = MessageCreator.createMessageWithButton(keyboard, "������������");
        settingsMessage.setChatId(userId);
        return settingsMessage;
    }
}
