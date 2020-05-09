import io.restassured.RestAssured;
import io.restassured.builder.ResponseBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Base64;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author : saniyagao
 * create at : 2020/5/7 5:09 下午
 * @description : baidu
 **/
public class Baidu {

    public static void setup(){
        useRelaxedHTTPSValidation();
        //设置全局变量，接口都走代理
        RestAssured.proxy("127.0.0.1",8888);
        //设置全局变量，发送请求，都加上头信息
        RestAssured.filters((req,res,ctx)->{
            if (req.getURI().contains("xueqiu.com")){
                req.header("id","tester");
                req.cookie("");
            }
            Response resOrigin = ctx.next(req, res);
            return resOrigin;
        });
    }


    @Test
    public void testGetHtml(){
        given()
                .log().all()
        .when()
                .get("http://www.baidu.com")
        .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void testMp3(){
        useRelaxedHTTPSValidation();
        given()
                .log().all()
                .proxy("127.0.0.1",8888)
                .queryParam("wd","mp3")
                .header("User-Agent","")
                .cookie("","")
//                form表单
//                .formParam()
        .when()
                .get("https://www.baidu.com/s")
        .then()
                .statusCode(200);
//                .body()
    }

    @Test
    public void testPostJson(){

        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("a",1);
        map.put("b","tester");
        map.put("array",new String[]{"1","2","3"});
        given()
                .contentType(ContentType.JSON)
                .body(map)
        .when()
                .post("http://www.baidu.com")
        .then()
                .log().all()
                .statusCode(200);
    }


    @Test
    public void testChangeRes(){
//        如果响应是经过Base64加密的response，可以用filters先解密，再返回
        given()
                .filter((req,res,ctx)->{{
            Response resOrigin = ctx.next(req, res);
            //先new一个ResponseBuilder对象，重新设置body内容
            ResponseBuilder responseBuilder=new ResponseBuilder().clone(resOrigin);
            String decodedContent=new String(Base64.getDecoder().decode(resOrigin.body().asString().trim()));
            responseBuilder.setBody(decodedContent);
            //再把ResponseBuilder构建成Response对象返回
            Response resNew = responseBuilder.build();
            return resNew;
        }
        })
                .log().all()
        .when()
                .get("http://127.0.0.1:8000/base64.json").prettyPeek()
        .then()
                .log().all()
                .statusCode(200)
                .body("data.items[0].quote.name",equalTo("搜狗"));
    }


    @Test
    public void test(){

        given()
                .log().all().when().get("http://127.0.0.1:8000/base64.json").then().log().all().statusCode(200);
    }
}
