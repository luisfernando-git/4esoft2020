package unicesumar.terceiroBimestre;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PingPongHandler extends Thread {
	
	private final Socket socket;
    
    private PrintWriter output;
    
    public PingPongHandler(Socket socket) {
        this.socket = socket;
    }
    
    public void run() {

        try {
            output = new PrintWriter(socket.getOutputStream());
            Scanner input = new Scanner(socket.getInputStream());
            String mensagem = "";

            while (!mensagem.equalsIgnoreCase("end")) {
                mensagem = input.nextLine();
                handleMensagem(mensagem);
            }

            if (mensagem.equalsIgnoreCase("end")) {
                sendMensagem("Conexão Finalizada...");
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendMensagem(String mensagem) {

        try {
            output.println(mensagem);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void handleMensagem(String mensagem) throws IOException {
        if (mensagem.equalsIgnoreCase("ping")) {
            sendMensagem("pong");
        }

        if (mensagem.equals("end")) {
        	sendMensagem("Conexão Finalizada...");
            socket.close();
        } else {
        	sendMensagem("Mensagem desconhecida...");
        }
    }
}
