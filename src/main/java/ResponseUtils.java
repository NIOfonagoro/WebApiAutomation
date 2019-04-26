import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.util.Arrays;
import java.util.List;

public class ResponseUtils {
    public static String getHeader(CloseableHttpResponse response, String headerName) {
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

    public static String getHeadersUsingJava8(CloseableHttpResponse response, String headerName)
    {
        List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());
        Header matchedHeader = httpHeaders.stream()
                .filter(header -> headerName.equalsIgnoreCase(header.getName()))
                .findFirst().orElseThrow(() -> new RuntimeException("Didn't find the header"));

        return matchedHeader.getValue();
    }

    public static boolean headerIsPresent(CloseableHttpResponse response, String headerName){
        List<Header> httpHeader = Arrays.asList(response.getAllHeaders());

        return httpHeader.stream()
                .anyMatch(header -> header.getName().equalsIgnoreCase(headerName));
    }
}
