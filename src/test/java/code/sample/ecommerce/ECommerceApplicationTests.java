package code.sample.ecommerce;

import static org.assertj.core.api.Assertions.assertThat;

import code.sample.ecommerce.model.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ECommerceApplicationTests {

  @Value(value="${local.server.port}")
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void emptyArrayShouldReturnPriceOfZero() {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> httpEntity = new HttpEntity<String>("[]", headers);

    var price = this.restTemplate.postForObject("http://localhost:" + port + "/checkout",
        httpEntity,
        Price.class);

    assertThat(price.price()).isEqualTo(0);
  }

  @Test
  public void shouldAppyCorrectPricesToProducts() {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> rolexHttpEntity =
        new HttpEntity<String>("[\"001\"]", headers);
    HttpEntity<String> michaelKorsHttpEntity =
        new HttpEntity<String>("[\"002\"]", headers);
    HttpEntity<String> swatchHttpEntity =
        new HttpEntity<String>("[\"003\"]", headers);
    HttpEntity<String> casioHttpEntity =
        new HttpEntity<String>("[\"004\"]", headers);
    HttpEntity<String> allWatchesHttpEntity =
        new HttpEntity<String>("[\"001\",\"002\",\"003\",\"004\"]", headers);

    Price price;

    price = this.restTemplate.postForObject("http://localhost:" + port + "/checkout",
        rolexHttpEntity,
        Price.class);
    assertThat(price.price()).isEqualTo(100);


    price = this.restTemplate.postForObject("http://localhost:" + port + "/checkout",
        michaelKorsHttpEntity,
        Price.class);
    assertThat(price.price()).isEqualTo(80);


    price = this.restTemplate.postForObject("http://localhost:" + port + "/checkout",
        swatchHttpEntity,
        Price.class);
    assertThat(price.price()).isEqualTo(50);


    price = this.restTemplate.postForObject("http://localhost:" + port + "/checkout",
        casioHttpEntity,
        Price.class);
    assertThat(price.price()).isEqualTo(30);


    price = this.restTemplate.postForObject("http://localhost:" + port + "/checkout",
        allWatchesHttpEntity,
        Price.class);
    assertThat(price.price()).isEqualTo(260);
  }

  @Test
  public void shouldFailIfMediaTypeIsNotJson() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.TEXT_PLAIN);

    HttpEntity<String> httpEntity =
        new HttpEntity<String>("[\"001\"]", headers);


    ResponseEntity<Price> response = this.restTemplate.postForEntity("http://localhost:" + port + "/checkout",
        httpEntity,
        Price.class);


    assertThat(response.getStatusCode().value()).isEqualTo(415);
  }

  @Test
  public void shouldFailIfBodyIsNotStringArray() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> httpEntity =
        new HttpEntity<String>("{\"foo\": [\"001\"]}", headers);


    ResponseEntity<Price> response = this.restTemplate.postForEntity("http://localhost:" + port + "/checkout",
        httpEntity,
        Price.class);


    assertThat(response.getStatusCode().value()).isEqualTo(400);
  }

  @Test
  public void shouldIgnoreUnkownProductIds() {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> unkownIdHttpEntity = new HttpEntity<String>("[\"foo\",\"bar\",\"baz\"]", headers);
    HttpEntity<String> mixedIdHttpEntity = new HttpEntity<String>("[\"foo\",\"001\",\"baz\"]", headers);

    Price unknown = this.restTemplate.postForObject("http://localhost:" + port + "/checkout",
        unkownIdHttpEntity,
        Price.class);
    assertThat(unknown.price()).isEqualTo(0);

    Price mixed = this.restTemplate.postForObject("http://localhost:" + port + "/checkout",
        mixedIdHttpEntity,
        Price.class);
    assertThat(mixed.price()).isEqualTo(100);
  }


  @Test
  public void shouldApplyDiscountsWhereApplicable() {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> rolexDiscountHttpEntity =
        new HttpEntity<String>(
            "[\"001\",\"001\",\"001\",\"001\",\"001\",\"001\",\"001\"]", headers);
    HttpEntity<String> michaelKorsDiscountHttpEntity =
        new HttpEntity<String>(
            "[\"002\",\"002\",\"002\",\"002\",\"002\",\"002\",\"002\"]", headers);

    Price rolexDiscount = this.restTemplate.postForObject("http://localhost:" + port + "/checkout",
        rolexDiscountHttpEntity,
        Price.class);
    assertThat(rolexDiscount.price()).isEqualTo(200+200+100);

    Price mixed = this.restTemplate.postForObject("http://localhost:" + port + "/checkout",
        michaelKorsDiscountHttpEntity,
        Price.class);
    assertThat(mixed.price()).isEqualTo(120+120+120+80);
  }
}
