package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import koneksi.Koneksi; 
import model.Booking;  

public class BookingController {


    
    private String generateBookingCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        while (sb.length() < 8) {
            int index = (int) (rnd.nextFloat() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }


    public String createBooking(Booking booking) {
        String kodeBooking = generateBookingCode();
        String sql = "INSERT INTO bookings (Kode, Nama, Tlp, Tgl, Jam, Durasi, Jenis, Detail, FND, Total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, kodeBooking);
            stmt.setString(2, booking.getNama());
            stmt.setString(3, booking.getTlp());
            stmt.setString(4, booking.getTgl());
            stmt.setString(5, booking.getJam());
            stmt.setInt(6, booking.getDurasi());
            stmt.setString(7, booking.getJenis());
            stmt.setString(8, booking.getDetail());
            stmt.setString(9, booking.getFnd());
            stmt.setInt(10, booking.getTotal());
            
            stmt.executeUpdate();
            return kodeBooking;
            
        } catch (SQLException e) {
            System.err.println("SQL Error saat membuat booking: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookingList = new ArrayList<>();
        String sql = "SELECT * FROM bookings ORDER BY Id ASC";
        
        try (Connection conn = Koneksi.getKoneksi();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("Id"));
                booking.setKode(rs.getString("Kode"));
                booking.setNama(rs.getString("Nama"));
                booking.setTlp(rs.getString("Tlp"));
                booking.setTgl(rs.getString("Tgl"));
                booking.setJam(rs.getString("Jam"));
                booking.setDurasi(rs.getInt("Durasi"));
                booking.setJenis(rs.getString("Jenis"));
                booking.setDetail(rs.getString("Detail"));
                booking.setFnd(rs.getString("FND"));
                booking.setTotal(rs.getInt("Total"));
                bookingList.add(booking);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error saat mengambil semua booking: " + e.getMessage());
            e.printStackTrace();
        }
        return bookingList;
    }

    public Booking getBookingByCode(String kode) {
        String sql = "SELECT * FROM bookings WHERE Kode = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Booking booking = new Booking();
                booking.setNama(rs.getString("Nama"));
                booking.setTgl(rs.getString("Tgl"));
                booking.setJam(rs.getString("Jam"));
                booking.setDurasi(rs.getInt("Durasi"));
                booking.setJenis(rs.getString("Jenis"));
                booking.setDetail(rs.getString("Detail"));
                booking.setFnd(rs.getString("FND"));
                return booking;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateBooking(String kode, Booking booking) {
        String sql = "UPDATE bookings SET Nama=?, Tgl=?, Jam=?, Durasi=?, Jenis=?, Detail=?, FND=? WHERE Kode=?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, booking.getNama());
            stmt.setString(2, booking.getTgl());
            stmt.setString(3, booking.getJam());
            stmt.setInt(4, booking.getDurasi());
            stmt.setString(5, booking.getJenis());
            stmt.setString(6, booking.getDetail());
            stmt.setString(7, booking.getFnd());
            stmt.setString(8, kode);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBooking(String kode) {
        String sql = "DELETE FROM bookings WHERE Kode = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}