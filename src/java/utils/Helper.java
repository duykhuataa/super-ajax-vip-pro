package utils;

import constant.Constant;
import dto.UserGoogleDTO;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.ClassDAO;
import dao.CourseDAO;
import java.io.InputStream;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import model.Class;
import model.User;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

public class Helper {

    public static String getToken(final String code) throws ClientProtocolException, IOException {
        // call api to get token
        String response = Request.Post(Constant.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", Constant.GOOGLE_CLIENT_ID)
                        .add("client_secret", Constant.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Constant.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", Constant.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static UserGoogleDTO getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = Constant.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();

        UserGoogleDTO googlePojo = new Gson().fromJson(response, UserGoogleDTO.class);

        return googlePojo;
    }

    public static String getRandomNumberString() {//oldpassword generate by system 
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public static Properties getPropertiesByFileName(String fileName) {
        Properties properties = new Properties();
        try ( InputStream inputStream = Helper.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println(e);
        }
        return properties;
    }

    public static String generatePassword() {
        SecureRandom random = new SecureRandom();
        int length = Integer.valueOf(getPropertiesByFileName("constant/const.properties")
                .getProperty("password.generaterandom.length"));
        StringBuilder captcha = new StringBuilder(length);
        String characters = getPropertiesByFileName("constant/const.properties").getProperty("charater.captcha");
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            captcha.append(randomChar);
        }
        return captcha.toString();
    }

    public static String getRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int charsLength = chars.length();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(charsLength)));
        }
        return sb.toString();
    }

    public static String getUsernameFromEmail(String email) {
        return email.split("@")[0];
    }

    public static ArrayList<Integer> getTotalElementOfPage(String object, String keyValue, String userId) {
        int numOfElementAtPage = Integer.parseInt(Helper.getPropertiesByFileName("constant/const.properties").getProperty("number" + object + "OfPage"));
        int totalPage = new ClassDAO().getTotalOfPage(numOfElementAtPage, keyValue, object, userId);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= totalPage; i++) {
            list.add(i);
        }
        return list;
    }

    public static HashMap<ArrayList<Object>, Integer> getListAndIndex(String index, int listPageSize, String object, String keyValue, String userId) {
        HashMap<ArrayList<Object>, Integer> hashmap = new HashMap<>();
        ArrayList<Object> objectList = new ArrayList<>();
        int indexMap = 1;
        int numElementOfPage = Integer.parseInt(Helper.getPropertiesByFileName("constant/const.properties").getProperty("number" + object + "OfPage"));
        if (index == null || listPageSize == 1) {
            objectList.addAll(new CourseDAO().getAllObjectByPage(1, numElementOfPage, keyValue, object, userId));
        } else {
            indexMap = Integer.parseInt(index);
            if (indexMap > listPageSize) {
                indexMap = listPageSize;
            }
            objectList.addAll(new CourseDAO().getAllObjectByPage(indexMap, numElementOfPage, keyValue, object, userId));
        }
        hashmap.put(objectList, indexMap);
        return hashmap;
    }

    public static String getKeyWordSearch(String object) {
        return (object.equals("User") ? "Username like ? or Email like ? or FullName like ?"
                : ((object.equals("Class") ? "ClassName like ? or ClassCode like ? or CourseCode like ?"
                : "CourseCode like ? or CourseName like ?")));
    }

    public static Timestamp getTimestampFromString(String date) {
        try {
            return new Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
                    .parse(date).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Timestamp getCurrentTimestamp() {
        try {
            return new Timestamp(new Date().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
