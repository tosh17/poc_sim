package th.pocersimulyator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import th.simplsql.MyDb;
import th.sql.DBHolder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author a_scherbakov
 */
public class Table {

    Cards karts;

    private final List<Player> players = new ArrayList<>();
    private final List<OneCard> onTable = new ArrayList<>();

    Table(Player play1, Player play2) {
        System.out.println("Добро пожаловать на игру 2 человека");
        System.out.println("  Игрок1 : " + play1.getName() + "    Игрок2 : " + play2.getName());
        players.add(play1);
        players.add(play2);
        play1.setStatus(true);
        play2.setStatus(true);

        System.out.println("Перемешиваем колоду");
        karts = new Cards();
        karts.sort();

    }

    Table(Player play[]) {
//        System.out.println();
//        System.out.println();
//        System.out.println("Добро пожаловать на игру " + play.length + " человека");
        for (Player p : play) {
            players.add(p);
            p.setStatus(true);
            p.resetHand();
        }
//        System.out.println("Открываем новую и Перемешиваем колоду");
        karts = new Cards();
        karts.sort();
    }

    public void preflop() {
//        System.out.println("============== Префлоп=============");
//        System.out.println("Сдача карт ");
        for (int i = 0; i < 2; i++) {
            for (Player p : players) {
                p.giveCard(karts.getCard());
            }
        }
        showAllHands();
    }

    public void preflop(int x, int y) {
//        System.out.println("============== Префлоп=============");
//        System.out.println("Сдача карт ");
        players.get(0).giveCard(karts.getCard(x));
        players.get(0).giveCard(karts.getCard(y));
        for (int i = 0; i < 2; i++) {
            for (Player p : players) {
                if (p != players.get(0)) {
                    p.giveCard(karts.getCard());
                }
            }
        }
        showAllHands();
    }

    public void flop() {
//        System.out.println();
//        System.out.println("============== Флоп=================");
        onTable.add(karts.getCard());
        onTable.add(karts.getCard());
        onTable.add(karts.getCard());
        for (Player p : players) {
            p.givetable(onTable.get(0), onTable.get(1), onTable.get(2));
        }
        showAllHandsPlus();
        showOnTables();
    }

    public void flop(int x, int y, int z) {
        System.out.println();
        System.out.println("============== Флоп=================");
        onTable.add(karts.getCard());
        onTable.add(karts.getCard());
        onTable.add(karts.getCard());
        for (Player p : players) {
            p.givetable(onTable.get(0), onTable.get(1), onTable.get(2));
        }
        showAllHandsPlus();
        showOnTables();
    }

    public void tern() {
//        System.out.println();
//        System.out.println("============== Терн=================");
        onTable.add(karts.getCard());
        for (Player p : players) {
            p.givetable(onTable.get(3));
        }
        showAllHandsPlus();
        showOnTables();
    }

    public void river() {
//        System.out.println();
//        System.out.println("============== Ривер=================");
        onTable.add(karts.getCard());
        for (Player p : players) {
            p.givetable(onTable.get(4));
        }
        showAllHandsPlus();
        showOnTables();
    }

    public void itog() throws ClassNotFoundException, SQLException {
        Player lider;
        int wincost = 0;
        for (int i = 0; i < players.size() - 1; i++) {
            while (i < players.size() - 1 && !players.get(i).getStatus()) {
                i++;
            }
            for (int j = i + 1; j < players.size(); j++) {
                int b = players.get(i).GetCombination().eq(players.get(j).GetCombination());
                switch (b) {
                    case -1:
                        players.get(i).setStatus(false);
                        j = players.size();

                        break;
                    case 1:
                        players.get(j).setStatus(false);
                        break;

                }

            }
        }
//        System.out.println("============== Итог Игры=================");
        int ilog[] = new int[16];
        ilog[0] = players.size();
        for (Player p : players) {
            if (p.getStatus()) {
                Combination tempComb = p.GetCombination();
//                System.out.println("Игрок " + p.getName() + " победил с  " + tempComb.getCombName());
                p.win();
                ilog[15] = tempComb.getCombinate();
                //забиваем в лог выигрушную комбинацию
                for (int tempIndex = 10; tempIndex < 15; tempIndex++) {
                    ilog[tempIndex] = tempComb.getBestCard(tempIndex - 10);
                }
            }

        }

     // for (Player p : players) {
       Player p = players.get(0);
        if (p.getStatus()) {
            ilog[1] = 1;
        } else {
            ilog[1] = 0;
        }
        //забиваем текущую руку
        for (int tempIndex = 2; tempIndex < 9; tempIndex++) {
            ilog[tempIndex] = p.getCodeHand(tempIndex - 2);
        }
        ilog[9] = p.GetCombination().getCombinate();

        //структуриреем данные
        ArrayList<Integer> mass = new ArrayList<>();
        //рука
        mass.add(ilog[2]);
        mass.add(ilog[3]);
        mass = new Sortirovka(mass).puzyrek();
        ilog[2] = mass.get(0);
        ilog[3] = mass.get(1);
        mass.clear();
//            флоп
        for (int tempIndex = 4; tempIndex < 7; tempIndex++) {
            mass.add(ilog[tempIndex]);
        }
        mass = new Sortirovka(mass).puzyrek();
        for (int tempIndex = 4; tempIndex < 7; tempIndex++) {
            ilog[tempIndex] = mass.get(tempIndex - 4);
        }
        mass.clear();
//            выигравшая рука
        for (int tempIndex = 10; tempIndex < 15; tempIndex++) {
            mass.add(ilog[tempIndex]);
        }
        mass = new Sortirovka(mass).puzyrek();
        for (int tempIndex = 10; tempIndex < 15; tempIndex++) {
            ilog[tempIndex] = mass.get(tempIndex - 10);
        }
        mass.clear();

        try (Connection con = DBHolder.getConnection();){
                gameLog(ilog, con);
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
     //  }
    }

    private static void gameLog(int k[], Connection con) throws SQLException {

        PreparedStatement pstmt = con.prepareStatement("INSERT INTO log " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        for (int i = 0; i < 16; i++) {
            pstmt.setInt(i + 1, k[i]);
        }
        int rowCount = pstmt.executeUpdate();
    }

    private void showAllHands() {
//        for (Player p : players) {
//            System.out.println("Игрок " + p.getName() + "  имеет ");
//            System.out.println(p.showHand());
//        }
    }

    private void showOnTables() {
//        System.out.println("На столе");
//        for (OneCard c : onTable) {
//            System.out.print(c.getInfo());
//        }
//        System.out.println();
    }

    private void showAllHandsPlus() {
//        for (Player p : players) {
//            System.out.println("Игрок " + p.getName() + "  имеет ");
//            System.out.println(p.showHand());
//            System.out.println("  Имеет комбинацию    " + p.GetCombination().getCombName());
//            // p.showTerVer();
//
//        }
    }

}
