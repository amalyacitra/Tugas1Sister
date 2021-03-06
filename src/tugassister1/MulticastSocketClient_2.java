/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugassister1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 *
 * @author A_CITRA
 */
public class MulticastSocketClient_2 {
    final static String INET_ADDR = "224.0.0.2";
    final static int PORT = 8888;

    public static void main(String[] args) throws UnknownHostException {
        
        InetAddress address = InetAddress.getByName(INET_ADDR);

        
        byte[] buf = new byte[256];
        
        
        try {
            MulticastSocket clientSocket = new MulticastSocket(PORT);
   
            clientSocket.joinGroup(address);

            while (true) {
                
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);

                String msg = new String(buf, 0, buf.length);
                System.out.println("Client 1 received msg: " + msg);
            }
        } catch (IOException ex) {
          ex.printStackTrace();
        }
    }
}
