package bookstore.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToCartRequest {
    @NotNull
    private Long bookId;

    @NotNull
    private Integer quantity;
}
