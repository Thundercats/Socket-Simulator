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
    private static InetAddress address;
    private static boolean flag = true;
    private static final int PORT_NUM = 9999;
    private static byte[] bytesReceived;
    private static byte[] bytesSend;
    private static String message;
    private static int DATA_SIZE = 1024;
    private static int port;
    
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
                //message = "Server received: ";
                message = new String(packet.getData());
                
                bytesSend = new byte[DATA_SIZE];
                //bytesSend = message.getBytes();
                bytesSend = ("Server received " + packet.getData()).getBytes();
                address = packet.getAddress();
                port = packet.getPort();
                packet2 = new DatagramPacket(bytesSend, bytesSend.length, address, port);
                socket.send(packet2);
                
                System.out.println("Message received: " + message);
                System.out.println("The address: " + address + " The port #: " + port);
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
