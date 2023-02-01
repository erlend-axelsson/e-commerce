package code.sample.ecommerce.service.discount;

import static org.assertj.core.api.Assertions.assertThat;

import code.sample.ecommerce.model.Product;
import java.util.List;
import org.junit.jupiter.api.Test;

public class MichaelKorsDiscountTest {

  @Test
  public void ShouldNotApplyDiscountWhenNotApplicable(){
    MichaelKorsDiscount discount = new MichaelKorsDiscount();

    var discounts = discount.getDiscounts(List.of(new Product("001", "Rolex", 100)));

    assertThat(discounts.size()).isEqualTo(0);
  }

  @Test
  public void ShouldApplyDiscountWhenApplicable(){
    MichaelKorsDiscount discount = new MichaelKorsDiscount();

    var discounts = discount.getDiscounts(List.of(
        new Product("002", "Michael Kors", 80),
        new Product("002", "Michael Kors", 80)
    ));

    assertThat(discounts.size()).isEqualTo(2);

    assertThat(discounts.get(0).id()).isEqualTo("REMOVE");
    assertThat(discounts.get(0).name()).isEqualTo("Slett Michael Kors x2");
    assertThat(discounts.get(0).price()).isEqualTo(-160);

    assertThat(discounts.get(1).id()).isEqualTo("DISCOUNT");
    assertThat(discounts.get(1).name()).isEqualTo("Michael Kors 2 for 120");
    assertThat(discounts.get(1).price()).isEqualTo(120);
  }

  @Test
  public void ShouldApplyMultipleDiscountWhenApplicable(){
    MichaelKorsDiscount discount = new MichaelKorsDiscount();

    var discounts = discount.getDiscounts(List.of(
        new Product("002", "Michael Kors", 80),
        new Product("999", "Foo", 404),
        new Product("002", "Michael Kors", 80),
        new Product("002", "Michael Kors", 80),
        new Product("002", "Michael Kors", 80),
        new Product("002", "Michael Kors", 80),
        new Product("002", "Michael Kors", 80),
        new Product("002", "Michael Kors", 80)
    ));

    assertThat(discounts.size()).isEqualTo(6);

    assertThat(discounts.get(0).id()).isEqualTo("REMOVE");
    assertThat(discounts.get(0).name()).isEqualTo("Slett Michael Kors x2");
    assertThat(discounts.get(0).price()).isEqualTo(-160);

    assertThat(discounts.get(1).id()).isEqualTo("DISCOUNT");
    assertThat(discounts.get(1).name()).isEqualTo("Michael Kors 2 for 120");
    assertThat(discounts.get(1).price()).isEqualTo(120);

    assertThat(discounts.get(2).id()).isEqualTo("REMOVE");
    assertThat(discounts.get(2).name()).isEqualTo("Slett Michael Kors x2");
    assertThat(discounts.get(2).price()).isEqualTo(-160);

    assertThat(discounts.get(3).id()).isEqualTo("DISCOUNT");
    assertThat(discounts.get(3).name()).isEqualTo("Michael Kors 2 for 120");
    assertThat(discounts.get(3).price()).isEqualTo(120);

    assertThat(discounts.get(4).id()).isEqualTo("REMOVE");
    assertThat(discounts.get(4).name()).isEqualTo("Slett Michael Kors x2");
    assertThat(discounts.get(4).price()).isEqualTo(-160);

    assertThat(discounts.get(5).id()).isEqualTo("DISCOUNT");
    assertThat(discounts.get(5).name()).isEqualTo("Michael Kors 2 for 120");
    assertThat(discounts.get(5).price()).isEqualTo(120);
  }
}
