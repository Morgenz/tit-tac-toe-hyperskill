
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

interface Player {
    public void makeMove(char[][] board);
}

class humanPlayer implements Player {
    char xOrO;

    humanPlayer(char xOrO) {
        this.xOrO = xOrO;
    }

    public void makeMove(char[][] board) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the coordinates: ");
        try {
            enterCoords(board, Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()), xOrO);
        } catch (Exception e) {
            if (e instanceof NumberFormatException)
                System.out.println("You should enter numbers!");
            else
                System.out.println(e.getMessage());
        }
    }

    void enterCoords(char[][] arr, int x, int y, char s) {
        x--;
        y--;
        if (x < 0 || x > 2 || y < 0 || y > 2)
            throw new IllegalArgumentException("Coordinates should be from 1 to 3!");
        if (arr[2 - y][x] != '_')
            throw new IllegalArgumentException("This cell is occupied! Choose another one!");
        arr[2 - y][x] = s;
    }
}

class easyComputerPlayer implements Player {
    char xOrO;

    easyComputerPlayer(char xOrO) {
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

class Game {
    static String[] typeOfPlayers = {"easy", "user"};
    char[][] board;
    static String mode="0";
    Player player1;
    Player player2;

    public static String getMode() {
        return mode;
    }

    public Game() {
        this.board = setEmptyBoard(new char[3][3]);
        String[] userInput = getUserInput();
        if (userInput[0] != "exit") {
            this.mode = userInput[0];
            switch (userInput[1]) {
                case "user":
                    player1 = new humanPlayer('X');
                    break;

                case "easy":
                    player1 = new easyComputerPlayer('X');
                    break;
            }
            switch (userInput[2]) {
                case "user":
                    player2 = new humanPlayer('O');
                    break;
                case "easy":
                    player2 = new easyComputerPlayer('O');
                    break;
            }
        } else {
            this.mode = "exit";
        }
    }

    static char[][] setEmptyBoard(char[][] arr) {
        for (int i = 0; i < 3; i++) {
            Arrays.fill(arr[i], '_');
        }
        return arr;
    }

    void printGame() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++)
                if (this.board[i][j] == '_')
                    System.out.print("  ");
                else
                    System.out.print(this.board[i][j] + " ");
            System.out.println("|");
        }
        System.out.println("---------");
    }

    static char getWinner(char[][] arr) {

        for (int i = 0; i < 3; i++) {
            //poziomo
            if (arr[i][0] == arr[i][1] && arr[i][1] == arr[i][2] && arr[i][0] != '_')
                return arr[i][0];

            //pionowo
            if (arr[0][i] == arr[1][i] && arr[0][i] == arr[2][i] && arr[0][i] != '_')
                return arr[0][i];
        }
        if (arr[0][0] == arr[1][1] && arr[1][1] == arr[2][2] && arr[0][0] != '_')
            return arr[0][0];

        if (arr[2][0] == arr[1][1] && arr[1][1] == arr[0][2] && arr[0][2] != '_')
            return arr[0][2];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] == '_')
                    return '_';
            }
        return 'D';
    }

    void playTheGame() {
        if (this.mode.equals("exit")) {
            return;
        }
        printGame();
        while (getWinner(board) == '_') {
            player1.makeMove(board);
            printGame();
            if (getWinner(board) != '_')
                break;
            player2.makeMove(board);
            printGame();
            if (getWinner(board) != '_')
                break;
        }
        printWinner(board);
    }

    private String[] getUserInput() {
        System.out.println("Input command:");
        Scanner scanner = new Scanner(System.in);
        String[] modeAndPlayers = scanner.nextLine().split(" ");
        while (!modeAndPlayers[0].equals("start")
                || modeAndPlayers.length != 3
                || !Arrays.asList(typeOfPlayers).containsAll(Arrays.asList(modeAndPlayers[1], modeAndPlayers[2]))) {
            if (modeAndPlayers[0].equals("exit"))
                return new String[]{"exit"};
            System.out.println("Bad parameters!");
            modeAndPlayers = scanner.nextLine().split(" ");
        }
        return modeAndPlayers;
    }

    static void printWinner(char[][] board) {
        if (getWinner(board) == 'X') {
            System.out.println("X wins");
        } else if (getWinner(board) == 'O')
            System.out.println("O wins");
        else if (getWinner(board) == 'D')
            System.out.println("Draw");
        else
            System.out.println("Game not finished");
    }
}

public class Main {
    public static void main(String[] args) {
        while (!Game.getMode().equals("exit")) {
            Game game = new Game();
            game.playTheGame();
        }
    }
}
