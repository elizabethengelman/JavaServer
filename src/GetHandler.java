import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
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
              createIndexResponse();
        }else if(request.getPath().equals("/logs")){
            Authenticator auth = new Authenticator(request);
            FileReader reader = new FileReader();
            if (auth.authenticated()){
                generator.setStatusLine("200");
                generator.setHeaders(CONTENT_TYPE_HTML_HEADER);
                generator.setBody(reader.readFile(currentDirectory + request.getPath()));

            }else{
                generator.setStatusLine("401");
                generator.setHeaders();
                generator.setBody("Authentication required".getBytes());
            }
        }else if (new File(currentDirectory + request.getPath()).exists()) {
            FileReader reader = new FileReader();
            if (isAnImage()) {
                createImageResponse(reader);
            }else if((new File(currentDirectory + request.getPath())).isDirectory()){
                createSubDirectoryResponse();
            }else {
                if (request.getPath().equals("/partial_content.txt")){
                    createPartialContentResponse(reader);
                }else{
                    createTextFileResponse(reader);
                }
            }
        }else if(request.getPath().equals("/redirect")){
            createRedirectResponse();
        }else if(request.getPath().contains("/parameters")){
            createParameterResponse();
        }else if(request.getPath().equals("/method_options")){
            createMethodOptionsResponse();
        }else if (request.getPath().equals("/form")){
            generator.create200StatusWithoutHeaders();
            generator.setBody();
        }else{
            generator.setStatusLine("404");
            generator.setHeaders(CONTENT_TYPE_HTML_HEADER);
            generator.setBody("Not found".getBytes());
        }

    }

    private void createMethodOptionsResponse() {
        generator.setStatusLine("200");
        generator.setHeaders("Allow: GET,HEAD,POST,OPTIONS,PUT");
        generator.setBody();
    }

    private void createParameterResponse() {
        generator.setStatusLine("200");
        generator.setHeaders(CONTENT_TYPE_HTML_HEADER);
        generator.setBody(iterateThroughParameters().getBytes());
    }

    private void createRedirectResponse() {
        generator.setStatusLine("307");
        generator.setHeaders("Location: http://localhost:5000/");
        request.setPath("/");
    }

    private void createPartialContentResponse(FileReader reader) {
        byte[] file = reader.readFile(currentDirectory + request.getPath());
        String range = request.getRange();
        String partialContentLength = range.substring(range.indexOf("-")+1);
        String originalContentLength = Integer.toString(file.length);
        Date date = new Date();
        generator.setStatusLine("206");
        generator.setHeaders(CONTENT_TYPE_HTML_HEADER,
                             "Content-Range: bytes " + range + "/" + originalContentLength,
                             "Date: " + date.toString(),
                             "Content-Length: " + partialContentLength);
        generator.setBody(file);
    }

    private void createTextFileResponse(FileReader reader) {
        generator.setStatusLine("200");
        generator.setHeaders(CONTENT_TYPE_HTML_HEADER);
        generator.setBody(reader.readFile(currentDirectory + request.getPath()));
    }

    private void createSubDirectoryResponse() {
        updatedDirectory = currentDirectory + request.getPath();
        String namesOfFiles = getDirectoryFileNames(updatedDirectory, request.getPath());
        generator.setStatusLine("200");
        generator.setHeaders(CONTENT_TYPE_HTML_HEADER);
        generator.setBody(namesOfFiles.getBytes());
    }

    private void createImageResponse(FileReader reader) {
        setTypeOfImage();
        generator.setStatusLine("200");
        generator.setHeaders("Content-Type: " + typeOfImage);
        generator.setBody(reader.readFile(currentDirectory + request.getPath()));
    }

    private String getDirectoryFileNames(String directory, String currentPath) {
        DirectoryBuilder directoryBuilder = new DirectoryBuilder(directory, currentPath);
        return directoryBuilder.getLinksOfFiles();
    }

    public void sendResponse(OutputStream outputStream) { //maybe try to have the input stream of file link up right to the
                                                          //output stream of the writer
        try {
            byte[] requestHeader = generator.fullHeader;
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

    private void createIndexResponse(){
        String namesOfFiles = getDirectoryFileNames(currentDirectory, "/");
        generator.setStatusLine("200");
        generator.setHeaders("Content-Type: text/html");
        generator.setBody(namesOfFiles.getBytes());
    }
}
