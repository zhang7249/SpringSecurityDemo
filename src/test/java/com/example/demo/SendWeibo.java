package com.example.demo;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import java.io.IOException;
import java.util.Date;

public class SendWeibo {

  public static void main(String[] args) throws IOException, InterruptedException {


    //新浪微博登录页面
    String baseUrl = "https://passport.weibo.cn/signin/login?entry=mweibo&res=wel&wm=3349&r=http%3A%2F%2Fm.weibo.cn%2F";

    WebClient webClient = new WebClient(BrowserVersion.CHROME);

    webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
    HtmlPage page = webClient.getPage(baseUrl);


    //等待页面加载
    Thread.sleep(2000);

    //获取输入帐号的控件
    HtmlInput usr = (HtmlInput) page.getElementById("loginName");

    usr.setValueAttribute("zhang724914749@gmail.com");

    //获取输入密码的控件
    HtmlInput pwd = (HtmlInput) page.getElementById("loginPassword");

    pwd.setValueAttribute("Zhang112409");

    //点击登录
    DomElement button = page.getElementById("loginAction");


    page =(HtmlPage) button.click();


    //等待页面加载
    Thread.sleep(1000);


    //获取到“写微博”这个按钮，因为这个按钮没有name和id,获取所有<a>标签
    DomNodeList<DomElement> button2 = page.getElementsByTagName("a");


    //跳转到发送微博页面
    page =(HtmlPage)button2.get(4).click();

    //等待页面加载
    Thread.sleep(1000);



    //获取发送控件 标签为<a>中的2个
    DomNodeList<DomElement> button3 = page.getElementsByTagName("a");
    //获取文本宇
    HtmlTextArea content =(HtmlTextArea) page.getElementById("txt-publisher");

    DomElement fasong = button3.get(1);

    content.focus();

    Date date = new Date();

    //填写你要发送的内容
    content.setText("使用JAVA发送微博！！！！\n"+date);



    //改变发送按钮的属性，不能无法发送
    fasong.setAttribute("class", "fr txt-link");

    //发送！！！
    page = (HtmlPage)fasong.click();


    Thread.sleep(5000);

    System.out.println(page.asText());



  }

}
