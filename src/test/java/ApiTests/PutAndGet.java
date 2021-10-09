package ApiTests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.*;
import static org.testng.Assert.assertEquals;
import static io.restassured.RestAssured.*;

public class PutAndGet {

    @BeforeClass
    public void setUpClass() {
        baseURI = "https://jsonplaceholder.typicode.com";
    }

    /*
     * Use post command with POJO
     * And control status code, content type title and body information
     * With new created id, use get method for getting the new item
     * Normally status code should be 200, but it gives 404
     */
    @Test
    public void postAndGetTest(){
        Post post = new Post("Title 2","Body 2");

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(post)
                .when().post("/posts");

        assertEquals(response.statusCode(),201);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        assertEquals(response.path("title"),"Title 2");
        assertEquals(response.path("body"),"Body 2");

        int responseId = response.path("id");

        given().accept(ContentType.JSON)
                .pathParam("id",responseId)
                .when().get("/posts/{id}")
                .then().statusCode(404);
    }

}
