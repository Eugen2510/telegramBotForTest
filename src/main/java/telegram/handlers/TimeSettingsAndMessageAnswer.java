package telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.customer.User;

public class TimeSettingsAndMessageAnswer {
    public static SendMessage handleMessage(Update update, long chatId, User user){
        SendMessage message = AlertTimesSettings.settingsAlertTimeAlertTimesMessage( chatId);
        String text = "Ви ввели не коректні данні, для збереження коректних налаштувань, скористайтесь клавіатурою нижче:";
        String inputMessage = update.getMessage().getText();

        int isInt;
        try {
            isInt = Integer.parseInt(inputMessage);
            if (isInt < 9 || isInt > 18){
                text = "Нажаль в обраний вами час банки не працюють. Оберіть час з клавіатури нижче:";
                message.setText(text);
                return message;
            }
        }catch (NumberFormatException e){
            message.setText(text);
            return message;
        }

        user.setNotificationTime(inputMessage);
        text = "Час сповіщень встановлено на: " + user.getNotificationTime() + ":00";
        message = new SendMessage();
        message.setText(text);
        message.setChatId(chatId);
        return message;
    }
}
