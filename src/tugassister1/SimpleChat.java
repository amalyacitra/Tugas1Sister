package tugassister1;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import java.io.*;
import java.util.List;
import java.util.LinkedList;

public class SimpleChat extends ReceiverAdapter { //adapter mengimplementasikan receiver
    JChannel channel; //inisialisasi channel yang merupakan stack protokol
    String user_name=System.getProperty("user.name", "n/a"); //inisialisasi username
    final List<String> state=new LinkedList<String>(); //list untuk state

    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view); //untuk mengoutputkan view yang diterima saat running
    }

    public void receive(Message msg) {
        String line=msg.getSrc() + ": " + msg.getObject(); //menerima message yang masuk dan dimasukkan ke variabel line
        System.out.println(line); //mengoutputkan message
        synchronized(state) {
            state.add(line); //message ditambahkan ke dalam list status
        }
    }

    public void getState(OutputStream output) throws Exception {
        synchronized(state) {
            Util.objectToStream(state, new DataOutputStream(output));//mendapatkan state dari outputstream
        }
    }

    @SuppressWarnings("unchecked")
    public void setState(InputStream input) throws Exception {
        List<String> list=(List<String>)Util.objectFromStream(new DataInputStream(input)); //memasukkan state dari input user
        synchronized(state) {
            state.clear(); //mengosongkan state
            state.addAll(list); //menambahkan list ke dalam state
        }
        System.out.println("received state (" + list.size() + " messages in chat history):"); //mengoutputkan ukuran state yang diterima
        for(String str: list) { 
            System.out.println(str); //mengoutputkan isi list
        }
    }


    private void start() throws Exception {
        channel=new JChannel(); //inisialisasi channel
        channel.setReceiver(this); //set receiver channel
        channel.connect("ChatCluster"); //menghubungkan channel
        channel.getState(null, 10000); //membuat state dengan inisialisasi 10.000
        eventLoop(); //melakukan prosedur eventloop
        channel.close(); //menutup channel
    }

    private void eventLoop() {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in)); //inisialisasi variabel input dari user
        while(true) { //jika keadaan benar
            try { //jika tidak terjadi error
                System.out.print("> "); System.out.flush(); //mengoutputkan >
                String line=in.readLine().toLowerCase(); //menginputkan inputan user dan diconvert jadi lowecase kedalam variabel line
                if(line.startsWith("quit") || line.startsWith("exit")) {
                    break; //jika line berisi quit/exit maka berhenti
                }
                line="[" + user_name + "] " + line; //menampilkan nama user dengan pesannya
                Message msg=new Message(null, null, line); //inisialisasi message dengan memasukkan pesan ke variabel msg
                channel.send(msg); //channel mengirimkan pesan tersebut
            }
            catch(Exception e) { //jika terjadi error
            }
        }
    }


    public static void main(String[] args) throws Exception {
        new SimpleChat().start(); //inisialisasi simplechat dengan melakukan prosedur start
    }
}
