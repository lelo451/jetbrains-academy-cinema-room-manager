package cinema;

import java.util.BitSet;

import static java.lang.System.lineSeparator;
import static java.util.Objects.checkIndex;

public class ScreenRoom {
    public static final int MAX_ROWS = 9;
    public static final int MAX_COLS = 9;
    public static final int SMALL_ROOM = 60;
    public static final int NORMAL_PRICE = 10;
    public static final int DISCOUNTED_PRICE = 8;

    private final int rows;
    private final int cols;
    private final BitSet seats;

    public ScreenRoom(int rows, int cols) {
        checkIndex(rows - 1, MAX_ROWS);
        checkIndex(cols - 1, MAX_COLS);
        this.rows = rows;
        this.cols = cols;
        seats = new BitSet(rows * cols);
    }

    public int book(int row, int col) {
        checkIndex(--row, rows);
        checkIndex(--col, cols);
        seats.set(row * cols + col);
        return isSmallRoom() || (row < rows / 2) ? NORMAL_PRICE : DISCOUNTED_PRICE;
    }

    public SeatState getSeatState(int row, int col) {
        if (row < 1 || row > rows || col < 1 || col > cols) {
            return SeatState.WRONG;
        }
        final var index = --row * cols + --col;
        return seats.get(index) ? SeatState.BOOKED : SeatState.SEAT_FREE;
    }

    public int ticketsSold() {
        return seats.cardinality();
    }

    public int totalSeats() {
        return rows * cols;
    }

    public double getPercentage() {
        return (double) ticketsSold() / totalSeats()*100;
    }

    public boolean isSmallRoom() {
        return totalSeats() <= SMALL_ROOM;
    }

    public int incomeCurrent() {
        if (isSmallRoom()) {
            return ticketsSold() * NORMAL_PRICE;
        }
        return ticketsSold() * DISCOUNTED_PRICE + seats.get(0, rows / 2 * cols).cardinality() * 2;
    }

    public int incomeTotal() {
        if (isSmallRoom()) {
            return totalSeats() * NORMAL_PRICE;
        }
        return totalSeats() * DISCOUNTED_PRICE + rows / 2 * cols * 2;
    }

    @Override
    public String toString() {
        final var result = new StringBuilder("Cinema:");
        result.append(lineSeparator()).append("  1 2 3 4 5 6 7 8 9", 0, 1 + cols * 2);
        for (int row = 1; row <= rows; row++) {
            result.append(lineSeparator()).append(row);
            for (int col = 1; col <= cols; col++) {
                result.append(" ").append(getSeatState(row, col).name().charAt(0));
            }
        }
        return result.append(lineSeparator()).toString();
    }

    public enum SeatState {
        SEAT_FREE, BOOKED, WRONG
    }
}
