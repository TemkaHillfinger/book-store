package bookstore.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE orders SET is_deleted = true WHERE id = ?")
@SQLRestriction(value = "is_deleted = false")
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false, columnDefinition = "VARCHAR")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    private BigDecimal total;
    @Column(nullable = false)
    private LocalDateTime orderDate;
    @Column(nullable = false)
    private String shippingAddress;
    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItems;
    @Column(nullable = false)
    private boolean isDeleted = false;

    public Order(ShoppingCart shoppingCart) {
        this.user = shoppingCart.getUser();
        this.status = Status.PENDING;
        this.orderDate = LocalDateTime.now();
    }

    public Order() {

    }

    @Getter
    public enum Status {
        PENDING("PENDING"),
        DELIVERED("DELIVERED");

        private final String status;

        Status(String status) {
            this.status = status;
        }
    }
}
