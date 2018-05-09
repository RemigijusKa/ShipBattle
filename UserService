package zaidimas.Services;

import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import zaidimas.Data.Game;
import zaidimas.Data.User;


public class UserService extends WebServiceClient {
    public static final String METHOD_CREATE_USER = "create_user?";



    public User createUser(String name, String email) {
        String url = SERVER + METHOD_CREATE_USER + "name=" + name + "&email=" + email;

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = client.execute(request);
            String body = convertStreamToString(response.getEntity().getContent());
            if (response.getStatusLine().getStatusCode() == 200) {
                return convertResponse(body);
            }
            System.out.println("Klaida is serverio:" + body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User convertResponse(String str) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(str);
        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            String id = (String) jsonObject.get("id");
            String name = (String) jsonObject.get("name");
            String email = (String) jsonObject.get("email");
            return new User(id, name, email);
        }
        return null;
    }


}
