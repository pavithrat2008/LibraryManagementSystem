
package service;

import Model.Book;
import Model.Member;
import Model.PremiumMember;
import Model.BorrowTransaction;
import Model.ReturnTransaction;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<BorrowTransaction> borrowTransactions =
            new ArrayList<>();
    private List<ReturnTransaction> returnTransactions =
            new ArrayList<>();

    private int transactionCounter = 1;
    private int returnCounter = 1;


    public Library() {
        loadBooks();
        loadMembers();
        loadTransactions();
        loadReturns();
    }



    public void addBook(Book book) {
        for (Book existingBook : books) {
            if (existingBook.getBookId() == book.getBookId()) {
                System.out.println("Error: Book ID already exists!");
                return;
            }
        }

        books.add(book);
        saveBooks();
        System.out.println("Book added successfully!");
    }



    public void addMember(Member member) {
        for (Member existingMember : members) {
            if (existingMember.getMemberId() == member.getMemberId()) {
                System.out.println("Error: Member ID already exists!");
                return;
            }
        }

        members.add(member);
        saveMembers();

        System.out.println("Member added successfully!");
    }


    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        for (Book book : books) {
            book.displayBook();
        }
    }

    public void displayMembers() {
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }

        for (Member member : members) {
            member.displayMember();
            System.out.println("--------------------");
        }
    }

    public void borrowBook(int bookId, int memberId) {
        Book selectedBook = null;
        Member selectedMember = null;

        for (Book book : books) {
            if (book.getBookId() == bookId) {
                selectedBook = book;
                break;
            }
        }

        for (Member member : members) {
            if (member.getMemberId() == memberId) {
                selectedMember = member;
                break;
            }
        }

        if (selectedBook == null) {
            System.out.println("Book not found.");
            return;
        }

        if (selectedMember == null) {
            System.out.println("Member not found.");
            return;
        }

        if (selectedBook.getQuantity() <= 0) {
            System.out.println("Book is out of stock.");
            return;
        }

        int limit = (selectedMember instanceof PremiumMember)
                ? ((PremiumMember) selectedMember).getBorrowingLimit()
                : 3;

        if (selectedMember.getBooksBorrowed() >= limit) {
            System.out.println("Borrowing limit reached.");
            return;
        }

        selectedBook.setQuantity(
                selectedBook.getQuantity() - 1);

        selectedMember.borrowBook();

        BorrowTransaction transaction =
                new BorrowTransaction(
                        transactionCounter++,
                        selectedBook,
                        selectedMember,
                        14);

        borrowTransactions.add(transaction);
        saveTransactions();
        saveBooks();
        saveMembers();

        System.out.println("Book borrowed successfully!");
        transaction.displayTransaction();
    }

    public void returnBook(int bookId, int memberId) {
        for (BorrowTransaction transaction : borrowTransactions) {

            if (transaction.getBook().getBookId() == bookId
                    && transaction.getMember().getMemberId()
                    == memberId
                    && !transaction.isReturned()) {

                ReturnTransaction returnTransaction =
                        new ReturnTransaction(
                                returnCounter++, transaction);

                returnTransactions.add(returnTransaction);

                transaction.getBook().setQuantity(
                        transaction.getBook().getQuantity() + 1);

                transaction.getMember().returnBook();
                transaction.markReturned();
                saveTransactions();
                saveReturns();
                saveBooks();
                saveMembers();

                System.out.println("Book returned successfully!");
                returnTransaction.displayReturnDetails();
                return;
            }
        }

        System.out.println(
                "No active borrowing transaction found.");
    }


    private void saveBooks() {
        StringBuilder data = new StringBuilder();

        for (Book book : books) {
            data.append(book.getBookId()).append("|")
                    .append(book.getTitle()).append("|")
                    .append(book.getAuthor()).append("|")
                    .append(book.getQuantity()).append("\n");
        }

        FileManager.saveData("books.txt", data.toString());
    }

    private void loadBooks() {
        String data = FileManager.loadData("books.txt");

        if (data.isEmpty()) {
            return;
        }

        String[] lines = data.split("\\R");

        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }

            String[] parts = line.split("\\|", -1);

            if (parts.length != 4) {
                System.out.println("Skipping invalid book record.");
                continue;
            }

            try {
                int bookId = Integer.parseInt(parts[0]);
                String title = parts[1];
                String author = parts[2];
                int quantity = Integer.parseInt(parts[3]);

                Book book = new Book(bookId, title, author, quantity);
                books.add(book);

            } catch (NumberFormatException e) {
                System.out.println("Skipping invalid book record.");
            }
        }

        System.out.println("Saved books loaded successfully!");
    }


    private void saveMembers() {
        StringBuilder data = new StringBuilder();

        for (Member member : members) {
            String type = (member instanceof PremiumMember)
                    ? "PREMIUM"
                    : "REGULAR";

            data.append(member.getMemberId()).append("|")
                    .append(member.getMemberName()).append("|")
                    .append(type).append("\n");
        }

        FileManager.saveData("members.txt", data.toString());
    }



    private void loadMembers() {
        String data = FileManager.loadData("members.txt");

        if (data.isEmpty()) {
            return;
        }

        String[] lines = data.split("\\R");

        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }

            String[] parts = line.split("\\|", -1);

            try {
                int memberId = Integer.parseInt(parts[0]);
                String memberName = parts[1];

                Member member;

                // Support older records containing only ID and name.
                if (parts.length == 2 || parts[2].equals("REGULAR")) {
                    member = new Member(memberId, memberName);
                } else if (parts[2].equals("PREMIUM")) {
                    member = new PremiumMember(memberId, memberName);
                } else {
                    System.out.println("Skipping invalid member record.");
                    continue;
                }

                members.add(member);

            } catch (RuntimeException e) {
                System.out.println("Skipping invalid member record.");
            }
        }

        System.out.println("Saved members loaded successfully!");
    }

    private void saveTransactions() {
        StringBuilder data = new StringBuilder();

        for (BorrowTransaction transaction : borrowTransactions) {
            data.append(transaction.getTransactionId()).append("|")
                    .append(transaction.getBook().getBookId()).append("|")
                    .append(transaction.getMember().getMemberId()).append("|")
                    .append(transaction.getBorrowDate()).append("|")
                    .append(transaction.getDueDate()).append("|")
                    .append(transaction.isReturned()).append("\n");
        }

        FileManager.saveData("transactions.txt", data.toString());
    }

    private void loadReturns() {
        String data = FileManager.loadData("returns.txt");

        if (data.isEmpty()) {
            return;
        }

        String[] lines = data.split("\\R");

        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }

            String[] parts = line.split("\\|", -1);

            if (parts.length != 4) {
                System.out.println("Skipping invalid return record.");
                continue;
            }

            try {
                int returnId = Integer.parseInt(parts[0]);
                int transactionId = Integer.parseInt(parts[1]);
                java.time.LocalDate returnDate =
                        java.time.LocalDate.parse(parts[2]);
                long fine = Long.parseLong(parts[3]);

                BorrowTransaction borrowTransaction = null;

                for (BorrowTransaction transaction : borrowTransactions) {
                    if (transaction.getTransactionId() == transactionId) {
                        borrowTransaction = transaction;
                        break;
                    }
                }

                if (borrowTransaction == null) {
                    System.out.println("Skipping return: transaction not found.");
                    continue;
                }

                ReturnTransaction returnTransaction =
                        new ReturnTransaction(
                                returnId, borrowTransaction, returnDate, fine);

                returnTransactions.add(returnTransaction);
                returnCounter = Math.max(returnCounter, returnId + 1);

            } catch (RuntimeException e) {
                System.out.println("Skipping invalid return record.");
            }
        }

        System.out.println("Saved return history loaded successfully!");
    }


    private void saveReturns() {
        StringBuilder data = new StringBuilder();

        for (ReturnTransaction returnTransaction : returnTransactions) {
            BorrowTransaction transaction =
                    returnTransaction.getBorrowTransaction();

            data.append(returnTransaction.getReturnId()).append("|")
                    .append(transaction.getTransactionId()).append("|")
                    .append(returnTransaction.getReturnDate()).append("|")
                    .append(returnTransaction.getFine()).append("\n");
        }

        FileManager.saveData("returns.txt", data.toString());
    }


    private void loadTransactions() {
        String data = FileManager.loadData("transactions.txt");

        if (data.isEmpty()) {
            return;
        }

        String[] lines = data.split("\\R");

        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }

            String[] parts = line.split("\\|", -1);

            if (parts.length != 6) {
                System.out.println("Skipping invalid transaction record.");
                continue;
            }

            try {
                int transactionId = Integer.parseInt(parts[0]);
                int bookId = Integer.parseInt(parts[1]);
                int memberId = Integer.parseInt(parts[2]);

                java.time.LocalDate borrowDate =
                        java.time.LocalDate.parse(parts[3]);
                java.time.LocalDate dueDate =
                        java.time.LocalDate.parse(parts[4]);

                boolean returned = Boolean.parseBoolean(parts[5]);

                Book foundBook = null;
                Member foundMember = null;

                for (Book book : books) {
                    if (book.getBookId() == bookId) {
                        foundBook = book;
                        break;
                    }
                }

                for (Member member : members) {
                    if (member.getMemberId() == memberId) {
                        foundMember = member;
                        break;
                    }
                }

                if (foundBook == null || foundMember == null) {
                    System.out.println("Skipping transaction: book or member missing.");
                    continue;
                }

                BorrowTransaction transaction =
                        new BorrowTransaction(
                                transactionId,
                                foundBook,
                                foundMember,
                                borrowDate,
                                dueDate,
                                returned
                        );

                borrowTransactions.add(transaction);

                if (!returned) {
                    foundMember.borrowBook();
                }

                transactionCounter = Math.max(
                        transactionCounter, transactionId + 1);

            } catch (RuntimeException e) {
                System.out.println("Skipping invalid transaction record.");
            }
        }

        System.out.println("Saved transactions loaded successfully!");
    }

    public void displayReturnHistory() {
        if (returnTransactions.isEmpty()) {
            System.out.println("No return history available.");
            return;
        }

        System.out.println("\n===== RETURN HISTORY =====");

        for (ReturnTransaction returnTransaction : returnTransactions) {
            returnTransaction.displayReturnDetails();
            System.out.println("--------------------");
        }
    }


}
