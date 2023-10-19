import java.util.Scanner;

public class TestMain {
    public static void main(String[] args) {
        MySQLDAO dao = MySQLDAO.getInstance();
        Scanner scanner = new Scanner(System.in);

        if (dao != null) {
            System.out.println("Виберіть дію:");
            System.out.println("1 - Вставити продукт");
            System.out.println("2 - Видалити продукт");
            System.out.println("3 - Вставити деталі продукту");
            System.out.println("4 - Вставити тип продукту");
            System.out.println("5 - Оновити продукт");
            System.out.println("6 - Оновити деталі продукту");
            System.out.println("7 - Оновити тип продукту");
            System.out.println("8 - Пошук за віковими обмеженнями");
            System.out.println("9 - Пошук за ціною");
            System.out.println("10 - Пошук за назвою");
            System.out.println("0 - Вийти");

            while (true) {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Вставка продукту
                        scanner.nextLine(); // Очистити буфер
                        System.out.println("назва продукту:");
                        String name = scanner.nextLine();
                        System.out.println("Ціна продукту:");
                        Float price = scanner.nextFloat();
                        System.out.println("Кількість товару:");
                        int amount = scanner.nextInt();
                        System.out.println("Вікові обмеження:");
                        float age_restrictions = scanner.nextFloat();

                        product[] products = dao.InsertP(name, price, amount, age_restrictions);
                        if (products != null) {
                            for (product product : products) {
                                System.out.println("Назва продукту: " + product.getName());
                                System.out.println("Ціна: " + product.getPrice());
                                System.out.println("Кількість: " + product.getAmount());
                                System.out.println("Вікові обмеження: " + product.getAge_restrictions());
                            }
                        } else {
                            System.out.println("Помилка при додаванні продукту.");
                        }
                        break;

                    case 2:
                        // Видалення продукту
                        System.out.println("Введіть ID продукту, який потрібно видалити:");
                        int productIdToDelete = scanner.nextInt();
                        dao.deleteproduct(productIdToDelete);
                        System.out.println("Продукт з ID " + productIdToDelete + " був видалений.");
                        break;

                    case 3:
                        // Вставка даних про продукт
                        System.out.println("Введіть ID продукту для вставки даних:");
                        int productIdForDetails = scanner.nextInt();
                        System.out.println("Вага:");
                        float weight = scanner.nextFloat();
                        scanner.nextLine();  // очистити буфер
                        System.out.println("Виробник:");
                        String manufactuer = scanner.nextLine();
                        System.out.println("Термін придатності (рік-місяць-день):");
                        String expireDate = scanner.nextLine();
                        System.out.println("Форма:");
                        String form = scanner.nextLine();

                        product_details[] details = dao.InsertDetailsForProduct(productIdForDetails, weight, manufactuer, expireDate, form);

                        if (details != null) {
                            for (product_details detail : details) {
                                System.out.println("ID деталі: " + detail.getProductId());
                                System.out.println("Вага: " + detail.getWeight());
                                System.out.println("Виробник: " + detail.getManufactuer());
                                System.out.println("Термін придатності: " + detail.getExpire_date());
                                System.out.println("Форма: " + detail.getForm());
                            }
                        } else {
                            System.out.println("Помилка при додаванні деталей для продукта.");
                        }
                        break;

                    case 4:
                        // Вставка типу продукту
                        System.out.println("Введіть ID продукту для вставки типу:");
                        int productIdForType = scanner.nextInt();
                        scanner.nextLine();  // Додайте це для очищення буфера
                        System.out.println("Тип продукту:");
                        String type_name = scanner.nextLine();  // Одержуємо рядок без зайвих очищень буфера
                        type_product[] typeProducts = dao.InsertTypeofProduct(productIdForType, type_name);

                        if (typeProducts != null) {
                            for (type_product typeProduct : typeProducts) {
                                System.out.println("ID продукту: " + typeProduct.getProductId());
                                System.out.println("Тип їжі: " + typeProduct.getType_name());
                            }
                        } else {
                            System.out.println("Помилка при додаванні типу продукту.");
                        }
                        break;

                    case 5:
                        // Оновлення продукту1
                        System.out.println("Введіть ID продукту для оновлення:");
                        int productIdToUpdate = scanner.nextInt();
                        scanner.nextLine();  // очистити буфер
                        System.out.println("Нова назва:");
                        String newName = scanner.nextLine();
                        System.out.println("Нова ціна:");
                        float newPrice = scanner.nextFloat();
                        System.out.println("Нова кількість:");
                        int newAmount = scanner.nextInt();
                        System.out.println("Нові вікові обмеження:");
                        float newAgeRestrictions = scanner.nextFloat();

                        product[] updatedProducts = dao.UpdateProduct(productIdToUpdate, newName, newPrice, newAmount, newAgeRestrictions);

                        if (updatedProducts != null) {
                            for (product updatedProduct : updatedProducts) {
                                System.out.println("Назва продукту: " + updatedProduct.getName());
                                System.out.println("Ціна: " + updatedProduct.getPrice());
                                System.out.println("Кількість: " + updatedProduct.getAmount());
                                System.out.println("Вікові обмеження: " + updatedProduct.getAge_restrictions());
                            }
                        } else {
                            System.out.println("Помилка при оновленні продукту.");
                        }
                        break;

                    case 6:
                        // Оновлення даних про продукт
                        System.out.println("Введіть ID деталі для оновлення:");
                        int detailIdToUpdate = scanner.nextInt();
                        System.out.println("Нова вага:");
                        float newWeight = scanner.nextFloat();
                        scanner.nextLine();  // очистити буфер
                        System.out.println("Новий виробник:");
                        String newManufacturer = scanner.nextLine();
                        System.out.println("Новий термін придатності (рік-місяць-день):");
                        String newExpireDate = scanner.nextLine();
                        System.out.println("Нова форма:");
                        String newForm = scanner.nextLine();

                        product_details[] updatedDetails = dao.UpdateProductDetails(detailIdToUpdate, newWeight, newManufacturer, newExpireDate, newForm);

                        if (updatedDetails != null) {
                            for (product_details detail : updatedDetails) {
                                System.out.println("ID деталі: " + detail.getProductId());
                                System.out.println("Вага: " + detail.getWeight());
                                System.out.println("Виробник: " + detail.getManufactuer());
                                System.out.println("Термін придатності: " + detail.getExpire_date());
                                System.out.println("Форма: " + detail.getForm());
                            }
                        } else {
                            System.out.println("Помилка при оновленні деталей продукту.");
                        }
                        break;

                    case 7:
                        // Оновлення типу продукту
                        System.out.println("Введіть ID типу продукту для оновлення:");
                        int typeIdToUpdate = scanner.nextInt();
                        scanner.nextLine();  // очистити буфер
                        System.out.println("Новий тип продукту:");
                        String newTypeName = scanner.nextLine();  // Використовуємо nextLine() для правильного зчитування рядка
                        type_product[] updatedTypeProducts = dao.UpdateTypeProduct(typeIdToUpdate, newTypeName);

                        if (updatedTypeProducts != null) {
                            for (type_product typeProduct : updatedTypeProducts) {
                                System.out.println("ID продукту: " + typeProduct.getProductId());
                                System.out.println("Тип їжі: " + typeProduct.getType_name());
                            }
                        } else {
                            System.out.println("Помилка при оновленні типу продукту.");
                        }
                        break;

                    case 8:
                        System.out.println("Введіть мінімальне значення вікових обмежень:");
                        float minAge = scanner.nextFloat();
                        scanner.nextLine();  // Додайте це для очищення буфера
                        System.out.println("Введіть максимальне значення вікових обмежень:");
                        float maxAge = scanner.nextFloat();
                        scanner.nextLine();  // Додайте це для очищення буфера
                        dao.searchProductsByAgeRestrictionsRange(minAge, maxAge);
                        break;

                    case 9:
                        // Пошук за ціною
                        System.out.println("Введіть мінімальну ціну:");
                        float minPrice = scanner.nextFloat();
                        System.out.println("Введіть максимальну ціну:");
                        float maxPrice = scanner.nextFloat();
                        dao.searchProductsByPriceRange(minPrice, maxPrice);
                        break;

                    case 10:
                        // Пошук за назвою
                        System.out.println("Введіть назву продукту:");
                        scanner.nextLine();  // Очистити буфер
                        String productName = scanner.nextLine();
                        dao.searchProductsByName(productName);
                        break;

                    case 0:
                        // Вихід
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Невірний вибір. Виберіть правильну дію.");
                        break;
                }
            }
        } else {
            System.out.println("Помилка при отриманні IDAO.");
        }
    }
}