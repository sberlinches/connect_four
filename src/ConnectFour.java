import java.util.Scanner;

/**
 * Connect Four
 * Is a two-player connection game in which the players first choose a color and then take turns dropping colored discs
 * from the top into a seven-column, six-row vertically suspended grid. The pieces fall straight down, occupying the
 * next available space within the column. The objective of the game is to connect four of one’s own discs of the same
 * color next to each other vertically, horizontally, or diagonally before your opponent.
 *
 * @author  Ayaka Adachi [ID:, Sec:005]
 * @author  Sergio Berlinches [ID:100291288, Sec:005]
 * @since   2017-03-25
 */
public class ConnectFour {

    private static final int COLUMNS                    = 7;
    private static final int COLUMNS_AND_PIPES          = COLUMNS * 2 + 1;
    private static final int ROWS                       = 6;
    private static final int MATRIX_EMPTY_ID            = 0;
    private static final int MATRIX_PIPE_ID             = 1;
    private static final int YELLOW_PLAYER_ID           = 2;
    private static final int RED_PLAYER_ID              = 3;
    private static final int WINNER_ID                  = 4;
    private static final char MATRIX_EMPTY_DECO         = ' ';
    private static final char MATRIX_PIPE_DECO          = '|';
    private static final char YELLOW_PLAYER_DECO        = 'Y';
    private static final char RED_PLAYER_DECO           = 'R';
    private static final char WINNER_PLAYER_DECO        = '$';
    private static final String YELLOW_PLAYER_NAME      = "yellow";
    private static final String RED_PLAYER_NAME         = "red";
    private static final String MOVE_MSG                = "Select an empty column (0-6) to drop a %s disk into:";
    private static final String INVALID_MOVE_MSG        = "Sorry, you have entered an invalid column number.";
    private static final String WINNER_MSG              = "Congratulations, the %s player wins!!!";
    private static Scanner scanner                      = new Scanner(System.in);
    private static int[][] MATRIX                       = createMatrix();
    private static int TURN                             = whoIsFirst();

    /**
     * @param arg arguments
     */
    public static void main(String[] arg) {

        do {
            printMatrix();
            promptForMove();
            swapTurn();
        } while(!isWinner());

        swapTurn();
        printMatrix();
        printWinner();
    }

    /**
     * This method initializes the matrix.
     *
     * @return The generated matrix.
     */
    private static int[][] createMatrix() {

        // 1. Initializes the matrix.
        // [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0] x6
        int[][] matrix = new int[ROWS][COLUMNS_AND_PIPES];

        // 2. Fills the matrix interchanging between the references of whitespaces and lines.
        // [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1] x6
        for(int[] row: matrix) {
            for(int col = 0; col < row.length; col++){
                row[col] = (isEven(col))? MATRIX_PIPE_ID: MATRIX_EMPTY_ID;
            }
        }

        return matrix;
    }

    /**
     * This method randomly determines who is first at the beginning of the game.
     * 
     * @return The player ID who starts the game.
     */
    private static int whoIsFirst() {
        return randomInteger(YELLOW_PLAYER_ID, RED_PLAYER_ID);
    }

    /**
     * This method generates a random integer within the provided range.
     *
     * @param min   Minimum value in the range.
     * @param max   Maximum value in the range.
     * @return      Random integer within min and max.
     */
    private static int randomInteger(int min, int max) {

        // random() method returns a random number between 0.0 and 0.999.
        // Multiplying by 4, gets a number in the range of 0.0 and 4.999.
        // Subtracting by 2 the multiplier and add it again to the result of the multiplication,
        // gets a number in the range of 2.0 and 4.999.
        return (int) Math.round(Math.random() * (max - min) + min);
    }

    /**
     * This method reads the matrix global variable and prints it on screen.
     */
    private static void printMatrix() {

        // 1. Iterates over the matrix reading row by row and column by column.
        for(int[] row: MATRIX) {
            for(int col: row) {
                // 2. Switches the references by the final decorations.
                switch (col) {
                    case MATRIX_EMPTY_ID:
                        System.out.print(MATRIX_EMPTY_DECO);
                        break;
                    case MATRIX_PIPE_ID:
                        System.out.print(MATRIX_PIPE_DECO);
                        break;
                    case YELLOW_PLAYER_ID:
                        System.out.print(colorize(YELLOW_PLAYER_DECO, YELLOW_PLAYER_NAME));
                        break;
                    case RED_PLAYER_ID:
                        System.out.print(colorize(RED_PLAYER_DECO, RED_PLAYER_NAME));
                        break;
                    case WINNER_ID:
                        System.out.print(WINNER_PLAYER_DECO);
                        break;
                }
            }
            System.out.println();
        }
    }

    /**
     * This method prompts the user for a move.
     */
    private static void promptForMove() {

        int move;

        // 1. Prompt for a move until the user enter a valid move.
        do {
            System.out.println(String.format(MOVE_MSG, getPlayerName()));
            move = scanner.nextInt();
        } while (!isValidMove(move));

        // 2. submit the move to the matrix.
        submitMove(move);
    }

    /**
     * This method checks if the entered move is valid.
     *
     * @param move  Move to be evaluated.
     * @return      If the move is valid or not.
     */
    private static boolean isValidMove(int move) {

        // 1. Check if the move is within the column range.
        // TODO check the if it's within the row range.
        boolean isValidMove = (move >= 0 && move <= 6);

        // 2. If It's an invalid move, prints a message.
        if(!isValidMove) System.out.println(INVALID_MOVE_MSG);

        return isValidMove;
    }

    /**
     * This method submits the introduced move into its position on the matrix.
     *
     * @param move Introduced move.
     */
    private static void submitMove(int move){

        // 1. Adjusts the move to fit with the whitespaces of the matrix
        // move = 0 * 2 + 1 = 1
        // [1, X, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1]
        move = move * 2 + 1;

        // 2. Iterates from the bottom of the matrix to the top
        for(int row = ROWS - 1; row >= 0; row--) {
            // 2.1 If the requested position is empty, fill it with the current player ID, and stop the iteration.
            if(MATRIX[row][move] == MATRIX_EMPTY_ID){
                MATRIX[row][move] = getPlayerID();
                break;
            }
        }
    }

    /**
     * This method swaps the turn from one player to another
     */
    private static void swapTurn() {
        // 1. If the yellow's player turn, changes to the red.
        TURN = (TURN == YELLOW_PLAYER_ID)? RED_PLAYER_ID: YELLOW_PLAYER_ID;
    }

    /**
     * This method determines and returns after each move, if the current player is winner or not.
     *
     * @return True is the current player is winner.
     */
    private static boolean isWinner() {
        // TODO: Each of these methods must submit with positions are the winners.
        return (checkRows() || checkColumns() || checkDiagonals());
    }

    /**
     * This method check every row, looking for four consecutive IDs of the same player.
     *
     * @return True if there are four consecutive IDs of the same player.
     */
    private static boolean checkRows() {
        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLUMNS; col += 2){
                if(
                    (MATRIX[row][col+1] != MATRIX_EMPTY_ID)&&
                    (MATRIX[row][col+3] != MATRIX_EMPTY_ID)&&
                    (MATRIX[row][col+5] != MATRIX_EMPTY_ID)&&
                    (MATRIX[row][col+7] != MATRIX_EMPTY_ID)&&
                    (
                        (MATRIX[row][col+1] == MATRIX[row][col+3])&&
                        (MATRIX[row][col+3] == MATRIX[row][col+5])&&
                        (MATRIX[row][col+5] == MATRIX[row][col+7])
                    ))
                    return true;
            }
        }
        return false;
    }

    /**
     * This method check every column, looking for four consecutive IDs of the same player.
     *
     * @return True if there are four consecutive IDs of the same player.
     */
    private static boolean checkColumns() {
        for(int col = 1; col < 15; col += 2){
            for(int row = 0; row < 3; row++){
                if(
                    (MATRIX[row][col] != MATRIX_EMPTY_ID)&&
                    (MATRIX[row+1][col] != MATRIX_EMPTY_ID)&&
                    (MATRIX[row+2][col] != MATRIX_EMPTY_ID)&&
                    (MATRIX[row+3][col] != MATRIX_EMPTY_ID)&&
                    (
                        (MATRIX[row][col] == MATRIX[row+1][col])&&
                        (MATRIX[row+1][col] == MATRIX[row+2][col])&&
                        (MATRIX[row+2][col] == MATRIX[row+3][col])
                    ))
                    return true;
            }
        }
        return false;
    }

    /**
     * This method check every diagonal, looking for four consecutive IDs of the same player.
     *
     * @return True if there are four consecutive IDs of the same player.
     */
    private static boolean checkDiagonals() {
        for(int row = 0; row < 3; row++){
            for(int col = 1; col < 9; col += 2){
                if(
                    (MATRIX[row][col] != MATRIX_EMPTY_ID)&&
                    (MATRIX[row+1][col+2] != MATRIX_EMPTY_ID)&&
                    (MATRIX[row+2][col+4] != MATRIX_EMPTY_ID)&&
                    (MATRIX[row+3][col+6] != MATRIX_EMPTY_ID)&&
                    (
                        (MATRIX[row][col] == MATRIX[row+1][col+2])&&
                        (MATRIX[row+1][col+2] == MATRIX[row+2][col+4])&&
                        (MATRIX[row+2][col+4] == MATRIX[row+3][col+6])
                    ))
                    return true;
            }
        }
        for(int row = 0; row < 3; row++){
            for(int col = COLUMNS; col < COLUMNS_AND_PIPES; col += 2){
                if(
                    (MATRIX[row][col] != MATRIX_EMPTY_ID)&&
                    (MATRIX[row+1][col-2] != MATRIX_EMPTY_ID)&&
                    (MATRIX[row+2][col-4] != MATRIX_EMPTY_ID)&&
                    (MATRIX[row+3][col-6] !=MATRIX_EMPTY_ID)&&
                    (
                        (MATRIX[row][col] == MATRIX[row+1][col-2])&&
                        (MATRIX[row+1][col-2] == MATRIX[row+2][col-4])&&
                        (MATRIX[row+2][col-4] == MATRIX[row+3][col-6])
                    ))
                    return true;
            }
        }
        return false;
    }

    /**
     * This method prints the winner message.
     */
    private static void printWinner() {
        System.out.printf(WINNER_MSG, getPlayerName());
    }

    /**
     * This method, according with the current turn, returns the player name.
     *
     * @return The player name.
     */
    private static String getPlayerName() {
        return  (TURN == YELLOW_PLAYER_ID)? YELLOW_PLAYER_NAME: RED_PLAYER_NAME;
    }

    /**
     * This method, according with the current turn, returns the player ID.
     *
     * @return The player ID.
     */
    private static int getPlayerID() {
        return  (TURN == YELLOW_PLAYER_ID)? YELLOW_PLAYER_ID: RED_PLAYER_ID;
    }

    /**
     * This method determines and returns if the number introduced is even or not
     *
     * @param num   The number to be evaluated.
     * @return      True if the number is even.
     */
    private static boolean isEven(int num) {
        return (num % 2 == 0);
    }

    /**
     * This method changes the font color.
     * 
     * @param input The character.
     * @param color The desired color [red, yellow]
     * @return      The character with the color changed.
     */
    private static String colorize(char input, String color) {

        int colorId;

        switch (color) {
            case "red":
                colorId = 31;
                break;
            case "yellow":
            default:
                colorId = 33;
        }

        // 1. clear the previous format and add the desired color.
        String clearFormat  = (char)27 + "[" + 0 + "m";
        String addColor     = (char)27 + "[" + colorId + "m";

        return addColor + input + clearFormat;
    }
}


