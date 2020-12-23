package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of rows: ");
        int rows = sc.nextInt();
        System.out.print("Enter the number of seats in each row: ");
        int seatsPerRow = sc.nextInt();

        String[][] matrix;
        matrix = fillMatrix(rows, seatsPerRow);

        int selection = 9;

        while (selection != 0) {
            System.out.println("1. Show the seats\n2. Buy a ticket\n0. Exit");
            selection = sc.nextInt();
            switch (selection) {
                case 1:
                    printSeatsInScreenRoom(matrix, rows, seatsPerRow);
                    break;
                case 2:
                    bookSeatInMatrix(matrix, sc, rows, seatsPerRow);
                    break;
                default:
                    break;
            }
        }
    }

    public static int price(int rows, int seatsPerRow, int rowNum) {
        int price;
        if (rows * seatsPerRow <= 60) {
            price = 10;
        } else {
            if (rows % 2 == 0 && rowNum <= rows / 2) {
                price = 10;
            } else if (rows % 2 == 0 && rowNum > rows / 2) {
                price = 8;
            } else if (rows % 2 != 0 && rowNum <= (rows - 1) / 2) {
                price = 10;
            } else {
                price = 8;
            }
        }
        return price;
    }

    public static void incomeCalculator(int rows, int seatsPerRow) {
        int income = 0;
        if (rows * seatsPerRow <= 60) {
            System.out.println("$" + (rows * seatsPerRow * 10));
        } else {
            if (rows % 2 == 0) {
                income = rows / 2 * seatsPerRow * 18;
                System.out.println("$" + income);
            } else {
                income = ((rows - 1) / 2) * seatsPerRow * 10 + (((rows - 1) / 2) + 1) * seatsPerRow * 8;
                System.out.println("$" + income);
            }
        }
    }

    public static String[][] fillMatrix(int rows, int seatsPerRow) {
        int rowCounter = 1;
        int colCounter = 1;

        String[][] matrix = new String[rows + 1][seatsPerRow + 1];

        for (int i = 0; i < rows + 1; i++) {
            for (int j = 0; j < seatsPerRow + 1; j++) {
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
        return matrix;
    }

    public static void printSeatsInScreenRoom(String[][] matrix, int rows, int seatsPerRow) {
        System.out.println("\nCinema:");
        for (int i = 0; i < rows + 1; i++) {
            for (int j = 0; j < seatsPerRow + 1; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static String[][] bookSeatInMatrix(String[][] matrix, Scanner sc, int rows, int seatsPerRow) {
        System.out.print("Enter a row number: ");
        int rowNum = sc.nextInt();
        System.out.print("Enter a seat number in that row: ");
        int seatNum = sc.nextInt();
        matrix[rowNum][seatNum] = "B";
        int price = price(rows, seatsPerRow, rowNum);
        System.out.println("Ticket price: $" + price);
        return matrix;
    }
}