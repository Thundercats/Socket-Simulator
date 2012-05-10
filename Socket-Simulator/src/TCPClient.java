import java.io.*;
import java.net.*;
 
public class TCPClient {
    
    public static void main(String[] args)
    {
	String host = "localhost";
	if(args.length>0)
	    host=arg[0];
        try {
            Socket socket = new Socket(host, 9999); //socket
            int i = 0;
            System.out.println("Connection established");
            
            DataInputStream in =
                    new DataInputStream(socket.getInputStream());
            
            System.out.println(i + " from server: " + in.readLine());
            
            //DataOutputStream out = 
            //        new DataOutputStream(socket.getOutputStream());//out
            //BufferedReader in = 
                    //new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            in.close();
            //socket.close();
        }
        catch(Exception e)
        {
            System.out.println("We gots an error "+ e.toString());
        }
    }
}
