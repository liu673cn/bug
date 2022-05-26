package com.github.catvod.parser;

import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.utils.Misc;
import com.github.catvod.utils.okhttp.OkHttpUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 并发解析，直到获得第一个结果
 * <p>
 * 默认解析超时时间为15秒，如果需要请自定义SpiderReq的HttpClient
 * <p>
 * Author: CatVod
 */
public class JsonParallel {
    private static final String ParseOKTag = "p_json_parse";

    public static JSONObject parse(LinkedHashMap<String, String> jx, String url) {
        try {
            if (jx.size() > 0) {
                ExecutorService executorService = Executors.newFixedThreadPool(3);
                CompletionService<JSONObject> completionService = new ExecutorCompletionService<JSONObject>(executorService);
                List<Future<JSONObject>> futures = new ArrayList<>();
                Set<String> jxNames = jx.keySet();
                for (String jxName : jxNames) {
                    String parseUrl = jx.get(jxName);
                    futures.add(completionService.submit(new Callable<JSONObject>() {
                        @Override
                        public JSONObject call() throws Exception {
                            try {
                                HashMap<String, String> reqHeaders = JsonBasic.getReqHeader(parseUrl);
                                String realUrl = reqHeaders.get("url");
                                reqHeaders.remove("url");
                                SpiderDebug.log(realUrl + url);
                                String json = OkHttpUtil.string(realUrl + url, ParseOKTag, reqHeaders);
                                JSONObject taskResult = Misc.jsonParse(url, json);
                                taskResult.put("jxFrom", jxName);
                                SpiderDebug.log(taskResult.toString());
                                return taskResult;
                            } catch (Throwable th) {
                                SpiderDebug.log(th);
                                return null;
                            }
                        }
                    }));
                }
                JSONObject pTaskResult = null;
                for (int i = 0; i < futures.size(); ++i) {
                    Future<JSONObject> completed = completionService.take();
                    try {
                        pTaskResult = completed.get();
                        if (pTaskResult != null) {
                            OkHttpUtil.cancel(ParseOKTag);
                            for (int j = 0; j < futures.size(); j++) {
                                try {
                                    futures.get(j).cancel(true);
                                } catch (Throwable th) {
                                    SpiderDebug.log(th);
                                }
                            }
                            futures.clear();
                            break;
                        }
                    } catch (Throwable th) {
                        SpiderDebug.log(th);
                    }
                }
                try {
                    executorService.shutdownNow();
                } catch (Throwable th) {
                    SpiderDebug.log(th);
                }
                if (pTaskResult != null)
                    return pTaskResult;
            }
        } catch (Throwable th) {
            SpiderDebug.log(th);
        }
        return new JSONObject();
    }
}
