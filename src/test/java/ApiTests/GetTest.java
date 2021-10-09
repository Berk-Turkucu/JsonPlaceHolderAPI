package ApiTests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.testng.Assert.*;
import org.testng.annotations.*;
import java.util.*;
import static io.restassured.RestAssured.*;

public class GetTest {

    @BeforeClass
    public void setUpClass() {
        baseURI = "https://jsonplaceholder.typicode.com";
    }

    /*
     * Use get command
     * And control status code and the content type
     */
    @Test
    public void test1() {
        Response response = given().accept(ContentType.JSON)
                .when().get("/posts");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
    }

    /*
     * Use get command with queryParam
     * And control status code, the content type and user count
     */
    @Test
    public void test2() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("userId", 1)
                .when().get("/posts?userId=");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        List<Map<String,Object>> userList = response.body().as(List.class);
        for (Map<String, Object> stringObjectMap : userList) {
            assertEquals(stringObjectMap.get("userId"), 1);
        }

        assertEquals(userList.size(),10);
    }

    /*
     * Use get command
     * Make sure response does not have duplication
     */
    @Test
    public void test3(){
        Response response = given().accept(ContentType.JSON)
                .when().get("/posts");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        List<Map<String,Object>> userList = response.body().as(List.class);
        Set<Object> idCheck =new HashSet<>();
        for (Map<String, Object> stringObjectMap : userList) {
            idCheck.add(stringObjectMap.get("id"));
        }
        assertEquals(userList.size(),idCheck.size());
    }

    /*
     * Use get command
     * And control status code, the content type, name and mail information
     */
    @Test
    public void test4(){
        Response response = given().accept(ContentType.JSON)
                .when().get("/posts/1/comments");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        List<Map<String,Object>> userList = response.body().as(List.class);
        assertEquals(userList.get(1).get("name"),"quo vero reiciendis velit similique earum");
        assertEquals(userList.get(4).get("email"),"Hayden@althea.biz");
    }

    /*
     * Use get command with wrong input
     * And control status code
     */
    @Test
    public void test5(){
        given().accept(ContentType.JSON)
                .when().get("/post")
                .then().statusCode(404);
    }





}
