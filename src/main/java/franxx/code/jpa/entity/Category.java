package franxx.code.jpa.entity;

import franxx.code.jpa.contract.UpdatedAtAware;
import franxx.code.jpa.listener.UpdatedAtListener;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Calendar;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@EntityListeners({
        UpdatedAtListener.class
})
public class Category implements UpdatedAtAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
