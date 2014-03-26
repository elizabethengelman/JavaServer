import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

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
        }else if (new File(currentDirectory + request.getPath()).exists()) {
            FileReader reader = new FileReader();
            if (isAnImage()) {
                createImageResponse(reader);
            }else if((new File(currentDirectory + request.getPath())).isDirectory()){
                createSubDirectoryResponse();
            }else {
                 createTextFileResponse(reader);
            }
        }else{
            create404Response();
        }
    }

    private void create404Response() {
        generator.setStatusLine("404");
        generator.setHeaders();
        generator.setBody("File not found".getBytes());
    }

    private void createTextFileResponse(FileReader reader) {
        generator.setStatusLine("200");
        generator.setHeaders("Content-Type: text/html");
        generator.setBody(reader.readFile(currentDirectory + request.getPath()));
    }

    private void createSubDirectoryResponse() {
        updatedDirectory = currentDirectory + request.getPath();
        String namesOfFiles = getDirectoryFileNames(updatedDirectory, request.getPath());
        generator.setStatusLine("200");
        generator.setHeaders("Content-Type: text/html");
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

    public void sendResponse(OutputStream outputStream) {
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
