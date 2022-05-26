package com.itheima.epTest;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class WechatTest {

//    @Test
//    void tokenTest(){
//        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wwc2af277d7bf4cc3f&corpsecret=dTH1syXQkRu44FhUPi5VUk7_Oo7iX90SATDMmhV0Gik";
////        String jsonString = HttpUtils.getInstance().endHttpGet(url);
//        String jsonString = HttpUtils.getInstance().sendHttpGet(url);
//
//        Gson gson = new Gson();
//        Map map = gson.fromJson(jsonString,Map.class);
//        String accessToken = (String) map.get("access_token");
//    }

@Test
void pushTask() {
    List<String> list = new ArrayList();
    list.add("1");
    list.add("2");
    list.add("3");
    list.add("4");
    list.add("5");
    list.forEach(item -> {
        System.out.println(item);
        if (item.equals("3")) {
            return;
        }
        System.out.println("11");
    });
};
}
