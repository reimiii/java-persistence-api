package franxx.code.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "payments_gopay")
@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentGopay extends Payment {

    @Column(name = "gopay_id")
    private String gopayId;
}
