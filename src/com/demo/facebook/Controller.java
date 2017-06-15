package com.demo.facebook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restfb.types.User;

public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String code = "";

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		code = req.getParameter("code");
		if (code == null || code.equals("")) {
			throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
		}
		FBConnection fbConnection = new FBConnection();
		String accessToken = fbConnection.getAccessToken(code);

		User user = Util.getUser(accessToken);

		ServletOutputStream out = res.getOutputStream();
		out.println("<h1>Facebook Login using Java</h1>");
		out.println("<h2>Application Main Menu</h2>");
		out.println("<div>Welcome " + user.getName());
		out.println("<div>Your Email: " + user.getEmail());
		out.println("<div>Bio : " + user.getAbout());
		out.println("<div>Gender : " + user.getGender());
		out.println("<div>Age Range : " + user.getAgeRange());
		out.println("<div>RelationShip status : " + user.getRelationshipStatus());
		out.println("<div>Location : " + user.getLocation());
		out.println("<div>Languages : " + user.getLanguages());
		out.println("<div>PublicKey : " + user.getPublicKey());
		out.println("<div>Birthday : " + user.getBirthday());
		out.println("<div>All Details : " + user.toString());
		Util.publish(accessToken, "This is my Second Post");
	}

}
