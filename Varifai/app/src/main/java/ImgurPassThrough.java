import android.graphics.Bitmap;
import android.util.JsonReader;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sasawat on 09/19/2015.
 */
public class ImgurPassThrough {
    private static final String clientid = "89cafc0cb410643"; //Imgur client ID

    private static final String uploadUrl = "https://api.imgur.com/3/image";

    public static String upload(Bitmap image)
    {
        //Turning the image into data for Imgur JSON
        ByteArrayOutputStream river = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, river);
        byte[] rawdata = river.toByteArray();

        URL url;
        try {
            url = new URL(uploadUrl);
        }
        catch(Exception ex)
        {
            return "ERROR";
        }

        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
        }
        catch(Exception ex)
        {
            return "ERROR";
        }

        conn.setRequestProperty("Authorization", "Client-ID " + clientid);

        JSONObject content = new JSONObject();
        try {
            content.put("image", rawdata);
            content.put("type", "file");
            content.put("name", "temp.jpg");
        }
        catch(Exception ex)
        {
            return "ERROR";
        }

        OutputStreamWriter osw;
        try {
            osw = new OutputStreamWriter(conn.getOutputStream());
            osw.write(content.toString());
            osw.flush();
            osw.close();
        }
        catch(Exception ex)
        {
            return "ERROR";
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String responsestr = "";
            String line = "";
            while((line = reader.readLine()) != null)
            {
                responsestr = responsestr + line;
            }
            reader.close();
            JSONObject response = new JSONObject(responsestr);
            return "http://i.imgur.com/" + response.getString("data") + ".jpg";

        }
        catch(Exception ex)
        {
            return "ERROR";
        }


    }
}
