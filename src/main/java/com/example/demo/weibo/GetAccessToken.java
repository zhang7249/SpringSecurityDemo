package com.example.demo.weibo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.Timeline;
import javax.sound.midi.Soundbank;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.PartBase;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity.HeadersBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestTemplate;

public class GetAccessToken {


  public static  String status = "多图上传测试4    http://www.ityouknow.com";



  public static void main(String[] args) throws Exception {

    String accessToken=getAccessToken();

    //sendWeibo(status,accessToken);

    String[] filePath={"/Users/chuangzhang/Desktop/WX20191023-t1@2x.png",
                       "/Users/chuangzhang/Desktop/WX20191023-t2@2x.png",
                       "/Users/chuangzhang/Desktop/WX20191023-t3@2x.png",
                       "/Users/chuangzhang/Desktop/WX20191023-t4@2x.png"
                      };
    sendWeibo(status,filePath,accessToken);

  }

  public static String getAccessToken() throws IOException {

    HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    httpRequestFactory.setConnectionRequestTimeout(2000);
    httpRequestFactory.setConnectTimeout(10000);
    httpRequestFactory.setReadTimeout(7200000);
    HttpClient httpClient = HttpClientBuilder.create().disableCookieManagement().disableRedirectHandling().build();
    httpRequestFactory.setHttpClient(httpClient);
    RestTemplate restTemplate = new RestTemplate(httpRequestFactory);

    HttpHeaders httpHeaders=new HttpHeaders();
    httpHeaders.add("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
    httpHeaders.add("Accept-Encoding","gzip, deflate, br");
    httpHeaders.add("Accept-Language","zh-CN,zh;q=0.9");
    httpHeaders.add("Connection","keep-alive");
    httpHeaders.add("Cookie",AppInfo.cookie);
    httpHeaders.add("Host","api.weibo.com");
    httpHeaders.add("Sec-Fetch-Mode","navigate");
    httpHeaders.add("Sec-Fetch-Site","none");
    httpHeaders.add("Upgrade-Insecure-Requests","1");
    httpHeaders.add("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");
    httpHeaders.add("http.protocol.handle-redirects","0");

    HttpEntity<String> requestEntity = new HttpEntity<String>("",httpHeaders);
    ResponseEntity<String> responseEntity = restTemplate.exchange(AppInfo.authorizeURL, HttpMethod.GET,
        requestEntity,String.class);

    String local=responseEntity.getHeaders().get("Location").get(0);
    String code=local.substring(local.indexOf("=")+1);;

    System.out.println("code="+code);

    PostMethod postMethod = new PostMethod(AppInfo.accessTokenURL);
    postMethod.addParameter("client_id",AppInfo.key);
    postMethod.addParameter("client_secret",AppInfo.secret);
    postMethod.addParameter("grant_type","authorization_code");
    postMethod.addParameter("code",code);
    postMethod.addParameter("redirect_uri",AppInfo.redirect_URI);

    postMethod.getParams().setContentCharset("UTF-8");

    postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
        new DefaultHttpMethodRetryHandler(3, false));

    MultiThreadedHttpConnectionManager connectionManager;

    connectionManager = new MultiThreadedHttpConnectionManager();
    HttpConnectionManagerParams params = connectionManager.getParams();
    params.setDefaultMaxConnectionsPerHost(150);
    params.setConnectionTimeout(30000);
    params.setSoTimeout(30000);

    HttpClientParams clientParams = new HttpClientParams();
    // 忽略cookie 避免 Cookie rejected 警告
    clientParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);

    org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient(clientParams,connectionManager);

    postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
        new DefaultHttpMethodRetryHandler(3, false));
    client.executeMethod(postMethod);
    Header[] resHeader = postMethod.getResponseHeaders();
    int responseCode = postMethod.getStatusCode();

    String body=postMethod.getResponseBodyAsString();

    System.out.println("-------------------------------------------");
    System.out.println("resHeader="+resHeader.toString());
    System.out.println("responseCode="+responseCode);
    System.out.println("body="+body);

    JSONObject jo=JSONObject.parseObject(body);
    String accessToken=jo.getString("access_token");
    System.out.println("-------------------------------------------");
    System.out.println("-------------------------------------------");
    System.out.println("-------------------------------------------");
    System.out.println("accessToken="+accessToken);

    return accessToken;

  }



  public static void sendWeibo(String status,String accessToken) throws Exception {

    PostMethod postMethod = new PostMethod(AppInfo.sendUrl);
    postMethod.addParameter("status",status);
    postMethod.getParams().setContentCharset("UTF-8");
    httpRequest(postMethod,accessToken);

  }


  public static void sendWeibo(String status,String[] files,String accessToken) throws Exception {

    PostMethod postMethod = new PostMethod(AppInfo.sendUrl);
    Part[] parts = null;
    if (files == null) {
      parts = new Part[1];
    } else {
      parts = new Part[files.length + 1];
    }
    parts[0] = new StringPart("status",
        java.net.URLEncoder.encode(status, "utf-8"));
    int i=1;
    for(String filePath:files){
      byte[] content = readFileImage(filePath);
      System.out.println("content length:" + content.length);
      ImageItem item = new ImageItem("pic"+(i==2?"":i), content);
      parts[i++] = new ByteArrayPart(item.getContent(),
          item.getName(), item.getContentType());
    }
    postMethod.setRequestEntity(new MultipartRequestEntity(parts,
        postMethod.getParams()));

    httpRequest(postMethod,accessToken);

  }


  public static void httpRequest(org.apache.commons.httpclient.HttpMethod postMethod,String accessToken)
      throws IOException {

    org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
    InetAddress ipaddr = InetAddress.getLocalHost();
    List<Header> headers = new ArrayList<Header>();
    headers.add(new Header("Authorization", "OAuth2 " + accessToken));
    headers.add(new Header("API-RemoteIP", ipaddr.getHostAddress()));
    client.getHostConfiguration().getParams()
        .setParameter("http.default-headers", headers);

    postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
        new DefaultHttpMethodRetryHandler(3, false));
    client.executeMethod(postMethod);

    String body=postMethod.getResponseBodyAsString();

    System.out.println("-------------------------------------------");
    System.out.println("-------------------------------------------");
    System.out.println("-------------------------------------------");
    System.out.println();
    System.out.println("send result="+body);
    System.out.println();
    System.out.println("-------------------------------------------");
    System.out.println("-------------------------------------------");
    System.out.println("-------------------------------------------");

  }



  public static byte[] readFileImage(String filename) throws IOException {
    BufferedInputStream bufferedInputStream = new BufferedInputStream(
        new FileInputStream(filename));
    int len = bufferedInputStream.available();
    byte[] bytes = new byte[len];
    int r = bufferedInputStream.read(bytes);
    if (len != r) {
      bytes = null;
      throw new IOException("读取文件不正确");
    }
    bufferedInputStream.close();
    return bytes;
  }

  private static class ByteArrayPart extends PartBase {
    private byte[] mData;
    private String mName;

    public ByteArrayPart(byte[] data, String name, String type)
        throws IOException {
      super(name, type, "UTF-8", "binary");
      mName = name;
      mData = data;
    }

    protected void sendData(OutputStream out) throws IOException {
      out.write(mData);
    }

    protected long lengthOfData() throws IOException {
      return mData.length;
    }

    protected void sendDispositionHeader(OutputStream out)
        throws IOException {
      super.sendDispositionHeader(out);
      StringBuilder buf = new StringBuilder();
      buf.append("; filename=\"").append(mName).append("\"");
      out.write(buf.toString().getBytes());
    }
  }

}
