import android.graphics.Bitmap;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
            throw new RuntimeException("Imgur final URL is wrong in ImgurPassThrough");
        }

        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
        }
        catch(Exception ex)
        {
            throw new RuntimeException("HttpURLConnection Error in ImgurPassThrough");
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
            throw new RuntimeException("Unable to assemble JSON content in ImgurPassThrough");
        }


        return "";
    }
}
