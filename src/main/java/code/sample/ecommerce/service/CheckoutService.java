package code.sample.ecommerce.service;

import code.sample.ecommerce.model.Price;
import code.sample.ecommerce.model.Product;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {
  private static final Map<String, Product> products = Map.of(
      "001", new Product("Rolex", 100),
      "002", new Product("Michael Kors", 80),
      "003", new Product("Swatch", 50),
      "004", new Product("Casio", 30)
  );

  public Price calculatePrice(List<String> order) {

    List<Product> productsToCheckout = order.stream()
        .map(products::get)
        .filter(Objects::nonNull)
        .toList();

    Integer sum = productsToCheckout.stream()
        .map(Product::price)
        .reduce(0, Integer::sum);

    return new Price(sum);
  }
}
