package code.sample.ecommerce.service.discount;

import static org.assertj.core.api.Assertions.assertThat;

import code.sample.ecommerce.model.Product;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RolexDiscountTest {

  @Test
  public void ShouldNotApplyDiscountWhenNotApplicable(){
    RolexDiscount discount = new RolexDiscount();

    var discounts = discount.getDiscounts(List.of(new Product("002", "Michael Kors", 80)));

    assertThat(discounts.size()).isEqualTo(0);
  }

  @Test
  public void ShouldApplyDiscountWhenApplicable(){
    RolexDiscount discount = new RolexDiscount();

    var discounts = discount.getDiscounts(List.of(
        new Product("001", "Rolex", 100),
        new Product("001", "Rolex", 100),
        new Product("001", "Rolex", 100)
    ));

    assertThat(discounts.size()).isEqualTo(2);

    assertThat(discounts.get(0).id()).isEqualTo("REMOVE");
    assertThat(discounts.get(0).name()).isEqualTo("Slett Rolex x3");
    assertThat(discounts.get(0).price()).isEqualTo(-300);

    assertThat(discounts.get(1).id()).isEqualTo("DISCOUNT");
    assertThat(discounts.get(1).name()).isEqualTo("Rolex 3 for 200");
    assertThat(discounts.get(1).price()).isEqualTo(200);
  }

  @Test
  public void ShouldApplyMultipleDiscountWhenApplicable(){
    RolexDiscount discount = new RolexDiscount();

    var discounts = discount.getDiscounts(List.of(
        new Product("001", "Rolex", 100),
        new Product("001", "Rolex", 100),
        new Product("999", "Foo", 404),
        new Product("001", "Rolex", 100),
        new Product("001", "Rolex", 100),
        new Product("001", "Rolex", 100),
        new Product("001", "Rolex", 100),
        new Product("001", "Rolex", 100),
        new Product("002", "Michael Kors", 80)
    ));

    assertThat(discounts.size()).isEqualTo(4);

    assertThat(discounts.get(0).id()).isEqualTo("REMOVE");
    assertThat(discounts.get(0).name()).isEqualTo("Slett Rolex x3");
    assertThat(discounts.get(0).price()).isEqualTo(-300);

    assertThat(discounts.get(1).id()).isEqualTo("DISCOUNT");
    assertThat(discounts.get(1).name()).isEqualTo("Rolex 3 for 200");
    assertThat(discounts.get(1).price()).isEqualTo(200);

    assertThat(discounts.get(2).id()).isEqualTo("REMOVE");
    assertThat(discounts.get(2).name()).isEqualTo("Slett Rolex x3");
    assertThat(discounts.get(2).price()).isEqualTo(-300);

    assertThat(discounts.get(3).id()).isEqualTo("DISCOUNT");
    assertThat(discounts.get(3).name()).isEqualTo("Rolex 3 for 200");
    assertThat(discounts.get(3).price()).isEqualTo(200);
  }
}
