import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

/**
 * @author : saniyagao
 * create at : 2020/5/7 8:58 下午
 * @description : xueqiu
 **/
public class Xueqiu {
    @Test
    public void testSearch(){
        //信任https的任何证书
        useRelaxedHTTPSValidation();
//   设置全局变量，每个接口都走代理
//        RestAssured.proxy("127.0.0.1",8888);

        int stockId=given()
                .header("Cookie","aliyungf_tc=AQAAAMTGQB+2TwoAvSRacbypwhIjuKIQ; acw_tc=2760822515888563146868227e670b73604f3593bb9b39833c0961a728ef60; xq_a_token=48575b79f8efa6d34166cc7bdc5abb09fd83ce63; xqat=48575b79f8efa6d34166cc7bdc5abb09fd83ce63; xq_r_token=7dcc6339975b01fbc2c14240ce55a3a20bdb7873; xq_id_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1aWQiOi0xLCJpc3MiOiJ1YyIsImV4cCI6MTU4OTY4MjczMCwiY3RtIjoxNTg4ODU2MjkwMDgwLCJjaWQiOiJkOWQwbjRBWnVwIn0.L9te4fjOVEJS9H8S5vHTGGRxQQCDym3yD9Se7_ZlmwOykjd4hXFbKl_y3gTnThxtxOh68-MKwkrsGLxaDrNFLN0MoQ6Xc_sExUw0P0FOZNgW2xX0gdjaI1MsSflX823nOsq6kMhkL_YB-oi2KuVvtZNsL0UzLffXpAb8MT2nAT95qEV2b9zMihN0S34032HcemwsDxSYgsvH5qiXgMOpH_TFVHtdaFgGJMGHK_dlbSBUJBGuL5T3VPBGVUtWTJP33x9CEgBoL29IqIphQ1wgtI29yTFervE246WVbi_qXfG8mhY51ZJRfjicFGDPU1ennOF020PzlIWs9u4QSiTGhQ; u=901588856314691; Hm_lvt_1db88642e346389874251b5a1eded6e3=1588856316; device_id=24700f9f1986800ab4fcc880530dd0ed; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1588856319")
                .queryParam("code","SOGO")
        .when()
                .get("https://xueqiu.com/stock/search.json")
        .then()
                .log().all()
                .statusCode(200)
                .body("stocks.name",hasItem("搜狗"))
                .body("stocks.code",hasItem("SOGO"))
                .body("stocks.find {it.code=='SOGO'}.name",equalTo("搜狗"))
                .extract().path("stocks[0].stock_id");

        System.out.println(stockId);
    }

    @Test
    public void testLogin(){
        useRelaxedHTTPSValidation();
        Response response=given()
                .header("Cookie","aliyungf_tc=AQAAAMTGQB+2TwoAvSRacbypwhIjuKIQ; acw_tc=2760822515888563146868227e670b73604f3593bb9b39833c0961a728ef60; xq_a_token=48575b79f8efa6d34166cc7bdc5abb09fd83ce63; xqat=48575b79f8efa6d34166cc7bdc5abb09fd83ce63; xq_r_token=7dcc6339975b01fbc2c14240ce55a3a20bdb7873; xq_id_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1aWQiOi0xLCJpc3MiOiJ1YyIsImV4cCI6MTU4OTY4MjczMCwiY3RtIjoxNTg4ODU2MjkwMDgwLCJjaWQiOiJkOWQwbjRBWnVwIn0.L9te4fjOVEJS9H8S5vHTGGRxQQCDym3yD9Se7_ZlmwOykjd4hXFbKl_y3gTnThxtxOh68-MKwkrsGLxaDrNFLN0MoQ6Xc_sExUw0P0FOZNgW2xX0gdjaI1MsSflX823nOsq6kMhkL_YB-oi2KuVvtZNsL0UzLffXpAb8MT2nAT95qEV2b9zMihN0S34032HcemwsDxSYgsvH5qiXgMOpH_TFVHtdaFgGJMGHK_dlbSBUJBGuL5T3VPBGVUtWTJP33x9CEgBoL29IqIphQ1wgtI29yTFervE246WVbi_qXfG8mhY51ZJRfjicFGDPU1ennOF020PzlIWs9u4QSiTGhQ; u=901588856314691; Hm_lvt_1db88642e346389874251b5a1eded6e3=1588856316; device_id=24700f9f1986800ab4fcc880530dd0ed; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1588856319")
                .queryParam("code","SOGO")
                .when()
                .get("https://xueqiu.com/stock/search.json")
                .then()
                .log().all()
                .statusCode(200)
                .body("stocks.name",hasItem("搜狗"))
                .body("stocks.code",hasItem("SOGO"))
                .body("stocks.find {it.code=='SOGO'}.name",equalTo("搜狗"))
                .extract().response();

        String s = response.path("stocks[0].code").toString();
        assertThat(s,equalTo("SOGO"));

    }
}
