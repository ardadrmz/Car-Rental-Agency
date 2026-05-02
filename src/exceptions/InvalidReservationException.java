/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package exceptions;

/**
 *
 * @author ardad
 */
public class InvalidReservationException extends Exception {
    public InvalidReservationException(String msg) {
        super("Invalid reservation : "+msg);
    }
}
