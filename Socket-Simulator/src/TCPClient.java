import java.io.*;
import java.net.*;
 
public class TCPClient {
    private static DataOutputStream out;
    private static DataInputStream in;
    private static Socket socket;
    
    private static final int ONE_KB = 1024;
    private static final int TWO_KB = 2048;
    private static final int FOUR_KB = 4096;
    private static final int EIGHT_KB = 8192;
    private static final int SIXTEEN_KB = 16384;
    private static final int THIRTY_TWO_KB = 32768;
    private static final int SIXTY_FOUR_KB = 65536;
    private static byte[] bytes;
    private static byte[] inBytes;
    
    public static void main(String[] args)
    {
        try {
             socket = new Socket("localhost", 9999); //socket 
             
             System.out.println("Connection established");

             out = new DataOutputStream(socket.getOutputStream()); //Data to send OUT to Server
             System.out.println("out created");
             in = new DataInputStream(socket.getInputStream()); //Data being read IN from server
             System.out.println("in created");
             
             //1 BYTE ONLY
             byte sendByte = "a".getBytes()[0];
             long diff = 0;
             long start = 0;  
             out.write("A".getBytes()[0]); //flag
             for(int i = 0; i < 1000; i++)
             {
                 start = System.nanoTime(); 
                 out.write(sendByte);
                 out.flush();
                 in.readByte(); //received byte
                 diff += System.nanoTime() - start;
             }
             
              
             double timeInSeconds = diff / 1000000000.0;
             
             System.out.println("The size is " + " 1 byte " + " The avg RTT is " + timeInSeconds / 1000);
             
             //Reset and Send 1KB - 64KB
             start = 0;
             diff = 0;
             timeInSeconds = 0;
             int messageSize = ONE_KB; 
             
             while (messageSize <= SIXTY_FOUR_KB) {
             
                bytes = new byte[messageSize];
                inBytes = new byte[messageSize];
                //System.out.println("arrays created");
                
                /*
                 * filling the bytes array with "a"
                 */
                for(int i = 0; i < messageSize; i++)
                {
                     //System.out.println("byte array beign filled");
                    bytes[i] = "a".getBytes()[0]; 
                }
                
                
                //byte totalData = 0;
                int totalData = 0;
                
                //flags for letting server know what size to make buffer
                if(messageSize == ONE_KB)
                {
                   out.write("b".getBytes()[0]); 
                }
                else if(messageSize == TWO_KB)
                {
                   out.write("c".getBytes()[0]);
                }
                else if(messageSize == FOUR_KB)
                {
                    out.write("d".getBytes()[0]);
                }
                else if(messageSize == EIGHT_KB)
                {
                    out.write("e".getBytes()[0]);
                }
                else if(messageSize == SIXTEEN_KB)
                {
                    out.write("f".getBytes()[0]);
                }
                else if(messageSize == THIRTY_TWO_KB)
                {
                    out.write("g".getBytes()[0]);
                }
                else
                {
                    out.write("h".getBytes()[0]);
                }
                
                out.flush();
                
                for (int i = 0; i < 100; i++) 
                { 
                    start = System.nanoTime();     
                    out.write(bytes, 0, bytes.length); 
                    //out.writeByte(i);
                    //System.out.println("i " + i + " data sending out >>>>");
                    out.flush(); 
                    in.readFully(inBytes, 0, inBytes.length); 
                    totalData += inBytes[i];
                    diff += System.nanoTime() - start;
                    //System.out.println("i " + i + " data receiving in <<<<<<");
                }
                  
                
                timeInSeconds = diff / 1000000000.0;
                double throughput = (totalData * 8) / timeInSeconds;
                //double throughput = (messageSize * 100) / timeInSeconds;
                //out.print("The avg RTT is " + timeInSeconds / 1000);
                System.out.println( messageSize + "   "  + timeInSeconds / 100 + "   " + throughput + "\n"); 
                //System.out.println("message size before: " + messageSize);
                messageSize *= 2;
                //System.out.println("message size after: " + messageSize);
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
