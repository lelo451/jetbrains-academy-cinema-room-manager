package cinema;

import java.util.Scanner;

import static java.lang.System.out;

public class Cinema {
    private static Scanner scanner = new Scanner(System.in);
    private static ScreenRoom screenRoom;

    public static void main(String[] args) {
        out.println("Enter the number of rows:");
        final var rows = Integer.parseInt(scanner.nextLine());

        out.println("Enter the number of seats in each row:");
        final var cols = Integer.parseInt(scanner.nextLine());
        screenRoom = new ScreenRoom(rows, cols);

        Menu.create()
                .add("Show the seats", () -> System.out.println(screenRoom))
                .add("Buy a ticket", Cinema::buyTicket)
                .add("Statistics", Cinema::statistics)
                .addExit()
                .run();

    }

    private static void statistics() {
        out.printf("Number of purchased tickets: %d%n", screenRoom.ticketsSold());
        out.printf("Percentage: %.2f%%%n", screenRoom.getPercentage());
        out.printf("Current income: $%d%n", screenRoom.incomeCurrent());
        out.printf("Total income: $%d%n", screenRoom.incomeTotal());
    }

    private static void buyTicket() {
        for (var state = ScreenRoom.SeatState.WRONG; state != ScreenRoom.SeatState.SEAT_FREE; ) {
            out.println("Enter a row number:");
            final var row = Integer.parseInt(scanner.nextLine());

            out.println("Enter a seat number in that row:");
            final var col = Integer.parseInt(scanner.nextLine());

            state = screenRoom.getSeatState(row, col);
            if (state == ScreenRoom.SeatState.BOOKED) {
                out.println("That ticket has already been purchased!");
            } else if (state == ScreenRoom.SeatState.WRONG) {
                out.println("Wrong input!");
            } else {
                final var price = screenRoom.book(row, col);
                out.println("Ticket price: $" + price);
            }
        }

    }
}