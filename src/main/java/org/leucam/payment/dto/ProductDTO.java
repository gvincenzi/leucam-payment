package org.leucam.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String name;
    private String description;
    private String fileId;
    private String filePath;
    private Boolean active = Boolean.TRUE;
}
