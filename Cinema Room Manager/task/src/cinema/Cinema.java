package cinema;

public class Cinema {

    public static void main(String[] args) {
        int rows = 8;
        int cols = 9;
        int rowCounter = 1;
        int colCounter = 1;

        String[][] matrix = new String[8][9];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 && j == 0) {
                    matrix[i][j] = " ";
                } else if (j == 0) {
                    matrix[i][j] = String.format("%d", rowCounter);
                    rowCounter++;
                } else if (i == 0) {
                    matrix[i][j] = String.format("%d", colCounter);
                    colCounter++;
                } else {
                    matrix[i][j] = "S";
                }
            }
        }

        System.out.println("Cinema:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}