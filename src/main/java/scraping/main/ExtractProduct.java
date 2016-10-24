package scraping.main;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ExtractProduct {

  public static WebClient webClient =
      new WebClient(BrowserVersion.FIREFOX_45, "118.69.184.49", 8001);

  public static void main(String[] args) {
    String productURL = "https://www.sunfrog.com/Love-Photography-Black-40985612-Guys.html";

    HtmlPage page = null;
    try {
      page = webClient.getPage(productURL);
    } catch (FailingHttpStatusCodeException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String productXML = page.asXml();
    Document productDoc = Jsoup.parse(productXML);

    Elements proColors = productDoc.getElementsByClass("colorBorder");
    List<String> colors = new ArrayList<>();
    for (Element color : proColors) {
      colors.add(color.attr("for"));
    }

    List<String> sizes = new ArrayList<>();
    Element proSizes = productDoc.getElementsByClass("size-select").first();
    Elements labelSizes = proSizes.select("label");
    for (Element size : labelSizes) {
      sizes.add(size.text());
    }

    // get SKU
    Element skuEle = productDoc.getElementsByClass("explain").first();
    long sku = Long.parseLong(skuEle.select("p").get(2).text().split(" ")[2].trim());

    // get price
    Element priceEle = productDoc.getElementById("shirtTypes");
    String priceLocale = priceEle.select("option").first().text().split(" ")[2];
    Number priceNum = 0;
    try {
      priceNum = NumberFormat.getCurrencyInstance(Locale.US).parse(priceLocale);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    double price = priceNum.doubleValue() + 4;

    List<SizeColor> sizesColors =
        colors.stream().flatMap(color -> sizes.stream().map(new Function<String, SizeColor>() {
          @Override
          public SizeColor apply(String size) {
            double pricePro = 0.0;
            if (size.equals("2X") || size.equals("3X") || size.equals("4X") || size.equals("5X")) {
              pricePro = price + 3;
            } else {
              pricePro = price;
            }
            return new SizeColor(size, color, sku, pricePro);
          }
        })).collect(Collectors.toList());

    for (SizeColor sizeColor : sizesColors) {
      System.out.println(
          sizeColor.getSize() + " -- " + sizeColor.getColor() + " -- " + sizeColor.getPrice());
    }

  }
}
