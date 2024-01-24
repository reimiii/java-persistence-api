package franxx.code.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "transactions_debit")
@Data
public class TransactionDebit extends Transaction {

    @Column(name = "debit_amount")
    private Long debitAmount;
}
