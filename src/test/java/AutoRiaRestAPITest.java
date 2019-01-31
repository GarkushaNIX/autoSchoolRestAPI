import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


@Feature("Amazon test")
@RunWith(Parameterized.class)
public class AutoRiaRestAPITest {

    private String testURL;

    public AutoRiaRestAPITest(String testURL) {
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
        Response response = given().header("User-Agent", "Jmeter").when().get(testURL);
        String contentType = response.header("Content-Type");

        assertEquals(200, response.statusCode());
        assertEquals("gzip", response.header("Content-Encoding"));
        assertThat("Unexpected Content-Type header. Expected text/html but was " + contentType,
                contentType.contains("text/html"));

        System.out.println(get(testURL).getHeaders());
    }


}
