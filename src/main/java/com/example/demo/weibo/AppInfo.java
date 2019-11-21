package com.example.demo.weibo;

import java.util.HashMap;
import java.util.Map;

public class AppInfo {

//  App Key：1646008028
//  App Secret：88c2de9993556e938d5451139bbf3376

  public static String key = "2700906959";

  public static String secret = "b24fe3e3990fa8988b5dd0876c19bc53";

  public static String redirect_URI = "https://api.weibo.com/oauth2/default.html";

  public static String authorizeURL =
      "https://api.weibo.com/oauth2/authorize?client_id=" + key + "&redirect_uri=" + redirect_URI
          + "&response_type=code";

  public static String accessTokenURL = "https://api.weibo.com/oauth2/access_token";

  public static String sendUrl = "https://api.weibo.com/2/statuses/share.json";


  public static String cookie = "SINAGLOBAL=8263818130557.015.1569371974472; login_sid_t=ff7965c6dc74205872ed156631debbe0; cross_origin_proto=SSL; _s_tentry=www.google.com.hk; Apache=3066406618895.019.1571187253115; ULV=1571187253121:4:2:1:3066406618895.019.1571187253115:1570675066258; appkey=; un=zhang724914749@gmail.com; wvr=6; JSESSIONID=0DAB6E01F0DA2508745D65E4D9434E47; SCF=AuZIDlnCavdAaIUU0K2SvE5n_rFF5e4SOl00-zLEf6yHe-ElBW_hC9Y2xVkeUVSJ7jIZIxkw0m0cx7x-Qj4h8kA.; SUB=_2A25wq4D-DeRhGeFP7lcY9S3NyDiIHXVTwPU2rDV8PUJbktB-LWXmkW9NQQ54TRtr6ZUlApK2AGtSyr3hzQPcD0RG; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5VdRTCOGB2Z6SUXVkphQBz5JpX5K2hUgL.FoMpSK-4SKepe0B2dJLoIpqLxK.L1--L12zLxK.LB-BL1Kx-Kf5p; SUHB=0S7SQ21BxzL7NG";


}