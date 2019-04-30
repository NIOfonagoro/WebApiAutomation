import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.Charset;

public class DeleteAndPost extends BaseClass {
    private static String TOKEN = "aa409c466cfd0b8fae477e97cf53466da9b30c6a";
    private static String EMAIL = "nofonagoro@spartaglobal.com";
    private static String PASSWORD = "Budokai_3";

    @Test
    public void deleteIsSuccessful() throws IOException {
        HttpDelete request = new HttpDelete(BASE_ENDPOINT + "/repos/NIOfonagoro/Sparta-Postcodes-API-Homework");
        request.setHeader(HttpHeaders.AUTHORIZATION, "token " + TOKEN);

        response = client.execute(request);

        int actualStatusCode = response.getStatusLine().getStatusCode();

        Assert.assertEquals(actualStatusCode, 204);
    }

    @Test
    public void createRepoReturns201() throws IOException {
        // Create a HttpPost with a valid Endpoint
        HttpPost request = new HttpPost(BASE_ENDPOINT + "/user/repos");

        // Set the Basic Auth Header
        String auth = EMAIL + ":" + PASSWORD;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
        String authHeader = "Basic " + new String(encodedAuth);

        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        // Define JSON to Post and set as Entity
        String json = "(\"name\": \"deleteme\")";
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        //Send
        response = client.execute(request);

        int actualStatusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(actualStatusCode, 201);
    }
}
