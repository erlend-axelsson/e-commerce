package code.sample.ecommerce.service.discount;

import code.sample.ecommerce.model.Product;
import java.util.List;
import java.util.stream.LongStream;

public class MichaelKorsDiscount implements Discount{
  @Override
  public List<Product> getDiscounts(List<Product> order) {
    long numberOfItems = order.stream().filter(product -> product.id().equals("002")).count();
    long numberOfDiscounts = numberOfItems / 2;

    List<Product> discounts = LongStream.range(0, numberOfDiscounts)
        .mapToObj(ignore -> List.of(
            new Product("REMOVE", "Slett Michael Kors x2", -160),
            new Product("DISCOUNT", "Michael Kors 2 for 120", 120)
        ))
        .flatMap(List::stream)
        .toList();

    return discounts;
  }
}
