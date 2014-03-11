import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    public static class ServerThread extends Thread{
        Socket connectedClient = null;
        BufferedReader inputFromClient;
        PrintWriter outputToClient;
        String httpVersion;
        String requestMethod;
        String requestPath;

        public ServerThread(Socket newConnection){
            connectedClient = newConnection;
        }
        public void run(){
            try{
                inputFromClient = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));
                outputToClient = new PrintWriter(connectedClient.getOutputStream(), true);//needs the true to pass the test - autoFlush
                String request = inputFromClient.readLine();

                httpVersion = getHTTPVersion(request);
                requestMethod = getMethod(request);
                requestPath = getPath(request);

                System.out.println("This is the httpVersion: " + httpVersion);
                System.out.println("This is the request Method: " + requestMethod);
                System.out.println("This is the request path: " + requestPath);


                if (requestMethod.equals("GET")){
                    outputToClient.println(httpVersion + " 200 OK\r\n");//needs the println (instead of print) in order to pass
                    BufferedReader fileBR = new BufferedReader(new FileReader("../cob_spec/public/file1"));
                    String currentLine;
                    while ((currentLine = fileBR.readLine()) != null){
                        outputToClient.println(currentLine);
                    }
                    outputToClient.println("<html>file1 contents</html>");
                }
                else if (requestMethod.equals("POST")){
                    outputToClient.println(httpVersion + " 200 OK");
                }
                else if (requestMethod.equals("PUT")){
                    outputToClient.println(httpVersion + " 200 OK");
                }
                else if (requestMethod.equals("HEAD")){
                    outputToClient.println(httpVersion + " 200 OK");
                }
                else if(requestMethod.equals("OPTIONS")){
                    outputToClient.println(httpVersion + " 200 OK");
                    outputToClient.println("Allow: GET,HEAD,POST,OPTIONS,PUT");
                }


            }
            catch(IOException e){
                System.out.println(e);
            }
            //***do i need this in order for the test to work? otherwise it doesn't seem to close properly
            finally{
                try{
                    System.out.println("A client is going down, closing it's socket");
                    connectedClient.close();
                }
                catch(IOException e){
                }
            }


        }
    }

    public static String getHTTPVersion(String request){
        return request.split(" ")[2];
    }

    public static String getMethod(String request){
        return request.split(" ")[0];
    }

    public static String getPath(String request){
        return request.split(" ")[1];
    }
    public static void main(String[] args) throws IOException{

        int portNumber = 5000;
        System.out.println("Server started!");
        ServerSocket serverSocket = new ServerSocket(portNumber);
        try{
            while(true){
            Socket newConnection = serverSocket.accept();
            ServerThread newThread = new ServerThread(newConnection);
            newThread.start();
            System.out.println(newConnection.getInetAddress() + "connected");
            }
        }
        finally{
            serverSocket.close();
        }


    }


}