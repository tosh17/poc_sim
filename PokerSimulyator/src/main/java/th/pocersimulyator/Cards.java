/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.pocersimulyator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author a_scherbakov
 */
public class Cards {

    private final List<OneCard> cards = new ArrayList<>(); //ArrayList LinkedList

    Cards() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                cards.add(new OneCard(j, i));
            }
        }

    }

    public void sort() {
        OneCard temp1;
        OneCard temp2;
        Random randomGenerator = new Random();
        for (int i = 0; i < cards.size(); i++) {
            int j = randomGenerator.nextInt(cards.size());
            temp1 = cards.get(i);
            temp2 = cards.get(j);
            cards.set(i, temp2);
            cards.set(j, temp1);
        }
    }

    public void chiter(int n, int count, int code1, int code2) {
        OneCard temp1;
        OneCard temp2;
        for (int i = 0; i < 52; i++) {
            if (cards.get(i).getCode() == code1 || cards.get(i).getCode() == code2) {
                if(i!=n && n<2*count) {
                temp1 = cards.get(i);
                temp2 = cards.get(n);
                cards.set(n, temp1);
                cards.set(i, temp2);
                n = n + count;}
            }
        }
    }

    public void getInfoCard(int code) {
        cards.get(code).getInfo();
    }

    public OneCard getCard() {
        OneCard temp;
        temp = cards.get(0);
        cards.remove(0);
        return temp;
    }

    public void print() {
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).getInfo();
            System.out.println();
        }
    }
}
