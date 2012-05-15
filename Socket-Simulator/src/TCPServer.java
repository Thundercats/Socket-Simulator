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
    private static int bufferSize;
 
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
                
                byte flag;
                boolean safe = true;
                byte a = "A".getBytes()[0];
                byte one = "b".getBytes()[0];
                byte two = "c".getBytes()[0];
                byte four = "d".getBytes()[0];
                byte eight = "e".getBytes()[0];
                byte sixteen = "f".getBytes()[0];
                byte thirty_two = "g".getBytes()[0]; 
                
                while(true) 
                { 
                   if(safe == true)
                   {
                       flag = in.readByte();

                       if (flag == a) {
                           bytes = new byte[1];
                       } else if (flag == one) {
                           bytes = new byte[ONE_KB];
                       } else if (flag == two) {
                           bytes = new byte[TWO_KB];
                       } else if (flag == four) {
                           bytes = new byte[FOUR_KB];
                       } else if (flag == eight) {
                           bytes = new byte[EIGHT_KB];
                       } else if (flag == sixteen) {
                           bytes = new byte[SIXTEEN_KB];
                       } else if (flag == thirty_two) {
                           bytes = new byte[THIRTY_TWO_KB];
                       } else {
                           bytes = new byte[SIXTY_FOUR_KB];
                       }
                       
                        safe = false;
                   }
                  
                    
                  in.readFully(bytes);
                  System.out.println("reading in");
                  out.write(bytes, 0 , bytes.length);   
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
