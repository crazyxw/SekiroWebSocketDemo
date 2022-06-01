package com.h0p1.api.utils;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class StringUtil {

    public static Map<String, String> urlParamsToMap(String url) {
        String query = url.split("\\?")[1];
        return urlQueryToMap(query);
    }

    public static Map<String, String> urlQueryToMap(String query) {
        Map<String, String>map = new HashMap<String, String>();
        if (query.indexOf("&") > -1 && query.indexOf("=") > -1) {
            String[] arrTemp = query.split("&");
            for (String str : arrTemp) {
                String[] qs = str.split("=");
                map.put(qs[0], qs[1]);
            }
        }
        return map;
    }

}
