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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Table stol;
        Player p[];
        p = new Player[3];
        p[0] = new Player("Tosh17");
        p[1] = new Player("Второй");
        p[2] = new Player("Третий");
 /*       p[3] = new Player("четвертый");
        p[4] = new Player("Пятый");
        p[5] = new Player("Шестой");
        p[6] = new Player("Седьмой");
        p[7] = new Player("Восьмой");
        p[8] = new Player("Девятый");         */
        for (int i = 0; i < 1000; i++) {
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

        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        int k[] = {24, 50};
        TerverSimpl c = new TerverSimpl(k);
        c.printStat();
    }
}
