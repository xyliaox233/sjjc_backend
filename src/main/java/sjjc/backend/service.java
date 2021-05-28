package sjjc.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjjc.backend.repository.CompanyRepository;
import sjjc.backend.repository.DgjRepository;

@Service
public class service {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    DgjRepository dgjRepository;
}
