import java.util.Random;

public class SudokuMastermind {    private static final int GRID_SIZE = 9;
    public static void main(String[] args) {


        int[][] board = generateSudoku();
        printBoard(board);
        if (solveBoard(board)){
            System.out.println("SOLVED SUCCESSFULLY!");
        }else {
            System.out.println("UNSOLVABLE BOARD");
        }

        printBoard(board);
    }

    private static int[][] generateSudoku() {
        int[][] board = new int[9][9];
        Random random = new Random();

        // Fill the main diagonal with random values
        for (int i = 0; i < 9; i += 3) {
            fillSubGrid(board, i, i, random);
        }

        // Solve the partially filled puzzle to get a complete solution
        solveBoard(board);

        // Remove some elements to create the puzzle
        int numToRemove = 40; // Adjust this value for different difficulty levels
        while (numToRemove > 0) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                numToRemove--;
            }
        }

        return board;
    }

    private static void fillSubGrid(int[][] board, int row, int col, Random random) {
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffleArray(values, random);
        int count = 0;
        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                board[i][j] = values[count++];
            }
        }
    }

    private static void shuffleArray(int[] arr, Random random) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    private static void printBoard(int[][] board){
        for (int row = 0;row<GRID_SIZE;row++){
            for (int column=0;column<GRID_SIZE;column++){
                System.out.print(board[row][column] +" ");
                if (column == 2 || column == 5 ){
                    System.out.print("|");
                }
            }
            System.out.println();
            if (row == 2 || row == 5){
                System.out.println("_ _ _ |_ _ _ |_ _ _");
            }
        }
    }
    private static boolean isNumberInRow(int[][] board, int number, int row){
        for(int i = 0; i<GRID_SIZE;i++){
            if(board[row][i] ==  number){
                return true;
            }
        }
        return false;
    }
    private static boolean isNumberInColumn(int[][] board, int number, int column){
        for(int i = 0; i<GRID_SIZE;i++){
            if(board[i][column] ==  number){
                return true;
            }
        }
        return false;
    }
    private static boolean isNumberInBox(int[][] board, int number,int row, int column){
        int localBoxRow = row - row % 3;
        int localBoxColumn = column - column % 3;

        for(int i = localBoxRow;i<localBoxColumn+3;i++){
            for (int j = localBoxColumn;j<localBoxColumn+3;j++){
                if(board[row][column] ==  number){
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean isValidPlacement(int[][] board, int number, int row, int column){
        return !isNumberInRow(board,number,row) &&
                !isNumberInColumn(board, number, column) &&
                !isNumberInBox(board, number, row, column);
    }

    private static boolean solveBoard(int[][] board){
        for (int row = 0;row<GRID_SIZE;row++){
            for (int column = 0;column<GRID_SIZE;column++){
                if (board[row][column] == 0){
                    for (int numberToTry = 1;numberToTry <= GRID_SIZE; numberToTry++){
                        if(isValidPlacement(board,numberToTry,row,column)){
                            board[row][column] = numberToTry;

                            if(solveBoard(board)){
                                return true;
                            }else{
                                board[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}