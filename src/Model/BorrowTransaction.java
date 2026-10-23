
package Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BorrowTransaction {
    private int transactionId;
    private Book book;
    private Member member;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean returned;



    public BorrowTransaction(int transactionId, Book book,
                             Member member, int loanDays) {
        this.transactionId = transactionId;
        this.book = book;
        this.member = member;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(loanDays);
        this.returned = false;
    }

    public BorrowTransaction(int transactionId, Book book,
                             Member member, LocalDate borrowDate,
                             LocalDate dueDate, boolean returned) {
        this.transactionId = transactionId;
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returned = returned;
    }


    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }


    public boolean isReturned() {
        return returned;
    }

    public void markReturned() {
        returned = true;
    }

    public long calculateFine() {
        long lateDays = ChronoUnit.DAYS.between(
                dueDate, LocalDate.now()
        );

        if (lateDays > 0 && !returned) {
            return lateDays * 5;
        }

        return 0;
    }

    public void displayTransaction() {
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Book: " + book.getTitle());
        System.out.println("Member: " + member.getMemberName());
        System.out.println("Borrow Date: " + borrowDate);
        System.out.println("Due Date: " + dueDate);
        System.out.println("Returned: " + returned);
    }
}

