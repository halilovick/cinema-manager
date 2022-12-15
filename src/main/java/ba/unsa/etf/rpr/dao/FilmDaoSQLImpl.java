package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Film;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FilmDaoSQLImpl implements FilmDao {
    private Connection connection;

    public FilmDaoSQLImpl() {
        String server = new String();
        String user = new String();
        String pass = new String();
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            server = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            pass = prop.getProperty("db.password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            this.connection = DriverManager.getConnection(server, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Film getById(int id) {
        String query = "SELECT * FROM film WHERE id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Film film = new Film();
                film.setId(rs.getInt("id"));
                film.setIme(rs.getString("ime"));
                film.setZanr(rs.getString("zanr"));
                film.setTrajanje(rs.getInt("trajanje"));
                film.setCijena(rs.getInt("cijena"));
                film.setBroj_sale(rs.getInt("broj_sale"));
                rs.close();
                return film;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Film add(Film item) {
        String insert = "INSERT INTO film(ime, zanr, trajanje, cijena, broj_sale) VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, item.getIme());
            stmt.setObject(2, item.getZanr());
            stmt.setObject(3, item.getTrajanje());
            stmt.setObject(4, item.getCijena());
            stmt.setObject(5, item.getBroj_sale());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next(); // we know that there is one key
            item.setId(rs.getInt(1)); //set id to return it back
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Film update(Film item) {
        String insert = "UPDATE film SET ime = ?, zanr = ?, trajanje = ?, cijena = ?, broj_sale = ? WHERE id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, item.getIme());
            stmt.setObject(2, item.getZanr());
            stmt.setObject(3, item.getTrajanje());
            stmt.setObject(4, item.getCijena());
            stmt.setObject(5, item.getBroj_sale());
            stmt.setObject(6, item.getId());
            stmt.executeUpdate();
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(int id) {
        String insert = "DELETE FROM film WHERE id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Film> getAll() {
        String query = "SELECT * FROM film";
        List<Film> filmovi = new ArrayList<Film>();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) { // result set is iterator.
                Film film = new Film();
                film.setId(rs.getInt("id"));
                film.setIme(rs.getString("ime"));
                film.setZanr(rs.getString("zanr"));
                film.setTrajanje(rs.getInt("trajanje"));
                film.setCijena(rs.getInt("cijena"));
                film.setBroj_sale(rs.getInt("broj_sale"));
                filmovi.add(film);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace(); // poor error handling
        }
        return filmovi;
    }

    @Override
    public void resetIncrement() {
        String query = "ALTER TABLE film AUTO_INCREMENT = 1";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // poor error handling
        }
    }

    @Override
    public List<Film> searchByTrajanje(int a, int b) {
        if(a > b) throw new IllegalArgumentException("Nevalidan unos!");
        String query = "SELECT * FROM film WHERE trajanje BETWEEN ? AND ?";
        List<Film> filmovi = new ArrayList<Film>();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setObject(1, a);
            stmt.setObject(2, b);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) { // result set is iterator.
                Film film = new Film();
                film.setId(rs.getInt("id"));
                film.setIme(rs.getString("ime"));
                film.setZanr(rs.getString("zanr"));
                film.setTrajanje(rs.getInt("trajanje"));
                film.setCijena(rs.getInt("cijena"));
                film.setBroj_sale(rs.getInt("broj_sale"));
                filmovi.add(film);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace(); // poor error handling
        }
        return filmovi;
    }

    @Override
    public List<Film> searchByZanr(String z) {
        String query = "SELECT * FROM film WHERE zanr = ?";
        List<Film> filmovi = new ArrayList<Film>();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setObject(1, z);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) { // result set is iterator.
                Film film = new Film();
                film.setId(rs.getInt("id"));
                film.setIme(rs.getString("ime"));
                film.setZanr(rs.getString("zanr"));
                film.setTrajanje(rs.getInt("trajanje"));
                film.setCijena(rs.getInt("cijena"));
                film.setBroj_sale(rs.getInt("broj_sale"));
                filmovi.add(film);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace(); // poor error handling
        }
        return filmovi;
    }

    @Override
    public List<String> getAllNames() {
        String query = "SELECT * FROM film";
        List<String> filmovi = new ArrayList<String>();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) { // result set is iterator.
                filmovi.add(rs.getString("ime"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace(); // poor error handling
        }
        return filmovi;
    }

    @Override
    public Film getByIme(String ime) {
        String query = "SELECT * FROM film WHERE ime = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, ime);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Film film = new Film();
                film.setId(rs.getInt("id"));
                film.setIme(rs.getString("ime"));
                film.setZanr(rs.getString("zanr"));
                film.setTrajanje(rs.getInt("trajanje"));
                film.setCijena(rs.getInt("cijena"));
                film.setBroj_sale(rs.getInt("broj_sale"));
                rs.close();
                return film;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
