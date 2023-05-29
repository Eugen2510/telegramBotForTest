package telegram.user_data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import telegram.customer.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class UsersDataSaver {
    public static void saveUsers(String filePath, Map<Long, User> users){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter(filePath);
            Collection<User> values = users.values();
            String json = gson.toJson(values);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
