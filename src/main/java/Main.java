
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

interface Player {
    void makeMove(char[][] board);
}


class HumanPlayer implements Player {
    char xOrO;

    HumanPlayer(char xOrO) {
        this.xOrO = xOrO;
    }

    public void makeMove(char[][] board) {
        Scanner scanner = new Scanner(System.in);
        // Marcin: Formatting
        while (true) {
            System.out.println("Enter the coordinates: ");
            try {
                enterCoords(board, Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()), xOrO);
                return;
            } catch (Exception e) {
                if (e instanceof NumberFormatException) {
                    System.out.println("You should enter numbers!");
                } else {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    void enterCoords(char[][] arr, int x, int y, char s) {
        x--;
        y--;
        if (x < 0 || x > 2 || y < 0 || y > 2) {
            throw new IllegalArgumentException("Coordinates should be from 1 to 3!");
        }
        if (arr[2 - y][x] != '_') {
            throw new IllegalArgumentException("This cell is occupied! Choose another one!");
        }
        arr[2 - y][x] = s;
    }
}


class EasyComputerPlayer implements Player {
    char xOrO;

    EasyComputerPlayer(char xOrO) {
        this.xOrO = xOrO;
    }

    public void makeMove(char[][] board) {
        Random generator = new Random();
        System.out.println("Making move level \"easy\"");
        int x = Math.abs(generator.nextInt() % 3);
        int y = Math.abs(generator.nextInt() % 3);
        while (board[x][y] != '_') {
            x = Math.abs(generator.nextInt() % 3);
            y = Math.abs(generator.nextInt() % 3);
        }
        board[x][y] = xOrO;
    }
}


class MediumComputerPlayer implements Player {
    char xOrO;

    MediumComputerPlayer(char xOrO) {
        this.xOrO = xOrO;
    }

    public void makeMove(char[][] board) {
        Random generator = new Random();
        System.out.println("Making move level \"medium\"");
        int x = Math.abs(generator.nextInt() % 3);
        int y = Math.abs(generator.nextInt() % 3);
        if (tryToWin(board) || tryNotToLose(board)) {
            return;
        }
        while (board[x][y] != '_') {
            x = Math.abs(generator.nextInt() % 3);
            y = Math.abs(generator.nextInt() % 3);
        }
        board[x][y] = xOrO;
    }

    //check if there is row or column with 2 slots with its X or O and free slot, if it is possible counter==2
    public boolean tryToWin(char[][] board) {
        int counter = 0;
        // Code is duplicated with tryToLose function
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == xOrO) {
                    counter++;
                } else if (!(board[i][j] == '_')) {
                    counter--;
                }
            }
            if (counter == 2) {
                for (int j = 0; j < 3; j++)
                    if (board[i][j] == '_') {
                        board[i][j] = xOrO;
                    }
                return true;
            }
            counter = 0;

            for (int j = 0; j < 3; j++) {
                if (board[j][i] == xOrO) {
                    counter++;
                } else if (!(board[j][i] == '_')) {
                    counter--;
                }
            }
            if (counter == 2) {
                for (int j = 0; j < 3; j++) {
                    if (board[j][i] == '_') {
                        board[j][i] = xOrO;
                    }
                }
                return true;
            }
            counter = 0;

        }
        return false;
    }

    public boolean tryNotToLose(char[][] board) {
        int counter = 0;
        char enemy;
        if (xOrO == 'X') {
            enemy = 'O';
        } else {
            enemy = 'X';
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == enemy) {
                    counter++;
                } else if (!(board[i][j] == '_')) {
                    counter--;
                }
            }
            if (counter == 2) {
                for (int j = 0; j < 3; j++)
                    if (board[i][j] == '_') {
                        board[i][j] = xOrO;
                    }
                return true;
            }
            counter = 0;

            for (int j = 0; j < 3; j++) {
                if (board[j][i] == enemy) {
                    counter++;
                } else if (!(board[j][i] == '_')) {
                    counter--;
                }
            }
            if (counter == 2) {
                for (int j = 0; j < 3; j++) {
                    if (board[j][i] == '_') {
                        board[j][i] = xOrO;
                    }
                }
                return true;
            }
            counter = 0;
        }
        return false;
    }
}

class Game {
    // Marcin: That string array could be replaced to enum
    enum PlayerType {
        easy, user, medium;
    }
    char[][] board;
    Player[] players;


    public Game() {
        this.board = setEmptyBoard(new char[3][3]);
        players = setPlayers();
    }

    Player[] setPlayers() {
        String[] userInput = getUserInput();
        Player[] players = new Player[2];
        if (userInput[0].equals("exit")) {
            throw new RuntimeException();
        } else {

            switch (userInput[1]) {
                case "user":
                    players[0] = new HumanPlayer('X');
                    break;

                case "easy":
                    players[0] = new EasyComputerPlayer('X');
                    break;
                case "medium":
                    players[0] = new MediumComputerPlayer('X');
                    break;

            }
            switch (userInput[2]) {
                case "user":
                    players[1] = new HumanPlayer('O');
                    break;
                case "easy":
                    players[1] = new EasyComputerPlayer('O');
                    break;
                case "medium":
                    players[1] = new MediumComputerPlayer('O');
                    break;
            }
        }
        return players;
    }

    static char[][] setEmptyBoard(char[][] arr) {
        int numberOfRows = 3;
        for (int i = 0; i < numberOfRows; i++) {
            Arrays.fill(arr[i], '_');
        }
        return arr;
    }

    void printGame() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                if (this.board[i][j] == '_') {
                    System.out.print("  ");
                } else {
                    System.out.print(this.board[i][j] + " ");
                }
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    static char getWinner(char[][] arr) {

        for (int i = 0; i < 3; i++) {
            if (arr[i][0] == arr[i][1] && arr[i][1] == arr[i][2] && arr[i][0] != '_')
                return arr[i][0];
            if (arr[0][i] == arr[1][i] && arr[0][i] == arr[2][i] && arr[0][i] != '_')
                return arr[0][i];
        }
        if (arr[0][0] == arr[1][1] && arr[1][1] == arr[2][2] && arr[0][0] != '_')
            return arr[0][0];

        if (arr[2][0] == arr[1][1] && arr[1][1] == arr[0][2] && arr[0][2] != '_')
            return arr[0][2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] == '_')
                    return '_';
            }
        }
        return 'D';
    }

    void playTheGame() {

        printGame();
        while (getWinner(board) == '_') {
            players[0].makeMove(board);
            printGame();
            if (getWinner(board) != '_') {
                break;
            }
            players[1].makeMove(board);
            printGame();
            if (getWinner(board) != '_') {
                break;
            }
        }
        printWinner(board);
    }

    private String[] getUserInput() {
        System.out.println("Input command:");
        Scanner scanner = new Scanner(System.in);
        String[] modeAndPlayers = scanner.nextLine().split(" ");
        while (!modeAndPlayers[0].equals("start")
                || modeAndPlayers.length != 3
                || checkIfPlayerNameIsInvalid(modeAndPlayers[1])
                || checkIfPlayerNameIsInvalid(modeAndPlayers[2])) {
            if (modeAndPlayers[0].equals("exit")) {
                return new String[]{"exit"};
            }
            System.out.println("Bad parameters!");
            modeAndPlayers = scanner.nextLine().split(" ");
        }
        return modeAndPlayers;
    }

    boolean checkIfPlayerNameIsInvalid(String player) {
        for (PlayerType p : PlayerType.values()) {
            if (p.name().equals(player)) {
                return false;
            }
        }
        return true;
    }

    static void printWinner(char[][] board) {
        if (getWinner(board) == 'X') {
            System.out.println("X wins");
        } else if (getWinner(board) == 'O') {
            System.out.println("O wins");
        } else if (getWinner(board) == 'D') {
            System.out.println("Draw");
        } else {
            System.out.println("Game not finished");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        while (true) {
            try {
                Game game = new Game();
                game.playTheGame();
            } catch (RuntimeException e) {
                break;
            }

        }
    }
}
