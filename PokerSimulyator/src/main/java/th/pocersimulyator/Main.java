/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.pocersimulyator;

import java.sql.SQLException;
import th.simplsql.*;

/**
 *
 * @author a_scherbakov
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // TODO code application logic here
         playGame(5,1000);
//        terVerSimpl5card();
    }

    private static void playGame(int play, int count) throws ClassNotFoundException, SQLException {
        Table stol;
        Player p[];
        p = new Player[play];
        for (int i = 1; i <= play; i++) {
            p[i - 1] = new Player("====№" + i + "=====");
        }
        for (int i = 0; i < count; i++) {
            System.out.println("======================" + i + "====================");
            stol = new Table(p);
            stol.preflop();
            stol.flop();
            stol.tern();
            stol.river();
            stol.itog();
        }
        System.out.println();
        for (Player i : p) {
            System.out.println("Игрок " + i.getName() + " имеет " + i.getWin() + " побед");
        }

    }

    private static void terVerSimpl2card() {
        ConvertCard c = new ConvertCard();
        for (int i = 0; i < 51; i++) {
            for (int j = i + 1; j < 52; j++) {
                System.out.println("  ");
                System.out.println("  ");
                System.out.println(c.getCard(i) + " " + c.getCard(j));
                System.out.println("  ");

                int k[] = {i, j};
                TerverSimpl cc = new TerverSimpl(k);
                cc.printStat();
            }
        }
    }

    private static void terVerSimpl5card() throws ClassNotFoundException, SQLException {
        MyDb base = new MyDb();
        ConvertCard c = new ConvertCard();
        for (int i1 = 25; i1 < 48; i1++) {
            for (int i2 = i1 + 1; i2 < 49; i2++) {
                for (int i3 = i2 + 1; i3 < 50; i3++) {
                    for (int i4 = i3 + 1; i4 < 51; i4++) {
                        for (int i5 = i4 + 1; i5 < 52; i5++) {
                            
                            System.out.println("  ");
                            System.out.println("  ");
                            System.out.println(c.getCard(i1) + " " + c.getCard(i2) + " " + c.getCard(i3) + " " + c.getCard(i4) + " " + c.getCard(i5));
                            System.out.println("  ");

                            int k[] = {i1, i2, i3, i4, i5};
                            TerverSimpl cc = new TerverSimpl(k);
                            float f[] = new float[10];
                            for (int z = 0; z < 10; z++) {
                                f[z] = (float) cc.getPersent(z);
                            }
                            base.set5SimpVer(k, f);
                            //  cc.printStat();
                        }
                    }
                }
            }
        }
        base.close();
    }
}
