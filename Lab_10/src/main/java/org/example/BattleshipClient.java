package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BattleshipClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to server");

            Scanner input = new Scanner(socket.getInputStream());
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            String serverMessage = input.nextLine();
            System.out.println(serverMessage);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                String message = input.nextLine();
                System.out.println(message);

                if (message.startsWith("Game Over")) {
                    break;
                }

                if (message.equals("Your Turn")) {
                    System.out.print("Enter your move (row column): ");
                    String move = scanner.nextLine();
                    output.println(move);

                    if (move.equals("quit")) {
                        break;
                    }
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
