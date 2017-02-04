package tugassister1;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MulticastSocketServer {

    final static String INET_ADDR = "224.0.0.2"; //inisialisasi IP address
    final static int PORT = 8888; //inisialisasi port

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        
        InetAddress addr = InetAddress.getByName(INET_ADDR); //address dimasukkan dengan nama dari INET_ADDR
        
        try  { //jika tersambung dan tidak kena exception
            DatagramSocket serverSocket = new DatagramSocket(); //inisialisasi datagramsocket
            for (int i = 0; i < 500; i++) { //perulangan sampai 500 kali
                String msg = "Sent message no " + i; //mengirim pesan sesuai iterasi i
                DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(), //membuat message datagrampacket dengan mendapatkan byte, addr dan port nya
                        msg.getBytes().length, addr, PORT);
                serverSocket.send(msgPacket); //mengirim message tersebut

                System.out.println("Server sent packet with msg: " + msg);//jika terkirim akan keluar pesan
                Thread.sleep(500);//waktu dilakukan hingga 500
            }
        } catch (IOException ex) { //jika dapat error exception
            ex.printStackTrace();
        }
    }
}
