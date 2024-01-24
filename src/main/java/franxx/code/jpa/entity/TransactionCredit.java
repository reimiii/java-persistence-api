package franxx.code.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "transactions_credit")
@Data
public class TransactionCredit extends Transaction {

    @Column(name = "credit_amount")
    private Long creditAmount;
}
