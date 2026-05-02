/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concreteClasses;

public class Invoice {

    private String invoiceID;
    private Reservation reservation;
    private double baseAmount;
    private double discountAmount;
    private double finalAmount;

    public Invoice(String invoiceID, Reservation reservation) {
        this.invoiceID = invoiceID;
        this.reservation = reservation;

        calculateTotal();
    }

    public void calculateTotal() {
        long days = reservation.calculateTotalDays();
        double dailyRate = reservation.getVehicle().getDailyRate();

        this.baseAmount = days * dailyRate;
        double loyaltyDiscount = reservation.getCustomer().getDiscountRate();
        this.discountAmount = this.baseAmount * loyaltyDiscount;
        this.finalAmount = this.baseAmount - this.discountAmount;
        System.out.println("Total amount to pay: " + finalAmount);
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
    

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

    @Override
    public String toString() {
        return invoiceID + ","
                + reservation.getReservationID() + "," + baseAmount + "," + discountAmount + "," + finalAmount;
    }
}
