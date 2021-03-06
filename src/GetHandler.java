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
    String typeOfImage;
    String currentDirectory;
    String updatedDirectory;

    public GetHandler(){
        generator = new ResponseGenerator();
    }

    public void createResponse(HttpRequest httpRequest, String directory) {
        request = httpRequest;
        currentDirectory = directory;
        if (request.getPath().equals("/")) {
            String namesOfFiles = getDirectoryFileNames(currentDirectory, "/");
            generator.create200StatusWithoutHeaders();
            generator.setBody(namesOfFiles.getBytes());
        }else if(request.getPath().equals("/logs")){
            Authenticator auth = new Authenticator(request);
            FileReader reader = new FileReader();
            if (auth.authenticated()){
                generator.create200StatusWithoutHeaders();
                generator.setBody(reader.readFile(currentDirectory + request.getPath()));
            }else{
                generator.create401Status();
                generator.setBody("Authentication required".getBytes());
            }
        }else if (new File(currentDirectory + request.getPath()).exists()) {
            FileReader reader = new FileReader();
            if (isAnImage()) {
                setTypeOfImage();
                generator.create200StatusForImage(typeOfImage);
                generator.setBody(reader.readFile(currentDirectory + request.getPath()));
            }else if((new File(currentDirectory + request.getPath())).isDirectory()){
                updatedDirectory = currentDirectory + request.getPath();
                String namesOfFiles = getDirectoryFileNames(updatedDirectory, request.getPath());
                generator.create200StatusWithoutHeaders();
                generator.setBody(namesOfFiles.getBytes());
            }else {
                if (request.getPath().equals("/partial_content.txt")){
                    generator.setBody(reader.readFile(currentDirectory + request.getPath()));
                    String range = request.getRange();
                    String newContentLength = range.substring(range.indexOf("-")+1);
                    generator.create206Status(Integer.toString(generator.body.length), range, newContentLength);
                }else{
                    generator.create200StatusForTextFile();
                    generator.setBody(reader.readFile(currentDirectory + request.getPath()));
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
        }else if (request.getPath().equals("/form")){
            generator.create200StatusWithoutHeaders();
            generator.setBody();
        }else{
            generator.create404Status();
            generator.setBody("File not found".getBytes());
        }
    }

    private String getDirectoryFileNames(String directory, String currentPath) {
        DirectoryBuilder directoryBuilder = new DirectoryBuilder(directory, currentPath);
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

    private void setTypeOfImage(){
        if (isAGifFile()){
            typeOfImage = "image/gif";
        }else if (isAJpegFile()){
            typeOfImage = "image/jpg";
        }else if (isAPngFile()){
            typeOfImage = "image/png";
        }
    }
}
