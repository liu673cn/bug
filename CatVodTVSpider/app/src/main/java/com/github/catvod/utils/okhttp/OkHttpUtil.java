package com.github.catvod.utils.okhttp;

import com.github.catvod.crawler.SpiderDebug;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class OkHttpUtil {

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    private static final int DEFAULT_TIMEOUT = 15;

    private static final Object lockO = new Object();

    private static OkHttpClient defaultClient = null;

    /**
     * 不自动重定向
     */
    private static OkHttpClient noRedirectClient = null;

    public static OkHttpClient defaultClient() {
        synchronized (lockO) {
            if (defaultClient == null) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder()
                        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .sslSocketFactory(new SSLSocketFactoryCompat(SSLSocketFactoryCompat.trustAllCert), SSLSocketFactoryCompat.trustAllCert);
                defaultClient = builder.build();
            }
            return defaultClient;
        }
    }

    public static OkHttpClient noRedirectClient() {
        synchronized (lockO) {
            if (noRedirectClient == null) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder()
                        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .followRedirects(false)
                        .followSslRedirects(false)
                        .retryOnConnectionFailure(true)
                        .sslSocketFactory(new SSLSocketFactoryCompat(SSLSocketFactoryCompat.trustAllCert), SSLSocketFactoryCompat.trustAllCert);
                noRedirectClient = builder.build();
            }
            return noRedirectClient;
        }
    }

    public static String string(OkHttpClient client, String url, String tag, Map<String, String> paramsMap, Map<String, String> headerMap, Map<String, List<String>> respHeaderMap) {
        OKCallBack<String> stringCallback = new OKCallBack<String>() {
            @Override
            public String onParseResponse(Call call, Response response) {
                try {
                    if (respHeaderMap != null) {
                        respHeaderMap.clear();
                        respHeaderMap.putAll(response.headers().toMultimap());
                    }
                    return response.body().string();
                } catch (IOException e) {
                    return "";
                }
            }

            @Override
            public void onFailure(Call call, Exception e) {
                setResult("");
                SpiderDebug.log(e);
            }

            @Override
            public void onResponse(String response) {
            }
        };
        OKRequest req = new OKRequest(METHOD_GET, url, paramsMap, headerMap, stringCallback);
        req.setTag(tag);
        req.execute(client);
        return stringCallback.getResult();
    }

    public static String stringNoRedirect(String url, Map<String, String> headerMap, Map<String, List<String>> respHeaderMap) {
        return string(noRedirectClient(), url, null, null, headerMap, respHeaderMap);
    }

    public static String string(String url, Map<String, String> headerMap, Map<String, List<String>> respHeaderMap) {
        return string(defaultClient(), url, null, null, headerMap, respHeaderMap);
    }

    public static String string(String url, Map<String, String> headerMap) {
        return string(defaultClient(), url, null, null, headerMap, null);
    }

    public static String string(String url, String tag, Map<String, String> headerMap) {
        return string(defaultClient(), url, tag, null, headerMap, null);
    }

    public static void get(OkHttpClient client, String url, OKCallBack callBack) {
        get(client, url, null, null, callBack);
    }

    public static void get(OkHttpClient client, String url, Map<String, String> paramsMap, OKCallBack callBack) {
        get(client, url, paramsMap, null, callBack);
    }

    public static void get(OkHttpClient client, String url, Map<String, String> paramsMap, Map<String, String> headerMap, OKCallBack callBack) {
        new OKRequest(METHOD_GET, url, paramsMap, headerMap, callBack).execute(client);
    }

    public static void post(OkHttpClient client, String url, OKCallBack callBack) {
        post(client, url, null, callBack);
    }

    public static void post(OkHttpClient client, String url, Map<String, String> paramsMap, OKCallBack callBack) {
        post(client, url, paramsMap, null, callBack);
    }

    public static void post(OkHttpClient client, String url, Map<String, String> paramsMap, Map<String, String> headerMap, OKCallBack callBack) {
        new OKRequest(METHOD_POST, url, paramsMap, headerMap, callBack).execute(client);
    }

    public static void post(OkHttpClient client, String url, String tag, Map<String, String> paramsMap, Map<String, String> headerMap, OKCallBack callBack) {
        OKRequest req = new OKRequest(METHOD_POST, url, paramsMap, headerMap, callBack);
        req.setTag(tag);
        req.execute(client);
    }

    public static void postJson(OkHttpClient client, String url, String jsonStr, OKCallBack callBack) {
        postJson(client, url, jsonStr, null, callBack);
    }

    public static void postJson(OkHttpClient client, String url, String jsonStr, Map<String, String> headerMap, OKCallBack callBack) {
        new OKRequest(METHOD_POST, url, jsonStr, headerMap, callBack).execute(client);
    }

    /**
     * 根据Tag取消请求
     */
    public static void cancel(OkHttpClient client, Object tag) {
        if (client == null || tag == null) return;
        for (Call call : client.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public static void cancel(Object tag) {
        cancel(defaultClient(), tag);
    }
    
    public static void cancelAll() {
        cancelAll(defaultClient());
    }

    /**
     * 取消所有请求请求
     */
    public static void cancelAll(OkHttpClient client) {
        if (client == null) return;
        for (Call call : client.dispatcher().queuedCalls()) {
            call.cancel();
        }
        for (Call call : client.dispatcher().runningCalls()) {
            call.cancel();
        }
    }

    /**
     * 获取重定向地址
     *
     * @param headers
     * @return
     */
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
