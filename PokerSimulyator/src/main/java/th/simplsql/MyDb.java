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

    public void select() throws SQLException {
        pstmt = con.prepareStatement("SELECT \n"
                + "  log.wincomb, \n"
                + "  log.winhand1, \n"
                + "  log.winhand2, \n"
                + "  log.winhand3, \n"
                + "  log.winhand4, \n"
                + "  log.winhand5  \n"
                + "FROM \n"
                + "  public.log WHERE  log.flop1=23 and log.flop2=31 AND log.flop3=49 and log.river=9");
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
}
