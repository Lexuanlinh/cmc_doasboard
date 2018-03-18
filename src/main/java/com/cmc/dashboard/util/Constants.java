package com.cmc.dashboard.util;

public class Constants {

    public static final String SESSION_USER = "user";
    public static final String PAGE_ERROR_500 = "500";
    public static final String PAGE_ERROR_403 = "403";
    
    
    public static class HTTP_STATUS {
    	//SUCCESS
    	public static final String OK = "200";
    	public static final String CREATED = "201";
    	//ERROR
    	public static final String BAD_REQUEST = "400";
    	public static final String UNAUTHORIZED = "401";
    	public static final String FORBIDDEN = "403";
    	public static final String NOT_FOUND = "200";
    	public static final String INTERNAL_SERVER_ERROR = "500";
    }
    
    public static class HTTP_STATUS_MSG {
    	public static final String ERROR_COMMON = "Có lỗi xẩy ra";
    	public static final String ERROR_NOT_FOUND = "Tài nguyên không có sẵn";
    }
}
