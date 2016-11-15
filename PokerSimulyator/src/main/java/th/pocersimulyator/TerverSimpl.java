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
public class TerverSimpl {

    long combin[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    long count = 0;

    TerverSimpl(int k[]) {
       perebor(k,0);
     //  perebor(k);
    }

    private void perebor(int k[],int x) {
        if (k.length == 7) {
            Combination comb = new Combination(k);
            combin[comb.getCombinate() - 1]++;
            count++;
        } else {
            int t[] = new int[k.length + 1];
            System.arraycopy(k, 0, t, 0, k.length);
            for (int i = x; i < 52; i++) {
                boolean b = true;
                for (int j : k) {
                    if (j == i) {
                        b = false;
                    }
                }
                if (b) {
                    t[k.length] = i;
                    perebor(t,i);
                }
            }
        }
    }
     private void perebor(int k[]) {
        if (k.length == 7) {
            Combination comb = new Combination(k);
            combin[comb.getCombinate() - 1]++;
            count++;
        } else {
            int t[] = new int[k.length + 1];
            System.arraycopy(k, 0, t, 0, k.length);
            for (int i = 0; i < 52; i++) {
                boolean b = true;
                for (int j : k) {
                    if (j == i) {
                        b = false;
                    }
                }
                if (b) {
                    t[k.length] = i;
                    perebor(t,i+1);
                }
            }
        }
    }

    public long getStat(int i) {
        return combin[i];
    }

    public long getstatCount() {
        return count;
    }

    public double getPersent(int i) {
        return (double)(combin[i]) * 100 / count;
    }

    public void printStat() {
        System.out.println("Вероятность Старшей карты = " + getPersent(0));
        System.out.println("Вероятность Пары = " + getPersent(1));
        System.out.println("Вероятность Двух пар  = " + getPersent(2));
        System.out.println("Вероятность Сета  = " + getPersent(3));
        System.out.println("Вероятность стрита = " + getPersent(4));
        System.out.println("Вероятность Флеша= " + getPersent(5));
        System.out.println("Вероятность ФулХаус = " + getPersent(6));
        System.out.println("Вероятность Каре = " + getPersent(7));
        System.out.println("Вероятность Стритфлефа = " + getPersent(8));
        System.out.println("Вероятность Флешрояль = " + getPersent(9));

    }
}
