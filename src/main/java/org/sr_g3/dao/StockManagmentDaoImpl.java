package org.sr_g3.dao;

import org.sr_g3.model.Product;
import org.sr_g3.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException("Error getting stock", e);
        }

        //return as an arraylist
        return products;

    }

    @Override
    public void addStock(Product product) {

    }

    @Override
    public void updateStock(Product product) {

    }

    @Override
    public void deleteStock(long id) {

    }
}
