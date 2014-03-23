import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by elizabethengelman on 3/20/14.
 */
public class GetHandler implements Handler {
    HttpRequest request;
    ResponseGenerator generator;

    public GetHandler(HttpRequest req){
        request = req;
        generator = new ResponseGenerator();
    }

    public void createResponse() {
        if (request.getPath().equals("/")) {
            String namesOfFiles = getDirectoryFileNames();
            generator.create200StatusWithoutHeaders();
            generator.setBody(namesOfFiles.getBytes());
        }else if (new File("../cob_spec/public" + request.getPath()).exists()) {
            FileReader reader = new FileReader();
            if (isAnImage()) {
                generator.create200StatusForImage();
                generator.setBody(reader.readFile(request.getPath()));
            }else {
                if (request.getPath().equals("/partial_content.txt")){
                    generator.setBody(reader.readFile(request.getPath()));
                    generator.create206Status(Integer.toString(generator.body.length));
                }else{
                    generator.create200StatusForTextFile();
                    generator.setBody(reader.readFile(request.getPath()));
                }
            }
        }else if(request.getPath().equals("/redirect")){
            generator.createRedirectStatus();
            request.setPath("/");
        }else if(request.getPath().contains("/parameters")){
            String tempBody = iterateThroughParameters();
            generator.create200StatusWithoutHeaders();
            generator.setBody(tempBody.getBytes());
        }else if(request.getPath().equals("/method_options")){
            generator.create200StatusForOptionsMethod();
            generator.setBody();
        }else if(request.getPath().equals("/logs")){
            Authenticator auth = new Authenticator(request);
            if (auth.authenticated()){
                generator.create200StatusWithoutHeaders();
                generator.setBody();
            }else{
                generator.create401Status();
                generator.setBody("Authentication required".getBytes());
            }
        }else if (request.getPath().equals("/form")){
            generator.create200StatusWithoutHeaders();
            generator.setBody("data = cosby".getBytes());
        }else{
            generator.create404Status();
            generator.setBody("File not found".getBytes());
        }
    }

    private String getDirectoryFileNames() {
        DirectoryBuilder directoryBuilder = new DirectoryBuilder();
        return directoryBuilder.getLinksOfFiles();
    }

    public void sendResponse(OutputStream outputStream) {
        try {
            byte[] requestHeader = generator.header;
            byte[] requestBody = generator.body;
            DataOutputStream dOut = new DataOutputStream(outputStream);
            dOut.write(requestHeader);
            dOut.write(requestBody);
        } catch (IOException e) {
            System.out.println(e);
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

    private Boolean isAnImage() {
        if (isAGifFile() || isAJpegFile() || isAPngFile()) {
            return true;
        } else {
            return false;
        }
    }

    private String iterateThroughParameters(){
        Map<String, String> params = request.getIndividualParams();
        String tempBody = new String();
        Iterator it = params.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pairs = (Map.Entry)it.next();
            tempBody += pairs.getKey() + " = " + pairs.getValue() + "\n";
            it.remove();
        }
        return tempBody;
    }
}
