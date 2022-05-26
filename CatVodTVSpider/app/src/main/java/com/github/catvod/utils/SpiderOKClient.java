package com.github.catvod.utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class SpiderOKClient {
    private static OkHttpClient noRedirectClient = null;

    public static OkHttpClient noRedirectClient() {
        if (noRedirectClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .followRedirects(false)
                    .followSslRedirects(false)
                    .retryOnConnectionFailure(true)
                    .sslSocketFactory(new SSLSocketFactoryCompat(SSLSocketFactoryCompat.trustAllCert), SSLSocketFactoryCompat.trustAllCert);
            noRedirectClient = builder.build();
        }
        return noRedirectClient;
    }

    public static String getRedirectLocation(Map<String, List<String>> headers) {
        if (headers == null)
            return null;
        if (headers.containsKey("location"))
            return headers.get("location").get(0);
        if (headers.containsKey("Location"))
            return headers.get("Location").get(0);
        return null;
    }
}
