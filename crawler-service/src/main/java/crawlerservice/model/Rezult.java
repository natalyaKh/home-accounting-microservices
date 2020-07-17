package crawlerservice.model;

public class Rezult {
    String productName;
    Price price;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Rezult(String productName, Price price) {
        this.productName = productName;
        this.price = price;
    }

    public Rezult() {
    }
}
