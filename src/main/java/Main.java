
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

interface Player {
    // Marcin: Methods in interfaces are public by default
    public void makeMove(char[][] board);
}

// Marcin: Classes should start with capital letter
class humanPlayer implements Player {
    char xOrO;

    humanPlayer(char xOrO) {
        this.xOrO = xOrO;
    }

    public void makeMove(char[][] board) {
        Scanner scanner = new Scanner(System.in);

        // Marcin: Formatting
        while(true){
            System.out.println("Enter the coordinates: ");
        try {
            // Marcin: Integer.parseInt logic should be extracted to variable
            enterCoords(board, Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()), xOrO);
            return;
        } catch (Exception e) {
            if (e instanceof NumberFormatException) // Marcin: Brackets
                System.out.println("You should enter numbers!");
            else // Marcin: Brackets
                System.out.println(e.getMessage());
        }
        }
    }

    void enterCoords(char[][] arr, int x, int y, char s) {
        x--;
        y--;
        if (x < 0 || x > 2 || y < 0 || y > 2) // Marcin: Brackets
            throw new IllegalArgumentException("Coordinates should be from 1 to 3!");
        if (arr[2 - y][x] != '_') // Marcin: Brackets
            throw new IllegalArgumentException("This cell is occupied! Choose another one!");
        arr[2 - y][x] = s;
    }
}

// Classes should start with capital letter
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

// Classes should start with capital letter
class mediumComputerPlayer implements Player {
    char xOrO;

    mediumComputerPlayer(char xOrO) {
        this.xOrO = xOrO;
    }

    public void makeMove(char[][] board) {
        Random generator = new Random();
        System.out.println("Making move level \"medium\"");
        int x = Math.abs(generator.nextInt() % 3);
        int y = Math.abs(generator.nextInt() % 3);
        // formatting
        if(tryToWin(board)||tryNotToLose(board)){ // Marcin: Formatting
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
                if (board[i][j] == xOrO) // Marcin: Brackets
                    counter++;
                else if (!(board[i][j] == '_')) {
                    counter--;
                }
            }
            if (counter == 2) {
                for (int j = 0; j < 3; j++)
                    if (board[i][j] == '_') // Marcin: Brackets
                        board[i][j] = xOrO;
               return true;
            }
            counter=0;

            for (int j = 0; j < 3; j++) {
                if (board[j][i] == xOrO) // Marcin: Brackets
                    counter++;
                else if (!(board[j][i] == '_')) {
                    counter--;
                }
            }
            if (counter == 2) {
                for (int j = 0; j < 3; j++) // Marcin: Brackets
                    if (board[j][i] == '_') // Marcin: Brackets
                        board[j][i] = xOrO;
                return true;
            }
            counter=0;

        }
    return false;
    }
    public boolean tryNotToLose(char[][] board) {
        int counter = 0;
        char enemy;
        if(xOrO=='X')  // Marcin: Brackets
            enemy='O';
        else // Marcin: Brackets
            enemy='X';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == enemy) // Marcin: Brackets
                    counter++;
                else if (!(board[i][j] == '_')) {
                    counter--;
                }
            }
            if (counter == 2) {
                for (int j = 0; j < 3; j++)
                    if (board[i][j] == '_') // Marcin: Brackets
                        board[i][j] = xOrO;
                return true;
            }
            counter=0;

            for (int j = 0; j < 3; j++) {
                if (board[j][i] == enemy) // Marcin: Brackets
                    counter++;
                else if (!(board[j][i] == '_')) {
                    counter--;
                }
            }
            if (counter == 2) {
                for (int j = 0; j < 3; j++)  // Marcin: Brackets
                    if (board[j][i] == '_') // Marcin: Brackets
                        board[j][i] = xOrO;
                return true;
            }
            counter=0;

        }
        return false;
    }
}

class Game {
    // Marcin: That string array could be replaced to enum
    static String[] typeOfPlayers = {"easy", "user","medium"};
    char[][] board;
    // Marcin: Mode static variable can be replaced with throwing exception and handling it in main function :)
    static String mode = "0";
    Player player1;
    Player player2;

    public static String getMode() {
        return mode;
    }

    public Game() {
        // Marcin: Constructor should not have any logic
        this.board = setEmptyBoard(new char[3][3]);
        String[] userInput = getUserInput();
        if (!userInput[0] .equals( "exit")) {
            mode = userInput[0];
            // Marcin: This logic can be extracted to method
            switch (userInput[1]) {
                case "user":
                    player1 = new humanPlayer('X');
                    break;

                case "easy":
                    player1 = new easyComputerPlayer('X');
                    break;
                case "medium":
                    player1 = new mediumComputerPlayer('X');
                    break;

            }
            switch (userInput[2]) {
                case "user":
                    player2 = new humanPlayer('O');
                    break;
                case "easy":
                    player2 = new easyComputerPlayer('O');
                    break;
                case "medium":
                    player2 = new mediumComputerPlayer('O');
                    break;
            }
        } else {
            mode = "exit";
        }
    }

    static char[][] setEmptyBoard(char[][] arr) {
        // Marcin: Number '3' can be extracted to variable. It is a magic number right now
        for (int i = 0; i < 3; i++) {
            Arrays.fill(arr[i], '_');
        }
        return arr;
    }

    void printGame() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++)  // Marcin: Brackets
                if (this.board[i][j] == '_') // Marcin: Brackets
                    System.out.print("  ");
                else // Marcin: Brackets
                    System.out.print(this.board[i][j] + " ");
            System.out.println("|");
        }
        System.out.println("---------");
    }

    static char getWinner(char[][] arr) {

        for (int i = 0; i < 3; i++) {
            //poziomo // Marcin: Is that comment necessary? It can be moved to method :)
            if (arr[i][0] == arr[i][1] && arr[i][1] == arr[i][2] && arr[i][0] != '_')
                return arr[i][0];
            //pionowo // Marcin: Is that comment necessary? It can be moved to method :)
            if (arr[0][i] == arr[1][i] && arr[0][i] == arr[2][i] && arr[0][i] != '_')
                return arr[0][i];
        }
        if (arr[0][0] == arr[1][1] && arr[1][1] == arr[2][2] && arr[0][0] != '_')
            return arr[0][0];

        if (arr[2][0] == arr[1][1] && arr[1][1] == arr[0][2] && arr[0][2] != '_')
            return arr[0][2];
        for (int i = 0; i < 3; i++)  // Marcin: Brackets
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] == '_')
                    return '_';
            }
        return 'D';
    }

    void playTheGame() {
        if (mode.equals("exit")) {
            return;
        }
        printGame();
        while (getWinner(board) == '_') {
            player1.makeMove(board);
            printGame();
            if (getWinner(board) != '_') // Marcin: Brackets
                break;
            player2.makeMove(board);
            printGame();
            if (getWinner(board) != '_') // Marcin: Brackets
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
            if (modeAndPlayers[0].equals("exit")) // Marcin: Brackets
                return new String[]{"exit"};
            System.out.println("Bad parameters!");
            modeAndPlayers = scanner.nextLine().split(" ");
        }
        return modeAndPlayers;
    }

    static void printWinner(char[][] board) {
        if (getWinner(board) == 'X') {
            System.out.println("X wins");
        } else if (getWinner(board) == 'O')  // Marcin: Brackets
            System.out.println("O wins");
        else if (getWinner(board) == 'D')  // Marcin: Brackets
            System.out.println("Draw");
        else  // Marcin: Brackets
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
