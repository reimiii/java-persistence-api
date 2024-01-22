package franxx.code.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("VP")
@Data
@EqualsAndHashCode(callSuper = true)
public class VicePresident extends Employee {

    @Column(name = "total_manager")
    private Integer totalManager;
}
