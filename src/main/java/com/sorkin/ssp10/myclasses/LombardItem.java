package com.sorkin.ssp10.myclasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class LombardItem {

    int id;
    String client;
    String item;
    String cost;   //decimal (10,2)

    public static ArrayList<LombardItem> getAllItems() {
        String query = "SELECT * FROM lombard";
        ArrayList<LombardItem> lombard = new ArrayList();

        try (Connection conn = new DbConnection().getConn()) {
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                LombardItem item = new LombardItem(res.getInt("id"),
                        res.getString("client"), res.getString("item"),
                        res.getString("cost"));
                lombard.add(item);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        } catch (Exception ex) {
            System.err.println("Нет доступа к БД");
            return null;
        }

        return lombard;
    }

    public static LombardItem getItem(int id) {
        String query = "SELECT * FROM lombard WHERE id = ?";

        try (Connection conn = new DbConnection().getConn()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                LombardItem item = new LombardItem(res.getInt("id"),
                        res.getString("client"), res.getString("item"),
                        res.getString("cost"));
                return item;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        } catch (Exception ex) {
            System.err.println("Нет доступа к БД");
            return null;
        }
    }

    public static boolean addItem(String client, String item, String cost) {
        String query = "INSERT INTO lombard (client, item, cost) VALUES "
                + "(?, ?, ?)";

        try (Connection conn = new DbConnection().getConn()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, client);
            stmt.setString(2, item);
            stmt.setString(3, cost);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        } catch (Exception ex) {
            System.err.println("Нет доступа к БД");
            return false;
        }
        return true;
    }

    public static boolean deleteItem(int id) {
        String query = "DELETE FROM lombard WHERE id = ?";

        try (Connection conn = new DbConnection().getConn()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        } catch (Exception ex) {
            System.err.println("Нет доступа к БД");
            return false;
        }
        return true;
    }

    public static boolean updateItem(int id, String client, String item, String cost) {
        String query = "UPDATE lombard SET "
                + "client = ?, item = ?, cost = ? "
                + "WHERE id = ?";
        boolean isUpd = false;

        try (Connection conn = new DbConnection().getConn()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, client);
            stmt.setString(2, item);
            stmt.setString(3, cost);
            stmt.setInt(4, id);

            isUpd = (stmt.executeUpdate() == 1);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        } catch (Exception ex) {
            System.err.println("Нет доступа к БД");
            return false;
        }
        return isUpd;
    }

    private LombardItem(int id, String client, String item, String cost) {
        this.id = id;
        this.client = client;
        this.item = item;
        this.cost = cost;
    }

    public int getId() {
        return this.id;
    }

    public String getClient() {
        return this.client;
    }

    public String getItem() {
        return this.item;
    }

    public String getCost() {
        return this.cost;
    }
}
