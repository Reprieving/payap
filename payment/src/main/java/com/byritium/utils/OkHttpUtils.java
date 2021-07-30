package com.byritium.utils;

import com.byritium.constance.InterfaceProvider;
import com.byritium.dto.SSLContextDto;
import com.byritium.dto.SSLRequest;
import com.byritium.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class OkHttpUtils {
    private static final Logger log = LoggerFactory.getLogger(OkHttpUtils.class);

    private static final ConcurrentHashMap<InterfaceProvider, OkHttpClient> sslClientMap = new ConcurrentHashMap<>();

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build();

    private static OkHttpClient getSSLClient(InterfaceProvider interfaceProvider, String path, String password) throws GeneralSecurityException, FileNotFoundException {
        OkHttpClient okHttpClient = sslClientMap.get(interfaceProvider);
        if (okHttpClient == null) {
            SSLContextDto sslContextDto = trustManagerForCertificates(path, password); //SSLContext.getInstance("TLS");
            SSLSocketFactory sslSocketFactory = sslContextDto.getSslContext().getSocketFactory();
            X509TrustManager trustManager = sslContextDto.getX509TrustManager();

            OkHttpClient.Builder wechatSSLClientBuilder = new OkHttpClient.Builder();
            wechatSSLClientBuilder
                    .connectTimeout(1, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .followRedirects(true)
                    .hostnameVerifier((s, sslSession) -> true)
                    .sslSocketFactory(sslSocketFactory, trustManager);

            okHttpClient = wechatSSLClientBuilder.build();
            sslClientMap.put(interfaceProvider, okHttpClient);
        }

        return okHttpClient;
    }

    private static SSLContextDto trustManagerForCertificates(String path, String password)
            throws GeneralSecurityException, FileNotFoundException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        InputStream certStream = new FileInputStream(new File(path));
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(certStream);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] passwordChars = password.toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(passwordChars);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        //  keyStore.load(in,CLIENT_KET_PASSWORD.toCharArray());
        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, passwordChars);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagers, null);

        return new SSLContextDto(sslContext, (X509TrustManager) trustManagers[0]);
    }

    private static KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * get请求
     * 对于小文档，响应体上的string()方法非常方便和高效。
     * 但是，如果响应主体很大(大于1 MB)，则应避免string()，
     * 因为它会将整个文档加载到内存中。在这种情况下，将主体处理为流。
     *
     * @param url
     * @return
     */
    public static String httpGet(String url) {
        if (url == null || "".equals(url)) {
            log.error("url为null!");
            return "";
        }

        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("http GET 请求成功; [url={}]", url);
                return Objects.requireNonNull(response.body()).string();
            } else {
                log.warn("Http GET 请求失败; [errorCode = {} , url={}]", response.code(), url);
            }
        } catch (IOException e) {
            throw new RuntimeException("同步http GET 请求失败,url:" + url, e);
        }
        return null;
    }

    public static String httpGet(String url, Map<String, String> headers, Map<String, Object> params) {
        Request.Builder requestBuilder = new Request.Builder();
        if (headers != null && headers.size() > 0) {
            headers.forEach(requestBuilder::addHeader);
        }

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();

        if (params != null && params.size() > 0) {
            params.forEach((name, value) -> urlBuilder.addQueryParameter(name, String.valueOf(value)));
        }
        requestBuilder.url(urlBuilder.build());


        Request request = requestBuilder.build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("http GET 请求成功; [url={}]", url);
                return Objects.requireNonNull(response.body()).string();
            } else {
                log.warn("Http GET 请求失败; [errorxxCode = {} , url={}]", response.code(), url);
            }
        } catch (IOException e) {
            throw new RuntimeException("同步http GET 请求失败,url:" + url, e);
        }
        return null;
    }


    /**
     * 同步 POST JSON
     *
     * @param url
     * @param headers
     * @param map
     * @return
     */
    public static String httpPostJson(String url, Map<String, String> headers, Map<String, Object> map) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null && headers.size() > 0) {
            headers.forEach(requestBuilder::addHeader);
        }

        Gson gson = new Gson();
        String json = gson.toJson(map);

        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = requestBuilder
                .post(body)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("http Post 请求成功; [url={}, requestContent={}]", url, json);
                return Objects.requireNonNull(response.body()).string();
            } else {
                log.warn("Http POST 请求失败; [ errorCode = {}, url={}, param={}]", response.code(), url, json);
            }
        } catch (IOException e) {
            throw new RuntimeException("同步http请求失败,url:" + url, e);
        }
        return null;
    }

    /**
     * 同步 POST JSON
     *
     * @param url
     * @param headers
     * @param json
     * @return
     */
    public static String httpPostJson(String url, Map<String, String> headers, String json) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null && headers.size() > 0) {
            headers.forEach(requestBuilder::addHeader);
        }

        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = requestBuilder
                .post(body)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("http Post 请求成功; [url={}, requestContent={}]", url, json);
                return Objects.requireNonNull(response.body()).string();
            } else {
                log.warn("Http POST 请求失败; [ errorCode = {}, url={}, param={}]", response.code(), url, json);
                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            throw new RuntimeException("同步http请求失败,url:" + url, e);
        }
    }

    /**
     * 同步 POST JSON
     *
     * @param url
     * @param headers
     * @param json
     * @return
     */
    public static String httpPostJson(String url, Map<String, String> headers, String json, SSLRequest sslRequest) {
        InterfaceProvider interfaceProvider = sslRequest.getInterfaceProvider();
        String path = sslRequest.getCertPath();
        String password = sslRequest.getCertPassword();

        Assert.notNull(interfaceProvider, "interfaceProvider can't be null");
        Assert.state(StringUtils.hasText(path), "cert path can't be null");
        Assert.state(StringUtils.hasText(password), "cert password can't be null");

        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null && headers.size() > 0) {
            headers.forEach(requestBuilder::addHeader);
        }

        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = requestBuilder
                .post(body)
                .build();
        try {
            OkHttpClient sslClient = getSSLClient(interfaceProvider, path, password);

            Response response = sslClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("http Post 请求成功; [url={}, requestContent={}]", url, json);
                return Objects.requireNonNull(response.body()).string();
            } else {
                log.warn("Http POST 请求失败; [ errorCode = {}, url={}, param={}]", response.code(), url, json);
                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("同步http请求失败,url:" + url, e);
        }
    }

    /**
     * 同步 POST XML
     *
     * @param url
     * @param headers
     * @param xml
     * @return
     */
    public static String httpPostXml(String url, Map<String, String> headers, String xml) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null && headers.size() > 0) {
            headers.forEach(requestBuilder::addHeader);
        }

        RequestBody body = FormBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);

        Request request = requestBuilder
                .post(body)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("http Post 请求成功; [url={}, requestContent={}]", url, xml);
                return Objects.requireNonNull(response.body()).string();
            } else {
                log.warn("Http POST 请求失败; [ errorCode = {}, url={}, param={}]", response.code(), url, xml);
            }
        } catch (IOException e) {
            throw new RuntimeException("同步http请求失败,url:" + url, e);
        }
        return null;
    }

    /**
     * 同步 POST XML
     *
     * @param url
     * @param headers
     * @param xml
     * @return
     */
    public static String httpPostXml(String url, Map<String, String> headers, String xml, SSLRequest sslRequest) {
        InterfaceProvider interfaceProvider = sslRequest.getInterfaceProvider();
        String path = sslRequest.getCertPath();
        String password = sslRequest.getCertPassword();

        Assert.notNull(interfaceProvider, "interfaceProvider can't be null");
        Assert.state(StringUtils.hasText(path), "cert path can't be null");
        Assert.state(StringUtils.hasText(password), "cert password can't be null");

        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null && headers.size() > 0) {
            headers.forEach(requestBuilder::addHeader);
        }

        RequestBody body = FormBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);

        Request request = requestBuilder
                .post(body)
                .build();
        try {
            OkHttpClient sslClient = getSSLClient(interfaceProvider, path, password);
            Response response = sslClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("http Post 请求成功; [url={}, requestContent={}]", url, xml);
                return Objects.requireNonNull(response.body()).string();
            } else {
                log.warn("Http POST 请求失败; [ errorCode = {}, url={}, param={}]", response.code(), url, xml);
            }
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("同步http请求失败,url:" + url, e);
        }
        return null;
    }

    /**
     * 同步 POST FORM
     *
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static String httpPostForm(String url, Map<String, String> headers, Map<String, Object> params) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null && headers.size() > 0) {
            headers.forEach(requestBuilder::addHeader);
        }

        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            params.forEach((name, value) -> builder.add(name, String.valueOf(value)));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            log.error("参数序列化异常");
            throw new BusinessException("参数序列化异常");
        }

        RequestBody body = builder.build();

        Request request = requestBuilder
                .post(body)
                .build();

        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                String rspBody = Objects.requireNonNull(response.body()).string();
                log.info("httpPostForm; [postUrl={}, requestContent={}, responseCode={}, responseContent={}]", url, json, response.code(), rspBody);
                return rspBody;
            } else {
                log.warn("Http Post Form请求失败,[url={}, param={}]", url, json);
            }
        } catch (IOException e) {
            log.error("Http Post Form请求失败,[url={}, param={}]", url, json, e);
            throw new BusinessException("Http Post Form请求失败,url:" + url);
        }
        return null;
    }

    /**
     * 同步 DELETE FORM
     *
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static String httpDeleteForm(String url, Map<String, String> headers, Map<String, Object> params) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null && headers.size() > 0) {
            headers.forEach(requestBuilder::addHeader);
        }

        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            params.forEach((name, value) -> builder.add(name, String.valueOf(value)));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            log.error("参数序列化异常");
            throw new BusinessException("参数序列化异常");
        }

        RequestBody body = builder.build();

        Request request = requestBuilder
                .delete(body)
                .build();

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("httpPostDelete; [postUrl={}, requestContent={}, responseCode={}]", url, json, response.code());
                return Objects.requireNonNull(response.body()).string();
            } else {
                log.warn("Http Delete Form请求失败,[url={}, param={}]", url, json);
            }
        } catch (IOException e) {
            log.error("Http Delete Form请求失败,[url={}, param={}]", url, json, e);
            throw new BusinessException("Http Delete Form请求失败,url:" + url);
        }
        return null;
    }
}
