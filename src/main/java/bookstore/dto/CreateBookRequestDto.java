package bookstore.dto;

import jakarta.persistence.Column;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    private String title;
    @Column(name = "author")
    private String author;
    private String isbn;
    private BigDecimal price;
    private String description;
    private String coverImage;
}
