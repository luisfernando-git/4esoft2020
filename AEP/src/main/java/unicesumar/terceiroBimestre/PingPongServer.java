package unicesumar.terceiroBimestre;

import java.io.IOException;
import java.net.ServerSocket;

public class PingPongServer {
	
	private static final int PORT = 8081;

    public static void main(String[] args) {

        final PingPongServer server = new PingPongServer();
        server.listenClient();
    }
    
    private void listenClient() {

        try (ServerSocket socket = new ServerSocket(PORT)) {
            while (true) {
                PingPongHandler client = new PingPongHandler(socket.accept());
                client.start();

                if (client.isInterrupted() || !client.isAlive()) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
