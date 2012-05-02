



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author firen
 */
public class UDPServerThread extends Thread {
    private DatagramSocket socket;
    private DatagramPacket packet, packet2;
    private boolean flag = true;
    int port;
    
    public UDPServerThread() throws IOException {
        this("UPDServerThread");
    }
    
    public UDPServerThread(String clientName) throws IOException
    {
        super(clientName);
        socket = new DatagramSocket(4445);
    }
    
    public void run()
    {
        while(flag)
        {
            try {
                byte[] bytesReceived = new byte[1024];
                
                packet = new DatagramPacket(bytesReceived, bytesReceived.length);
                socket.receive(packet);
                
                String message = new String(packet.getData()); 
                System.out.println("Message received: " + message);
           }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            
            flag = false;
        }
        
        socket.close();
    }
}
