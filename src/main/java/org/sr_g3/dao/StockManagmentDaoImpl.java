package org.sr_g3.dao;

import org.sr_g3.model.Product;
import org.sr_g3.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StockManagmentDaoImpl implements StockManagementDao {


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

        }catch (SQLException e){
            throw new RuntimeException("Error getting stock", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //return as an arraylist
        return products;

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

    }

    @Override
    public void updateStock(Product product) {

    }

    @Override
    public void deleteStock(long id) {

    }
    @Override
    public Product getStockById(long id) {
        Product p = null;
        try {
            Connection conn = ConnectionUtil.getDbCon();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM products WHERE id = ?");
            ps.setInt(1, (int)id);

            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()) {
                p = new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("unit_price"),
                    resultSet.getInt("quantity"),
                    resultSet.getDate("imported_date").toLocalDate()
                );

            }
            return p;

        } catch (SQLException e) {

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return p;
    }

}
