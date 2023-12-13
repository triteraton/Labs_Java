package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BattleshipServer {

    private static int[][] board;

    public static boolean isValidMove(int row, int column, int[][] gameBoard) {
        int numRows = gameBoard.length;
        int numColumns = gameBoard[0].length;

        // Проверка находится ли выбранная клетка в пределах игрового поля
        if (row < 0 || row >= numRows || column < 0 || column >= numColumns) {
            return false;
        }

        // Проверка, что на данной клетке еще не было сделано хода
        int cellValue = gameBoard[row][column];
        if (cellValue == 2 || cellValue == 3) {
            return false;
        }

        return true;
    }

    // Функция для обновления состояния игры после хода игрока 1
    public static void updateGameState(int row, int column, int[][] gameBoard) {
        // Проверяем содержимое выбранной клетки
        int cellValue = gameBoard[row][column];

        if (cellValue == 0) {
            // Промах
            gameBoard[row][column] = 3;
        } else if (cellValue == 1) {
            // Попадание
            gameBoard[row][column] = 2;
        }
    }

    // Функция для проверки победы игрока 1
    public static boolean checkWin(int[][] gameBoard) {
        // Проходим по всем клеткам игрового поля и проверяем,
        // что нет ни одной клетки с кораблем игрока 2 (значение 4 или 5).
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                int cellValue = gameBoard[i][j];
                if (cellValue == 4 || cellValue == 5) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server started, waiting for connections...");

            Socket player1Socket = serverSocket.accept();
            System.out.println("Player 1 connected");

            Socket player2Socket = serverSocket.accept();
            System.out.println("Player 2 connected");

            Scanner player1Input = new Scanner(player1Socket.getInputStream());
            PrintWriter player1Output = new PrintWriter(player1Socket.getOutputStream(), true);

            Scanner player2Input = new Scanner(player2Socket.getInputStream());
            PrintWriter player2Output = new PrintWriter(player2Socket.getOutputStream(), true);

            player1Output.println("You are Player 1");
            player2Output.println("You are Player 2");

            boolean player1Turn = true;
            boolean gameOver = false;
            String winner = "";

            while (!gameOver) {
                if (player1Turn) {
                    player1Output.println("Your Turn");
                    player2Output.println("Opponent's Turn");

                    String move = player1Input.nextLine();
                    player2Output.println("Opponent's Move: " + move);

                    if (move.equals("quit")) {
                        winner = "Player 2";
                        gameOver = true;
                    } else {
                        // Разбиение строки хода на координаты
                        String[] coordinates = move.split(" ");
                        int row = Integer.parseInt(coordinates[0]);
                        int column = Integer.parseInt(coordinates[1]);

                        // Проверка валидности хода
                        if (isValidMove(row, column, board)) {
                            // Обновление состояния игры и проверка победы
                            updateGameState(row, column, board);
                            if (checkWin(board)) {
                                winner = "Player 1";
                                gameOver = true;
                            }
                        } else {
                            // Неверный ход, игрок 1 теряет ход
                            player1Output.println("Invalid move. Try again.");
                            player1Turn = false;
                        }
                    }

                    player1Turn = false;
                } else {
                    player2Output.println("Your Turn");
                    player1Output.println("Opponent's Turn");

                    String move = player2Input.nextLine();
                    player1Output.println("Opponent's Move: " + move);

                    if (move.equals("quit")) {
                        winner = "Player 1";
                        gameOver = true;
                    } else {
                        // Perform move validation and update game state
                        // ...

                        // Check if player 2 won
                        // ...
                    }

                    player1Turn = true;
                }
            }

            player1Output.println("Game Over! Winner: " + winner);
            player2Output.println("Game Over! Winner: " + winner);

            player1Socket.close();
            player2Socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}