import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by elizabethengelman on 3/23/14.
 */
public class RequestLogger {
    HttpRequest request;

    public void logRequest(String thingToLog){
        try{
            FileWriter writer = new FileWriter("../cob_spec/public/logs", true);
            writer.write(thingToLog + "\n\n");
            writer.close();
        }catch(IOException e){

        }
    }
}

