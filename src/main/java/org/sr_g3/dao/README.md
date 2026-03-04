# DAO


abstract method to fetch stock and return as a list of product objects:
```java
List<Product> fetchStock(int limit, int offset);
```



Example of using DAO to fetch stock:
```java
        StockManagementDao dao = new StockManagmentDaoImpl();
        //use an arraylist to get all tuple from fetch stock
        List<Product> products = dao.fetchStock(5, 0);
        for (Product product : products) {
            System.out.println(product);
        }
```