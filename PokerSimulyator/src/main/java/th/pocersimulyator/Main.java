/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.pocersimulyator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import th.simplsql.*;
import th.sql.*;

/**
 *
 * @author a_scherbakov
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBHolder.nop();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Closing connection pool...");
                DBHolder.close();
            }
        });

// TODO code application logic here
        playGame(5, 200000);

   
        //  selectwin2();
    }

    private static void playGame(int play, int count) throws ClassNotFoundException, SQLException {
        Table stol;
        Player p[];
        p = new Player[play];
        for (int i = 1; i <= play; i++) {
            p[i - 1] = new Player("====№" + i + "=====");
        }
        int x = 0;
        int y = 5;
        int z=0;
   //     createlogflop(p.length, x, y);
        do {
            z++;
            for (int i = 0; i < count; i++) {
                System.out.println("===="+z+"==================" + i + "====================");
                stol = new Table(p);
                for(int r=0;r<z%10;r++){stol.karts.sort();}
                stol.preflop(x, y);
                stol.flop();
                stol.tern();
                stol.river();
                stol.itog();
            }
        }while (!logflop(p.length, x, y));

        //   System.out.println();
        //   for (Player i : p) {
        //        System.out.println("Игрок " + i.getName() + " имеет " + i.getWin() + " побед");
        //     }
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

    private static void creba() throws SQLException, ClassNotFoundException {
        MyDb base = new MyDb();
        base.createBase(3, 0, 20);
        base.close();
    }

    private static void selectwin2() throws ClassNotFoundException, SQLException {
        MyDb base = new MyDb();
        for (int x = 0; x < 51; x++) {
            for (int y = x + 1; y < 52; y++) {
                base.selectWin2CardLog(x, y);
            }
        }

        base.close();
    }

    private static boolean logflop(int p, int x, int y) throws ClassNotFoundException, SQLException {
        boolean b;
        MyDb base = new MyDb();
        b = base.logFlopnew(p, x, y);
        base.close();
        return b;
    }

    private static void createlogflop(int p, int x, int y) throws ClassNotFoundException, SQLException {
        MyDb base = new MyDb();
        base.createFlopTable(p, x, y);
        base.close();
    }
}
