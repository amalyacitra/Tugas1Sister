package tugassister1;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastSocketClient_1 {

    final static String INET_ADDR = "224.0.0.2";//inisialisasi IP address
    final static int PORT = 8888;//inisialisasi port

    public static void main(String[] args) throws UnknownHostException {
        
        InetAddress address = InetAddress.getByName(INET_ADDR); //address dimasukkan dengan nama dari INET_ADDR

        
        byte[] buf = new byte[256]; //inisialisasi variabel array byte sebanyak 256
        
        
        try {//jika tersambung dan tidak kena exception
            MulticastSocket clientSocket = new MulticastSocket(PORT); //inisialisasi variabel client dengan multicastsocket port
   
            clientSocket.joinGroup(address); //client masuk grup dengan address

            while (true) { //selama kondisinya true lakukan hal berikut
                
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);//membuat messagepacket dengan data array byte dengan panjang array
                clientSocket.receive(msgPacket);//client menerima messagepacket yang didapat

                String msg = new String(buf, 0, buf.length);//message dibuat dengan parameter array byte dengan panjang array
                System.out.println("Client 1 received msg: " + msg);
            }
        } catch (IOException ex) { //jika terkena exception error
          ex.printStackTrace();
        }
    }
}
