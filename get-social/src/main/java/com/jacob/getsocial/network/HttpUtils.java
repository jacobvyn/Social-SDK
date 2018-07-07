package com.jacob.getsocial.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.webkit.WebSettings;

import com.jacob.getsocial.common.IOUtils;
import com.jacob.getsocial.common.Logging;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpUtils {
    private static final String LOG_TAG = HttpUtils.class.getSimpleName();

    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;
    private static final String HEADER_USER_AGENT = "User-Agent";
    private static String sUserAgent;


    private HttpUtils() {
    }

    @NonNull
    public static HttpRawResponse doRequest(String url) {
        Logging.out(LOG_TAG, "Making request by: " + url);
        HttpRawResponse response = new HttpRawResponse();
        HttpURLConnection connection = null;
        try {
            connection = createConnection(url);
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                byte[] bytes = IOUtils.toByteArray(inputStream);
                response.setBody(bytes);
            }
            response.setCode(responseCode);
            response.setMessage(connection.getResponseMessage());
            Logging.out(LOG_TAG, "responseCode " + responseCode);

        } catch (MalformedURLException e) {
            Logging.out(LOG_TAG, e.getMessage());
            response.setMessage(e.getMessage());
        } catch (ProtocolException e) {
            Logging.out(LOG_TAG, e.getMessage());
            response.setMessage(e.getMessage());
        } catch (IOException e) {
            Logging.out(LOG_TAG, e.getMessage());
            String reason;
            if (e.getMessage().contains("Unable to resolve host")) {
                reason = "No Internet connection";
            } else {
                reason = "Network operation timeout.";
            }
            response.setMessage(reason);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }

    private static HttpURLConnection createConnection(String urlStr) throws IOException {
        URL url = new URL(urlStr);

        String client_id = Base64.encodeToString("So6a_PyqTklqMw".getBytes(), Base64.NO_WRAP);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setRequestProperty(HEADER_USER_AGENT, getUserAgent());
//        connection.setRequestProperty("Authorization", "Basic -nUCj9w8Nb8N-89Q7Fr0SNvuwh8I");
        connection.setRequestProperty("Authorization", "Basic " + client_id);
        connection.setRequestProperty("grant_type", "installed_client");
        connection.setRequestProperty("device_id", "4d7674f3-46bf-4352-9784-91e87c74d106");

        return connection;
    }

    private static String getUserAgent() {
        return sUserAgent;
    }

    public static void setUserAgent(Context context) {
        if (sUserAgent == null) {
            sUserAgent = WebSettings.getDefaultUserAgent(context);
        }
    }
}