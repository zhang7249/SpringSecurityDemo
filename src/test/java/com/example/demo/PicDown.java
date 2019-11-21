package com.example.demo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.print.DocFlavor.INPUT_STREAM;
import sun.security.jca.GetInstance;

public class PicDown {


  public static void main(String[] args) throws IOException {

    String path="/Users/chuangzhang/Desktop/1574302767117cgcg.png";

    InputStream inputStream=getImageStream("https://www.jiketravel.com/static_pictures/warnings/2019-11-21/1574302767117.png");

    //File file=new File("");

    getFile(inputStream,path);


  }

  public static void getFile(InputStream is,String fileName) throws IOException{
    BufferedInputStream in=null;
    BufferedOutputStream out=null;
    in=new BufferedInputStream(is);
    out=new BufferedOutputStream(new FileOutputStream(new File(fileName)));
    int len=-1;
    byte[] b=new byte[1024];
    while((len=in.read(b))!=-1){
      out.write(b,0,len);
    }
    in.close();
    out.close();
  }

  //根据url获取图片流方法
  public static InputStream getImageStream(String url) throws IOException {
    System.out.println("url:" + url);
    InputStream inputStream = null;
    try {
      TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager()
      {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
          return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }
      } };
      System.out.println("getImageStream1开始------------");
      SSLContext sc = SSLContext.getInstance("TLS");
      sc.init(null, trustAllCerts, new SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setReadTimeout(50000);
      connection.setConnectTimeout(50000);
      connection.setRequestMethod("GET");
      System.out.println(
          "生成图片流开始------------" + connection.getResponseCode() + "-----" + HttpURLConnection.HTTP_OK);
      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        inputStream = connection.getInputStream();
        System.out.println("生成图片流结束------------");
        return inputStream;
      }
      else {
        System.out.println("生成图片流失败------------");
        return null;
      }
    }
    catch (IOException e) {
      System.out.println("获取图片出现异常，图片路径为：" + url);
      e.printStackTrace();
    }
    catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (KeyManagementException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

}
