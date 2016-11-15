/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.pocersimulyator;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author shcherbakov
 */
public class Sortirovka {

    ArrayList<Integer> mass = new ArrayList<>();
    long sek = 1000000000;
    static String descr="[eq";

    Sortirovka(ArrayList e) {
        mass=e;
    }

   

    public ArrayList puzyrek() {
        int size = mass.size();
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size - 1; i++) {
                int tempi = mass.get(i);
                int tempj = mass.get(i + 1);
                if (tempi > tempj) {
                    mass.set(i, tempj);
                    mass.set(i + 1, tempi);
                }
            }
        }
       return mass;
    }

    public ArrayList minElement() {
        int size = mass.size();
        int min;
        for (int i = 0; i < size - 1; i++) {
            min = mass.get(i);
            int tempj = i + 1;
            for (int j = i + 1; j < size; j++) {
                int temp = mass.get(j);
                if (temp < min) {
                    min = temp;
                    tempj = j;
                }
                if (tempj > i + 1) {
                    mass.set(tempj, mass.get(i));
                    mass.set(i, min);

                }

            }
            
        }
        return mass;
    }

    public ArrayList sort_mixer() {
        int left = 0;
        int right = mass.size() - 1;
        do {
            //Сдвигаем к концу массива "тяжелые элементы"
            for (int i = left; i < right; i++) {
                int temp = mass.get(i);
                int temp1 = mass.get(i + 1);
                if (temp > temp1) {
                    temp ^= temp1;
                    temp1 ^= temp;
                    temp ^= temp1;
                    mass.set(i, temp1);
                    mass.set(i + 1, temp);
                }
            }
            right--; // уменьшаем правую границу
            //Сдвигаем к началу массива "легкие элементы"
            for (int i = right; i > left; i--) {
                int temp = mass.get(i);
                int temp1 = mass.get(i - 1);
                if (temp < temp1) {
                    temp ^= temp1;
                    temp1 ^= temp;
                    temp ^= temp1;
                    mass.set(i, temp1);
                    mass.set(i - 1, temp);
                }
            }
            left++; // увеличиваем левую границу
        } while (left <= right);

   return mass;
    }

    public ArrayList rascheska() {
        int size = mass.size();
        boolean stop = true;
        int step = (int) (size / 1.247330950103979);
        int s;
        while (step > 1 || stop) {
            stop = false;
            for (int i = 0; i < size - step; i++) {
                int t = mass.get(i);
                int t1 = mass.get(i + step);
                if (t > t1) {
                    mass.set(i, t1);
                    mass.set(i + step, t);
                    stop = true;
                }

            }
            step--;
        }
   return mass;
    }

  
}