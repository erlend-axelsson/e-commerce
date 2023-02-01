package code.sample.ecommerce.service.discount;

import static org.assertj.core.api.Assertions.assertThat;

import code.sample.ecommerce.model.Product;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DiscountServiceTest {

  DiscountService discountService = new DiscountService();

  @Test
  public void shouldNotApplyDiscountWhenNotApplicable() {
    List<Product> order = discountService.discount(List.of(
        new Product("001", "Rolex", 100),
        new Product("002", "Michael Kors", 80),
        new Product("003", "Swatch", 50),
        new Product("004", "Casio", 30)
    ));

    assertThat(order.size()).isEqualTo(4);
    assertThat(order.get(0).id()).isEqualTo("001");
    assertThat(order.get(1).id()).isEqualTo("002");
    assertThat(order.get(2).id()).isEqualTo("003");
    assertThat(order.get(3).id()).isEqualTo("004");
  }

  @Test
  public void shouldApplyDiscountWhenApplicable() {
    List<Product> order = discountService.discount(List.of(
        new Product("001", "Rolex", 100),
        new Product("001", "Rolex", 100),
        new Product("001", "Rolex", 100),
        new Product("004", "Casio", 30)
    ));

    assertThat(order.size()).isEqualTo(6);
    assertThat(order.get(0).id()).isEqualTo("001");
    assertThat(order.get(1).id()).isEqualTo("001");
    assertThat(order.get(2).id()).isEqualTo("001");
    assertThat(order.get(3).id()).isEqualTo("004");
    assertThat(order.get(4).id()).isEqualTo("REMOVE");
    assertThat(order.get(5).id()).isEqualTo("DISCOUNT");
  }
}
