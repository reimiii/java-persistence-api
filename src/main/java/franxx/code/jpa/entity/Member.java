package franxx.code.jpa.entity;

import franxx.code.jpa.entity.embedded.Name;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Table(name = "members")
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    @Embedded
    private Name name;

    @ElementCollection
    @CollectionTable(
            name = "hobbies",
            joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id")
    )
    @Column(name = "name")
    private Collection<String> hobbies;
}
