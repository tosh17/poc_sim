/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.pocersimulyator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author a_scherbakov
 */
public class Cards {

    private final List<OneCard> cards = new ArrayList<>();

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
