import java.io.PrintWriter;

/**
 * Created by elizabethengelman on 3/28/14.
 */
public class FileWriter {
    public void writeToFile(String filePath, String contentToWrite){
        try{
            PrintWriter writer = new PrintWriter(filePath, "UTF-8");
            writer.println(contentToWrite);
            writer.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public String formatFileBody(String requestBody){
        String[] requestBodyPieces = requestBody.split("=");
        String fileContent = requestBodyPieces[0] + " = " + requestBodyPieces[1];
        return fileContent;
    }

}


