package org.sr_g3.dao;

import org.sr_g3.model.Product;
import org.sr_g3.utils.ConnectionUtil;

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

        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException("Error getting stock", e);
        }

        //return as an arraylist
        return products;

    }

    @Override
    public int countTotalRecords() {

        try (Connection conn = ConnectionUtil.getDbCon();) {

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) FROM v_all_products"
            );

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error counting records", e);
        }

        return 0;
    }

    @Override
    public void addStock(Product product) {

        try {
            Connection conn = ConnectionUtil.getDbCon();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO products(name,unit_price,quantity,imported_date) VALUES ( ?, ?, ?, ?);");
            ps.setString(1, product.getName());
            ps.setDouble(2,product.getUnit_price());
            ps.setInt(3, product.getQuantity());
            ps.setDate(4, java.sql.Date.valueOf(product.getImported_date()));
            int rs = ps.executeUpdate();

            System.out.println("Row Added: " + rs);

            conn.close();

        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException("Error getting stock", e);
        }

        try {
            Connection conn = ConnectionUtil.getDbCon();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO products(name,unit_price,quantity,imported_date) VALUES ( ?, ?, ?, ?);");
            ps.setString(1, product.getName());
            ps.setDouble(2,product.getUnit_price());
            ps.setInt(3, product.getQuantity());
            ps.setDate(4, java.sql.Date.valueOf(product.getImported_date()));
            ps.executeUpdate();
            conn.close();

        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException("Error getting stock", e);
        }

    }

    @Override
    public void updateStock(Long id, Product product) {

    }

    @Override
    public void updateStock(Product product) {

        try {
            Connection conn = ConnectionUtil.getDbCon();
            PreparedStatement ps = conn.prepareStatement("""
                    UPDATE products
                    SET name = ?, unit_price=?, quantity=?, imported_date=?
                    where id=?;""");
            ps.setString(1, product.getName());
            ps.setDouble(2,product.getUnit_price());
            ps.setInt(3, product.getQuantity());
            ps.setDate(4, java.sql.Date.valueOf(product.getImported_date()));
            ps.setLong(5, product.getProduct_id());


            ps.executeUpdate();
            conn.close();

        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException("Error Updating Stock", e);
        }

    }

    @Override
    public void deleteStockById(long id) {

        try {
            Connection conn = ConnectionUtil.getDbCon();
            PreparedStatement ps = conn.prepareStatement("""
                    DELETE FROM products
                    where id = ?;""");
            ps.setLong(1, id);

            int rs = ps.executeUpdate();
            conn.close();

        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException("Error Deleting Stock", e);
        }

    }

    @Override
    public int totalRow() {

        try {
            Connection conn = ConnectionUtil.getDbCon();
            PreparedStatement ps = conn.prepareStatement("""
                    SELECT count(*) from v_all_products;""");

            ResultSet rs = ps.executeQuery();

            int totalRow = rs.getInt(1);

            conn.close();

            return totalRow;

        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException("Error Deleting Stock", e);
        }

    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Product>> searchByName(String name) {
        return Optional.empty();
    }


}
