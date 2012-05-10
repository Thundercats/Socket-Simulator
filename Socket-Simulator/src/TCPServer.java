import java.net.*;
import java.io.*;
 
public class TCPServer {
    private static final int ONE_KB = 1024;
    private static final int TWO_KB = 2048;
    private static final int FOUR_KB = 4096;
    private static final int EIGHT_KB = 8192;
    private static final int SIXTEEN_KB = 16384;
    private static final int THIRTY_TWO_KB = 32768;
    private static final int SIXTY_FOUR_KB = 65536;
    //private ServerSocket serverSocket;
//    
//    public TCPServer(int portNum) throws IOException
//    {
//        serverSocket = new ServerSocket(portNum);
//        serverSocket.setSoTimeout(10000);
//    }
//    
    /**public void send()
    //{
        while(true)
        {
            try
            {
                ServerSocket server = new ServerSocket(9999);
                Socket skt = serverSocket.accept();
                DataInputStream in =
                        new DataInputStream(server.getInputStream());//in
                DataOutputStream out =
                        new DataOutputStream(server.getOutputStream());//out
                server.close();//close
            }
            catch(IOException e)
            {
                System.out.println("Errorr");
                break;
            }
        }
    }
    */
    public static void main(String[] args) throws IOException
    {
       int port = 9999;
       String data = "L";
       byte geoff = 'L';
       try
            {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket skt = serverSocket.accept();
            
            System.out.println("Connection Established");
            PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
            System.out.println("Data sending: '" + ONE_KB + "'\n");
            
            int messageSize = ONE_KB;
            //for(int i = 0; i < 1000; i++)
            byte[] bytes;
            
            while (messageSize <= SIXTY_FOUR_KB) {
              
                long start =  System.nanoTime();
                bytes = new byte[messageSize];
                
                for (int i = 0; i < 100; i++) {
                        out.print(ONE_KB);
                    }

                    
                    
                long diff = System.nanoTime() - start;
                double timeInSeconds = diff / 1000000000.0; 
                out.print("The avg RTT is " + timeInSeconds / 1000);
                messageSize *= 2;
            }
            
            out.close();
            skt.close();
            serverSocket.close();
            
        } catch (IOException e) {
            System.out.println("Errorr");
            //break;
        }
       
    }
}
