package franxx.code.jpa.contract;

import java.time.LocalDateTime;

public interface UpdatedAtAware {

    void setUpdatedAt(LocalDateTime updatedAt);
}
