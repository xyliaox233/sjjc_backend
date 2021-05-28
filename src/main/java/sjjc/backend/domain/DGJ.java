package sjjc.backend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.stereotype.Component;

import java.util.List;

@NoArgsConstructor
@Data
@NodeEntity
@Component
public class DGJ implements Entity{
    @GeneratedValue
    @Id
    int id;
    String name;
    String position;

    @Relationship(type = "持股")
    List<HoldingRelation> holdings;
}
