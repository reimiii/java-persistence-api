package franxx.code.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "brands")
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Brand extends AuditableEntity<String> {

    private String name;

    private String description;

    @OneToMany(mappedBy = "brand")
    private Collection<Product> products;

    @Version
    private Long version;
}
