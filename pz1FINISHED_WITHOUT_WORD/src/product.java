public class product {
        private String name;
        private float price;
        private int amount;
        private  float age_restrictions;
        private int id;

public int getId() {
    return id;
}
    public String getName(){
            return name;
        }
        public float getPrice(){
            return price;
        }
        public int getAmount() {
            return amount;
        }
        public float getAge_restrictions() {
            return age_restrictions;
        }

    public static class Builder {
        private String name;
        private float price;
        private int amount;
        private float age_restrictions;

        public Builder(String name) {
            this.name = name;
        }

        public Builder price(float price) {
            this.price = price;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder age_restrictions(float age_restrictions) {
            this.age_restrictions = age_restrictions;
            return this;
        }

        public product build() {
            return new product(this);
        }
    }
    private product(Builder builder) {
        name = builder.name;
        price = builder.price;
        amount = builder.amount;
        age_restrictions = builder.age_restrictions;
    }
}
