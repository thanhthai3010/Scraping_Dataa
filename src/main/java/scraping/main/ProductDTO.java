package scraping.main;

import java.io.Serializable;

public class ProductDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String linkProduct;
  private String productTitle;
  private double productShare;

  public String getLinkProduct() {
    return linkProduct;
  }

  public void setLinkProduct(String linkProduct) {
    this.linkProduct = linkProduct;
  }

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  public double getProductShare() {
    return productShare;
  }

  public void setProductShare(double productShare) {
    this.productShare = productShare;
  }

  public ProductDTO() {
    super();
    // TODO Auto-generated constructor stub
  }

  public ProductDTO(String linkProduct, String productTitle, double productShare) {
    super();
    this.linkProduct = linkProduct;
    this.productTitle = productTitle;
    this.productShare = productShare;
  }

}
