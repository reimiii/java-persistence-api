package franxx.code.jpa.entity;

import franxx.code.jpa.entity.embedded.DepartmentId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @EmbeddedId
    private DepartmentId id;

    private String name;
}
