import java.io.*;
import java.net.*;
import java.util.Timer;


public class UDPClient {
   
    private static String message = "a";
    private static final int PORT_NUM = 9999;
    private static DatagramSocket socket;
    private static DatagramPacket packet;
    private static DatagramPacket pktReceived;
    private static byte[] bytesSend;
    private static byte[] bytesReceived;
    private static int total;
    private static long totalTime;
    private static long totalTime2;
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
    private static String aSentence;
    
    UDPClient(int sizeOfData) throws SocketException, UnknownHostException {
        
        socket = new DatagramSocket();
        bytesSend = new byte[sizeOfData];
        bytesReceived = new byte[sizeOfData];
        total = 0; 
    }
    
    
    public void send(DatagramPacket aPacket) throws IOException
    {
        socket.send(aPacket);
    }
    
    public void receive(DatagramPacket aPacket) throws IOException
    {
        socket.receive(aPacket);
    }
    
    public void setAddress(String name) throws UnknownHostException
    {
        address = InetAddress.getByName(name);
    }
    
    public void createPacket(byte[] bytes, int len, InetAddress ipAddress)
    {
        packet = new DatagramPacket(bytes, len, ipAddress, PORT_NUM);
    }
    
    public void setMessage(int packetSize)
    { 
         for(int j = 0; j < packetSize; j++)
         {
             bytesSend[j] = "a".getBytes()[0]; //System.out.println("byte array beign filled");
        }
                
    }
    
    public String receiveMessage(byte[] aByte)
    {
        receivedMessage = new String(aByte);
        return receivedMessage;
    }
    
    public void close()
    {
        socket.close();
    }
    
    public static void main(String[] args) throws IOException {
       
        // Checks if there is an argument, else, show usage
        //if(args.length<=0)
        {
          //  System.out.println("Usage: UDPClient destination");
          //  System.exit(0);
        }
         
        
        UDPClient client;
        
        //1 Byte
        int packetSize = 1;
        totalTime = 0;
        client = new UDPClient(packetSize);
        client.setMessage(packetSize);
        client.setAddress("localhost");
        double avgRTT, throughput, timeInSeconds;
        
        for(int i = 0; i < 1000; i++)
        {
            long start = System.nanoTime();
            client.createPacket(bytesSend, bytesSend.length, address);
            client.send(packet);
            pktReceived = new DatagramPacket(bytesReceived, bytesReceived.length);
            socket.receive(pktReceived);
            total += bytesReceived.length;
            totalTime += System.nanoTime() - start;
        }
        
        timeInSeconds = totalTime / 1000000000.0;
        avgRTT = timeInSeconds / 1000;
        
        System.out.println(packetSize + "    " + avgRTT + "\n");
        
        //1KB - 64KB 
        packetSize = ONE_KB;
        //int packetSize = 1;
        while (packetSize <= SIXTY_FOUR_KB) { //Jump out once we hit 64KB!
            int numOfPacketsLost = 0;
            totalTime = 0;
            totalTime2 = 0;
            avgRTT = 0;
            throughput = 0;
            timeInSeconds = 0;
            client = new UDPClient(packetSize);
            client.setMessage(packetSize);
            client.setAddress("localhost");
            socket.setSoTimeout(1000);
              
            for (int i = 0; i < 100; i++) {
                
                long start = System.nanoTime(); 
                client.createPacket(bytesSend, bytesSend.length, address);
                client.send(packet); 
                pktReceived = new DatagramPacket(bytesReceived, bytesReceived.length);
                
                try{
			socket.receive(pktReceived);
                        total += bytesReceived.length; 
                        totalTime2 += System.nanoTime() - start;
                        
		}
		catch(SocketTimeoutException s)
		{
                        numOfPacketsLost++;
			continue;
		}
                
                totalTime += System.nanoTime() - start;
            }
            
            socket.close();
             
            timeInSeconds = totalTime / 1000000000.0;
            double timeInSeconds2 = totalTime2 / 1000000000.0; // doesn't count for lost packet
            throughput = total / timeInSeconds2;
            avgRTT = timeInSeconds / 100; 
             
            System.out.println(packetSize + "   " + numOfPacketsLost + "    " + avgRTT + "   " + throughput * 8 +"\n");
            
            if(packetSize == THIRTY_TWO_KB)
            {    
                packetSize = 64000;
            }
            else
            {
                packetSize *= 2;
            }
            
        }
    }
    
}
