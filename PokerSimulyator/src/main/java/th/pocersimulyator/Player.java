/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.pocersimulyator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author a_scherbakov
 */
public class Player {

    private final String name;
    private String type;
    private int inttype;
    private final List<OneCard> hand = new ArrayList<>();
    private final List<OneCard> table = new ArrayList<>();
    private boolean status = true;
    private int statwin = 0;

    Player(String name) {
        this.name = name;
    }

    public void giveCard(OneCard card) {
        hand.add(card);
    }

    public void givetable(OneCard card) {
        hand.add(card);
    }

    public void givetable(OneCard card1, OneCard card2, OneCard card3) {
        hand.add(card1);
        hand.add(card2);
        hand.add(card3);
    }

    public Combination GetCombination() {
        Combination comb;
        int a[] = new int[hand.size()];
        for (int i = 0; i < hand.size(); i++) {
            a[i] = hand.get(i).getCode();
        }
        comb = new Combination(a);
        return comb;
    }

    public String getName() {
        return name;
    }

    public String showHand() {
        String str = "";
        for (int i = 0; i < 2; i++) {
            str = str + hand.get(i).getInfo();
        }
        return str;
    }

    public void showTerVer() {
        String str = "";
        int a[] = new int[hand.size()];
        TerverSimpl ter;
        for (int i = 0; i < hand.size(); i++) {
            a[i] = hand.get(i).getCode();
        }
        ter=new TerverSimpl(a);
        ter.printStat();
    }

    public void setStatus(boolean b) {
        this.status = b;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void win() {
        statwin++;
    }

    public int getWin() {
        return statwin;
    }

    public void resetHand() {
        hand.clear();
    }
        public int getCodeHand(int i) {
        return hand.get(i).getCode();
    }

    public int[] getCodeHand() {
        int a[] = {getCodeHand(0), getCodeHand(1), getCodeHand(2), getCodeHand(3), getCodeHand(4), getCodeHand(5), getCodeHand(6)};
        return a;
    }
}
