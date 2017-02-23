/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatboxserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author tkint
 */
public class ChatBoxServer {

    private boolean loop = true;

    public ChatBoxServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Ouverture du serveur");

        Socket socket = serverSocket.accept();
        System.out.println("Client accepté");

        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;

        while (loop) {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            String clientMessage = bufferedReader.readLine();
            System.out.println("Le client a écrit: " + clientMessage);
            String serverResponse = analyzeMessage(clientMessage);
            System.out.println("Le serveur répond: " + serverResponse);
            printWriter.println(serverResponse);
            printWriter.flush();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        if (printWriter != null) {
            printWriter.close();
        }
        if (socket != null) {
            socket.close();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ChatBoxServer server = new ChatBoxServer(2000);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String analyzeMessage(String message) {
        String answer = "";

        switch (message.toLowerCase()) {
            case "bonjour":
                answer = "Bonjour, bienvenue dans ce test de ChatBox";
                break;
            case "deconnexion":
                answer = "Déconnexion";
                this.loop = false;
                break;
            default:
                answer = "Je n'ai pas compris votre message.";
                break;
        }

        return answer;
    }
}
