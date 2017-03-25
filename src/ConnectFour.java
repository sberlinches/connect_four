import java.util.Scanner;
public class ConnectFour {

    public static void main(String[] arg) {
	printPattern();
    }
 /** This method diplay the all resulut and pattern.
 */
    public static void printPattern(){
    	String[][] arr = Pattern();
		boolean loop = true;
		int count = 0;
		makePattern(arr);
		while(loop){
			if(count % 2 == 0)
				printYellow(arr);
			else
				printRed(arr);
			count++;
			makePattern(arr);
			if(checkWinner(arr) != null){
				if(checkWinner(arr) == "Y")
					System.out.println("The yellow player won.");
				else if(checkWinner(arr) == "R")
					System.out.println("The red player won.");
				else()
					System.out.println("Draw");
				loop = false;
			}
		
		}
		
	
    }
/** This method make a pattern.
* @return arr 
*/
    public static String[][] Pattern(){
		String[][] arr = new String[6][15];
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < arr[i].length; j++){
				if(j % 2 == 0)
					arr[i][j] = "|";
				else 
					arr[i][j] = " ";
			}
		
		}
		return arr;
	}
/** This method display the pattern.
*@param arr represents the row and the clomun

*/
	public static void makePattern(String[][] arr){
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < arr[i].length; j++){
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
	}
/** This method display the yellow player's turn.
*@param arr represents the row and the clomun
*/
	public static void printYellow(String[][] arr){
		Scanner sc = new Scanner(System.in);
		System.out.println("Select an empty column (0 - 6) to drop a yellow disk into:");
		int num = 2 * sc.nextInt() + 1;
		if(num > 7)
			System.out.println("Try again");
		for(int i = 5; i >= 0; i--){
			if(arr[i][num] == " "){
				arr[i][num] = "Y";
				break;
			}
			
	
		}
	
	}
/** This method display the red player's turn.
*@param arr represents the row and the clomun
*/
	public static void printRed(String[][] arr){
		Scanner sc = new Scanner(System.in);
		System.out.println("Select an empty column (0 - 6) to drop a red disk into:");
		int num = 2 * sc.nextInt() + 1;
		for(int i = 5; i >= 0; i--){
			if(arr[i][num] == " "){
				arr[i][num] = "R";
				break;
			}
			
	
		}
		
	
	}
/** This method check who the winner is line by line


*/
	public static  String checkWinner(String[][] arr){
		for(int i = 0; i < 6; i++){
			//check the vertical line
			for(int j = 0; j < 7; j += 2){
				if((arr[i][j+1] != " ")&&(arr[i][j+3] != " ")&&(arr[i][j+5] != " ")&&(arr[i][j+7] != " ")
				&&((arr[i][j+1] == arr[i][j+3])&&(arr[i][j+3] == arr[i][j+5])&&(arr[i][j+5] == arr[i][j+7])))
				
				return arr[i][j];
			
			}
		
		}
		//check the horizontal line.
		for(int i = 1; i < 15; i += 2){
			for(int j = 0; j < 3; j++){
				if((arr[j][i] != " ")&&(arr[j+1][i] != " ")&&(arr[j+2][i] != " ")&&(arr[j+3][i] != " ")
				&&((arr[j][i] == arr[j+1][i])&&(arr[j+1][i] == arr[j+2][i])&&(arr[j+2][i] == arr[j+3][i])))
				return arr[j][i];
			}
		}
		//check the diagonal
		for(int i = 0; i < 3; i++){
			for(int j = 1; j < 9; j += 2){
				if((arr[i][j] != " ")&&(arr[i+1][j+2] != " ")&&(arr[i+2][j+4] != " ")&&(arr[i+3][j+6] != " ")
				&&((arr[i][j] == arr[i+1][j+2])&&(arr[i+1][j+2] == arr[i+2][j+4])&&(arr[i+2][j+4] == arr[i+3][j+6])))
				return arr[i][j];
			
			}
		}
		//check the diagonal too
		for(int i = 0; i < 3; i++){
			for(int j = 7; j < 15; j += 2){
				if((arr[i][j] != " ")&&(arr[i+1][j-2] != " ")&&(arr[i+2][j-4] != " ")&&(arr[i+3][j-6] != " ")
				&&((arr[i][j] == arr[i+1][j-2])&&(arr[i+1][j-2] == arr[i+2][j-4])&&(arr[i+2][j-4] == arr[i+3][j-6])))
				return arr[i][j];
			}
		
		}
	    return null;
	
	}
}


