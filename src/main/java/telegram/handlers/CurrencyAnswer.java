package telegram.handlers;

import currency.Currency;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.button_utils.KeyboardCreator;
import telegram.button_utils.MessageCreator;
import telegram.customer.User;


import java.util.List;


public class CurrencyAnswer {
    public static SendMessage generateAnswer(User user, String input){
        String usd = user.getCurrencyUSD() == null ? Currency.USD.name() : "<" +Currency.USD.name() +">";
        String eur = user.getCurrencyEUR() == null ? Currency.EUR.name() : "<" +Currency.EUR.name() +">";
        List<InlineKeyboardButton> usdButton = KeyboardCreator.createButton(usd, usd.replaceAll("[<>]", ""));
        List<InlineKeyboardButton> eurButton = KeyboardCreator.createButton(eur, eur.replaceAll("[<>]", ""));
        List<InlineKeyboardButton> backToSettingsButton = KeyboardCreator.createButton("Повернутися до налаштувань", "backToSettings");



        if(user.getCurrencyUSD() != null && input.equals(Currency.USD.name())){
            usdButton = KeyboardCreator.createButton(input, input);
            eurButton = KeyboardCreator.createButton("<" + Currency.EUR + ">" , Currency.EUR.name());
            user.setCurrencyUSD(null);
            user.setCurrencyEUR(Currency.EUR);
        }
        else if (user.getCurrencyUSD() == null && input.equals(Currency.USD.name())) {
            usdButton = KeyboardCreator.createButton("<" + Currency.USD + ">", Currency.USD.name());
            user.setCurrencyUSD(Currency.USD);
        }
        else if (user.getCurrencyEUR() != null && input.equals(Currency.EUR.name())){
            usdButton = KeyboardCreator.createButton("<" + Currency.USD + ">" , Currency.USD.name());
            eurButton = KeyboardCreator.createButton(input, input);
            user.setCurrencyEUR(null);
            user.setCurrencyUSD(Currency.USD);
        }
        else if (user.getCurrencyEUR() == null && input.equals(Currency.EUR.name())) {
            eurButton = KeyboardCreator.createButton("<" + Currency.EUR + ">", Currency.EUR.name());
            user.setCurrencyEUR(Currency.EUR);
        }

        InlineKeyboardMarkup keyboard = KeyboardCreator.createKeyboard(usdButton, eurButton, backToSettingsButton);
        SendMessage currencyMessage = MessageCreator.createMessageWithButton(keyboard, "Оберіть валюту");
        currencyMessage.setChatId(user.getId());
        return currencyMessage;


    }
}
