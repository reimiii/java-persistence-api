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
@NamedQueries({
        @NamedQuery(name = "Brand.findAll", query = "select b from Brand b"),
        @NamedQuery(name = "Brand.findAllByName", query = "select b from Brand b where b.name = :name")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Brand.native.findAll", query = "select * from brands", resultClass = Brand.class)
})
public class Brand extends AuditableEntity<String> {

    private String name;

    private String description;

    @OneToMany(mappedBy = "brand")
    private Collection<Product> products;

    @Version
    private Long version;
}
