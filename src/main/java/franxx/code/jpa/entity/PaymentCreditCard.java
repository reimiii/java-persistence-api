package franxx.code.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "payments_credit_card")
@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentCreditCard extends Payment {

    @Column(name = "masked_card")
    private String maskedCard;

    private String bank;
}
