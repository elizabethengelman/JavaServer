import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by elizabethengelman on 3/20/14.
 */
public class GetHandler implements Handler {
    HttpRequest request;
    ResponseGenerator generator;
    String currentDirectory;
    String updatedDirectory;
    OutputStream outputStream;
    String typeOfImage;

    public GetHandler(){
        generator = new ResponseGenerator();
    }

    public Map<String, byte[]> createResponse() {
        Map<String, byte[]> responsePieces = new LinkedHashMap<String, byte[]>();
        if (new File(currentDirectory + request.getPath()).exists()) {
            FileReader reader = new FileReader();
            if (isAnImage()) {
                createImageResponse(reader);
            }else if((new File(currentDirectory + request.getPath())).isDirectory()){
                createSubDirectoryResponse();
            }else {
                createTextFileResponse(reader);
            }
        }
        responsePieces.put("header", generator.fullHeader);
        responsePieces.put("body", generator.body);
        return responsePieces;
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

    public void sendResponse(Map<String, byte[]> responsePieces) {
        try {
            DataOutputStream dOut = new DataOutputStream(outputStream);
            dOut.write(responsePieces.get("header"));
            dOut.write(responsePieces.get("body"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void processResponse(HttpRequest httpRequest, String directory, OutputStream os){
        request = httpRequest;
        currentDirectory = directory;
        outputStream = os;
        Map<String, byte[]> responsePieces = createResponse();
        sendResponse(responsePieces);
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
}
