package zaidimas.Services;

import java.io.InputStream;

public class WebServiceClient {

    public static final String SERVER = "http://miskoverslas.lt/laivu_musis/";
    

    String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


}
