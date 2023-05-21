package com.example.weatherapp.util;

import com.example.weatherapp.dto.ipAddress.IpAddress;
import com.example.weatherapp.external.IpAddressRestService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class HttpUtilsTest {

    @Mock
    IpAddressRestService ipAddressRestService;

    @Test
    void testGetRequestOrExternalIp() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Mockito.doAnswer(a -> "19.199.123.156").when(request).getHeader("X-Forwarded-For");
        String result = new HttpUtils(ipAddressRestService).getRequestOrExternalIp(request);
        Assertions.assertEquals("19.199.123.156", result);
    }

    @Test
    void testGetRequestOrExternalIpWithLocalIpAddress() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Mockito.doAnswer(a -> new IpAddress("19.199.123.156")).when(ipAddressRestService).getIpAddress();
        String result = new HttpUtils(ipAddressRestService).getRequestOrExternalIp(request);
        Assertions.assertEquals("19.199.123.156", result);
    }
}
