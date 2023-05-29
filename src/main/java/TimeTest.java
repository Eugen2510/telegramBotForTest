import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class TimeTest {
    public static void main(String[] args) {
        LocalTime time = LocalTime.now();
        System.out.println("time.getHour() = " + time.getHour());
        ScheduledExecutorService scheduler  =  Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // Здесь можно вызвать метод, который отправляет сообщение из вашего бота
                // Например:
                // MyTelegramBot.sendMessage("YOUR_CHAT_ID", "Ваше сообщение");
            }
        }, 0, 1, TimeUnit.DAYS);

    }
}
