import java.net.*;
import java.io.*;
 
public class TCPServer {
    
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
            System.out.println("Data sending: '" + data + "'\n");
            
            long start =  System.nanoTime();
            
            for(int i = 0; i < 1000; i++)
            {
                out.print(geoff);
            }
            
            long diff = System.nanoTime() - start;
            double timeInSeconds = diff / 1000000000.0; 
            out.print("The avg RTT is " + timeInSeconds / 1000);
            out.close();
            skt.close();
            serverSocket.close();
            
        } catch (IOException e) {
            System.out.println("Errorr");
            //break;
        }
       
    }
}
