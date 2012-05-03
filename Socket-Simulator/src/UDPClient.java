import java.io.*;
import java.net.*;
import java.util.Timer;


public class UDPClient {
   
    private static final int PORT_NUM = 9999;
    private static DatagramSocket socket;
    private static DatagramPacket packet;
    private static byte[] bytesSent;
    private static final int MAX = 1000;
    private static final int DATA_SIZE = 1024;
    private static InetAddress address;
    private String receivedMessage;
    private static Timer t;
    
    UDPClient() throws SocketException, UnknownHostException {
        
        socket = new DatagramSocket();
        bytesSent = new byte[DATA_SIZE];
        //address = InetAddress.getByName("localhost");
        //packet = new DatagramPacket(bytesSent, bytesSent.length, address, PORT_NUM);
    }
    
    
    public void send(DatagramPacket aPacket) throws IOException
    {
        socket.send(aPacket);
    }
    
    public void setAddress(String name) throws UnknownHostException
    {
        address = InetAddress.getByName(name);
    }
    
    public void createPacket(byte[] aBytesSent, int len, InetAddress ipAddress)
    {
        packet = new DatagramPacket(aBytesSent, len, ipAddress, PORT_NUM);
    }
    
    public void setMessage(String message)
    {
        bytesSent = message.getBytes();
    }
    
    public void receiveMessage(byte[] aByte)
    {
        receivedMessage = new String(aByte);
        //System.out.println("Received message: " + receivedMessage);
    }
    
    public void close()
    {
        socket.close();
    }
    
    public static void main(String[] args) throws IOException {
        //t = new Timer();
        long start = System.nanoTime();
        for(int i = 0; i < 10; i++) {
            UDPClient client = new UDPClient();
            client.setMessage("hello, this is the client");
            client.setAddress("localhost");
            client.createPacket(bytesSent, bytesSent.length, address);
            client.send(packet);
            client.receiveMessage(packet.getData());  
        }
        long difference = System.nanoTime() - start;
        double timeInSeconds = (double) difference / 1000000000.0;
        System.out.println("The elapse time is " + timeInSeconds);
        System.out.println("The avg is  " + timeInSeconds / 10);
    }
    
}
