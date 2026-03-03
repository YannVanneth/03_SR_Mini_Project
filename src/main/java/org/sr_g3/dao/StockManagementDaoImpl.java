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
    public void addStock(Product product) {
        try{
            var conn = ConnectionUtil.getDbCon();
            var ps = conn.prepareStatement("""
                        INSERT INTO v_all_products (id,name,unit_price,quantity,imported_date)
                        VALUES (?,?,?,?,?)
                        """);

            ps.setLong(1,product.getProduct_id());
            ps.setString(2, product.getName());
            ps.setDouble(3, product.getUnit_price());
            ps.setInt(4, product.getQuantity());
            ps.setDate(5, Date.valueOf(product.getImported_date()));

            ps.execute();

            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
    public void deleteStock(long id) {
        try{
            var conn = ConnectionUtil.getDbCon();
            var ps = conn.prepareStatement("""
                       DELETE FROM v_all_products
                       WHERE id = ?;
                       """);

            ps.setLong(1, id);
            ps.execute();

            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> getProductById(Long id) {

        try{
            var conn = ConnectionUtil.getDbCon();
            var ps = conn.prepareStatement("""
                        SELECT  id,name,unit_price,quantity,imported_date 
                        FROM v_all_products 
                        WHERE id = ?
                        """);
            ps.setLong(1, id);

            var rs = ps.executeQuery();

            Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("unit_price"),
                    rs.getInt("quantity"),
                    rs.getDate("imported_date").toLocalDate()
            );
            conn.close();

            return Optional.of(product);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
