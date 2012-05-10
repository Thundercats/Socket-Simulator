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
            //DataInputStream in =
            //        new DataInputStream(server.getInputStream());//in
            System.out.println("Connection Established");
            PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
            System.out.println("Data sending: '" + data + "'\n");
            //DataOutputStream out =
            //        new DataOutputStream(server.getOutputStream());//out
            //server.close();//close
            // out.print(data);
            out.print(geoff);
            out.close();
            skt.close();
            serverSocket.close();
            
        } catch (IOException e) {
            System.out.println("Errorr");
            //break;
        }
       
    }
}
