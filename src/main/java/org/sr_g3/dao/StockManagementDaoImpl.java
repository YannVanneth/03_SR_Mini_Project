package org.sr_g3.dao;

import org.sr_g3.model.Product;
import org.sr_g3.utils.ConnectionUtil;
import org.sr_g3.utils.Console;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StockManagementDaoImpl implements StockManagementDao {

    @Override
    public List<Product> fetchStock(int limit, int offset) {

        List<Product> products = new ArrayList<>();

        try {
            Connection conn = ConnectionUtil.getDbCon();
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, unit_price, quantity, imported_date FROM v_all_products ORDER BY id LIMIT ? OFFSET ?;");
            ps.setInt(1, limit); // items per query
            ps.setInt(2, offset);// skip n items per query

            ResultSet rs = ps.executeQuery();

            //iterate from db and add to arraylist as tuple
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("unit_price"),
                        rs.getInt("quantity"),
                        rs.getDate("imported_date").toLocalDate()
                );
                //add per object to an arraylist
                products.add(product);
            }

            conn.close();

        }catch (Exception e){
            throw new RuntimeException("Error getting stock", e);
        }

        //return as an arraylist
        return products;

    }
    public long getNextProductId() {
        try (Connection conn = ConnectionUtil.getDbCon();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT nextval('products_id_seq')")) {   // ← change sequence name if different

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return -1; // fallback
        } catch (Exception e) {
            System.err.println("Error getting next ID: " + e.getMessage());
            return -1;
        }
    }
    @Override
    public int countTotalRecords() {
        return 0;
    }

    @Override
    public void addStock(Product product) {
        try {
            var conn = ConnectionUtil.getDbCon();
            var ps = conn.prepareStatement("""
            INSERT INTO products (name, unit_price, quantity, imported_date)
            VALUES (?, ?, ?, ?)
            RETURNING id
            """);

            ps.setString(1, product.getName());
            ps.setDouble(2, product.getUnit_price());
            ps.setInt(3, product.getQuantity());
            ps.setDate(4, Date.valueOf(product.getImported_date()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long newId = rs.getLong("id");
                product.setProduct_id(newId);  // ← update the object with real ID
            }

            conn.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to add product: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateStock(Long id, Product product) {
        try{

            var conn = ConnectionUtil.getDbCon();
            var ps = conn.prepareStatement("""
                       UPDATE v_all_products
                       SET name = ?,
                           unit_price = ?,
                           quantity = ?,
                           imported_date = ?
                       WHERE id = ?;
                       """);

            ps.setString(1, product.getName());
            ps.setDouble(2, product.getUnit_price());
            ps.setInt(3, product.getQuantity());
            ps.setDate(4, Date.valueOf(product.getImported_date()));
            ps.setLong(5, product.getProduct_id());

            ps.executeUpdate();

            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteStockById(long id) {

    }

//    @Override
//    public void deleteStock(long id) {
//        try{
//            var conn = ConnectionUtil.getDbCon();
//            var ps = conn.prepareStatement("""
//                       DELETE FROM v_all_products
//                       WHERE id = ?;
//                       """);
//
//            ps.setLong(1, id);
//            ps.execute();
//
//            conn.close();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public Optional<Product> getProductById(Long id) {
        try (Connection conn = ConnectionUtil.getDbCon();
             PreparedStatement ps = conn.prepareStatement("""
             SELECT id, name, unit_price, quantity, imported_date 
             FROM v_all_products 
             WHERE id = ?
             """)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Product product = new Product();
                product.setProduct_id(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setImported_date(rs.getDate("imported_date").toLocalDate());

                return Optional.of(product);
            } else {
                return Optional.empty();  // ID not found
            }

        } catch (Exception e) {
            Console.printErrorMessage("Error fetching product ID " + id + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public int totalRow() {
        return 0;
    }

    @Override
    public Optional<List<Product>> searchByName(String name) {

        try{
            var conn = ConnectionUtil.getDbCon();
            var ps = conn.prepareStatement("""
                        SELECT id,name,unit_price,quantity,imported_date 
                        FROM v_all_products
                        WHERE name LIKE ?
                        """);

            var rs = ps.executeQuery();

            var products = new ArrayList<Product>();

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("unit_price"),
                        rs.getInt("quantity"),
                        rs.getDate("imported_date").toLocalDate()
                );
                //add per object to an arraylist
                products.add(product);
            }

            conn.close();

            return Optional.of(products);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
