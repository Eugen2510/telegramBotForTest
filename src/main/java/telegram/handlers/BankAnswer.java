package telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.button_utils.KeyboardCreator;
import telegram.button_utils.MessageCreator;
import telegram.customer.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BankAnswer {
    public static SendMessage generateAnswer(User user){
        List<List<InlineKeyboardButton>> collect = Stream.of("NBU", "PrivatBank", "MonoBank")
                .map(s -> s.equals(user.getBank()) ? "<" + s + ">" : s)
                .map(s -> KeyboardCreator.createButton(s, s.replaceAll("\\![A-Za-z]", "")))
                .collect(Collectors.toList());

        List<InlineKeyboardButton> backToSettingsButton = KeyboardCreator.createButton("Повернутися до налаштувань", "backToSettings");

        collect.add(backToSettingsButton);
        InlineKeyboardMarkup banks = KeyboardCreator.createKeyboard(collect);

        SendMessage bankMessage = MessageCreator.createMessageWithButton(banks, "Оберіть банк");
        bankMessage.setChatId(user.getId());

        return bankMessage;
    }
}
