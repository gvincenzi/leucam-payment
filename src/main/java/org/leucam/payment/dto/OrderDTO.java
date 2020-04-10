package org.leucam.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.leucam.payment.dto.type.ActionType;
import org.leucam.payment.dto.type.ColorType;
import org.leucam.payment.dto.type.FrontBackType;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private ActionType actionType;
    private FrontBackType frontBackType;
    private ColorType colorType;
    private Integer numberOfCopies;
    private Integer pagesPerSheet;
    private UserDTO user;
    private ProductDTO product;
    private Boolean paid = Boolean.FALSE;

    @JsonIgnore
    public BigDecimal computeTotalToPay() {
        /*return getQuantity() != null && getProduct() != null
                && getProduct().getPricePerUnit() != null ? new BigDecimal(getQuantity()).multiply(getProduct().getPricePerUnit()) : BigDecimal.ZERO;
         */
        return BigDecimal.ZERO;
    }
}
