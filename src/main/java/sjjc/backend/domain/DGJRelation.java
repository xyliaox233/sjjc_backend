package sjjc.backend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@RelationshipEntity(type = "董高监")
@NoArgsConstructor
@Data
@Component
public class DGJRelation implements Serializable {
    @Id
    @GeneratedValue
    int id;

    @StartNode
    Entity from;

    @EndNode
    Entity to;

    @Property(name = "position")
    String position;

    @Override
    public int hashCode() {
        return id;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj instanceof DGJRelation) {
            return ((DGJRelation) obj).getId() == this.id;
        }
        return false;
    }

}
