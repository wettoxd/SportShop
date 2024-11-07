package com.example.sportshop;




import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBase {

    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "sportshop";
    private final String LOGIN = "root";
    private final String PASS = "";
    private Connection dbConn = null;
    public void updateProduct(int id, String name, int price, int stock_quantity, int supplier_id, String description, String photo) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE products SET name = ?, price = ?, stock_quantity = ?, supplier_id = ?, description = ?, photo = ? WHERE id = ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, name);
        prSt.setInt(2, price);
        prSt.setInt(3, stock_quantity);
        prSt.setInt(4, supplier_id);
        prSt.setString(5, description);
        prSt.setString(6, photo);
        prSt.setInt(7, id);
        prSt.executeUpdate();
    }
    public void updateSupplier(int id, String name, String email, String phone_number, String description) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE suppliers SET name = ?, email = ?, phone_number = ?, description = ? WHERE id = ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, name);
        prSt.setString(2, email);
        prSt.setString(3, phone_number);
        prSt.setString(4, description);
        prSt.setInt(5, id);
        prSt.executeUpdate();
    }

    public void updateUser(int id, String FIO, String login, String password, String email) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE users SET FIO = ?, login = ?, pass = ?, email = ? WHERE id = ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, FIO);
        prSt.setString(2, login);
        prSt.setString(3, password);
        prSt.setString(4, email);
        prSt.setInt(5, id);
        prSt.executeUpdate();
    }
    public void addSupplier(String name, String email, String phone_number, String description) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO suppliers (name, email, phone_number, description) VALUES (?, ?, ?, ?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, name);
        prSt.setString(2, email);
        prSt.setString(3, phone_number);
        prSt.setString(4, description);
        prSt.executeUpdate();
    }

    public void addUser(String FIO, String login, String password, String email) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO users (FIO, login, pass, email) VALUES (?, ?, ?, ?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, FIO);
        prSt.setString(2, login);
        prSt.setString(3, password);
        prSt.setString(4, email);
        prSt.executeUpdate();
    }
    public void addProduct(String name, int price, int stock_quantity, int supplier_id, String description, String photo) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO products (name, price, stock_quantity, supplier_id, description, photo) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, name);
        prSt.setInt(2, price);
        prSt.setInt(3, stock_quantity);
        prSt.setInt(4, supplier_id);
        prSt.setString(5, description);
        prSt.setString(6, photo);
        prSt.executeUpdate();
    }




    public void deleteProduct(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM products WHERE id = ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, id);
        prSt.executeUpdate();
    }
    public void deleteUser(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM users WHERE id = ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, id);
        prSt.executeUpdate();
    }
    public void deleteSupplier(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM suppliers WHERE id = ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, id);
        prSt.executeUpdate();
    }


    Connection getDbConnection() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:13306/sportshop", "javafxTest", "changeme");
        return con;
    }
    public ArrayList<ArrayList<String>> getAllProducts() throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM products");
        ArrayList<ArrayList<String>> records = new ArrayList<>();
        while (res.next()) {
            ArrayList<String> product = new ArrayList<>();
            product.addAll(Arrays.asList(Integer.toString(res.getInt("id")), res.getString("name"), Integer.toString(res.getInt("price")), Integer.toString(res.getInt("stock_quantity")), Integer.toString(res.getInt("supplier_id")), res.getString("description"), res.getString("photo")));
            records.add(product);
        }
        System.out.println(records);
        return records;
    }

    public ArrayList<ArrayList<String>> getAllUsers() throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM users");
        ArrayList<ArrayList<String>> records = new ArrayList<>();
        while (res.next()) {
            ArrayList<String> product = new ArrayList<>();
            product.addAll(Arrays.asList(Integer.toString(res.getInt("id")),res.getString("FIO"), res.getString("login"), res.getString("pass"), res.getString("email")));
            records.add(product);
        }
        System.out.println(records);
        return records;
    }
    public ArrayList<ArrayList<String>> getAllSuppliers() throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM suppliers");
        ArrayList<ArrayList<String>> records = new ArrayList<>();
        while (res.next()) {
            ArrayList<String> product = new ArrayList<>();
            product.addAll(Arrays.asList(Integer.toString(res.getInt("id")),res.getString("name"), res.getString("email"), res.getString("phone_number"), res.getString("description")));
            records.add(product);
        }
        System.out.println(records);
        return records;
    }


    public int getUser(String login, String password) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getDbConnection().prepareStatement("SELECT role_id FROM users where login=? and pass=? group by role_id");
        statement.setString(1, login);
        statement.setString(2, password);
        ResultSet res = statement.executeQuery();


        int role = 0;
        while (res.next()) {

            role = res.getInt("role_id");
        }
        return role;
    }

    public void insertUser(String FIO, String login, String password, String email) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO users  VALUES (null,?,?,?,?,1)";

        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, FIO);
        prSt.setString(2, login);
        prSt.setString(3, password);
        prSt.setString(4, email);
        prSt.executeUpdate();

    }

    public ArrayList<String> getProduct() throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM products ORDER BY id");
        ArrayList<String> products = new ArrayList<>();
        while (res.next()) {
            products.add(res.getString("name"));
        }

        return products;
    }

    public int getProduct(String product_name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM products where name='" + product_name + "' ORDER BY id";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        int id = 0;
        while (res.next())
            id = res.getInt("id");

        return id;
    }

    public String getCategory(Integer id_category) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM сategory_of_products where id='" + id_category + "'");
        String category = "";
        while (res.next()) {
            category=(res.getString("name"));
        }

        return category;
    }
    public ArrayList<String> getCategories() throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM сategory_of_products ORDER BY id");
        ArrayList<String> category = new ArrayList<>();
        while (res.next()) {
            category.add(res.getString("name"));
        }

        return category;
    }

    public ArrayList<String> getPickupPoint() throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM pickuppoints ORDER BY id");
        ArrayList<String> pickuppoint = new ArrayList<>();
        while (res.next()) {
            pickuppoint.add(res.getString("name"));
        }

        return pickuppoint;
    }



    public int getIdPickupPoint(String name) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT id FROM pickuppoints where name='" + name + "'");
        int pickuppointt = 0;
        while (res.next()) {
            pickuppointt = res.getInt("id");
        }
        return pickuppointt;
    }

    public ArrayList<String> getClient() throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT FIO FROM users where role_id='1' ORDER BY id");
        ArrayList<java.lang.String> client = new ArrayList<>();
        while (res.next()) {
            client.add(res.getString("FIO"));
        }


        return client;
    }

    public int getIdClient(String name) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT id FROM users where FIO='" + name + "'");
        int clientid = 0;
        while (res.next()) {
            clientid = res.getInt("id");
        }
        return clientid;
    }

    public void insertOrder(int id_pickuppoint, int client) throws SQLException, ClassNotFoundException {
        PreparedStatement prSt = getDbConnection().prepareStatement("INSERT INTO orders(id,customer_id,order_date,total_amount,pickup_point_id) VALUES (null,?,current_date,0,?)");
        prSt.setInt(1, client);
        prSt.setInt(2, id_pickuppoint);
        prSt.executeUpdate();
    }

    public void insertOrderProduct(int id_order, int id_product, int quantity) throws SQLException, ClassNotFoundException {
        PreparedStatement prSt = getDbConnection().prepareStatement("INSERT INTO orderproducts(id,order_id, product_id, quantity) VALUES (null,?, ?,?)");
        prSt.setInt(1, id_order);
        prSt.setInt(2, id_product);
        prSt.setInt(3, quantity);
        prSt.executeUpdate();
    }

    public int getmaxOrder() throws SQLException, ClassNotFoundException {
        String sql = "SELECT max(id) from orders";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        int id = 0;
        while (res.next())
            id = res.getInt("max(id)");

        return id;
    }

    public int getClient(String log) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM users where login='" + log + "' ORDER BY id";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        int id = 0;
        while (res.next())
            id = res.getInt("id");

        return id;
    }

    public ArrayList<ProductData> getProducts() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM products ORDER BY `id` DESC";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<ProductData> product = new ArrayList<>();
        while (res.next())
            product.add(new ProductData(res.getInt("id"), res.getString("name"), res.getInt("category_id"), res.getInt("price"), res.getInt("stock_quantity"), res.getInt("supplier_id"),res.getString("description"), res.getString("photo")));


        return product;
    }


    public ArrayList<String> getSupplier(int supplier) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM suppliers where id='" + supplier + "'";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<String> manufacturer = new ArrayList<>();
        while (res.next()) {
            List<String> supplierDetails = Arrays.asList(res.getString("name"), res.getString("email"), res.getString("phone_number"), res.getString("description"));
            manufacturer.addAll(supplierDetails);
        }

        return manufacturer;
    }
}


