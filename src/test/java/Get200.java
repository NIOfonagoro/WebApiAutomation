import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class Get200 {

    public static final String BASE_ENDPOINT = "https://api.github.com";
    CloseableHttpClient client;
    CloseableHttpResponse response;

    @BeforeMethod
    public void setup(){
        client = HttpClientBuilder.create().build();
    }

    @AfterMethod
    public void closeResource() throws IOException {
        client.close();
        if(response != null)
        {
            response.close();
        }
    }

    @Test
    public void baseUrlReturns200() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);

        HttpResponse response = client.execute(get);

        int actualStatus = response.getStatusLine().getStatusCode();

        Assert.assertEquals(actualStatus, 200);
    }

    @Test
    public void rateLimitReturns200() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT + "/rate_limit");

        HttpResponse response = client.execute(get);

        int actualStatus = response.getStatusLine().getStatusCode();

        Assert.assertEquals(actualStatus, 200);
    }

    @Test
    public void searchReposReturns200() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT + "/search/repositories?q=java");

        HttpResponse response = client.execute(get);

        int actualStatus = response.getStatusLine().getStatusCode();

        Assert.assertEquals(actualStatus, 200);
    }
}
