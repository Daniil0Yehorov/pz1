public interface IDAO {
    //inserts
    product [] InsertP(String name,float price,int amount,float age_restrictions);
    product_details [] InsertDetailsForProduct(int productId,float weight,String manufactuer,String expire_date,String form);
    type_product [] InsertTypeofProduct(int productId,String type_name);
    //delete FUll product,product_details,type_product  BECAUSE CASCADE CASCADE))
    void deleteproduct(int id);

    // Another functions
    product_details[] getDetailsForProduct(int productId);
    type_product[] getTypeForProduct(int productId);
    void searchProductsByAgeRestrictionsRange(float minAge, float maxAge);
    void searchProductsByName(String name);
    void  searchProductsByPriceRange(float minPrice, float maxPrice);
    // Update
    product [] UpdateProduct(int id,String name,float price,int amount,float age_restrictions);
    product_details [] UpdateProductDetails(int productId,float weight,String manufactuer,String expire_date,String form);
    type_product [] UpdateTypeProduct(int productId,String type_name);
}
