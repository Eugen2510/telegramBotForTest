package telegram.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.button_utils.KeyboardCreator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InfoCommand extends BotCommand {

    public InfoCommand(){
        super("info", "Info command");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String text = "Вітаю! Цей бот допоможе вам дізнатись актуальні курси валют." +
                "Для початку налаштувань натисніть команду /start з закріпленого меню" +
                ", або введіть /start з клавіатури. Ви можете обрати банк для розрахунку" +
                "курсів, валюту, зручне для вас округлення, а також час для отримання повідомлень." +
                "\nНалаштування за замовчанням: \nбанк - ПриватБанк\nвалюта - USD" +
                "\nокруглення - до 2 знаків після коми.\nДля отримання інформації відповідно " +
                "ваших налаштуваннь натисніть /get з головного меню, або введіть /get з клавіатури" +
                ", або натисніть на кнопку \"отримати інфо\"\nБережіть себе!\nБажаємо перемоги!!!.";

        List<InlineKeyboardButton> button = KeyboardCreator.createButton("Отримати інфо", "Get info");

        InlineKeyboardMarkup keyboard = KeyboardCreator.createKeyboard(button);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(keyboard);
        sendMessage.setChatId(chat.getId());
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
