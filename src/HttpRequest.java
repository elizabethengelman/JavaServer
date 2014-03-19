import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
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

    public String getParametersFromPath(){
        String parameters = getPath().substring(getPath().lastIndexOf("?") + 1);
        System.out.println("these are the parameters " + parameters);
        return parameters;
    }

    public String getParameterVariableName(String parameter){
        String variableName = parameter.substring(0, parameter.indexOf("="));
        return variableName;
    }

    public String getParameterVariableValue(String parameter){
        String variableValue = parameter.substring(parameter.indexOf("=") + 1);
        return decodeCharacters(variableValue);
    }

    public Map<String, String> getIndividualParams(){
        Map<String, String> paramPairs = new LinkedHashMap<String, String>();
        String[] params = getParametersFromPath().split("&");
        for(String param : params){
            String varName = getParameterVariableName(param);
            System.out.println(varName);
            String varValue = getParameterVariableValue(param);
            System.out.println(varValue);
            paramPairs.put(varName, varValue);
        }

        return paramPairs;
    }

    public HashMap<String, String> getIndParams(){
        String params = getParametersFromPath();

        return null;
    }

    public String decodeCharacters(String value){
        String s1 = value.replace("%20", " ");
        String s2 = s1.replace("%3C", "<");
        String s3 = s2.replace("%2C", ",");
        String s4 = s3.replace("%3E", ">");
        String s5 = s4.replace("%3D", "=");
        String s6 = s5.replace("%3B", ";");
        String s7 = s6.replace("%2B", "+");
        String s8 = s7.replace("%40", "@");
        String s9 = s8.replace("%23", "#");
        String s10 = s9.replace("%24", "$");
        String s11 = s10.replace("%5B", "[");
        String s12 = s11.replace("%3A", ":");
        String s13 = s12.replace("%22", "\"");
        String s14 = s13.replace("%3F", "?");
        String s15 = s14.replace("%26", "&");
        String s16 = s15.replace("%5D", "]");
        return s16;
    }
}
