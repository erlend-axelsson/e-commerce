package code.sample.ecommerce.service.checkout;

import static org.assertj.core.api.Assertions.assertThat;

import code.sample.ecommerce.model.Price;
import code.sample.ecommerce.service.discount.DiscountService;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CheckoutServiceTest {

  CheckoutService checkoutService = new CheckoutService(new DiscountService());

  @Test
  public void shouldReturnPriceObjectWithZeroValueWhenGettingEmptyOrder() {
    Price price = checkoutService.calculatePrice(List.of());

    assertThat(price.price()).isEqualTo(0);
  }

  @Test
  public void shouldReturnCorrectPriceForWatches() {
    Price rolex = checkoutService.calculatePrice(List.of("001"));
    Price michaelKors = checkoutService.calculatePrice(List.of("002"));
    Price swatch = checkoutService.calculatePrice(List.of("003"));
    Price casio = checkoutService.calculatePrice(List.of("004"));
    Price all = checkoutService.calculatePrice(List.of("001", "002", "003", "004"));

    assertThat(rolex.price()).isEqualTo(100);
    assertThat(michaelKors.price()).isEqualTo(80);
    assertThat(swatch.price()).isEqualTo(50);
    assertThat(casio.price()).isEqualTo(30);
    assertThat(all.price()).isEqualTo(260);
  }

  @Test
  public void shouldApplyDiscountToPriceWhenApplicable() {
    Price rolex = checkoutService.calculatePrice(List.of("001", "001", "001"));
    Price michaelKors = checkoutService.calculatePrice(List.of("002", "002"));

    assertThat(rolex.price()).isEqualTo(200);
    assertThat(michaelKors.price()).isEqualTo(120);
  }

}
