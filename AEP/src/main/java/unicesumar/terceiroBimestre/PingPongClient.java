package unicesumar.terceiroBimestre;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class PingPongClient {
	
	private final Logger logger = Logger.getLogger(PingPongClient.class.getName());
	
	private static final int PORT = 8081;
	
	private static final String SERVER_ADDRESS = "localhost";
	 
	public static void main(String[] args) throws Exception {
       final PingPongClient client = new PingPongClient();
       client.execute();
	}
	 
	private void execute() throws Exception {

		int cont = 0;
        List<Long> temposList = new ArrayList<>();

        while (cont < 1000) {
            String comando = "ping";

            Long inicio = System.currentTimeMillis();

            handleComunicacao(comando);

            Long termino = System.currentTimeMillis();

            logger.info(String.format("Início: %s. Término: %s.", inicio, termino));
            logger.info(String.format("Média: %s.", termino - inicio));
            temposList.add(termino - inicio);
            cont++;
        }

        handleComunicacao("end");

        Collections.sort(temposList);

        Long menorTempo = temposList.get(0);
        Long maiorTempo = temposList.get(temposList.size() - 1);

        logger.info("Menor tempo: " + menorTempo);
        logger.info("Maior tempo: " + maiorTempo);

        double media = temposList.stream().mapToDouble(Long::doubleValue).average().orElse(0);

        if (media == 0) {
            logger.info("Não foi possível extrair a média.");
        }

        logger.info("Média: " + media);
    }
	
	private String handleComunicacao(String mensagem) throws IOException {

        Socket connection = new Socket(SERVER_ADDRESS, PORT);
        Scanner serverInput = new Scanner(connection.getInputStream());
        PrintWriter serverOutput = new PrintWriter(connection.getOutputStream());

        serverOutput.println(mensagem);
        serverOutput.flush();

        String response = serverInput.nextLine();

        if (Objects.isNull(response)) {
            logger.info("Resposta nula!");
            connection.close();
            return "Resposta nula!";
        }

        logger.info("Retorno: " + response);
        connection.close();
        return response;
    }
}
