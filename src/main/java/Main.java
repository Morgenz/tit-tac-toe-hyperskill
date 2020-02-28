
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    char[][] board = new char[3][3];
    static String[] typeOfPlayers = {"easy", "user"};

    static void printGame(char[][] arr) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++)
                if (arr[i][j] == '_')
                    System.out.print("  ");
                else
                    System.out.print(arr[i][j] + " ");
            System.out.println("|");
        }
        System.out.println("---------");
    }

    static void fillGame(char[][] arr, String game) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                arr[i][j] = game.charAt(i * 3 + j);
    }

    static void enterCoords(char[][] arr, int x, int y, char s) {
        x--;
        y--;
        if (x < 0 || x > 2 || y < 0 || y > 2)
            throw new IllegalArgumentException("Coordinates should be from 1 to 3!");
        if (arr[2 - y][x] != '_')
            throw new IllegalArgumentException("This cell is occupied! Choose another one!");
        arr[2 - y][x] = s;
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

    static void makeComputerMoveEasyDiff(char[][] arr, char typeOfPlayer) {
        Random generator = new Random();
        System.out.println("Making move level \"easy\"");
        int x = Math.abs(generator.nextInt() % 3);
        int y = Math.abs(generator.nextInt() % 3);
        while (arr[x][y] != '_') {
            x = Math.abs(generator.nextInt() % 3);
            y = Math.abs(generator.nextInt() % 3);
        }
        arr[x][y] = typeOfPlayer;
    }

    static void setEmptyBoard(char[][] arr) {
        for (int i = 0; i < 3; i++) {
            Arrays.fill(arr[i], '_');
        }
    }

    static void makePlayerMove(char [][]board, char xOrO) {
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


    static boolean playTheGame(char [][] board) {
        System.out.println("Input command:");
        Scanner scanner = new Scanner(System.in);
        String[] modeAndPlayers = scanner.nextLine().split(" ");

        while (!modeAndPlayers[0].equals("start")
                || modeAndPlayers.length != 3
                || !Arrays.asList(typeOfPlayers).containsAll(Arrays.asList(modeAndPlayers[1], modeAndPlayers[2]))) {
            if (modeAndPlayers[0].equals("exit"))
                return false;
            System.out.println("Bad parameters!");
            modeAndPlayers = scanner.nextLine().split(" ");
        }
        String player1=modeAndPlayers[1];
        String player2=modeAndPlayers[2];


        while (getWinner(board) == '_') {
            if (player1.equals("user")) {
                makePlayerMove(board, 'X');
            } else {
                makeComputerMoveEasyDiff(board, 'X');
            }
            printGame(board);
            if (getWinner(board) != '_')
                break;
            if (player2.equals("user")) {
                makePlayerMove(board, 'O');
            } else {
                makeComputerMoveEasyDiff(board, 'O');
            }
            printGame(board);
            if (getWinner(board) != '_')
                break;

        }
        printWinner(board);
        setEmptyBoard(board);
        return true;
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

    public static void main(String[] args) {
        Main b = new Main();
        char[][] board = b.board;
        Scanner scanner = new Scanner(System.in);
        setEmptyBoard(board);

        while (playTheGame(board)) ;


    }
}
