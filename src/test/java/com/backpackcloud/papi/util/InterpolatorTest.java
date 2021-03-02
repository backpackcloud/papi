package com.backpackcloud.papi.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterpolatorTest {

  public static class Customer {
    private String id;
    private String email;
  }

  @Test
  public void testBasicInterpolation() {
    Customer customer = new Customer();
    customer.id = "480fb86d-3abc-438c-9e8e-37b14a1c8c42";
    customer.email = "user@example.com";

    Interpolator interpolator = new Interpolator(customer);
    String result = interpolator.apply("/customers/{id}/orders/{email}");
    assertEquals("/customers/" + customer.id + "/orders/" + customer.email, result);
  }

}
