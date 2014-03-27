import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpRequest {
    InputStream inputStream;
    BufferedReader inputFromClient;
    String requestString = "";

    public HttpRequest(InputStream is){
        inputStream = is;
        inputFromClient = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String newRequestString = inputFromClient.readLine();
            requestString += newRequestString;
            System.out.println("First request string: " + requestString);

            while (!isEndOfHeader(newRequestString)){
                if ((newRequestString = inputFromClient.readLine()) != null){
                    requestString += newRequestString;
                }
            }
            System.out.println("THE REQUEST STRING" + requestString);
            System.out.println("this req has content lenght" + hasContentLength());
            if (hasContentLength()){
                readAdditionalBytes();
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    private boolean hasContentLength(){
        return requestString.contains("Content-Length");
    }

    private void readAdditionalBytes(){
        String sub = requestString.substring(requestString.indexOf("Content-Length"), requestString.indexOf("Content-Type"));
        int contentLength = Integer.parseInt(sub.substring(sub.indexOf(":") + 2));
        String s = inputFromClient.red 
    }
    private boolean isEndOfHeader(String newRequestString) {
        return newRequestString == null || newRequestString.equals("") || newRequestString.contains("\r\n");
    }

    public String getMethod(){
        return requestString.split(" ")[0];
    }

    public String getPath(){
        return requestString.split(" ")[1];
    }

    public void setPath(String newPath){
        requestString = requestString.replace(getPath(), newPath);
    }

    public String getHttpVersion(){
        return requestString.split(" ")[2];
    }

    public String getRange(){
        if (requestString.contains("Range")){
            String s = requestString.substring(requestString.indexOf("Range"), requestString.indexOf("Connection"));
            String range = s.substring(s.indexOf("bytes") + 6);
            return range;
        }else{
            return "";
        }
    }

    public String authorizationHeaderInfo(){
        if (requestString.contains("Authorization")){
            String s = requestString.substring(requestString.indexOf("Authorization"), requestString.indexOf("Connection"));//this needs to change
            String authInfo = s.substring(s.indexOf("Basic") + 6);
            Base64 decoder = new Base64();
            byte[] decodedBytes = decoder.decode(authInfo);
            return new String(decodedBytes);
        }else{
            return "";
        }
    }

    private String getParametersFromPath(){
        String parameters = getPath().substring(getPath().lastIndexOf("?") + 1);
        return parameters;
    }

    private String getParameterName(String parameter){
        String variableName = parameter.substring(0, parameter.indexOf("="));
        return variableName;
    }

    private String getParameterValue(String parameter){
        String variableValue = parameter.substring(parameter.indexOf("=") + 1);
        return decodeCharacters(variableValue);
    }

    public Map<String, String> getIndividualParams(){
        Map<String, String> paramPairs = new LinkedHashMap<String, String>();
        String[] params = getParametersFromPath().split("&");
        for(String param : params){
            String varName = getParameterName(param);
            String varValue = getParameterValue(param);
            paramPairs.put(varName, varValue);
        }
        return paramPairs;
    }

    public String decodeCharacters(String value){
        String[][] replacements = {{"%20", " "}, {"%3C", "<"}, {"%2C", ","},
                                   {"%3E", ">"}, {"%3D", "="}, {"%3B", ";"},
                                   {"%2B", "+"}, {"%40", "@"}, {"%23", "#"},
                                   {"%24", "$"}, {"%5B", "["}, {"%3A", ":"},
                                   {"%22", "\""}, {"%3F", "?"}, {"%26", "&"},
                                   {"%5D", "]"}};

        String decodedValue = value;
        for(String[] replacement : replacements){
            decodedValue = decodedValue.replace(replacement[0], replacement[1]);
        }
        return decodedValue;
    }
}


