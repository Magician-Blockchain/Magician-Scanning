package com.blockchain.scanning.commons.util.okhttp;

import com.blockchain.scanning.commons.util.JSONUtil;
import okhttp3.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * okHttp utility class
 */
public class OkHttpUtil {

    /**
     * The status code of a successful request
     */
    private static int success = 200;

    /**
     * The type of request content is json
     */
    public static String contentTypeJson = "application/json;charset=UTF-8";

    /**
     * okHttp client
     */
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager())
            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
            .build();

    /**
     * Initiate json request in post mode
     * @param url
     * @param parameter
     * @return
     */
    public static String postJson(String url, Map<String, Object> parameter) throws Exception {
        String jsonParameter = JSONUtil.toJSONString(parameter);

        MediaType mediaType = MediaType.parse(contentTypeJson);

        RequestBody requestbody = RequestBody.create(jsonParameter, mediaType);
        Request request = new Request.Builder()
                .header("Content-type", contentTypeJson)
                .url(url)
                .post(requestbody)
                .build();

        Call call = okHttpClient.newCall(request);
        Response response = call.execute();

        int code = response.code();
        if(code != success){
            throw new Exception("An exception occurred when initiating a request, code:" + code);
        }
        return response.body().string();
    }
}
