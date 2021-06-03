package sjjc.backend.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sjjc.backend.domain.Company;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface CompanyRepository extends Neo4jRepository<Company,Long> {
    Company findById(long id);
    Company findByMyId(String id);

    @Query("MATCH (e:Company) where e.orgName=~$namePattern return ID(e),e limit 1")
    Company findByOrgName(String namePattern);

    @Query("MATCH (e:Company) where e.orgName=~$namePattern and e.outDegree>'0' return ID(e),e order by e.orgName limit 15")
    LinkedList<Company> findCompaniesByKeyword(String namePattern);

}
