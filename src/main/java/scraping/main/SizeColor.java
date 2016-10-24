package scraping.main;

public class SizeColor {

  private String size;
  private String color;
  private long sku;
  private double price;

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public SizeColor() {
    super();
    // TODO Auto-generated constructor stub
  }

  public SizeColor(String size, String color, long sku, double price) {
    super();
    this.size = size;
    this.color = color;
    this.sku = sku;
    this.price = price;
  }

  public long getSku() {
    return sku;
  }

  public void setSku(long sku) {
    this.sku = sku;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }


}
