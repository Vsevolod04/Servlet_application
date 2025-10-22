package com.sorkin.ssp10.myclasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private final int id;
    private final String login;
    private final String password;
    private final String role;

    static public User getUser(String login, String password) {
        String query = "SELECT * "
                + "FROM users WHERE login = ? "
                + "AND password = ?";

        try (Connection conn = new DbConnection().getConn()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, login);
            stmt.setString(2, password);
            ResultSet res = stmt.executeQuery();
            if (!res.next()) {
                return null;
            } else {
                return new User(res.getInt("id"), res.getString("login"),
                        res.getString("password"), res.getString("role"));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        } catch (Exception ex) {
            System.err.println("Нет доступа к БД");
            return null;
        }

    }

    static public User createUser(String login, String password, String role) {
        String query = "INSERT INTO users (login, password, role) VALUES "
                + "(?, ?, ?)";

        try (Connection conn = new DbConnection().getConn()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        } catch (Exception ex) {
            System.err.println("Нет доступа к БД");
            return null;
        }

        return getUser(login, password);
    }

    static public Boolean exists(String login) {
        String query = "SELECT * "
                + "FROM users WHERE login = ? ";

        try (Connection conn = new DbConnection().getConn()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, login);
            ResultSet res = stmt.executeQuery();
            if (!res.next()) {
                return Boolean.FALSE;
            } else {
                return Boolean.TRUE;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        } catch (Exception ex) {
            System.err.println("Нет доступа к БД");
            return null;
        }

    }

    private User(int id, String login, String password, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRole() {
        return this.role;
    }

}
