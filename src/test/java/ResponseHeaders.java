import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class ResponseHeaders {

    public static final String BASE_ENDPOINT = "https://api.github.com";
    CloseableHttpClient client;
    CloseableHttpResponse response;

    @BeforeMethod
    public void setup() {client = HttpClientBuilder.create().build(); }

    @AfterMethod
    public void closeResource() throws IOException {
        client.close();
        if(response != null)
        {
            response.close();
        }
    }

    @Test
    public void contentTypeIsJson() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);

        response = client.execute(get);

        Header contentType = response.getEntity().getContentType();
        assertEquals(contentType.getValue(), "application/json; charset=utf-8");

        ContentType ct = ContentType.getOrDefault(response.getEntity());
        assertEquals(ct.getMimeType(), "application/json");
    }

    @Test
    public void serverIsGithub() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);

        response = client.execute(get);

        String headerValue = getHeader(response, "Server");

        assertEquals(headerValue, "GitHub.com");
    }

    private String getHeader(CloseableHttpResponse response, String headerName) {
        //Get all headers
        Header[] headers = response.getAllHeaders();
        List<Header> httpHeaders = Arrays.asList(headers);
        String returnHeader = "";

        //Loop over header list
        for (Header header : httpHeaders) {
            if (headerName.equalsIgnoreCase(header.getName())){
                returnHeader = header.getValue();
            }
        }

        //If no headers found, throw an exception
        if (returnHeader.isEmpty()){
            throw new RuntimeException("Didn't find the header " + headerName);
        }

        //Return the header
        return returnHeader;
    }
}
