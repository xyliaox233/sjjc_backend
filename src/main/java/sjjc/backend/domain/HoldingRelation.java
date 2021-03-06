package sjjc.backend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@RelationshipEntity(type = "持股")
@NoArgsConstructor
@Data
@Component
public class HoldingRelation implements Serializable {
    @Id
    @GeneratedValue
    int id;

    @StartNode
    Entity from;

    @EndNode
    Entity to;

    @Property(name = "displayName")
    String displayName;

    @Property(name = "holdRatio")
    String holdRatio;

    @Property(name = "isLargestHolding")
    String isLargestHolding;


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
        if (obj instanceof HoldingRelation) {
            return ((HoldingRelation) obj).getId() == this.id;
        }
        return false;
    }


}
