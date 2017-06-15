package com.demo.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import com.restfb.types.User;

public class Util {
	public final static String CLIENT_ID = "1683138785327065";
	public final static String SECRET_KEY = "ad65949930aec39e2968974dbd0d2c7f";
	public static final String REDIRECT_URI = "http://localhost:8000/Facebook/fbhome";
	private final static String ACCESS_TOKEN = "EAAX6zkloN9kBAI1VqN4I7G2THCrtE4847Er5xckWGdyqGvuWelPX3zZBknPImjG9vd5AQ43RAc8tXZClG7mVqJIDsdpjNpIbDPqnaJ2EfqGFnvHlcZCHkDGrJE5zfYhILqd3bLZBRjb7aYBormZA0MH0tw0mJN5BdTxDjRVZBhJNwxtg1ZCPikCGAnkX90I68kZD";
	private final static String USER_PARAMETERS = "id,first_name,last_name,name,gender,cover,picture";

	public static AccessToken extendToken() {
		return getFBClient(ACCESS_TOKEN).obtainExtendedAccessToken(Util.CLIENT_ID, Util.SECRET_KEY);
	}

	public static FacebookClient getFBClient(String accessToken) {
		return new DefaultFacebookClient(accessToken, Version.LATEST);
	}

	public static User getUser(String accessToken) {
		return getFBClient(accessToken).fetchObject("me", User.class, Parameter.with("fields", USER_PARAMETERS));
	}

	public static void publish(String accessToken, String message) {
		getFBClient(accessToken).publish("me/feed", FacebookType.class, Parameter.with("message", message));
	}

	public static boolean isValidAccessToken(String accessToken) {
		if (accessToken == null) {
			throw new RuntimeException();
		}
		return getFBClient(ACCESS_TOKEN).debugToken(accessToken).isValid();
	}
}
