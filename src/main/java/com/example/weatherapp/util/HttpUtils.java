package com.example.weatherapp.util;

import com.example.weatherapp.external.IpAddressRestService;
import jakarta.servlet.http.HttpServletRequest;

public class HttpUtils {

    private final IpAddressRestService ipAddressRestService;
    private static final String[] IP_HEADERS = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    public HttpUtils(IpAddressRestService ipAddressRestService) {
        this.ipAddressRestService = ipAddressRestService;
    }

    public String getRequestOrExternalIp(HttpServletRequest request) {
        for (String header : IP_HEADERS) {
            String value = request.getHeader(header);
            if (value == null || value.isEmpty()) {
                continue;
            }
            String[] parts = value.split("\\s*,\\s*");
            return parts[0];
        }
        return ipAddressRestService.getIpAddress().getIp();
    }
}
