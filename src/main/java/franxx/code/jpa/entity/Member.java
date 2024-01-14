package franxx.code.jpa.entity;

import franxx.code.jpa.entity.embedded.Name;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

import java.util.Collection;
import java.util.Map;

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

    @Transient
    private String fullName;

    @ElementCollection
    @CollectionTable(
            name = "hobbies",
            joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id")
    )
    @Column(name = "name")
    private Collection<String> hobbies;

    @ElementCollection
    @CollectionTable(
            name = "skills",
            joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id")
    )
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    private Map<String, Integer> skills;

    @PostLoad
    public void postLoad() {
        fullName = name.getTitle() + ". " + name.getFirstName();
    }
}
