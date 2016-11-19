/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.simplsql;

import java.sql.*;
import java.util.Properties;
import java.util.logging.*;
import th.pocersimulyator.*;

/**
 *
 * @author a_scherbakov
 */
public class MyDb {

    private Connection connection = null;
    //URL к базе состоит из протокола:подпротокола://[хоста]:[порта_СУБД]/[БД] и других_сведений
    //private String url = "jdbc:mysql://195.133.146.79/mytest";
    private String url = "jdbc:postgresql://127.0.0.1:5432/mytest";
    //Имя пользователя БД
    private String name = "user";
    //Пароль
    private String password = "654321";
    private Connection con = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;

    public MyDb() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        //Создаём соединение
        Properties properties = new Properties();
        properties.setProperty("user", "user");
        properties.setProperty("password", "654321");
        properties.setProperty("useUnicode", "true");
        properties.setProperty("characterEncoding", "utf8");
        con = DriverManager.getConnection(url, properties);
        stmt = con.createStatement();

    }

    public void selectWin2CardLog(int x, int y) throws SQLException {
        pstmt = con.prepareStatement("select avg(lim.win) from (select * from log Where hand1=? And Hand2=?  limit 1000) as lim");
        pstmt.setInt(1, x);
        pstmt.setInt(2, y);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            System.out.println("Карты " + new ConvertCard().getCard(x) + " & " + new ConvertCard().getCard(y) + " = " + rs.getString(1));
            pstmt = con.prepareStatement("INSERT INTO card2winver " + "VALUES (?,?,?)");
            pstmt.setInt(1, x);
            pstmt.setInt(2, y);
            pstmt.setFloat(3, rs.getFloat(1));
            int rowCount = pstmt.executeUpdate();
        }

    }

    public void select() throws SQLException {
        pstmt = con.prepareStatement("SELECT \n"
                + "  avg(win) FROM \n"
                + "  public.log WHERE  log.hand1=? and log.hand2=?");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }

    }

    public void setKolodBase() throws SQLException {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                pstmt = con.prepareStatement("INSERT INTO `Cards` " + "VALUES (?,?,?,?)");
                pstmt.setInt(1, j + i * 13);
                pstmt.setInt(2, j);
                pstmt.setInt(3, i);
                pstmt.setString(4, new ConvertCard().getCard(j + i * 13));
                int rowCount = pstmt.executeUpdate();
            }
        }
    }

    public void set2SimpVer(int k[], float f[]) throws SQLException {
        pstmt = con.prepareStatement("INSERT INTO card2SimplVer " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
        pstmt.setInt(1, k[0]);
        pstmt.setInt(2, k[1]);
        for (int i = 0; i < 10; i++) {
            pstmt.setFloat(i + 3, f[i]);
        }
        int rowCount = pstmt.executeUpdate();
    }

    public void set5SimpVer(int k[], float f[]) throws SQLException {
        pstmt = con.prepareStatement("INSERT INTO card5SimplVer " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        for (int i = 0; i < 5; i++) {
            pstmt.setInt(i + 1, k[i]);
        }

        for (int i = 0; i < 10; i++) {
            pstmt.setFloat(i + 6, f[i]);
        }
        int rowCount = pstmt.executeUpdate();
    }

    public void gameLog(int k[]) throws SQLException {

        pstmt = con.prepareStatement("INSERT INTO log " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        for (int i = 0; i < 16; i++) {
            pstmt.setInt(i + 1, k[i]);
        }
        int rowCount = pstmt.executeUpdate();
    }

    public void close() throws SQLException {
        con.close();
    }

    public void createBase(int p, int c1, int c2) throws SQLException {

        //String str = "p" + p + "i" + c1 + "j" + c2;
    }

    public void createFlopTable() throws SQLException {
        pstmt = con.prepareStatement("CREATE TABLE flop ("
                + "id smallint NOT NULL,"
                + "flop1 smallint NOT NULL,"
                + "flop2 smallint NOT NULL,"
                + "flop3 smallint NOT NULL)");
        //pstmt.setString(1, str);
        int rowCount = pstmt.executeUpdate();
        int id = 0;
        for (int i1 = 0; i1 < 50; i1++) {
            for (int i2 = i1 + 1; i2 < 51; i2++) {
                for (int i3 = i2 + 1; i3 < 52; i3++) {
                    pstmt = con.prepareStatement("INSERT INTO flop VALUES (?,?,?,?)");
                    int i = 0;
                    pstmt.setInt(++i, ++id);
                    pstmt.setInt(++i, i1);
                    pstmt.setInt(++i, i2);
                    pstmt.setInt(++i, i3);
                    rowCount = pstmt.executeUpdate();
                }

            }
        }
    }

    private boolean notik(int a[]) {
        int x = a.length;
        for (int i = 0; i < x - 1; i++) {
            for (int j = i + 1; j < x; j++) {
                if (a[i] == a[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int flopId(int x, int y, int z) {
        int id = 0;
        for (int i1 = 0; i1 < 50; i1++) {
            for (int i2 = i1 + 1; i2 < 51; i2++) {
                for (int i3 = i2 + 1; i3 < 52; i3++) {
                    id++;
                    if (i1 == x && i2 == y && i3 == z) {
                        return id;
                    }
                }
            }
        }
        return id;
    }

    public boolean logFlop(int p, int x, int y) throws SQLException {
        int id;
        int win;
        ResultSet rs;
        int rowCount;
        int game;
        boolean ok = true;
        String str = "p" + p + "i" + x + "j" + y + "flopwin";

        pstmt = con.prepareStatement("select * INTO temp" + str + " from log where hand1=? And hand2=?");
        pstmt.setInt(1, x);
        pstmt.setInt(2, y);
        rowCount = pstmt.executeUpdate();
        pstmt = con.prepareStatement("Delete from log where hand1=? And hand2=?");
        pstmt.setInt(1, x);
        pstmt.setInt(2, y);

        rowCount = pstmt.executeUpdate();

        for (int i1 = 0; i1 < 50; i1++) {
            for (int i2 = i1 + 1; i2 < 51; i2++) {
                for (int i3 = i2 + 1; i3 < 52; i3++) {
                    int a[] = {x, y, i1, i2, i3};
                    if (notik(a)) {
//                        pstmt = con.prepareStatement("SELECT \n"
//                                + "  id FROM \n"
//                                + "  flop WHERE  flop1=? and flop2=? and flop3=?");
//                        pstmt.setInt(1, i1);
//                        pstmt.setInt(2, i2);
//                        pstmt.setInt(3, i3);
//                        rs = pstmt.executeQuery();
//                        id = 0;
//                        while (rs.next()) {
//                            id = rs.getInt(1);
//                        }
    id = flopId(i1, i2, i3);
                        pstmt = con.prepareStatement("SELECT \n"
                                + "  sum(win),count(win) FROM \n"
                                + "  temp" + str + " WHERE  flop1=? and flop2=? and flop3=?");
                        pstmt.setInt(1, i1);
                        pstmt.setInt(2, i2);
                        pstmt.setInt(3, i3);
                        rs = pstmt.executeQuery();
                        win = 0;
                        game = 0;
                        while (rs.next()) {
                            win = rs.getInt(1);
                            game = rs.getInt(2);
                        }
                        pstmt = con.prepareStatement("SELECT \n"
                                + "  win,game FROM \n"
                                + " " + str + " WHERE  id=?");
                        pstmt.setInt(1, id);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            win += rs.getInt(1);
                            game += rs.getInt(2);
                        }
                        pstmt = con.prepareStatement("UPDATE " + str + " SET win=?,game=? where id=?");

                        pstmt.setInt(1, win);
                        pstmt.setFloat(2, game);
                        pstmt.setFloat(3, id);
                        rowCount = pstmt.executeUpdate();
                        if (win < 10000) {
                            ok = false;
                        }

//                        pstmt = con.prepareStatement("Delete  FROM \n"
//                                + "  temp" + str + " WHERE  flop1=? and flop2=? and flop3=?");
//                        pstmt.setInt(1, i1);
//                        pstmt.setInt(2, i2);
//                        pstmt.setInt(3, i3);
//                        rowCount = pstmt.executeUpdate();
                    }
                }

            }
        }

        pstmt = con.prepareStatement("DROP TABLE temp" + str);
        rowCount = pstmt.executeUpdate();

        return ok;
    }

    public void createFlopTable(int p, int x, int y) throws SQLException {
        String str = "p" + p + "i" + x + "j" + y + "flopwin";
        int id;
        pstmt = con.prepareStatement("CREATE TABLE " + str + " ("
                + "id smallint PRIMARY KEY,"
                + "win smallint,"
                + "game smallint)");
        int rowCount = pstmt.executeUpdate();
        for (int i1 = 0; i1 < 50; i1++) {
            for (int i2 = i1 + 1; i2 < 51; i2++) {
                for (int i3 = i2 + 1; i3 < 52; i3++) {
                    int a[] = {x, y, i1, i2, i3};
                    if (notik(a)) {
//                        pstmt = con.prepareStatement("SELECT \n"
//                                + "  id FROM \n"
//                                + "  public.flop WHERE  flop1=? and flop2=? and flop3=?");
//                        pstmt.setInt(1, i1);
//                        pstmt.setInt(2, i2);
//                        pstmt.setInt(3, i3);
//                        ResultSet rs = pstmt.executeQuery();
//                        id = 0;
//                        while (rs.next()) {
//                            id = rs.getInt(1);
//                        }
                        id = flopId(i1, i2, i3);
                        pstmt = con.prepareStatement("INSERT INTO " + str + " VALUES (?,?,?)");

                        pstmt.setInt(1, id);
                        pstmt.setInt(2, 0);
                        pstmt.setInt(3, 0);
                        rowCount = pstmt.executeUpdate();
                    }
                }
            }

        }
    }

}
