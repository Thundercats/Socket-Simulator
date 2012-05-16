import java.net.*;
import java.io.*;
 
public class TCPServer {
    private static final int ONE_KB = 1024;
    private static final int TWO_KB = 2048;
    private static final int FOUR_KB = 4096;
    private static final int EIGHT_KB = 8192;
    private static final int SIXTEEN_KB = 16384;
    private static final int THIRTY_TWO_KB = 32000;
    private static final int SIXTY_FOUR_KB = 64000;
    private static byte[] bytes;
    private static DataInputStream in;
    private static DataOutputStream out;
    private static ServerSocket serverSocket;
    private static Socket skt;
 
    public static void main(String[] args) throws IOException
    {
       int port = 9999; 
       try
            {
                serverSocket = new ServerSocket(port);
                skt = serverSocket.accept();
                in = new DataInputStream(skt.getInputStream());
                out = new DataOutputStream(skt.getOutputStream());
                bytes = new byte[ONE_KB];
                //bytes = new byte[SIXTY_FOUR_KB];
                System.out.println("Connection Established"); 
                
                while(true) 
                {
                  in.read(bytes);
                  System.out.println("reading in");
                  out.write(bytes);   
                  System.out.println("sending out");
                }
                 
            
        } catch (IOException e) {
            System.out.println("Errorr");
            e.printStackTrace(); 
        }
                out.close();
                in.close();
                skt.close();
                serverSocket.close();
       
    }
}
