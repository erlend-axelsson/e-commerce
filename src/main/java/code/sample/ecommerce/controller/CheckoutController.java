package code.sample.ecommerce.controller;

import code.sample.ecommerce.model.Price;
import code.sample.ecommerce.service.CheckoutService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckoutController {
  CheckoutService checkoutService;

  @Autowired
  public CheckoutController(CheckoutService checkoutService){
    this.checkoutService = checkoutService;
  }

  @PostMapping("/checkout")
  public Price checkout(@RequestBody List<String> order){
    return checkoutService.calculatePrice(order);
  }

}
