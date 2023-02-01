package code.sample.ecommerce.service.checkout;

import code.sample.ecommerce.model.Price;
import code.sample.ecommerce.model.Product;
import code.sample.ecommerce.service.discount.DiscountService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

  private final Logger logger = LoggerFactory.getLogger(CheckoutService.class);
  private final DiscountService discountService;

  @Autowired
  CheckoutService(DiscountService discountService) {
    this.discountService = discountService;
  }
  private static final Map<String, Product> products = Map.of(
      "001", new Product("001", "Rolex", 100),
      "002", new Product("002", "Michael Kors", 80),
      "003", new Product("003", "Swatch", 50),
      "004", new Product("004", "Casio", 30)
  );

  public Price calculatePrice(List<String> order) {

    List<Product> productsToCheckout = order.stream()
        .map(products::get)
        .filter(Objects::nonNull)
        .toList();

    logger.info("Calculating order: {}", productsToCheckout);

    List<Product> discountedItems = discountService.discount(productsToCheckout);

    logger.info("Calculating price of discounted order: {}", discountedItems);

    Integer sum = discountedItems.stream()
        .map(Product::price)
        .reduce(0, Integer::sum);

    logger.info("Calculated price of discounted order: {}", sum);

    return new Price(sum);
  }
}
