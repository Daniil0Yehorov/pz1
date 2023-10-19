import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MySQLDAO implements IDAO{

    private static Connection con = null;
   //SQL скріпти
    private static MySQLDAO instance;
    private static String INSERT_PRODUCT = "INSERT INTO product (name, price, amount, age_restrictions) VALUES (?, ?, ?, ?)";
    private static String SELECT_ALL_PRODUCTS = "SELECT * FROM product";
    private static String DELETE_BY_ID ="DELETE FROM product WHERE id = ?";
    private static String INSERT_DETAILS_FOR_PRODUCT_BY_ID = "INSERT INTO product_details " +
            "(product_id, weight, manufactuer, expire_date, form) VALUES (?, ?, ?, ?, ?)";
    private static String GET_product_details_for_product_id="SELECT * FROM product_details WHERE product_id = ?";
    private static String INSERT_type_of_product = "INSERT INTO type_product (product_id, type_name) VALUES (?, ?)";

    private static String Update_product="UPDATE product SET `name`=?,`price`=?,amount=?,age_restrictions=? WHERE product.id=?";
    private static String  SELECT_PRODUCT_BY_ID="Select * From product Where product_id=?";
    private static String Update_product_details="Update product_details Set weight=?,manufactuer=?," +
            "expire_date=?,form=? where product_id=?";
    private static String Update_type_product = "update type_product set type_name=? where product_id=?";

    private static String Search_range_age_restrictions="select * from product Where `age_restrictions`>? and `age_restrictions`<?;";
    private static String Search_range_price="select * from product Where `price`>? AND price<?;";
    private static String Search_name="select * from product Where `name`=?;";
    private static String SELECT_TYPE_FOR_PRODUCT_BY_ID="select * from type_product where product_id=?";
    private MySQLDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/mylastdb";
            String user = "root";
            String password ="JJi2dlwK23D23DgksqA";
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Помилка при завантаженні драйвера бази даних.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Помилка при підключенні до бази даних.");
        }
    }

    public static MySQLDAO getInstance() {
        if (instance == null) {
            instance = new MySQLDAO();
        }
        return instance;
    }
    @Override
    public product[] InsertP(String name, float price, int amount, float age_restrictions) {
        try {
            // об'єкт продукту
            product newProduct = new product.Builder(name)
                    .price(price)
                    .amount(amount)
                    .age_restrictions(age_restrictions)
                    .build();

            // Підготовка SQL-запиту
            PreparedStatement preparedStatement = con.prepareStatement(INSERT_PRODUCT);
            preparedStatement.setString(1, newProduct.getName());
            preparedStatement.setFloat(2, newProduct.getPrice());
            preparedStatement.setInt(3, newProduct.getAmount());
            preparedStatement.setFloat(4, newProduct.getAge_restrictions());

            // Виконання SQL-запиту на вставку
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Отримати оновлений список продуктів після вставки
                return getAllProducts();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Обробка винятків при помилці
        }
        return new product[0];
    }
    public product[] getAllProducts() {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(SELECT_ALL_PRODUCTS);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<product> productList = new ArrayList<>();
            while (resultSet.next()) {
                product.Builder productBuilder = new product.Builder(resultSet.getString("name"))
                        .price(resultSet.getFloat("price"))
                        .amount(resultSet.getInt("amount"))
                        .age_restrictions(resultSet.getFloat("age_restrictions"));
                productList.add(productBuilder.build());
            }

            return productList.toArray(new product[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Обробити помилку SQL
        }
    }

    public void deleteproduct(int id) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(DELETE_BY_ID);
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Продукт з id = " + id + " не був знайдений.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Обробка винятків при видаленні
        }
    }
    @Override
    public product_details[] InsertDetailsForProduct(int productId, float weight, String manufactuer, String expire_date, String form) {
     try {
         // об'єкт продукту
         product_details newProductDetails = new product_details.Builder(productId)
                 .weight(weight)
                 .manufactuer(manufactuer)
                 .expire_date(expire_date)
                 .form(form)
                 .build();
         // Підготовка SQL-запиту
         PreparedStatement preparedStatement = con.prepareStatement(INSERT_DETAILS_FOR_PRODUCT_BY_ID);
         preparedStatement.setInt(1, newProductDetails.getProductId());
         preparedStatement.setFloat(2, newProductDetails.getWeight());
         preparedStatement.setString(3, newProductDetails.getManufactuer());
         preparedStatement.setString(4, newProductDetails.getExpire_date());
         preparedStatement.setString(5, newProductDetails.getForm());

         // Виконання SQL-запиту на вставку
         int rowsAffected = preparedStatement.executeUpdate();

         if (rowsAffected > 0) {
             // Отримати оновлені дані про деталі продукта після вставки
             return getDetailsForProduct(productId);
         }
     } catch (SQLException e) {
         e.printStackTrace();
         // Обробка винятків при помилці вставки
     }
     return new product_details[0];
 }
    public product_details[] getDetailsForProduct(int productId) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(GET_product_details_for_product_id);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<product_details> detailsList = new ArrayList<>();

            while (resultSet.next()) {
                product_details details = new product_details.Builder(productId)
                        .weight(resultSet.getFloat("weight"))
                        .manufactuer(resultSet.getString("manufactuer"))
                        .expire_date(resultSet.getString("expire_date"))
                        .form(resultSet.getString("form"))
                        .build();
                detailsList.add(details);
            }

            return detailsList.toArray(new product_details[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Обробити помилку SQL
        }
    }
    public type_product[] getTypeForProduct(int productId){
        try {
            PreparedStatement preparedStatement = con.prepareStatement(SELECT_TYPE_FOR_PRODUCT_BY_ID);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<type_product> typeProducts = new ArrayList<>();

            while (resultSet.next()) {
                type_product typeProduct = new type_product.Builder(resultSet.getInt("product_id"))
                        .typeName(resultSet.getString("type_name"))
                        .build();
                typeProducts.add(typeProduct);
            }

            return typeProducts.toArray(new type_product[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Обробити помилку SQL
        }
    }
    @Override
    public type_product[] InsertTypeofProduct(int productId, String type_name) {
        try {
            //  об'єкт type_product
            type_product.Builder builder = new type_product.Builder(productId);
            builder.typeName(type_name);
            type_product newTypeProduct = builder.build();

            // Підготовка SQL-запиту
            PreparedStatement preparedStatement = con.prepareStatement(INSERT_type_of_product);
            preparedStatement.setInt(1, newTypeProduct.getProductId());
            preparedStatement.setString(2, newTypeProduct.getType_name());

            // Виконання SQL-запиту на вставку
            int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            // Отримати оновлені дані про деталі продукта після вставки
            return getTypeForProduct(productId);
        }
        } catch (SQLException e) {
            e.printStackTrace();
            // Обробка винятків при помилці вставки
        }
        return new type_product[0];
    }
    public product [] UpdateProduct(int id,String name,float price,int amount,float age_restrictions){
        try {

            // Підготовка SQL-запиту
            PreparedStatement preparedStatement = con.prepareStatement(Update_product);
            preparedStatement.setString(1, name);
            preparedStatement.setFloat(2, price);
            preparedStatement.setInt(3, amount);
            preparedStatement.setFloat(4, age_restrictions);
            preparedStatement.setInt(5, id);

            // Виконання SQL-запиту на вставку
            int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            // Отримати оновлені дані про деталі продукта після вставки
            return getAllProducts();
        }
        } catch (SQLException e) {
            e.printStackTrace();
            // Обробка винятків при помилці вставки
        }
        return new product[0];
    }

    public product_details[] UpdateProductDetails(int productId, float weight, String manufacturer, String expireDate, String form) {
        try {
            // Підготовка SQL-запиту
            String updateDetailsQuery = Update_product_details;
            PreparedStatement preparedStatement = con.prepareStatement(updateDetailsQuery);
            preparedStatement.setFloat(1, weight);
            preparedStatement.setString(2, manufacturer);
            preparedStatement.setString(3, expireDate);
            preparedStatement.setString(4, form);
            preparedStatement.setInt(5, productId);

            // Виконання SQL-запиту на вставку
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Отримати оновлені дані про деталі продукта після вставки
                return getDetailsForProduct(productId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Обробка винятків при помилці вставки
        }
        return new product_details[0];
    }
    public type_product[] UpdateTypeProduct(int productId, String type_name) {
        try {
            // SQL-запрос
            PreparedStatement preparedStatement = con.prepareStatement(Update_type_product);
            preparedStatement.setString(1, type_name);
            preparedStatement.setInt(2, productId);

            // Виконання SQL-запиту
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                // Оновлення успішне, повернути нові дані
                return getTypeForProduct(productId);
            } else {
                // Нічого не було оновлено, можливо, вказаний productId не існує
                System.out.println("Нічого не було оновлено. Можливо, вказаний продукт не існує.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Обробка винятків при помилці виконання SQL-запиту
            System.out.println("Помилка при оновленні типу продукту.");
        }
        return null;
    }
    public void searchProductsByAgeRestrictionsRange(float minAge, float maxAge) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(Search_range_age_restrictions);
            preparedStatement.setFloat(1, minAge);
            preparedStatement.setFloat(2, maxAge);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                product product = new product.Builder(resultSet.getString("name"))
                        .price(resultSet.getFloat("price"))
                        .age_restrictions(resultSet.getFloat("age_restrictions"))
                        .amount(resultSet.getInt("amount"))
                        .build();

                System.out.println("Назва продукту: " + product.getName());
                System.out.println("Ціна: " + product.getPrice());
                System.out.println("Вікові обмеження: " + product.getAge_restrictions());
                System.out.println("Кількість: " + product.getAmount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchProductsByPriceRange(float minPrice, float maxPrice) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(Search_range_price);
            preparedStatement.setFloat(1, minPrice);
            preparedStatement.setFloat(2, maxPrice);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                product product = new product.Builder(resultSet.getString("name"))
                        .price(resultSet.getFloat("price"))
                        .age_restrictions(resultSet.getFloat("age_restrictions"))
                        .amount(resultSet.getInt("amount"))
                        .build();

                System.out.println("Назва продукту: " + product.getName());
                System.out.println("Ціна: " + product.getPrice());
                System.out.println("Вікові обмеження: " + product.getAge_restrictions());
                System.out.println("Кількість: " + product.getAmount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchProductsByName(String name) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(Search_name);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                product product = new product.Builder(resultSet.getString("name"))
                        .price(resultSet.getFloat("price"))
                        .age_restrictions(resultSet.getFloat("age_restrictions"))
                        .amount(resultSet.getInt("amount"))
                        .build();

                System.out.println("Назва продукту: " + product.getName());
                System.out.println("Ціна: " + product.getPrice());
                System.out.println("Вікові обмеження: " + product.getAge_restrictions());
                System.out.println("Кількість: " + product.getAmount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
