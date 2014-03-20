import java.io.File;

/**
 * Created by elizabethengelman on 3/20/14.
 */
public class GetHandler {
    HttpRequest request;
    ResponseGenerator generator;

    public GetHandler(HttpRequest req){
        request = req;
        generator = new ResponseGenerator(); 
    }

    public void createResponse(){
        if (request.getPath().equals("/")) {
            generator.create200StatusWithoutHeaders();
            generator.setBody();
        }else if (new File("../cob_spec/public" + request.getPath()).exists()) {
            if (request.getMethod().equals("GET")) {
                FileReader reader = new FileReader();
                if (isAnImage()) {
                    generator.create200StatusForImage();
                    generator.setBody(reader.readFile(request.getPath()));
                } else {
                    generator.create200StatusForTextFile();
                    generator.setBody(reader.readFile(request.getPath()));
                }
            }

        }else{
            generator.create404Status();
        }
    }

    private Boolean isAGifFile() {
        Boolean outcome = false;
        if (request.getPath().contains("gif")) {
            outcome = true;
        }
        return outcome;
    }

    private Boolean isAJpegFile() {
        Boolean outcome = false;
        if (request.getPath().contains("jpeg")) {
            outcome = true;
        }
        if (request.getPath().contains("jpg")) {
            outcome = true;
        }
        return outcome;
    }

    private Boolean isAPngFile() {
        Boolean outcome = false;
        if (request.getPath().contains("png")) {
            outcome = true;
        }
        return outcome;
    }

    public Boolean isAnImage() {
        if (isAGifFile() || isAJpegFile() || isAPngFile()) {
            return true;
        } else {
            return false;
        }
    }
}
