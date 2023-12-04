package dao;

import db.MySqlConnection;
import biodata.BiodataTable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ilham
 */

public class BiodataDAO {

    // Metode untuk menyimpan biodata ke database
    public static int insert(BiodataTable biodataTable) {
        int result =-1;
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO biodata (nama, jenis_kelamin, nomor_hp, alamat) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, biodataTable.getNama());
            statement.setString(2, biodataTable.getJenisKelamin());
            statement.setString(3, biodataTable.getNomorHP());
            statement.setString(4, biodataTable.getAlamat());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Metode untuk memperbarui biodata di database
    public static int update(BiodataTable biodataTable) {
        int result =-1;
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE biodata SET nama=?, jenis_kelamin=?, nomor_hp=?, alamat=? WHERE id=?")) {
            statement.setString(1, biodataTable.getNama());
            statement.setString(2, biodataTable.getJenisKelamin());
            statement.setString(3, biodataTable.getNomorHP());
            statement.setString(4, biodataTable.getAlamat());
            statement.setInt(5, biodataTable.getId());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Metode untuk menghapus biodata dari database
    public static int delete(BiodataTable biodataTable) {
        int result =-1;
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM biodata WHERE id=?")) {
            statement.setInt(1, biodataTable.getId());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Metode untuk mendapatkan semua biodata dari database
    public static List<BiodataTable> getAll() {
        List<BiodataTable> biodataList = new ArrayList<>();
        try (Connection connection = MySqlConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM biodata");
             ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    BiodataTable biodataTable = new BiodataTable();
                    biodataTable.setId(resultSet.getInt("id"));
                    biodataTable.setNama(resultSet.getString("nama"));
                    biodataTable.setJenisKelamin(resultSet.getString("jenis_kelamin"));
                    biodataTable.setNomorHP(resultSet.getString("nomor_hp"));
                    biodataTable.setAlamat(resultSet.getString("alamat"));
                    biodataList.add(biodataTable);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return biodataList;
    }
}
