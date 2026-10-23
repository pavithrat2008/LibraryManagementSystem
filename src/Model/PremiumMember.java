
package Model;

public class PremiumMember extends Member {

    private int borrowingLimit;

    public PremiumMember(int memberId, String memberName) {
        super(memberId, memberName);
        this.borrowingLimit = 5;
    }

    public int getBorrowingLimit() {
        return borrowingLimit;
    }

    @Override
    public void displayMember() {
        System.out.println("Premium Member Details");
        super.displayMember();
        System.out.println("Borrowing Limit: " + borrowingLimit);
    }
}
