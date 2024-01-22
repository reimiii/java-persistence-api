package franxx.code.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employees")
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("EMPLOYEE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class Employee {

    @Id
    private String id;

    private String name;
}
