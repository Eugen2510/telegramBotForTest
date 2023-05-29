package telegram.user_data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import telegram.customer.User;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UsersDataGetter {
    public static  Map<Long, User> getUsers(String filePath){
        StringBuilder dataFromFile = new StringBuilder();
        Map<Long, User> users = new ConcurrentHashMap<>();
        try (FileReader reader = new FileReader(filePath)){
            int readChar;
            while ((readChar = reader.read()) != -1){
                dataFromFile.append((char) readChar);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (dataFromFile.length() == 0){
            return users;
        }

        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(List.class, User.class).getType();
        List<User> usersList = gson.fromJson(dataFromFile.toString(), type);

        for (User user : usersList) {
            users.put(user.getId(), user);
        }

        return users;
    }
}
