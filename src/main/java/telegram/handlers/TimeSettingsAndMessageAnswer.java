package telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.commands.StartCommand;
import telegram.customer.User;

public class TimeSettingsAndMessageAnswer {
    public static SendMessage handleMessage(Update update, long chatId, User user){
        SendMessage message = AlertTimesSettings.settingsAlertTimeAlertTimesMessage( chatId);
        String text = "Ви ввели не коректні данні, для збереження коректних налаштувань, скористайтесь клавіатурою нижче:";
        String inputMessage = update.getMessage().getText();

        int isInt;
        try {
            isInt = Integer.parseInt(inputMessage);

        }catch (NumberFormatException e){
            if(inputMessage.equals("Припинити сопіщення.")){
                text = "Розсилку сповіщень за розкладом вимкнено." +
                        "Ви зможете знову налаштувати автоматичну розсилку в будь-який час.";
                user.setNotificationTime(null);
            }
            message.setText(text);
            return message;
        }

        if (isInt < 9 || isInt > 18){
            text = "Нажаль в обраний вами час банки не працюють. Оберіть час з клавіатури нижче:";
            message.setText(text);
        }else {
        user.setNotificationTime(inputMessage);
        text = "Час сповіщень встановлено на: " + user.getNotificationTime() + ":00";
        message = new SendMessage();
        message.setText(text);
        message.setChatId(chatId);
        }
        return message;
    }
}
