package sjjc.backend.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sjjc.backend.domain.Company;
import sjjc.backend.domain.DGJ;

@Repository
@Transactional
public interface DgjRepository  extends Neo4jRepository<DGJ,Long> {

}
