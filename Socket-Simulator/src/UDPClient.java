import java.io.*;
import java.net.*;
import java.util.Timer;


public class UDPClient {
   
    private static final int PORT_NUM = 9999;
    private static DatagramSocket socket;
    private static DatagramPacket packet;
    private static byte[] bytesSent;
    private static final int MAX = 1000;
    private static final int DATA_SIZE = 1;
    private static final int ONE_KB = 1024;
    private static final int FOUR_KB = 4096;
    private static final int EIGHT_KB = 8192;
    private static final int SIXTEEN_KB = 16384;
    private static final int THIRTY_TWO_KB = 32768;
    private static final int SIXTY_FOUR_KB = 65536;
    
    private static InetAddress address;
    private String receivedMessage;
    private static Timer t;
    
    UDPClient(int sizeOfData) throws SocketException, UnknownHostException {
        
        socket = new DatagramSocket();
        bytesSent = new byte[sizeOfData];
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
        //Need to test:  1Kbyte, 4KB, 8 KB, 16KB, 32 KB, and 64KB
        long start = System.nanoTime();
//        for(int i = 0; i < MAX; i++) {  <--- this whole loop is for the for b(ii) only
//            UDPClient client = new UDPClient(DATA_SIZE);
//            client.setMessage("hello, this is the client");
//            client.setAddress("localhost");
//            client.createPacket(bytesSent, bytesSent.length, address);
//            client.send(packet);
//            client.receiveMessage(packet.getData());  
//        }
        
        UDPClient client;
        
        /*
         * This is what I initially came up with for part b(iii)
         * I sort of feel I am calculating delay improperly but I have no reference!
         * Basically what I attempting with this loop is to start out with 1KB 
         * and increase the size of the message by doubling it, then doing the process 
         * 100 times like the directions ask. I included 2KB even thoguh it wasn't asked of us.
         */
        int packetSize = 1024;
        while (packetSize <= SIXTY_FOUR_KB) { //Jump out once we hit 64KB!
            
            for (int i = 0; i < MAX; i++) {
                
                client = new UDPClient(packetSize);
                client.setMessage("Hello, this client."); 
                client.setAddress("localhost");
                client.createPacket(bytesSent, bytesSent.length, address);
                client.send(packet);
                client.receiveMessage(packet.getData());
            }
            
            packetSize *= 2;
        }
        
        long difference = System.nanoTime() - start;
        double timeInSeconds = (double) difference / 1000000000.0;
        
        System.out.println("The elapse time is " + timeInSeconds);
        System.out.println("The avg is  " + timeInSeconds / MAX);
    }
    
}
