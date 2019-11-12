import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    // Convert input string to char array[3][3]
    private static char[][] createCharArray() {
        char[][] symbols = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                symbols[i][j] = '_';
            }
        }
        return symbols;
    }

    private static void printDoubleArray(char[][] array) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(j == 2 ? array[i][j] + " |\n" : array[i][j] + " ");
            }
        }
        System.out.println("---------");
    }

    private static boolean isVertical(char[][] array, char ch) {
        boolean vertical0 = array[0][0] == ch && (array[0][0] == array[1][0] && array[2][0] == array[1][0]);
        boolean vertical1 = array[0][1] == ch && (array[0][1] == array[1][1] && array[2][1] == array[1][1]);
        boolean vertical2 = array[0][2] == ch && (array[0][2] == array[1][2] && array[2][2] == array[1][2]);

        return vertical0 || vertical1 || vertical2;
    }

    private static boolean isHorizontal(char[][] array, char ch) {
        boolean horizontal0 = array[0][0] == ch && (array[0][0] == array[0][1] && array[0][2] == array[0][1]);
        boolean horizontal1 = array[1][0] == ch && (array[1][0] == array[1][1] && array[1][2] == array[1][1]);
        boolean horizontal2 = array[2][0] == ch && (array[2][0] == array[2][1] && array[2][2] == array[2][1]);

        return horizontal0 || horizontal1 || horizontal2;
    }

    private static boolean isDiagonal(char[][] array, char ch) {
        boolean diagonalLeftToRight = array[0][0] == ch && (array[0][0] == array[1][1] && array[1][1] == array[2][2]);
        boolean diagonalRightToLeft = array[0][2] == ch && (array[0][2] == array[1][1] && array[1][1] == array[2][0]);

        return diagonalLeftToRight || diagonalRightToLeft;
    }

    private static boolean checkWinner (char[][] array) {
        boolean xWins = isVertical(array, 'X') || isHorizontal(array, 'X') || isDiagonal(array, 'X');
        boolean oWins = isVertical(array, 'O') || isHorizontal(array, 'O') || isDiagonal(array, 'O');
        return xWins || oWins;
    }

    private static boolean checkGameState(char[][] array) {
        if (checkWinner(array)) {
            return false; // Game is finished, return false to break a Main loop
        }
        else {
            return Arrays.deepToString(array).contains("_"); // If there are empty slots on a field, the game will continue
        }
    }

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        char[][] symbols = createCharArray();

        printDoubleArray(symbols);

        boolean isNotFinished = true; // Flag, checks the game is not finished yet
        boolean isWaitingForInput = true; // Flag, control the while-loop waiting for correct input
        int i, j;
        int turnCounter = 0;

        // MAIN game loop
        while (isNotFinished) {

            while (isWaitingForInput) { // Input loop
                System.out.print("Enter coordinates: ");
                try {
                    String[] tempStr = reader.readLine().trim().split(" ");
                    j = Integer.parseInt(tempStr[0]);
                    i = Integer.parseInt(tempStr[1]);
                    if (j > 0 && j < 4 && i > 0 && i < 4) {
                        j = j - 1;
                        i = Math.abs(i - 3);
                        if (symbols[i][j] == '_') {
                            turnCounter++;
                            symbols[i][j] = turnCounter % 2 == 0 ? 'O' : 'X';
                            isWaitingForInput = false;
                            printDoubleArray(symbols);
                        } else {
                            System.out.println("This cell is occupied! Choose another one!");
                        }
                    } else {
                        System.out.println("Coordinates should be from 1 to 3!");
                    }
                } catch (Exception e) {
                    System.out.println("You should enter numbers!");
                }
            }

            isNotFinished = checkGameState(symbols);
            isWaitingForInput = true;
        }

        if (checkWinner(symbols)) {
            char winner = turnCounter % 2 == 0 ? 'O' : 'X';
            System.out.println(winner + " wins");
        }
        else {
            System.out.println("Draw");
        }
    }
}
