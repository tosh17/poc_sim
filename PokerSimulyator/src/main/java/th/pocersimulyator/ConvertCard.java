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
public final class ConvertCard {
    //Туз Пик

    private final String[] suit = {"Worms", "Diamonds", "Baptize", "Peaks"};
    private final String[] suitRus = {"Червы", "Бубны", "Крести", "Пики"};
    private final String[] number = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "V", "Q", "K", "A"};
    private final String[] numberRus = {"T", "2", "3", "4", "5", "6", "7", "8", "9", "10", "В", "Д", "К", "Т"};
    private final int intSuit[] = {0, 1, 2, 3};
    private final String[] suitPic = {"♥", "♦", "♣", "♠"};
    private final int intNumber[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    public String getSuit(int i) {
        return suit[i];
    }

    public String getSuitRus(int i) {
        return suitRus[i];
    }

    public String getSuitPic(int i) {
        return suitPic[i];
    }

    public String getNumber(int i) {
        return number[i];
    }

    public String getNumberRus(int i) {
        return numberRus[i];
    }

    public int getCode(int i1, int i2) {
        return i1 + i2 * 13;
    }

    public String getCard(int code) {
        String str = "";
        int i1 = code % 13;
        str = number[i1];
        i1 = (code - i1) / 13;
        str = str + suitRus[i1];
        return str;
    }

    /* public String suitToRus(String str1) {
        int i = 0;
        while (!suitRus[i++].equals(str1));
        return suitRus[i];
    }

    String numberToSuit(int n) {
        int i = 0;
        while (intSuit[i++] != n);
        return suit[i];
    }*/
}
