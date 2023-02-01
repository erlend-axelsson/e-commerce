package code.sample.ecommerce.service.discount;

import code.sample.ecommerce.model.Product;
import java.util.List;
import java.util.stream.LongStream;

public class RolexDiscount implements Discount{
  @Override
  public List<Product> getDiscounts(List<Product> order) {
    long numberOfRolexes = order.stream().filter(product -> product.id().equals("001")).count();
    long numberOfDiscounts = numberOfRolexes / 3;

    List<Product> discounts = LongStream.range(0, numberOfDiscounts)
        .mapToObj(ignore -> List.of(
            new Product("REMOVE", "Slett Rolex x3", -300),
            new Product("DISCOUNT", "Rolex 3 for 200", 200)
        ))
        .flatMap(List::stream)
        .toList();

    return discounts;
  }
}
