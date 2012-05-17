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
	System.out.println("Connecting to "+host +":"+port);
        try {
             socket = new Socket(host, port); //socket 
		if(args.length>=3)
		{
			if(args[2].equals("timeout")){
				socket.setSoTimeout(1003); // FIRST line
				System.out.println("Timeout set to 1003 ms");
			}
			if(args[2].equals("fast")){
           	 		socket.setPerformancePreferences(0, 1, 2); // SECOND line
				System.out.println("Sets the performance preference wtih high bandwidth to be prioritized above low latency, "
                                        + "low latency above short connection time ");
			}
			if(args[2].equals("slow")){
            	 		socket.setPerformancePreferences(1, 0, 0); // THIRD line
				System.out.println("sets the performance perference with short connection time "
                                        + "to be prioritized above high bandwidth and low latency ");
			}
			//default: System.out.println("Invalid option supplied. No modifications made");
		}
             
             System.out.println("Connection established");

             out = new DataOutputStream(socket.getOutputStream()); //Data to send OUT to Server
             System.out.println("out created");
             in = new DataInputStream(socket.getInputStream()); //Data being read IN from server
             System.out.println("in created");
             int messageSize = ONE_KB;  
             
             //1 BYTE ONLY
             byte sendByte = "a".getBytes()[0];
             byte receivedByte;
             
             long start, diff = 0;   
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
             
                bytes = new byte[messageSize];
                inBytes = new byte[messageSize]; 
                /*
                 * filling the bytes array with "a"
                 */
                for(int i = 0; i < messageSize; i++)
                { 
                    bytes[i] = "a".getBytes()[0]; 
                }
                
                
                int totalData = 0;
                for (int i = 0; i < 100; i++) 
                {   
                        start = System.nanoTime(); 
                        out.write(bytes, 0, bytes.length);  
                        out.flush(); 
                        in.readFully(inBytes, 0, inBytes.length); 
                        totalData += inBytes.length; 
                        diff += System.nanoTime() - start;
                }
                   
                timeInSeconds = diff / 1000000000.0;
                double throughput = (totalData * 8) / timeInSeconds; 
                System.out.println( messageSize + "   "  + totalData+"  "+ timeInSeconds / 100 + "   " + throughput + "\n");  
                messageSize *= 2; 
            } 
            
            in.close();
            out.close();
            socket.close();
        }
        catch(Exception e)
        {
            System.out.println("Error, please try again "+ e.toString());
        }
    }
}
