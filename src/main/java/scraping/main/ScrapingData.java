package scraping.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ScrapingData {


  public static WebClient webClient =
      new WebClient(BrowserVersion.FIREFOX_45, "118.69.184.49", 8001);

  public static void getData()
      throws FailingHttpStatusCodeException, MalformedURLException, IOException {

    String masterURL =
        "https://www.sunfrog.com/search/paged2.cfm?schTrmFilter=sales&search=halloween&cID=35&offset=";

    String firstPage = masterURL + "0";

    HtmlPage page = webClient.getPage(firstPage);
    String xmlMasterPage = page.asXml();
    Document doc = Jsoup.parse(xmlMasterPage);

    List<ProductDTO> listPros = getListPros(doc);


    webClient.close();
  }

  public static List<ProductDTO> getListPros(Document doc) {
    List<ProductDTO> listPros = new ArrayList<>();

    Elements products = doc.getElementsByClass("frameitWrapper");
    for (Element pro : products) {
      ProductDTO product = new ProductDTO();
      Element proLink = pro.select("a").first();
      String hrefLink = proLink.attr("href");
      product = extractProduct(hrefLink);
      listPros.add(product);
    }

    return listPros;
  }

  public static ProductDTO extractProduct(String hrefLink) {
    ProductDTO product = new ProductDTO();

    HtmlPage page = null;
    try {
      page = webClient.getPage(hrefLink);
    } catch (FailingHttpStatusCodeException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String xmlNextPage = page.asXml();
    Document doc = Jsoup.parse(xmlNextPage);
    product.setLinkProduct(hrefLink);

    String shareCount = doc.getElementsByClass("don-count").first().toString()
        .replaceAll("<span class=\"don-count\">", "").replaceAll("</span>", "");
    System.out.println(doc.toString());
    product.setProductShare(getShare(shareCount));
    product.setProductTitle(doc.title());

    return product;
  }

  public static double getShare(String share) {
    double result = 0.0;
    if (share.contains("k")) {
      share = share.replace("k", "");
      result = Double.parseDouble(share) * 100;
    } else {
      result = Double.parseDouble(share);
    }

    return result;
  }

  public static void main(String[] args) {
    try {
      getData();
    } catch (FailingHttpStatusCodeException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
