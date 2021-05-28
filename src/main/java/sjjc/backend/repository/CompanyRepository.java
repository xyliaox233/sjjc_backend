package sjjc.backend.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sjjc.backend.domain.Company;

@Repository
@Transactional
public interface CompanyRepository extends Neo4jRepository<Company,Long> {


}
