package telegram;


import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.handlers.BankAnswer;
import telegram.handlers.CurrencyAnswer;
import telegram.handlers.NumberOfDecimalPlacesAnswer;
import telegram.handlers.SettingsAnswer;
import telegram.commands.StartCommand;
import telegram.customer.User;
import telegram.customer.UserStateSaver;

public class MyCurrencyTelegramBot extends TelegramLongPollingCommandBot {
    User user = new User();
    MyCurrencyTelegramBot() {
        register(new StartCommand());
    }
    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }

    @Override
    public String getBotToken() {

        return BotConstants.BOT_TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        long userId = update.getCallbackQuery().getFrom().getId();
        String input = update.getCallbackQuery().getData();
        user.setId(userId);

        System.out.println("user.getId() = " + user.getId());
        if (update.hasCallbackQuery()){
            System.out.println(update.getCallbackQuery().getData());
            System.out.println(userId);
            if (input.equals("Settings") || input.equals("backToSettings")){

                sendApiMethodAsync(SettingsAnswer.generateAnswer(userId));
            }

            if (input.equals("bank")){
                sendApiMethodAsync(BankAnswer.generateAnswer(user));
            }

            if (input.equals("NBU") || input.equals("MonoBank") || input.equals("PrivatBank")){
                UserStateSaver.saveBank(user, input);
                sendApiMethodAsync(BankAnswer.generateAnswer(user));
            }

            if (input.equals("currency") || input.equals("USD") || input.equals("EUR")){
                sendApiMethodAsync(CurrencyAnswer.generateAnswer(user, input));
            }

            if (input.equals("numOfDecimal")
                    || input.equals("1")
                    || input.equals("2")
                    ||  input.equals("3")
                    ||  input.equals("4"))
            {
                sendApiMethodAsync(NumberOfDecimalPlacesAnswer.generateAnswer(user, input));
            }

        }


    }
}
