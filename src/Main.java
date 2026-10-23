
import Model.Book;
import Model.Member;
import Model.PremiumMember;
import service.Library;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        int choice;

        do {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. Display Books");
            System.out.println("3. Add Regular Member");
            System.out.println("4. Add Premium Member");
            System.out.println("5. Display Members");
            System.out.println("6. Borrow Book");
            System.out.println("7. Return Book");
            System.out.println("8. Display Return History");
            System.out.println("9. Exit");

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        System.out.print("Enter Book ID: ");
                        int bookId = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Book Title: ");
                        String title = sc.nextLine();

                        System.out.print("Enter Author Name: ");
                        String author = sc.nextLine();

                        System.out.print("Enter Quantity: ");
                        int quantity = Integer.parseInt(sc.nextLine());

                        if (bookId <= 0 || quantity <= 0
                                || title.isBlank() || author.isBlank()) {
                            System.out.println("Invalid book details.");
                            break;
                        }

                        library.addBook(
                                new Book(bookId, title, author, quantity));
                        break;

                    case 2:
                        library.displayBooks();
                        break;

                    case 3:
                        System.out.print("Enter Member ID: ");
                        int memberId = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Member Name: ");
                        String memberName = sc.nextLine();

                        if (memberId <= 0 || memberName.isBlank()) {
                            System.out.println("Invalid member details.");
                            break;
                        }

                        library.addMember(
                                new Member(memberId, memberName));
                        break;

                    case 4:
                        System.out.print("Enter Premium Member ID: ");
                        int premiumId = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Member Name: ");
                        String premiumName = sc.nextLine();

                        if (premiumId <= 0 || premiumName.isBlank()) {
                            System.out.println("Invalid member details.");
                            break;
                        }

                        library.addMember(
                                new PremiumMember(premiumId, premiumName));
                        break;

                    case 5:
                        library.displayMembers();
                        break;

                    case 6:
                        System.out.print("Enter Book ID: ");
                        int borrowBookId =
                                Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Member ID: ");
                        int borrowMemberId =
                                Integer.parseInt(sc.nextLine());

                        library.borrowBook(
                                borrowBookId, borrowMemberId);
                        break;

                    case 7:
                        System.out.print("Enter Book ID: ");
                        int returnBookId =
                                Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Member ID: ");
                        int returnMemberId =
                                Integer.parseInt(sc.nextLine());

                        library.returnBook(
                                returnBookId, returnMemberId);
                        break;

                    case 8:
                        library.displayReturnHistory();
                        break;

                    case 9:
                        System.out.println("Thank you for using the library!");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println(
                        "Error: Please enter a valid number.");
                choice = 0;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                choice = 0;
            }

        } while (choice != 9);

        sc.close();
    }
}
