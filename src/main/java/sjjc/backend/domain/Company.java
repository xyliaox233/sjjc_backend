package sjjc.backend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

@NoArgsConstructor
@Data
@NodeEntity
@Component
public class Company implements Entity{
    @Id
    @GeneratedValue
    int id;
    @Property
    String org_id;
    String stock_id;
    String org_name;
    String org_short;
    String type_name;

    @Relationship(type = "持股")
    List<HoldingRelation> holdings;

    @Relationship(type = "董高监")
    List<DGJ> dgjs;
}
