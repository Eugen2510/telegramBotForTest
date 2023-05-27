package telegram.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
                "\nокруглення до 2 знаків після коми.\nБережіть себе!\nБажаємо перемоги!!!.";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chat.getId());
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
