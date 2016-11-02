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
        Player tosh = new Player("Tosh17");
        Player barin = new Player("какашка");
        Table stol = new Table(tosh, barin);
        stol.preflop();
        stol.flop();
        stol.tern();
        stol.river();
        stol.itog();

        Player p[];
        p = new Player[4];
        p[0] = new Player("Tosh17");
        p[1] = new Player("какаш");
        p[2] = new Player("свин");
        p[3] = new Player("лох");
        for (int i = 0; i < 100000; i++) {
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
}
