package telegram.schedule;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.MyCurrencyTelegramBot;
import telegram.customer.User;
import telegram.handlers.GetInfoAnswer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;


public class ScheduledMessagesSender {
    public static void runSending(Map<Long, User> users, MyCurrencyTelegramBot bot){
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime ldt1  = ldt.plusHours(1).minusMinutes(ldt.getMinute()).minusSeconds(ldt.getSecond());
        long sleep = Duration.between(ldt, ldt1).getSeconds()*1000;
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sleep = 60*60*1000;
        while (true){
            int hour = LocalDateTime.now().getHour();
            for (User user : users.values()){
                String time = user.getNotificationTime();
                if(time == null){
                    continue;
                }
                if (Integer.parseInt(time) == hour){
                    SendMessage message = GetInfoAnswer.generateAnswer(user);
                    try {
                        bot.execute(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
