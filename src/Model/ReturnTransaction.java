
package Model;

import java.time.LocalDate;

public class ReturnTransaction {
    private int returnId;
    private BorrowTransaction borrowTransaction;
    private LocalDate returnDate;
    private long fine;

    public ReturnTransaction(int returnId,
                             BorrowTransaction borrowTransaction) {
        this.returnId = returnId;
        this.borrowTransaction = borrowTransaction;
        this.returnDate = LocalDate.now();

        this.fine = borrowTransaction.calculateFine();
        borrowTransaction.markReturned();
    }

    public void displayReturnDetails() {
        System.out.println("Return Transaction ID: " + returnId);
        System.out.println("Book: "
                + borrowTransaction.getBook().getTitle());
        System.out.println("Member: "
                + borrowTransaction.getMember().getMemberName());
        System.out.println("Return Date: " + returnDate);
        System.out.println("Fine: Rs. " + fine);
    }

    public long getFine() {
        return fine;
    }

    public int getReturnId() {
        return returnId;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public BorrowTransaction getBorrowTransaction() {
        return borrowTransaction;
    }

    public ReturnTransaction(int returnId,
                             BorrowTransaction borrowTransaction,
                             LocalDate returnDate,
                             long fine) {
        this.returnId = returnId;
        this.borrowTransaction = borrowTransaction;
        this.returnDate = returnDate;
        this.fine = fine;
    }

}

