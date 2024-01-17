package franxx.code.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "brands")
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Brand {

    @Id
    private String id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "brand")
    private Collection<Product> products;
}
