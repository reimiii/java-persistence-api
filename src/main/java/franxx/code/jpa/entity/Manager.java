package franxx.code.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("MANAGER")
@Data
@EqualsAndHashCode(callSuper = true)
public class Manager extends Employee {

    @Column(name = "total_employee")
    private Integer totalEmployee;
}
