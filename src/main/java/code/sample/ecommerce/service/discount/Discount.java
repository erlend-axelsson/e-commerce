package code.sample.ecommerce.service.discount;

import code.sample.ecommerce.model.Product;
import java.util.List;

public interface Discount {
  List<Product> getDiscounts(List<Product> order);
}
