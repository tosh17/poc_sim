/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.pocersimulyator;

/**
 *
 * @author a_scherbakov
 */
public class OneCard {

    private final String suit;
    private final String suitRus;
    private final String suitPic;
    private final String number;
    private final String numberRus;
    private final int intSuit;
    private final int intNumber;
    private final int code;
    private boolean aCard = false;

    OneCard(int n1, int n2) {
        ConvertCard tempcard = new ConvertCard();
        this.suit = tempcard.getSuit(n2);
        this.suitRus = tempcard.getSuitRus(n2);
        this.suitPic = tempcard.getSuitPic(n2);
        this.number = tempcard.getNumber(n1);
        this.numberRus = tempcard.getNumberRus(n1);
        this.intSuit = n2;
        this.intNumber = n1;
        this.code = tempcard.getCode(n1, n2);
        if (number.equals("A")) {
            this.aCard = true;
        }
    }

    public String getInfo() {
       return (this.number + this.suitRus+" ");
    }

    public String getNumber() {
        return number;
    }

    public String getNumberRus() {
        return numberRus;
    }

    public int getCode() {
        return code;
    }
}
