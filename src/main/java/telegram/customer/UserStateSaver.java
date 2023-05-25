package telegram.customer;

import currency.Currency;

public class UserStateSaver {
    public static void saveBank(User user, String bank){
        user.setBank(bank);
    }

}
