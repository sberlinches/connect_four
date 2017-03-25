import java.util.Scanner;

/**
 *
 */
public class ConnectFour {

    private static final int LAYOUT_EMPTY_ID            = 0;
    private static final int LAYOUT_LINE_ID             = 1;
    private static final int YELLOW_PLAYER_ID           = 2;
    private static final int RED_PLAYER_ID              = 3;
    private static final char LAYOUT_EMPTY_SIGN         = ' ';
    private static final char LAYOUT_LINE_SIGN          = '|';
    private static final char YELLOW_PLAYER_SIGN        = 'Y';
    private static final char RED_PLAYER_SIGN           = 'R';
    private static final String YELLOW_PLAYER_NAME      = "yellow";
    private static final String RED_PLAYER_NAME         = "red";
    private static final String MOVE_MSG                = "Select an empty column (0-6) to drop a %s disk into:";
    private static final String INVALID_MOVE_MSG        = "Sorry, you have entered an invalid column number.";
    private static final String WINNER_MSG              = "Congratulations, the %s player wins!!!";
    private static Scanner scanner                      = new Scanner(System.in);
    private static int[][] LAYOUT                       = setLayout();
    private static int TURN                             = whoIsFirst();

    /**
     *
     * @param arg
     */
    public static void main(String[] arg) {

        do {
            printLayout();
            promptForMove();
            swapTurn();
        } while(!isWinner());

        printLayout();
        printWinner();
    }

    /**
     *
     * @return
     */
    private static int[][] setLayout() {

        int[][] layout = new int[6][15];

        for(int[] row: layout) {
            for(int j = 0; j < row.length; j++){
                row[j] = (isEven(j))? LAYOUT_LINE_ID: LAYOUT_EMPTY_ID;
            }
        }

        return layout;
    }

    /**
     *
     * @return
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
     *
     */
    private static void printLayout() {

        for(int[] row: LAYOUT) {
            for(int col: row) {
                switch (col) {
                    case LAYOUT_EMPTY_ID:
                        System.out.print(LAYOUT_EMPTY_SIGN);
                        break;
                    case LAYOUT_LINE_ID:
                        System.out.print(LAYOUT_LINE_SIGN);
                        break;
                    case YELLOW_PLAYER_ID:
                        System.out.print(YELLOW_PLAYER_SIGN);
                        break;
                    case RED_PLAYER_ID:
                        System.out.print(RED_PLAYER_SIGN);
                        break;
                }
            }
            System.out.println();
        }
    }

    /**
     *
     */
    private static void promptForMove() {

        int move;
        String playerName = getPlayerName();

        // 1. Prompt for a move until the user entered a valid move.
        do {
            System.out.println(String.format(MOVE_MSG, playerName));
            move = scanner.nextInt();
        } while (!isValidMove(move));

        setMove(move, TURN);
    }

    /**
     * Checks if the entered move is valid.
     *
     * @param move  Move to be evaluated.
     * @return      If the move is valid or not.
     */
    private static boolean isValidMove(int move) {

        boolean isValidMove = (move >= 0 && move <= 6);

        if(!isValidMove)
            System.out.println(INVALID_MOVE_MSG);

        return isValidMove;
    }

    /**
     *
     * @param playerMove
     * @param turn
     */
    private static void setMove(int playerMove, int turn){

        playerMove = 2 * playerMove + 1;

        for(int row = 5; row >= 0; row--) {
            if(LAYOUT[row][playerMove] == LAYOUT_EMPTY_ID){
                LAYOUT[row][playerMove] = (turn == YELLOW_PLAYER_ID)? YELLOW_PLAYER_ID: RED_PLAYER_ID;
                break;
            }
        }
    }

    /**
     *
     */
    private static void swapTurn() {
        TURN = (TURN == YELLOW_PLAYER_ID)? RED_PLAYER_ID: YELLOW_PLAYER_ID;
    }

    /**
     *
     * @return
     */
    private static boolean isWinner() {
        return (checkHorizontal() || checkVertical() || checkDiagonal());
    }

    /**
     *
     * @return
     */
    private static boolean checkHorizontal() {
        for(int i = 1; i < 15; i += 2){
            for(int j = 0; j < 3; j++){
                if(
                    (LAYOUT[j][i] != LAYOUT_EMPTY_ID)&&
                    (LAYOUT[j+1][i] != LAYOUT_EMPTY_ID)&&
                    (LAYOUT[j+2][i] != LAYOUT_EMPTY_ID)&&
                    (LAYOUT[j+3][i] != LAYOUT_EMPTY_ID)&&
                        (
                            (LAYOUT[j][i] == LAYOUT[j+1][i])&&
                            (LAYOUT[j+1][i] == LAYOUT[j+2][i])&&
                            (LAYOUT[j+2][i] == LAYOUT[j+3][i])
                        )
                    )
                    return true;
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    private static boolean checkVertical() {
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j += 2){
                if(
                    (LAYOUT[i][j+1] != LAYOUT_EMPTY_ID)&&
                    (LAYOUT[i][j+3] != LAYOUT_EMPTY_ID)&&
                    (LAYOUT[i][j+5] != LAYOUT_EMPTY_ID)&&
                    (LAYOUT[i][j+7] != LAYOUT_EMPTY_ID)&&
                    (
                        (LAYOUT[i][j+1] == LAYOUT[i][j+3])&&
                        (LAYOUT[i][j+3] == LAYOUT[i][j+5])&&
                        (LAYOUT[i][j+5] == LAYOUT[i][j+7])
                    )
                )
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    private static boolean checkDiagonal() {
        for(int i = 0; i < 3; i++){
            for(int j = 1; j < 9; j += 2){
                if(
                    (LAYOUT[i][j] != LAYOUT_EMPTY_ID)&&
                    (LAYOUT[i+1][j+2] != LAYOUT_EMPTY_ID)&&
                    (LAYOUT[i+2][j+4] != LAYOUT_EMPTY_ID)&&
                    (LAYOUT[i+3][j+6] != LAYOUT_EMPTY_ID)&&
                        (
                            (LAYOUT[i][j] == LAYOUT[i+1][j+2])&&
                            (LAYOUT[i+1][j+2] == LAYOUT[i+2][j+4])&&
                            (LAYOUT[i+2][j+4] == LAYOUT[i+3][j+6])
                        )
                    )
                    return true;
            }
        }
        for(int i = 0; i < 3; i++){
            for(int j = 7; j < 15; j += 2){
                if(
                    (LAYOUT[i][j] != LAYOUT_EMPTY_ID)&&
                    (LAYOUT[i+1][j-2] != LAYOUT_EMPTY_ID)&&
                    (LAYOUT[i+2][j-4] != LAYOUT_EMPTY_ID)&&
                    (LAYOUT[i+3][j-6] !=LAYOUT_EMPTY_ID)&&
                        (
                            (LAYOUT[i][j] == LAYOUT[i+1][j-2])&&
                            (LAYOUT[i+1][j-2] == LAYOUT[i+2][j-4])&&
                            (LAYOUT[i+2][j-4] == LAYOUT[i+3][j-6])
                        )
                    )
                    return true;
            }
        }
        return false;
    }

    /**
     *
     */
    private static void printWinner() {
        swapTurn();
        String playerName = getPlayerName();
        System.out.printf(WINNER_MSG, playerName);
    }

    /**
     *
     * @return
     */
    private static String getPlayerName() {
        return  (TURN == YELLOW_PLAYER_ID)? YELLOW_PLAYER_NAME: RED_PLAYER_NAME;
    }

    /**
     *
     * @param num
     * @return
     */
    private static boolean isEven(int num) {
        return (num % 2 == 0);
    }
}


