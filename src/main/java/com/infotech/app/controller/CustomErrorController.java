package com.infotech.app.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.infotech.app.model.ErrorJson;

@RestController
public class CustomErrorController implements ErrorController 
{

	private static final String PATH = "/error";

	@Value("${debug}")	//value come from application.properties file
	private boolean debug;
	
	@Autowired
	private ErrorAttributes errorAttributes;
	
	@RequestMapping(value = PATH)
	ErrorJson error(HttpServletRequest request, HttpServletResponse response)
	{
		//Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring.
		//here we just define response body
		return new ErrorJson(response.getStatus(), getErrorAttributes(request, debug));
	}
	
	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) 
	{
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
	}

	@Override
	public String getErrorPath() 
	{
		return PATH;
	}
	
	
}
