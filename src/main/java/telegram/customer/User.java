package telegram.customer;

import currency.Currency;
import lombok.Data;

@Data
public class User {
    private long id;
    private String bank;
    private Currency currencyUSD;
    private Currency currencyEUR;
    private int numOfDecimal;

    public User (){
        bank = "MonoBank";
        currencyUSD = Currency.USD;
        numOfDecimal = 2;
    }
}
