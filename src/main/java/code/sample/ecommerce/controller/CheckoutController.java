package code.sample.ecommerce.controller;

import code.sample.ecommerce.model.Price;
import code.sample.ecommerce.service.checkout.CheckoutService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckoutController {
  private final Logger logger = LoggerFactory.getLogger(CheckoutController.class);
  private final CheckoutService checkoutService;

  @Autowired
  public CheckoutController(CheckoutService checkoutService){
    this.checkoutService = checkoutService;
  }

  @PostMapping("/checkout")
  public Price checkout(@RequestBody List<String> order){
    logger.info("Received order: {}", order);

    Price calculatedPrice = checkoutService.calculatePrice(order);

    logger.info("Returning calculated price: {}", calculatedPrice);

    return calculatedPrice;
  }

}
