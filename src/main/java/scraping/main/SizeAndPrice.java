package scraping.main;

public class SizeAndPrice {

  private String size;
  private int price;

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public SizeAndPrice() {
    super();
    // TODO Auto-generated constructor stub
  }

  public SizeAndPrice(String size, int price) {
    super();
    this.size = size;
    this.price = price;
  }

}
