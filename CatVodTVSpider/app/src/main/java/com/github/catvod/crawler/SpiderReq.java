package com.github.catvod.crawler;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.Parser;

public class SpiderReq {

    private static final String defaultTag = "sp_req_default";

    private static OkHttpClient defaultClient = initDefaultClient();

    private static OkHttpClient initDefaultClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public static void cancel(String tag) {
        try {
            RxHttpPlugins.cancelAll(tag);
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
    }

    /**
     * Get
     *
     * @param su
     * @return
     */
    public static SpiderReqResult get(SpiderUrl su) {
        return get(defaultClient, su.url, defaultTag, su.headers);
    }

    /**
     * Get whit custom tag
     *
     * @param su
     * @param tag
     * @return
     */
    public static SpiderReqResult get(SpiderUrl su, String tag) {
        return get(defaultClient, su.url, tag, su.headers);
    }

    /**
     * Get with custom http client
     *
     * @param client
     * @param su
     * @return
     */
    public static SpiderReqResult get(OkHttpClient client, SpiderUrl su) {
        return get(client, su.url, defaultTag, su.headers);
    }

    /**
     * Get with custom http client & tag
     *
     * @param client
     * @param su
     * @param tag
     * @return
     */

    public static SpiderReqResult get(OkHttpClient client, SpiderUrl su, String tag) {
        return get(client, su.url, tag, su.headers);
    }

    /**
     * Get base
     *
     * @param client
     * @param url
     * @param tag
     * @param headers
     * @return
     */
    public static SpiderReqResult get(OkHttpClient client, String url, String tag, HashMap<String, String> headers) {
        try {
            return RxHttp.get(url)
                    .setOkClient(client)
                    .tag(tag)
                    .addAllHeader(headers)
                    .execute(new Parser<SpiderReqResult>() {
                        @Override
                        public SpiderReqResult onParse(@NotNull Response response) throws IOException {
                            String content = "";
                            if (response.body() != null) {
                                content = response.body().string();
                            }
                            Map<String, List<String>> headers = response.headers().toMultimap();
                            return new SpiderReqResult(headers, content);
                        }
                    });
        } catch (Throwable e) {
            SpiderDebug.log(e);
        }
        return SpiderReqResult.empty;
    }

    /**
     * Header
     *
     * @param url
     * @param headers
     * @return
     */
    public static SpiderReqResult header(String url, HashMap<String, String> headers) {
        return header(defaultClient, url, defaultTag, headers);
    }

    /**
     * Header
     *
     * @param client
     * @param url
     * @param tag
     * @param headers
     * @return
     */
    public static SpiderReqResult header(OkHttpClient client, String url, String tag, HashMap<String, String> headers) {
        try {
            return RxHttp.head(url)
                    .setOkClient(client)
                    .tag(tag)
                    .addAllHeader(headers)
                    .execute(new Parser<SpiderReqResult>() {
                        @Override
                        public SpiderReqResult onParse(@NotNull Response response) throws IOException {
                            Map<String, List<String>> headers = response.headers().toMultimap();
                            return new SpiderReqResult(headers, "");
                        }
                    });
        } catch (Throwable e) {
            SpiderDebug.log(e);
        }
        return SpiderReqResult.empty;
    }

    /**
     * Post Body
     *
     * @param url
     * @param body
     * @param headers
     * @return
     */
    public static SpiderReqResult postBody(String url, RequestBody body, HashMap<String, String> headers) {
        return postBody(defaultClient, url, body, defaultTag, headers);
    }

    /**
     * Post Body
     *
     * @param client
     * @param url
     * @param body
     * @param tag
     * @param headers
     * @return
     */
    public static SpiderReqResult postBody(OkHttpClient client, String url, RequestBody body, String tag, HashMap<String, String> headers) {
        try {
            return RxHttp.postBody(url)
                    .setOkClient(client)
                    .setBody(body)
                    .tag(tag)
                    .addAllHeader(headers)
                    .execute(new Parser<SpiderReqResult>() {
                        @Override
                        public SpiderReqResult onParse(@NotNull Response response) throws IOException {
                            String content = "";
                            if (response.body() != null) {
                                content = response.body().string();
                            }
                            Map<String, List<String>> headers = response.headers().toMultimap();
                            return new SpiderReqResult(headers, content);
                        }
                    });
        } catch (Throwable e) {
            SpiderDebug.log(e);
        }
        return SpiderReqResult.empty;
    }

    /**
     * Post Form
     *
     * @param url
     * @param form
     * @param headers
     * @return
     */
    public static SpiderReqResult postForm(String url, HashMap<String, String> form, HashMap<String, String> headers) {
        return postForm(defaultClient, url, form, defaultTag, headers);
    }

    /**
     * Post Form
     *
     * @param client
     * @param url
     * @param form
     * @param tag
     * @param headers
     * @return
     */
    public static SpiderReqResult postForm(OkHttpClient client, String url, HashMap<String, String> form, String tag, HashMap<String, String> headers) {
        try {
            return RxHttp.postForm(url)
                    .setOkClient(client)
                    .addAll(form)
                    .tag(tag)
                    .addAllHeader(headers)
                    .execute(new Parser<SpiderReqResult>() {
                        @Override
                        public SpiderReqResult onParse(@NotNull Response response) throws IOException {
                            String content = "";
                            if (response.body() != null) {
                                content = response.body().string();
                            }
                            Map<String, List<String>> headers = response.headers().toMultimap();
                            return new SpiderReqResult(headers, content);
                        }
                    });
        } catch (Throwable e) {
            SpiderDebug.log(e);
        }
        return SpiderReqResult.empty;
    }

    /**
     * Post Json
     *
     * @param url
     * @param json
     * @param headers
     * @return
     */
    public static SpiderReqResult postJson(String url, HashMap<String, String> json, HashMap<String, String> headers) {
        return postJson(defaultClient, url, json, defaultTag, headers);
    }

    /**
     * Post Json
     *
     * @param client
     * @param url
     * @param json
     * @param tag
     * @param headers
     * @return
     */
    public static SpiderReqResult postJson(OkHttpClient client, String url, HashMap<String, String> json, String tag, HashMap<String, String> headers) {
        try {
            return RxHttp.postJson(url)
                    .setOkClient(client)
                    .addAll(json)
                    .tag(tag)
                    .addAllHeader(headers)
                    .execute(new Parser<SpiderReqResult>() {
                        @Override
                        public SpiderReqResult onParse(@NotNull Response response) throws IOException {
                            String content = "";
                            if (response.body() != null) {
                                content = response.body().string();
                            }
                            Map<String, List<String>> headers = response.headers().toMultimap();
                            return new SpiderReqResult(headers, content);
                        }
                    });
        } catch (Throwable e) {
            SpiderDebug.log(e);
        }
        return SpiderReqResult.empty;
    }
}
