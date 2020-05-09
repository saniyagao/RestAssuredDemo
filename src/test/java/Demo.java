import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * @author : saniyagao
 * create at : 2020/5/7 9:31 下午
 * @description : demo
 **/
public class Demo {
    @Test
    public void testJson(){
        given()
        .when()
            .get("http://127.0.0.1:8000/tester.json")
        .then()
            .log().all()
            .statusCode(200)
            .body("store.book.category",hasItems("reference"))
            .body("store.book[0].author",equalTo("Nigel Rees"))
            .body("store.book[-1].author",equalTo("J. R. R. Tolkien"))
            .body("store.book.findAll {book->book.price>=5 &&book.price<=15}.size()",equalTo(3))
            .body("store.book.find {book-> book.price==8.95f}.price",equalTo(8.95F));
    }

    @Test
    public void testXML(){
        given()
        .when()
                .get("http://127.0.0.1:8000/tester.xml")
        .then()
                .log().all()
                .statusCode(200)
                .body("shopping.category[0].item[0].name",equalTo("Chocolate"))
                .body("shopping.category.findAll {it.@type=='groceries'}.size()", equalTo(1))
                .body("shopping.category.item.findAll {it.price==20}.name", equalTo("Coffee"))
//                .body("shopping.category.item.find {it.name=='Chocolate'}.price", equalTo(10))
                .body("**.find {it.price==20}.name",equalTo("Coffee"));
    }
}
