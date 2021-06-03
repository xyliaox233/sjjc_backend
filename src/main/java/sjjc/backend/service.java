package sjjc.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sjjc.backend.domain.Company;
import sjjc.backend.domain.Entity;
import sjjc.backend.domain.HoldingRelation;
import sjjc.backend.repository.CompanyRepository;
import sjjc.backend.repository.HoldingRelationRepository;

import java.util.*;

@Service
public class service {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private HoldingRelationRepository holdingRelationRepository;

    public Company getCompanyByMyId(String id) {
        return companyRepository.findByMyId(id);
    }

    private double calculateRisk(Company company){
        double safe=0;
        double stateOwnedRatio=Double.parseDouble(company.getStateOwnedRatio());
        if(company.getIsStateOwned().equals("1")&&stateOwnedRatio<50)stateOwnedRatio=55;
        double scale=Math.log1p(Double.parseDouble(company.getRegAsset())*Double.parseDouble(company.getStaffNum())/10000);
        double centerDegree=Math.pow(Integer.parseInt(company.getInDegree())+Integer.parseInt(company.getOutDegree()),1.5);
        safe=60+stateOwnedRatio-scale-centerDegree;
        System.out.println("safe:" +safe+
                " stateOwnedRatio:" +stateOwnedRatio+
                " scale:" +scale+
                " centerDegree:" +centerDegree);
        return safe;
    }

    public List<Map<String, Object>> findRelatedCompanies(String searchName) {
        List<Map<String, Object>> list = new ArrayList<>();
        Company root = companyRepository.findByOrgName(".*" + searchName + ".*");
        Set<HoldingRelation> holdingRelations = holdingRelationRepository.getMultiDepthChildrenCompaniesRelations(root.getId());
        Set<Company> companies = new HashSet<>();
        for (HoldingRelation holdingRelation : holdingRelations) {
            Company from = ((Company) holdingRelation.getFrom());
            Company to = ((Company) holdingRelation.getTo());
            companies.add(from);
            companies.add(to);

            Map<String, Object> relationMap = new HashMap<>();

            relationMap.put("group", "edges");
            relationMap.put("classes", "HoldingRelation");

            Map<String, String> relDataMap = new HashMap<>();
            relDataMap.put("id", holdingRelation.getId() + "");
            relDataMap.put("source", from.getOrgName());
            relDataMap.put("target", to.getOrgName());
            relDataMap.put("name", holdingRelation.getDisplayName());
            relDataMap.put("holdRatio", holdingRelation.getHoldRatio());

            relationMap.put("data", relDataMap);

            list.add(relationMap);
        }
        for (Company company : companies) {
            System.out.println(company.getOrgName());
            Map<String, Object> nodeMap = new HashMap<>();
            nodeMap.put("group", "nodes");
            nodeMap.put("data", company);
            nodeMap.put("classes", "Company");
            list.add(nodeMap);

            if (company.getId() != root.getId()) {
                Entity biggestHolder = holdingRelationRepository.getBiggestHolder(company.getId()).getFrom();
                if (biggestHolder instanceof Company) continue;
                Map<String, Object> holderMap = new HashMap<>();
                nodeMap.put("group", "nodes");
                nodeMap.put("data", biggestHolder);
                nodeMap.put("classes", biggestHolder.getClass().getName());
                list.add(holderMap);
                System.out.println((biggestHolder));
            }
        }
        return list;

    }

    public List<Map<String, Object>> findUpCompanies(String searchName) {
        List<Map<String, Object>> list = new ArrayList<>();
        Company root = companyRepository.findByOrgName(".*" + searchName + ".*");
        Set<HoldingRelation> holdingRelations = holdingRelationRepository.getMultiDepthUpperHoldersRelations(root.getId());
        Set<Entity> holders = new HashSet<>();
        for (HoldingRelation holdingRelation : holdingRelations) {
            Company from = ((Company) holdingRelation.getFrom());
            Company to = ((Company) holdingRelation.getTo());
            holders.add(from);
            holders.add(to);

            Map<String, Object> relationMap = new HashMap<>();

            relationMap.put("group", "edges");
            relationMap.put("classes", "HoldingRelation");

            Map<String, String> relDataMap = new HashMap<>();
            relDataMap.put("id", holdingRelation.getId() + "");
            relDataMap.put("source", from.getOrgName());
            relDataMap.put("target", to.getOrgName());
            relDataMap.put("name", holdingRelation.getDisplayName());
            relDataMap.put("holdRatio", holdingRelation.getHoldRatio());

            relationMap.put("data", relDataMap);

            list.add(relationMap);
        }
        for (Entity holder : holders) {
            System.out.println(holder);
            Map<String, Object> nodeMap = new HashMap<>();
            nodeMap.put("group", "nodes");
            nodeMap.put("data", holder);
            nodeMap.put("classes", holder.getClass().getName());
            list.add(nodeMap);
        }
        return list;
    }

    public List<Map<String, Object>> findRiskChart(String companyName) {
        List<Map<String, Object>> list = new ArrayList<>();
        Company root = companyRepository.findByOrgName(".*" + companyName + ".*");
        Set<HoldingRelation> holdingRelations = holdingRelationRepository.getMultiDepthChildrenCompaniesRelations(root.getId());
        Set<Company> companies = new HashSet<>();
        for (HoldingRelation holdingRelation : holdingRelations) {
            Company from = ((Company) holdingRelation.getFrom());
            Company to = ((Company) holdingRelation.getTo());
            companies.add(from);
            companies.add(to);

            Map<String, Object> relationMap = new HashMap<>();

            relationMap.put("group", "edges");
            relationMap.put("classes", "HoldingRelation");

            Map<String, String> relDataMap = new HashMap<>();
            relDataMap.put("id", holdingRelation.getId() + "");
            relDataMap.put("source", from.getOrgName());
            relDataMap.put("target", to.getOrgName());
            relDataMap.put("name", holdingRelation.getDisplayName());
            relDataMap.put("holdRatio", holdingRelation.getHoldRatio());

            relationMap.put("data", relDataMap);

            list.add(relationMap);
        }
        for (Company company : companies) {
            System.out.println(company.getOrgName());
            company.setSafety(calculateRisk(company));
            Map<String, Object> nodeMap = new HashMap<>();
            nodeMap.put("group", "nodes");
            nodeMap.put("data", company);
            nodeMap.put("classes", "Company");
            list.add(nodeMap);
        }
        return list;


    }

    public Company findDetail(String companyName) {
        return companyRepository.findByOrgName(".*"+companyName+".*");
    }

    public List<Map<String,Object>> getNameList(String keyword){
        LinkedList<Company> companies=companyRepository.findCompaniesByKeyword(".*"+keyword+".*");
        List<Map<String ,Object>> res=new LinkedList<>();
        for(Company company:companies){
            Map<String,Object> map=new HashMap<>();
            map.put("id",company.getId()+"");
            map.put("name",company.getOrgName());
            res.add(map);
        }
        return res;
    }


}