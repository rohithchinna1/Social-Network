package helpers;

import javax.servlet.http.HttpServletRequest;

public class Helper {
	public static String getURL(HttpServletRequest request){
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
		return base;
	}
}
