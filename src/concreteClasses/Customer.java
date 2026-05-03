package concreteClasses;

import abstractClasses.User;
import enums.UserRole;

public class Customer extends User {

    private String loyaltyTier;
    private int loyaltyPts;

    public Customer(String name, String contactNo, String userID, UserRole role) {
        super(name, contactNo, userID, UserRole.CUSTOMER);
        this.loyaltyPts = 0;
        this.loyaltyTier = "Bronze";
    }

    public void addLoyaltyPts(int pts) {
        this.loyaltyPts += pts;
        updateLoyaltyTier();
    }

    public void updateLoyaltyTier() {
        if (this.loyaltyPts >= 500) {
            this.loyaltyTier = "Gold";
        } else if (this.loyaltyPts >= 200) {
            this.loyaltyTier = "Silver";
        } else {
            this.loyaltyTier = "Bronze";
        }
    }

    public double getDiscountRate() {
        switch (this.loyaltyTier) {
            case "Gold":
                return 0.20;
            case "Silver":
                return 0.10;
            case "Bronze":
            default:
                return 0.00;
        }
    }

    public String getLoyaltyTier() {
        return loyaltyTier;
    }

    public int getLoyaltyPts() {
        return loyaltyPts;
    }

    @Override
    public String toString() {
        return super.toString() + "," + getLoyaltyTier() + "," + getLoyaltyPts();
    }
}
