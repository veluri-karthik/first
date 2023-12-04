package com.example.gateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.gateway.config.AppServiceRepository;
import com.example.gateway.config.AppServices;
import com.example.gateway.config.JWTTokenProvider;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTTokenFilter extends OncePerRequestFilter {

	@Autowired
	JWTTokenProvider jwtTokenProvider;

	@Autowired
	AppServiceRepository appServiceRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			System.out.println("this is filter class ");
			String requestUri = request.getServletPath();
			String methodType = request.getMethod();
			if (isAllowURLWithOutAuthentication(requestUri, methodType)) {
				filterChain.doFilter(request, response);

			} else {
				String jwtToken = request.getHeader("Authorization");
				String userNameFromheader = request.getHeader("userNmae");

				if (userNameFromheader == null || userNameFromheader.isEmpty()) {
					response = errorResponse(response, "user name not present", HttpStatus.NOT_ACCEPTABLE.value());
					return;
				}
				if (jwtToken == null || jwtToken.isEmpty()) {
					response = errorResponse(response, "token not present", HttpStatus.NOT_ACCEPTABLE.value());
					return;
				}
				jwtToken = jwtToken.replace("Bearer ", "");
				String jwtIdToken = jwtTokenProvider.getIdFromJwtToken(jwtToken);
				Claims claims = jwtTokenProvider.getAllClaimsFromToken(jwtToken);
				String userNameFromClaim = (String) claims.get("USER_NAme");
				if (userNameFromheader.equalsIgnoreCase(userNameFromClaim)) {
					if (requestUri.contains("user/menu") && (jwtIdToken != null && !jwtIdToken.isEmpty())
							&& jwtTokenProvider.validateJwtToken(jwtToken, jwtIdToken)) {
						request.setAttribute("userNmae", "jwtIdToken");
						filterChain.doFilter(request, response);

					} else if ((jwtIdToken != null && !jwtIdToken.isEmpty())
							&& jwtTokenProvider.validateJwtToken(jwtToken, jwtIdToken)) {
						if (!checkpermissionForRequestedURI(jwtIdToken, requestUri, methodType)) {
							System.err.println(checkpermissionForRequestedURI(jwtIdToken, requestUri, methodType));
							response = errorResponse(response, "UnAuthorized user to access this uri",
									HttpStatus.NOT_ACCEPTABLE.value());
							return;
						} else {
							request.setAttribute("userNmae", "jwtIdToken");
							filterChain.doFilter(request, response);

						}
					}
				} else {
					response = errorResponse(response, "Invalid token for user", HttpStatus.NOT_ACCEPTABLE.value());
					return;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			String message = "";
			String cause = e.toString();
			if (cause.contains("ExpiredJwtException")) {
				message = "Access Token Expired";
				response = errorResponse(response, message, HttpStatus.NOT_ACCEPTABLE.value());

			} else if (cause.contains("SignatureException")) {
				message = "Invalid Access Token";
				response = errorResponse(response, message, HttpStatus.NOT_ACCEPTABLE.value());

			} else {
				message = "please login";
				response = errorResponse(response, message, HttpStatus.NOT_ACCEPTABLE.value());

			}
		}

	}

	private HttpServletResponse errorResponse(HttpServletResponse response, String string, int value) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean isAllowURLWithOutAuthentication(String requestUri, String methodType) {
		// TODO Auto-generated method stub

		if (requestUri.contains("/login") 
				|| requestUri.contains("/logout") && methodType.equals("GET")) {
			return true;
		} else {
			return false;
		}
	}

//	public boolean compare

	public boolean checkpermissionForRequestedURI(String userNmae, String requestedURI, String methodType) {
		List<AppServices> appservices = appServiceRepository.findAll();// find by username
		if (appservices != null && !appservices.isEmpty()) {
			for (AppServices appServices2 : appservices) {
				String endPointURL = appServices2.getEndPointURL();
				String endPointMethod = appServices2.getEndPointMethod();
				if (null != appservices && null != endPointMethod && null != endPointURL) {
					if (requestedURI.equals(endPointURL) && methodType.equals(endPointMethod)) {
						return true;
					} else if (methodType.equalsIgnoreCase(endPointMethod)
							&& compareRequestedURtWithAccessableURL(requestedURI, appServices2.getEndPointURL())) {

					}

				}
			}
		}
		return false;
	}

	public boolean compareRequestedURtWithAccessableURL(String requestedURL, String accessableURL) {

		String maintURL = "";

		if (accessableURL.contains("*")) {
			String[] requestedURLArray = requestedURL.split("\\/");
			String[] accessableURLSArray = accessableURL.split("\\/");

			if (requestedURLArray.length >= accessableURLSArray.length) {

				List<Integer> indexes = new ArrayList<>();

				int index = 0;

//	here we are taking the path variable positions.

				for (String url : accessableURLSArray) {

					if (url.equals("*")) {
						indexes.add(index);
					}
					index++;
				}

//	here we are replacing the with values in order to check both are watching or not

				for (Integer index2 : indexes) {
					accessableURLSArray[index2] = requestedURLArray[index2];
				}

				index = 0;

//	here we are re forming the url from an array

				for (String finalUrl : accessableURLSArray) {

					if (!finalUrl.trim().equals("")) {
						maintURL += "/" + finalUrl;
					} else {
						maintURL += finalUrl;
					}

					index++;

				}
				if (requestedURL.equals(maintURL)) {
					return true;
				} else {
					return false;
				}
			}
		} else {

		}
		return false;
	}
}
