package franxx.code.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class AuditableEntity<T extends Serializable> {

    @Id
    private T id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
