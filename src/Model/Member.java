package Model;

public class Member {
        private int memberId;
        private String memberName;
        private int booksBorrowed;

        public Member(int memberId, String memberName) {
            this.memberId = memberId;
            this.memberName = memberName;
            this.booksBorrowed = 0;
        }

        public int getMemberId() {
            return memberId;
        }

        public String getMemberName() {
            return memberName;
        }

        public int getBooksBorrowed() {
            return booksBorrowed;
        }

        public void borrowBook() {
            booksBorrowed++;
        }

        public void returnBook() {
            if (booksBorrowed > 0) {
                booksBorrowed--;
            }
        }

        public void displayMember() {
            System.out.println("Member ID: " + memberId);
            System.out.println("Member Name: " + memberName);
            System.out.println("Books Borrowed: " + booksBorrowed);
        }

}
