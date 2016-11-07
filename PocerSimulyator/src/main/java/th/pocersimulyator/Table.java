package th.pocersimulyator;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println();
        System.out.println();
        System.out.println("Добро пожаловать на игру " + play.length + " человека");
        for (Player p : play) {
            players.add(p);
            p.setStatus(true);
            p.resetHand();
        }
        System.out.println("Открываем новую и Перемешиваем колоду");
        karts = new Cards();
        karts.sort();
    }

    public void preflop() {
        System.out.println("============== Префлоп=============");
        System.out.println("Сдача карт ");
        for (int i = 0; i < 2; i++) {
            for (Player p : players) {
                p.giveCard(karts.getCard());
            }
        }
        showAllHands();
    }

    public void flop() {
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
        System.out.println();
        System.out.println("============== Терн=================");
        onTable.add(karts.getCard());
        for (Player p : players) {
            p.givetable(onTable.get(3));
        }
        showAllHandsPlus();
        showOnTables();
    }

    public void river() {
        System.out.println();
        System.out.println("============== Ривер=================");
        onTable.add(karts.getCard());
        for (Player p : players) {
            p.givetable(onTable.get(4));
        }
        showAllHandsPlus();
        showOnTables();
    }

    public void itog() {
        Player lider;
        int wincost = 0;
        for (int i = 0; i < players.size()-1; i++) {
            while(i < players.size()-1 && !players.get(i).getStatus()) i++;
            for (int j = i+1; j < players.size(); j++) {
                int b = players.get(i).GetCombination().eq(players.get(j).GetCombination());
                switch (b) {
                    case -1:
                        players.get(i).setStatus(false);
                        j=players.size();
                       
                        
                        break;
                    case 1:
                        players.get(j).setStatus(false);
                        break;

                }

            }
        }
        System.out.println("============== Итог иИгры=================");

        for (Player p : players) {
            if (p.getStatus()) {
                System.out.println("Игрок " + p.getName() + " победил с  " + p.GetCombination().getCombName());
                p.win();
            }
        }
    }

    private void showAllHands() {
        for (Player p : players) {
            System.out.println("Игрок " + p.getName() + "  имеет ");
            System.out.println(p.showHand());
        }
    }

    private void showOnTables() {
        System.out.println("На столе");
        for (OneCard c : onTable) {
            System.out.print(c.getInfo());
        }
        System.out.println();
    }

    private void showAllHandsPlus() {
        for (Player p : players) {
            System.out.println("Игрок " + p.getName() + "  имеет ");
            System.out.println(p.showHand());
            System.out.println("  Имеет комбинацию    " + p.GetCombination().getCombName());
            p.showTerVer();

        }
    }

}
