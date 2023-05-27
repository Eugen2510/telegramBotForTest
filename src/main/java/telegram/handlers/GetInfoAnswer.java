package telegram.handlers;

import currency.Currency;
import currency.CurrencyService;
import currency.mono.MonoBankCurrencyService;
import currency.nbu.NBUCurrencyService;
import currency.privat.PrivatBankCurrencyService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import telegram.customer.User;

public class GetInfoAnswer {
    public static SendMessage generateAnswer(User user){

        long id = user.getId();
        String bank = user.getBank();
        Currency currencyUSD = user.getCurrencyUSD();
        Currency currencyEUR = user.getCurrencyEUR();
        int numOfDecimal = user.getNumOfDecimal();
        String notificationTime = user.getNotificationTime();

        SendMessage message = new SendMessage();
        message.setChatId(id);

        CurrencyService service = bank.equals("NBU")
                ? new NBUCurrencyService()
                : bank.equals("PrivatBank")
                ? new PrivatBankCurrencyService()
                : new MonoBankCurrencyService();

        String forOneCurrency = "";
        if (currencyEUR == null || currencyUSD == null){
            Currency notNullCurrency = currencyEUR == null
                    ? currencyUSD
                    : currencyEUR;
            double rateBuy = service.getRateBuy(notNullCurrency);
            double rateSell = service.getRateSell(notNullCurrency);
            forOneCurrency = bank + ":\nКурс продажу "
                    + notNullCurrency.name()
                    + "/UAH => "
                    + Math.round(rateBuy * Math.pow(10, numOfDecimal - 1))/Math.pow(10, numOfDecimal - 1)
                    + "\nКурс купівлі "
                    + notNullCurrency.name()
                    + "/UAH => "
                    + Math.round(rateSell * Math.pow(10, numOfDecimal - 1))/Math.pow(10, numOfDecimal - 1);
            message.setText(forOneCurrency);
            return message;
        }
        double rateBuyUsd = service.getRateBuy(currencyUSD);
        double rateSellUsd = service.getRateSell(currencyUSD);

        double rateBuyEur = service.getRateBuy(currencyEUR);
        double rateSellEur = service.getRateSell(currencyEUR);
        String forTwoCurrency =bank + ":\nКурс продажу "
                + currencyUSD.name()
                + "/UAH => "
                + Math.round(rateBuyUsd * Math.pow(10, numOfDecimal - 1))/Math.pow(10, numOfDecimal - 1)
                + "\nКурс купівлі "
                + currencyUSD.name()
                + "/UAH => "
                + Math.round(rateSellUsd * Math.pow(10, numOfDecimal - 1))/Math.pow(10, numOfDecimal - 1)
                +"\n\nКурс продажу "
                + currencyEUR.name()
                + "/UAH => "
                + Math.round(rateBuyEur * Math.pow(10, numOfDecimal - 1))/Math.pow(10, numOfDecimal - 1)
                + "\nКурс купівлі "
                + currencyEUR.name()
                + "/UAH => "
                + Math.round(rateSellEur * Math.pow(10, numOfDecimal - 1))/Math.pow(10, numOfDecimal - 1)
                ;
        message.setText(forTwoCurrency);
        return message;
    }
}
