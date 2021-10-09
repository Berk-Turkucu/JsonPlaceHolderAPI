package ApiTests;

import io.restassured.http.ContentType;
import org.testng.annotations.*;
import java.util.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PostPutPatchDeleteTests {

    @BeforeClass
    public void setUpClass() {
        baseURI = "https://jsonplaceholder.typicode.com";
    }

    /*
     * Use post command with using POJO
     * And control status code, title, body and id information
     */
    @Test
    public void postTest(){
        Post post = new Post("Title 1","Body 1");

        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(post)
                .when().post("/posts")
                .then().statusCode(201)
                .body("title",is("Title 1"),
                        "body",is("Body 1"),
                        "id",is(101));
    }

    /*
     * Use put command with using map
     * And control status code, title, body and id information
     */
    @Test
    public void putTest(){
        Map<String, Object> putRequestMap = new HashMap<>();
        putRequestMap.put("title", "Title 3");
        putRequestMap.put("body", "Body 3");

        given().contentType(ContentType.JSON)
                .body(putRequestMap)
                .pathParam("id",1)
                .when().put("/posts/{id}")
                .then().statusCode(200)
                .body("title",is("Title 3"),
                        "body",is("Body 3"),
                        "id",is(1));
    }

    /*
     * Use patch command with using map
     * And control status code, title and id information
     */
    @Test
    public void patchTest(){
        Map<String, Object> patchRequestMap = new HashMap<>();
        patchRequestMap.put("title", "Title 3");

        given().contentType(ContentType.JSON)
                .body(patchRequestMap)
                .pathParam("id",1)
                .when().patch("/posts/{id}")
                .then().statusCode(200)
                .body("title",is("Title 3"),
                        "id",is(1));
    }

    /*
     * Use delete command with pathParam
     * And control status code
     */
    @Test
    public void deleteTest(){
        given().pathParam("id",1)
                .when().delete("/posts/{id}")
                .then().statusCode(200);
    }



}
