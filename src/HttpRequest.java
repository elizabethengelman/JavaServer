import java.io.*;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;


public class HttpRequest {
    InputStream inputStream;
    BufferedReader inputFromClient;
    String requestString;

    public HttpRequest(InputStream is){
        inputStream = is;
        inputFromClient = new BufferedReader(new InputStreamReader(inputStream));
        try{
            requestString = inputFromClient.readLine();
        }
        catch(IOException e){
            System.out.println(e);
        }
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

    public String decodeCharacters(String value) {
        String result = new String();
        try{
            result = URLDecoder.decode(value, "UTF-8");
        }
        catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
}
