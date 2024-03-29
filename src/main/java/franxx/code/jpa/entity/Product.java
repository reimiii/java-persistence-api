package franxx.code.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Product {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "brand_id",
            referencedColumnName = "id"
    )
    private Brand brand;

    private String name;

    private Long price;

    private String description;

    @ManyToMany(mappedBy = "likes")
    private Collection<User> likedBy;

}
