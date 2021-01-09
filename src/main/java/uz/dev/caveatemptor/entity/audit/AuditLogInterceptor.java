package uz.dev.caveatemptor.entity.audit;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AuditLogInterceptor extends EmptyInterceptor {

    private Session currentSession;
    private long currentUserId;
    private Set<Auditable> inserts = new HashSet<>();
    private Set<Auditable> updates = new HashSet<>();

    public void setCurrentSession(Session session) {
        this.currentSession = session;
    }

    public void setCurrentUserId(long currentUserId) {
        this.currentUserId = currentUserId;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof Auditable)
            inserts.add((Auditable) entity);
        return false;
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof Auditable)
            updates.add((Auditable) entity);
        return false;
    }

    @Override
    public void postFlush(Iterator entities) {
        try (Session tempSession = currentSession
                .sessionWithOptions()
                .connection()
                .openSession()) {
            saveAllWithMessage(tempSession, inserts, "insert");
            saveAllWithMessage(tempSession, updates, "update");
            tempSession.flush();
        } finally {
            inserts.clear();
            updates.clear();
        }
    }

    private void saveAllWithMessage(Session tempSession, Set<Auditable> entities, String message) {
        for (Auditable entity : entities) {
            tempSession.persist(new AuditLogRecord(message, entity, currentUserId));
        }
    }
}
