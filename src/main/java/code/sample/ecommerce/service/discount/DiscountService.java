package code.sample.ecommerce.service.discount;

import code.sample.ecommerce.model.Product;
import java.util.List;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {
  Logger logger = LoggerFactory.getLogger(DiscountService.class);
  private static final List<Discount> discounts = List.of(new RolexDiscount(), new MichaelKorsDiscount());

  public List<Product> discount(List<Product> order) {

    logger.info("Applying discounts to order: {}", order);

    List<Product> discountedItems = Stream.concat(
        order.stream(),
        discounts.stream()
            .map(discount -> discount.getDiscounts(order))
            .flatMap(List::stream)
    ).toList();

    logger.info("Applied discounts to order: {}", discountedItems);

    return discountedItems;
  }
}
