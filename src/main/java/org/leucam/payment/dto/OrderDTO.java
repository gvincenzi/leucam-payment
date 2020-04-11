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
}
