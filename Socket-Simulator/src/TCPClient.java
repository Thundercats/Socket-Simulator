import java.io.*;
import java.net.*;
 
public class TCPClient {
    private static DataOutputStream out;
    private static DataInputStream in;
    private static Socket socket;
    
    private static final int ONE_KB = 1024; 
    private static final int SIXTY_FOUR_KB = 65536;
    private static byte[] bytes;
    private static byte[] inBytes;
    
    public static void main(String[] args)
    {
	String host = "localhost"; // Default to localhost
	int port = 9999; // Default to port 9999
	// Allow changing connection parameters
	if(args.length >0){
	    host = args[0]; // Set host
	    if(args.length>1)
		port = Integer.parseInt(args[1]); // Set port
	}
	System.out.println("Connecting to "+host +":"+port); // Print the hostname and port being connected to
        try {
             socket = new Socket(host, port); // Create a socket with the given hostname and port
             System.out.println("CONNECTION ESTABLISHED");
             
		if(args.length>=3)
		{
                    System.out.print("OPTIONS ENABLED ");
			if(args[2].equals("timeout")){
				socket.setSoTimeout(1000); // Sets the timeout for the created socket 
				System.out.println(": Timeout set to 1000 ms");
			}
			if(args[2].equals("fast")){
           	 		socket.setPerformancePreferences(0, 1, 2); // Sets the performance to prefer high bandwidth (see below)
				System.out.println(": Sets the performance preference with high bandwidth to be prioritized above low latency, "
                                        + "low latency above short connection time ");
			}
			if(args[2].equals("slow")){
            	 		socket.setPerformancePreferences(1, 0, 0); // Sets the performance to prefer short connection times
				System.out.println(": Sets the performance preference with short connection time "
                                        + "to be prioritized above high bandwidth and low latency ");
			} 
		}
                
             out = new DataOutputStream(socket.getOutputStream()); //Data to send OUT to Server 
             in = new DataInputStream(socket.getInputStream()); //Data being read IN from server 
             int messageSize = ONE_KB;  
             
             //1 BYTE ONLY
             byte sendByte = "a".getBytes()[0]; // Instantiates a single byte
             byte receivedByte;
             
             long start, diff = 0;   
	     // Send 1,000 bytes
             for(int i = 0; i < 1000; i++)
             {
                 start = System.nanoTime();
                 out.write(sendByte);
                 out.flush();
                 receivedByte = in.readByte();
                 diff += System.nanoTime() - start;
             }
             
             double timeInSeconds = diff / 1000000000.0;
             
             System.out.println("The size is " + " 1 byte " + " The avg RTT is " + timeInSeconds / 1000);
             
             //Reset and Send 1KB - 64KB
             start = 0;
             diff = 0;
             timeInSeconds = 0;
             
             while (messageSize <= SIXTY_FOUR_KB) {
             
                bytes = new byte[messageSize]; // Outgoing bytes
                inBytes = new byte[messageSize]; // Incoming bytes 
                
                for(int i = 0; i < messageSize; i++)
                { 
                    bytes[i] = "a".getBytes()[0]; // Create a filled byte array
                }
                
                
                int totalData = 0;
                for (int i = 0; i < 100; i++) 
                {   
                        start = System.nanoTime(); // Determines the start time of the sending
                        out.write(bytes, 0, bytes.length); // Sends to the server
                        out.flush(); 
                        in.readFully(inBytes, 0, inBytes.length); 
                        totalData += inBytes.length; // Counts the total bytes transferred
                        diff += System.nanoTime() - start; // Determines the end time of the sending
                }
                   
                timeInSeconds = diff / 1000000000.0; // Calculates the sending time in seconds
                double throughput = (totalData * 8) / timeInSeconds; // Calculates throughput
                System.out.println( messageSize + "   "  + totalData+"  "+ timeInSeconds / 100 + "   " + throughput + "\n");  
                messageSize *= 2; // Multiplies message size by 2. (Moves on to next packet size)
            } 
            
            in.close(); // Closes the input connection
            out.close(); // Closes the outbound connection
            socket.close(); // Closes the socket
        }
        catch(Exception e)
        {
            System.out.println("Error, please try again "+ e.toString());
        }
    }
}
