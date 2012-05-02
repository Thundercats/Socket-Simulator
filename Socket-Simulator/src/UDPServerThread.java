import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author firen
 */
public class UDPServerThread extends Thread {
    private static DatagramSocket socket;
    private static DatagramPacket packet, packet2;
    private static boolean flag = true;
    private static final int PORT_NUM = 9999;
    private static byte[] bytesReceived;
    private static String message;
    private static int DATA_SIZE = 1024;
    
    public UDPServerThread() throws IOException {
        this("UPDServerThread");
    }
    
    public UDPServerThread(String clientName) throws IOException
    {
        super(clientName);
        socket = new DatagramSocket(PORT_NUM);
    }
    
    public void close()
    {
        socket.close();
    }
    
    
    public void run()
    {
        while(flag)
        {
            try {
                
                bytesReceived = new byte[DATA_SIZE];
                packet = new DatagramPacket(bytesReceived, bytesReceived.length);
                socket.receive(packet);
                message = new String(packet.getData()); 
                System.out.println("Message received: " + message);
           }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            
            flag = false;
        }
        
        this.close();
        //socket.close(); close() method takes care of this
    }
}
