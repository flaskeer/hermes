package com.asterisk.opensource.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Http Util
 */
@Slf4j
public class HttpUtil {

    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 800;
    /**
     * 每个路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = 400;
	/**
	 * 获取连接的最大等待时间
	 */
	public final static int WAIT_TIMEOUT = 1000;
	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 1000;
	/**
	 * 读取超时时间
	 */
	public final static int READ_TIMEOUT = 1000000;
    private static final byte[] lock = new byte[0];

    private static volatile CloseableHttpClient defaultHttpClient;

	public static CloseableHttpClient getHttpClient() {
        if (defaultHttpClient == null) {
            synchronized (lock) {
                if (defaultHttpClient == null) {
                    RequestConfig requestConfig = RequestConfig.custom()
                            .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                            .setConnectionRequestTimeout(WAIT_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT)
                            .setSocketTimeout(READ_TIMEOUT).build();
                    SSLConnectionSocketFactory ssf = null;
                    try {
                        SSLContext sslContext = SSLContext.getInstance("TLS");
                        sslContext.init(null, new X509TrustManager[] {new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                            }

                            @Override
                            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                            }

                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }
                        }}, null);
                        ssf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
                    } catch (NoSuchAlgorithmException | KeyManagementException e) {

                    }
                    defaultHttpClient = HttpClientBuilder.create()
                            .setMaxConnTotal(MAX_TOTAL_CONNECTIONS)
                            .setMaxConnPerRoute(MAX_ROUTE_CONNECTIONS)
                            .setSSLSocketFactory(ssf)
                            .setDefaultRequestConfig(requestConfig).build();
                }
            }
        }
		return defaultHttpClient;
	}

	public static String postRequest(String uri, Map<String, String> rawParams, Map<String, String> headerParams) {
		HttpPost post = new HttpPost(uri);
		initHeader(post, headerParams);
		List<NameValuePair> params = new ArrayList<>();
		if (rawParams != null) {
			params.addAll(rawParams.keySet().stream().map(key -> new BasicNameValuePair(key, rawParams.get(key))).collect(Collectors.toList()));
		}
        post.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        return getResult(post);
	}


	public static String postRequest(String uri, Map<String, String> rawParams) {
		return postRequest(uri, rawParams, null);
	}

	public static String postRequest(String uri) {
		HttpPost post = new HttpPost(uri);
		return getResult(post);
	}

    public static String postRequest(String uri, String json, Map<String, String> headerParams) {
        HttpPost post = new HttpPost(uri);
        initHeader(post, headerParams);
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        post.setEntity(entity);
        return getResult(post);
    }

    public static String postRequest(String uri, String json) {
        return postRequest(uri, json, null);
    }

	public static String getRequest(String uri, Map<String, String> headerParams) {
		HttpGet get = new HttpGet(uri);
		initHeader(get, headerParams);
		return getResult(get);
	}

	public static String getRequest(String uri) {
		HttpGet get = new HttpGet(uri);
		return getResult(get);
	}

	private static String getResult(HttpUriRequest request) {
		CloseableHttpClient httpClient = getHttpClient();
		try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
			HttpEntity entity = httpResponse.getEntity();
			return EntityUtils.toString(entity);
		} catch (IOException e) {
			log.error("请求失败,error:{}",e);
		}
		return null;
	}

	/**
	 * HTTP Get 获取内容
	 * @param url  请求的url地址 ?之前的地址
	 * @param params	请求的参数
	 * @param charset	编码格式
	 * @return	页面内容
	 */
	public static String getRequest(String url,Map<String,String> params,String charset){
		CloseableHttpClient httpClient = getHttpClient();
		if(StringUtils.isBlank(url)){
			return null;
		}
		try {
			url = getParameters(url, params, charset);
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode >= 400) {
				httpGet.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null){
				result = EntityUtils.toString(entity, Charset.defaultCharset());
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			log.error("请求异常,error :{}",e);
		}
		return null;
	}

	public static String getParameters(String url, Map<String, String> params, String charset) throws IOException {
		if(params != null && !params.isEmpty()){
            List<NameValuePair> pairs = new ArrayList<>(params.size());
            for(Map.Entry<String,String> entry : params.entrySet()){
                String value = entry.getValue();
                if(value != null){
                    pairs.add(new BasicNameValuePair(entry.getKey(),value));
                }
            }
            url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
        }
		return url;
	}

	/**
	 * 初始化消息头
	 *
	 * @param request GET/POST...
	 */
	public static void initHeader(HttpUriRequest request, Map<String, String> headerParams) {
		if (headerParams != null) {
			for (String key: headerParams.keySet()) {
				request.addHeader(key, headerParams.get(key));
			}
		}
	}

	public static String getRequest(String uri,String contentEnCoding) {
		HttpGet get = new HttpGet(uri);
		return getResult(get,contentEnCoding);
	}
	private static String getResult(HttpUriRequest request,String contentEncoding) {
		CloseableHttpClient httpClient = getHttpClient();
		try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
			HttpEntity entity = httpResponse.getEntity();
			return EntityUtils.toString(entity,contentEncoding);
		} catch (IOException e) {
			log.error("请求失败,error:{}",e);
		}
		return null;
	}
}
