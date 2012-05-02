



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;

/**
 *
 * @author firen
 */
public class UDPServer {
    
    public static void main(String[] args) throws IOException {
        new UDPServerThread().start();
    }
}
