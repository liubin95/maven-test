package domain.easyrules;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Order.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-12-16 : base version.
 */
@Data
public class Order {

  private BigDecimal total;

  private boolean isFirstTime;
}
