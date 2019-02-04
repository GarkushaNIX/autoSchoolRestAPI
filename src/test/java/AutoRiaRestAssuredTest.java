import io.qameta.allure.Feature;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;


@Feature("AutoRia Rest-Assured test")
@RunWith(Parameterized.class)
public class AutoRiaRestAssuredTest {

    private String testURL;

    public AutoRiaRestAssuredTest(String testURL) {
        this.testURL = testURL;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<String> testData() {

        ArrayList<String> list = new ArrayList<>();
        String line;

        String csvFile = "src\\main\\resources\\autoria_API_links.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) list.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Test
    public void testRestAPI() {

        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(testURL)
                .addHeader("User-Agent", "Jmeter")
                .log(LogDetail.ALL)
                .build();

        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectHeader("Content-Type", containsString("text/html"))
                .expectHeader("Content-Encoding", "gzip")
                .build();

        given().spec(requestSpec)
                .when().get()
                .then().spec(responseSpec);

    }

}
