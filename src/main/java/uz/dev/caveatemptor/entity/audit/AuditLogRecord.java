package uz.dev.caveatemptor.entity.audit;

import org.hibernate.annotations.CreationTimestamp;
import uz.dev.caveatemptor.util.Constants;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class AuditLogRecord {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private long id;

    @NotNull
    private String message;

    @NotNull
    private long entityId;

    @NotNull
    private Class<? extends Auditable> entityClass;

    @NotNull
    private long userId;

    @CreationTimestamp
    private LocalDateTime createdOn;

    protected AuditLogRecord() {
    }

    public AuditLogRecord(String message,
                          Auditable entityInstance,
                          Long userId) {
        this.message = message;
        this.entityId = entityInstance.getId();
        this.entityClass = entityInstance.getClass();
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditLogRecord)) return false;
        AuditLogRecord that = (AuditLogRecord) o;
        return entityId == that.entityId && userId == that.userId && message.equals(that.message) && entityClass.equals(that.entityClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, entityId, entityClass, userId);
    }
}
