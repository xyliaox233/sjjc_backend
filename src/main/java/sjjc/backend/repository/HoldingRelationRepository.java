package sjjc.backend.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sjjc.backend.domain.DGJ;
import sjjc.backend.domain.HoldingRelation;

import java.util.Set;

@Repository
@Transactional
public interface HoldingRelationRepository extends Neo4jRepository<HoldingRelation,Integer> {
    @Query("MATCH (e1:Company)-[r:持股*1..4]->(e2:Company) where ID(e1)=$id and (ID(e1)<ID(e2) or ID(e1)>ID(e2)) return e1,e2,r")
    Set<HoldingRelation> getMultiDepthChildrenCompaniesRelations(int id);

    @Query("MATCH p=()-[r:持股]->(e2:Company) where ID(e2)=$id and r.isLargestHolding='1' return p")
    HoldingRelation getBiggestHolder(int id);

    @Query("MATCH (e1:Company)-[r:持股*1..6]->(e2:Company)" +
            " where ID(e2)=$id and (ID(e1)<ID(e2) or ID(e1)>ID(e2))" +
            " return e1,e2,r")
    Set<HoldingRelation> getMultiDepthUpperHoldersRelations(int id);


}
