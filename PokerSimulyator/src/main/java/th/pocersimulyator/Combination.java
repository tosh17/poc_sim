/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.pocersimulyator;

import static java.lang.Math.pow;

/**
 *
 * @author a_scherbakov
 */
public class Combination {

    int card[][] = new int[5][2];
    double cost;
    //int bestcard[][] = new int[5][2];
    private int bestcost[][] = new int[2][5];
    private int bestcard[] = new int[5];
    private int temper = 0;
    private int tempcoint = 0;
    private int tempsum;
    private int maxsum;

    Combination(int code0, int code1, int code2, int code3, int code4) {
        int a[] = {code0, code1, code2, code3, code4};
        bestcost[0][0] = 0;
        setHand(a);

    }

    Combination(int code0, int code1, int code2, int code3, int code4, int code5) {
        int b[] = {code0, code1, code2, code3, code4, code5};
        bestcost[0][0] = 0;
        comb6to5(b);

    }

    Combination(int code0, int code1, int code2, int code3, int code4, int code5, int code6) {
        int b[] = {code0, code1, code2, code3, code4, code5, code6};
        bestcost[0][0] = 0;
        comb7to5(b);
    }

    Combination(int a[]) {
        bestcost[0][0] = 0;
        switch (a.length) {
            case 5:
                setHand(a);
                break;
            case 6:
                comb6to5(a);
                break;
            case 7:
                comb7to5(a);
                break;

        }

    }

    private String convertcost(int x) {
        String str = "";
        switch (x) {
            case 1:
                str = "Старшая Карта " + convertcard(bestcost[1][0], 0);
                break;
            case 2:
                str = "Пара " + convertcard(bestcost[1][0], 0) + " и дополнительной картой " + convertcard(bestcost[1][1], 0);
                break;
            case 3:
                str = "2 пары на " + convertcard(bestcost[1][0], 0) + " и " + convertcard(bestcost[1][1], 0) + " + доп " + convertcard(bestcost[1][2], 0);
                break;
            case 4:
                str = "СЕТ на " + convertcard(bestcost[1][0], 0);
                break;
            case 5:
                str = "стрит со старшей картой " + convertcard(bestcost[1][0], 0);
                break;
            case 6:
                str = "флэш со старшей картой " + convertcard(bestcost[1][0], 0);
                break;
            case 7:
                str = "ФуллХаус на " + convertcard(bestcost[1][0], 0) + " и " + convertcard(bestcost[1][1], 0);
                break;
            case 8:
                str = "Каре на " + convertcard(bestcost[1][0], 0) + " и дополнительной картой " + convertcard(bestcost[1][1], 0);
                break;
            case 9:
                str = "стрит-флэш со старшей картой " + convertcard(bestcost[1][0], 0);
                break;
            case 10:
                str = "флеш-роял";
                break;
        }
        return str;
    }

    private String convertcard(int n, int position) {
        ConvertCard temp = new ConvertCard();
        if (position == 0) {
            return temp.getNumberRus(n);
        }
        return temp.getSuitRus(n);
    }

    private void comb6to5(int b[]) {
        int a[] = new int[5];
        int t;
        for (int i = 0; i < 6; i++) {
            t = 0;
            for (int j = 0; j < 6; j++) {
                if (i != j) {
                    a[t++] = b[j];
                }
            }
            setHand(a);
        }
    }

    private void comb7to5(int b[]) {
        int a[] = new int[5];
        int t;
        for (int i = 0; i < 6; i++) {
            for (int j = i + 1; j < 7; j++) {
                t = 0;
                for (int x = 0; x < 7; x++) {
                    if (i != x && j != x) {
                        a[t++] = b[x];
                    }
                }
                setHand(a);
            }

        }
    }

    private void setHand(int a[]) {
        tempcoint = 0;
        //A=1
        for (int i = 0; i < 5; i++) {
            codeToMass(i, a[i]);
        }
        sort();
        comb5();
        //A=13
        tempcoint = 0;
        boolean acard = false;
        for (int i = 0; i < 5; i++) {
            if (card[i][0] == 0) {
                card[i][0] = 13;
                acard = true;
            }
        }
        if (acard) {
            sort();
            comb5();
        }
    }

    private void comb5() {
        //temper++;
        //System.out.println("Рука номер" + temper);
        //printHand();
        int count[] = {0, 0, 0, 0, 0};
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (card[i][0] == card[j][0]) {
                    count[i]++;
                }
            }
        }
        tempCount(count);
        if (flushRoyal()) {  // Флеш рояль, дополнительные карты не требуются
            bestcost[0][0] = 10;
            bestcardsave();
        } else if (bestcost[0][0] < 10 && streetFlush()) { // Комбинация стритфлеш + определяем старшую карту

            bestcostn(9);
            bestcost[1][0] = card[4][0];
            bestcardsave();

        } else if (bestcost[0][0] < 9 && kare()) {// Комбинация Каре + допл карта
            bestcostn(8);
            bestcost[1][0] = card[2][0];
            bestcardsave();
            for (int i = 0; i < 5; i++) { // Проверка на то что это старшая допл карта
                if (count[i] == 4) {
                    bestcost[1][0] = card[i][0];
                } else if (bestcost[1][1] < card[i][0]) {
                    bestcost[1][1] = card[i][0];
                }
            }

        } else if (bestcost[0][0] < 8 && fullHouse()) { // фулхаус
            bestcostn(7);
            bestcardsave();
            if (bestcost[1][0] <= card[2][0]) {  // Возможные комбинации 22333 или 33322 средняя карта из сета. Проверка что сет на максимуме
                bestcost[1][0] = card[2][0];
                if (bestcost[1][0] == card[0][0]) { // проверка комбинации если 33322
                    if (bestcost[1][1] < card[4][0]) {
                        bestcost[1][1] = card[4][0];
                        bestcardsave();
                    }
                } else // tckb 22333
                 if (bestcost[1][1] < card[1][0]) {
                        bestcost[1][1] = card[1][0];
                        bestcardsave();
                    }
            }

        } else if (bestcost[0][0] < 7 && flush()) { // Комбинация флеш
            bestcostn(6);
            boolean b = true;
            for (int i = 0; i < 5; i++) {
                if (bestcost[1][i] > card[4 - i][0]) {
                    b = false;
                    break;
                }
            }
            if (b) {
                for (int i = 0; i < 5; i++) {
                    bestcost[1][i] = card[4 - i][0];
                    bestcardsave();
                }
            }

        } else if (bestcost[0][0] < 6 && street()) { // Комбинация стрит
            bestcostn(5);
            if (bestcost[1][0] < card[4][0]) {
                bestcost[1][0] = card[4][0];
                bestcardsave();
            }

        } else if (bestcost[0][0] < 5 && set()) { // Комбинация Сет
            bestcostn(4);
            int temp = 2, t1, t2;
            int t[] = new int[2];
            for (int i = 0; i < 5; i++) {
                if (count[i] == 3) {
                    bestcost[1][0] = card[i][0];
                } else {
                    t[--temp] = card[i][0];
                }
                if (t[0] >= bestcost[1][1] && t[1] >= bestcost[1][2]) {
                    bestcost[1][1] = t[0];
                    bestcost[1][2] = t[1];
                    bestcardsave();
                }
            }

        } else if (bestcost[0][0] < 4 && twoPair()) {
            bestcostn(3);
            int t1 = 0, t2 = 0, t3 = 0;
            for (int i = 0; i < 5; i++) {
                if (t2 == 0 && count[i] == 2) {
                    t2 = card[i][0];
                } else if (t2 != card[i][0] && count[i] == 2) {
                    t1 = card[i][0];
                } else {
                    t3 = card[i][0];
                }
            }
            if (t3 >= bestcost[1][2] && t2 >= bestcost[1][1] && t1 >= bestcost[1][0]) {
                bestcost[1][0] = t1;
                bestcost[1][1] = t2;
                bestcost[1][2] = t3;
                bestcardsave();

            }
        } else if (bestcost[0][0] < 3 && pair()) {
            bestcostn(2);
            int temp = 0;
            for (int i = 4; i >= 0; i--) {
                if (count[i] == 2) {
                    bestcost[1][0] = card[i][0];
                    bestcardsave();
                } else {
                    bestcost[1][++temp] = card[i][0];
                }
            }

        } else if (bestcost[0][0] < 2 && one()) {
            bestcostn(1);
            boolean b = true;
            for (int i = 0; i < 5; i++) {
                if (bestcost[1][i] > card[4 - i][0]) {
                    bestcardsave();
                    b = false;
                    break;
                }
            }
            if (b) {
                for (int i = 0; i < 5; i++) {
                    bestcost[1][i] = card[4 - i][0];
                }
            }

        }
    }

    private void sort() {
        for (int i = 0; i < 5; i++) {
            for (int j = i; j < 5; j++) {
                if (card[i][0] > card[j][0]) {
                    int temp1[] = card[i];
                    int temp2[] = card[j];
                    card[i] = temp2;
                    card[j] = temp1;

                }
            }
        }

    }

    private void codeToMass(int i, int code) {
        card[i][0] = code % 13;
        card[i][1] = (code - card[i][0]) / 13;
    }

    private boolean flushRoyal() {
        if (card[4][0] != 13) {
            return false;
        }
        if (!streetFlush()) {
            return false;
        }
        return true;
    }

    private boolean streetFlush() {
        if (!flush()) {
            return false;
        }
        if (!street()) {
            return false;
        }
        return true;
    }

    private boolean kare() {
        if (tempcoint == 17) {
            return true;
        }
        return false;
    }

    private void tempCount(int count[]) {
        tempcoint = 0;
        tempsum = 0;

        for (int i = 0; i < 5; i++) {
            tempcoint += count[i];
            tempsum += card[i][0];
        }

    }

    private boolean flush() {
        for (int i = 0; i < 4; i++) {
            for (int j = i; j < 5; j++) {
                if (card[i][1] != card[j][1]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean street() {
//    A=1
        for (int i = 0; i < 4; i++) {
            if (card[i][0] + 1 != card[i + 1][0]) {
                return false;
            }
        }
        return true;
    }

    private boolean fullHouse() {
        if (tempcoint == 13) {
            return true;
        }
        return false;
    }

    private boolean set() {
        if (tempcoint == 11) {
            return true;
        }
        return false;
    }

    private boolean twoPair() {
        if (tempcoint == 9) {
            return true;
        }
        return false;
    }

    private boolean pair() {
        if (tempcoint == 7) {
            return true;
        }
        return false;
    }

    private boolean one() {
        return true;
    }

    private void printHand() {
        for (int i = 0; i < 5; i++) {
            OneCard temp;
            temp = new OneCard(card[i][0], card[i][1]);
            temp.getInfo();
        }
        System.out.println();
    }

    public int getCombinate() {
        return bestcost[0][0];
    }

    public int getDopCombinate(int i) {
        return bestcost[1][i];
    }

    public String getCombName() {
        return convertcost(bestcost[0][0]);
    }

    public int eq(Combination com) {
        int comb1, temp1;
        int comb2, temp2;
        comb1 = this.getCombinate();
        comb2 = com.getCombinate();
        if (comb1 > comb2) {
            return 1;
        } else if (comb1 < comb2) {
            return -1;
        } else {
            temp1 = this.getDopCombinate(0);
            temp2 = com.getDopCombinate(0);
            switch (comb1) {
                case 10:  //Fluhroyal
                    return 0;

                case 9:  //street flush
                    if (temp1 > temp2) {
                        return 1;
                    } else if (temp1 < temp2) {
                        return -1;
                    } else {
                        return 0;
                    }

                case 5: // street
                    if (temp1 > temp2) {
                        return 1;
                    } else if (temp1 < temp2) {
                        return -1;
                    } else {
                        return 0;
                    }

                case 8: //kare
                    if (temp1 > temp2) {
                        return 1;
                    } else if (temp1 < temp2) {
                        return -1;
                    } else {
                        return 0;
                    }
                case 7: // "ФуллХоус";
                    if (temp1 > temp2) {
                        return 1;
                    } else if (temp1 < temp2) {
                        return -1;
                    } else {

                        temp1 = this.getDopCombinate(1);
                        temp2 = com.getDopCombinate(1);
                        if (temp1 > temp2) {
                            return 1;
                        } else if (temp1 < temp2) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                case 4: // str = "СЕТ";
                    if (temp1 > temp2) {
                        return 1;
                    } else if (temp1 < temp2) {
                        return -1;
                    } else {

                        temp1 = this.getDopCombinate(1);
                        temp2 = com.getDopCombinate(1);
                        if (temp1 > temp2) {
                            return 1;
                        } else if (temp1 < temp2) {
                            return -1;
                        } else {
                            temp1 = this.getDopCombinate(2);
                            temp2 = com.getDopCombinate(2);
                            if (temp1 > temp2) {
                                return 1;
                            } else if (temp1 < temp2) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    }
                case 3: // str = "2 пары";
                    if (temp1 > temp2) {
                        return 1;
                    } else if (temp1 < temp2) {
                        return -1;
                    } else {

                        temp1 = this.getDopCombinate(1);
                        temp2 = com.getDopCombinate(1);
                        if (temp1 > temp2) {
                            return 1;
                        } else if (temp1 < temp2) {
                            return -1;
                        } else {
                            temp1 = this.getDopCombinate(2);
                            temp2 = com.getDopCombinate(2);
                            if (temp1 > temp2) {
                                return 1;
                            } else if (temp1 < temp2) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    }
                case 2: // "Пара";
                    if (temp1 > temp2) {
                        return 1;
                    } else if (temp1 < temp2) {
                        return -1;
                    } else {

                        temp1 = this.getDopCombinate(1);
                        temp2 = com.getDopCombinate(1);
                        if (temp1 > temp2) {
                            return 1;
                        } else if (temp1 < temp2) {
                            return -1;
                        } else {
                            temp1 = this.getDopCombinate(2);
                            temp2 = com.getDopCombinate(2);
                            if (temp1 > temp2) {
                                return 1;
                            } else if (temp1 < temp2) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    }
                case 1: //  "Старшая Карта";
                    if (temp1 > temp2) {
                        return 1;
                    } else if (temp1 < temp2) {
                        return -1;
                    } else {

                        temp1 = this.getDopCombinate(1);
                        temp2 = com.getDopCombinate(1);
                        if (temp1 > temp2) {
                            return 1;
                        } else if (temp1 < temp2) {
                            return -1;
                        } else {
                            temp1 = this.getDopCombinate(2);
                            temp2 = com.getDopCombinate(2);
                            if (temp1 > temp2) {
                                return 1;
                            } else if (temp1 < temp2) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    }
                case 6: //str = "флэш";
                    if (temp1 > temp2) {
                        return 1;
                    } else if (temp1 < temp2) {
                        return -1;
                    } else {

                        temp1 = this.getDopCombinate(1);
                        temp2 = com.getDopCombinate(1);
                        if (temp1 > temp2) {
                            return 1;
                        } else if (temp1 < temp2) {
                            return -1;
                        } else {
                            temp1 = this.getDopCombinate(2);
                            temp2 = com.getDopCombinate(2);
                            if (temp1 > temp2) {
                                return 1;
                            } else if (temp1 < temp2) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    }
            }

        }
        return 0;
    }

    private void bestcostn(int n) { // Если переходим в более высокий класс то зануляем доп карты
        if (bestcost[0][0] < n) {
            bestcost[0][0] = n;
            for (int i = 0; i < 5; i++) {
                bestcost[1][i] = 0;
            }
        }

    }

    private void bestcardsave() {
        for (int i = 0; i < 5; i++) {
            
                bestcard[i]=card[i][0]+13*card[i][1];
           
        }
    }
    public int getBestCard(int i){
    return bestcard[i];}
}
