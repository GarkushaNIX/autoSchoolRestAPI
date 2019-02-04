import io.qameta.allure.Feature;
import org.jsoup.Connection.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import org.jsoup.Jsoup;
import static org.junit.Assert.assertEquals;


@Feature("AutoRia JSoup test")
@RunWith(Parameterized.class)
public class AutoRiaJSoupTest {

    private String testURL;

    public AutoRiaJSoupTest(String testURL) {
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
    public void testJSoup() throws IOException {

        Response response = Jsoup.connect(testURL)
                .userAgent("Jmeter")
                .timeout(3000)
                .execute();

        String contentType = response.contentType();

        assertEquals(200, response.statusCode());
        assertEquals("gzip", response.header("Content-Encoding"));
        assertThat("Unexpected Content-Type header. Expected text/html but was " + contentType,
                contentType.contains("text/html"));

    }


}
