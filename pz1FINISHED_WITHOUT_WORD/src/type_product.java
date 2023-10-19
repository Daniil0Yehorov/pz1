public class type_product {
    private String type_name;
    private int productId;

    public int getProductId() {
        return productId;
    }
        public String getType_name() {
            return type_name;
        }

    public static class Builder {
        private String type_name;
        private int productId;

        public Builder(int productId) {
            this.productId = productId;
        }

        public Builder typeName(String type_name) {
            this.type_name = type_name;
            return this;
        }

        public type_product build() {
            return new type_product(this);
        }
    }

    private type_product(Builder builder) {
        productId=builder.productId;
        type_name = builder.type_name;
    }
}
