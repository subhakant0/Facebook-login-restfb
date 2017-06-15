package com.demo.facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FBConnection {
	private static final String SCOPE = "email,user_likes,publish_actions";

	public String getFBAuthUrl() {
		String fbLoginUrl = "";
		try {
			fbLoginUrl = "https://www.facebook.com/dialog/oauth?client_id=" + Util.CLIENT_ID + "&redirect_uri="
					+ URLEncoder.encode(Util.REDIRECT_URI, "UTF-8") + "&scope=" + SCOPE;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		/**
		 * URLEncoder.encode used to encode a URL Java recomend to use UTF-8
		 * technique to encode a URL
		 */
		return fbLoginUrl;
	}

	public URL getFBGraphUrl(String code) {
		URL fbGraphUrl = null;
		try {
			fbGraphUrl = new URL("https://graph.facebook.com/v2.9/oauth/access_token?client_id=" + Util.CLIENT_ID
					+ "&redirect_uri=" + URLEncoder.encode(Util.REDIRECT_URI, "UTF-8") + "&client_secret="
					+ Util.SECRET_KEY + "&code=" + code);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbGraphUrl;
	}

	public String getAccessToken(String code) {
		URLConnection fbConnection = null;
		try {
			/**
			 * The openConnection() method of URL class returns the object of
			 * URLConnection class.
			 */
			URL url = getFBGraphUrl(code);
			fbConnection = url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * The Java URLConnection class represents a communication link between
		 * the URL and the application. This class can be used to read and write
		 * data to the specified resource referred by the URL.
		 */
		StringBuffer urlDataStream = null;
		try {
			/**
			 * The URLConnection class provides many methods, we can display all
			 * the data of a webpage by using the getInputStream() method.The
			 * getInputStream() method returns all the data of the specified URL
			 * in the stream that can be read and displayed.
			 */
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fbConnection.getInputStream()));
			String inputLine;
			urlDataStream = new StringBuffer();
			while ((inputLine = bufferedReader.readLine()) != null)
				urlDataStream.append(inputLine + "\n");
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to connect with Facebook " + e);
		}

		String json = urlDataStream.toString();
		Gson gson = new Gson();
		Type stringStringMap = new TypeToken<Map<String, Object>>() {
		}.getType();
		Map<String, String> map = gson.fromJson(json, stringStringMap);
		String accessToken = map.get("access_token");
		Util.isValidAccessToken(accessToken);
		return accessToken;
	}
}
