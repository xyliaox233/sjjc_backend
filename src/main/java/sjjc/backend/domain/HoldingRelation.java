package sjjc.backend.domain;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "持股")
public class HoldingRelation {
    @Id
    @GeneratedValue
    int id;

    @StartNode
    Entity from;

    @EndNode
    Entity to;
}
