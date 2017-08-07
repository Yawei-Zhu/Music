package com.wind.music.util;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Urls {
    public static final String URL = "http://tingapi.ting.baidu.com/v1/restserver/ting";
    public static final String FORMAT = "format";
    public static final String FORMAT_JSON = "json";
    public static final String FORMAT_XML = "xml";

    public static final String METHOD = "method";
    public static final String METHOD_BILL = "baidu.ting.billboard.billList";

    public static final String TYPE = "type";
    public static final String SIZE = "size";
    public static final int SIZE_DEFAULT = 12;
    public static final String OFFSET = "offset";


    public static String getBillUrl(int type, int offset) {
        StringBuilder url = new StringBuilder();
        url.append(URL)
                .append("?").append(FORMAT).append("=").append(FORMAT_JSON)
                .append("&").append(METHOD).append("=").append(METHOD_BILL)
                .append("&").append(TYPE).append("=").append(type)
                .append("&").append(SIZE).append("=").append(SIZE_DEFAULT)
                .append("&").append(OFFSET).append("=").append(offset);

        return url.toString();
    }




}
