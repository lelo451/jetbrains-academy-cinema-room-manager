package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of rows: ");
        int rows = sc.nextInt();
        System.out.print("Enter the number of seats in each row: ");
        int seatsPerRow = sc.nextInt();
        System.out.print("Total income: ");
        if (rows * seatsPerRow <= 60) {
            System.out.println("$" + (rows * seatsPerRow * 10));
        } else {
            if (rows % 2 == 0) {
                int income = rows / 2 * seatsPerRow * 18;
                System.out.println("$" + income);
            } else {
                int income = ((rows - 1) / 2) * seatsPerRow * 10 + (((rows - 1) / 2) + 1) * seatsPerRow * 8;
                System.out.println("$" + income);
            }
        }
    }
}