package ma.projet.classes;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class EmployeTacheId implements Serializable {
    private Long employeId;
    private Long tacheId;

    public Long getEmployeId() { return employeId; }
    public void setEmployeId(Long employeId) { this.employeId = employeId; }
    public Long getTacheId() { return tacheId; }
    public void setTacheId(Long tacheId) { this.tacheId = tacheId; }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (employeId != null ? employeId.hashCode() : 0);
        hash = 31 * hash + (tacheId != null ? tacheId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        EmployeTacheId other = (EmployeTacheId) obj;
        if (employeId == null ? other.employeId != null : !employeId.equals(other.employeId)) return false;
        if (tacheId == null ? other.tacheId != null : !tacheId.equals(other.tacheId)) return false;
        return true;
    }
}
