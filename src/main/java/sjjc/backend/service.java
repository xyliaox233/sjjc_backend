package sjjc.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sjjc.backend.domain.Company;
import sjjc.backend.domain.DGJ;
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
        return Integer.parseInt(((safe*100)+"").split("\\.")[0])/100.0;
    }

    public List<Map<String, Object>> findRelatedCompanies(String searchName) {
        List<Map<String, Object>> list = new ArrayList<>();
        Company root = companyRepository.findByOrgName(".*" + searchName + ".*");
        System.out.println(root);
        Set<HoldingRelation> holdingRelations = holdingRelationRepository.getMultiDepthChildrenCompaniesRelations(root.getId());
        Set<Company> companies = new HashSet<>();
        for (HoldingRelation holdingRelation : holdingRelations) {
            Company from = ((Company) holdingRelation.getFrom());
            Company to = ((Company) holdingRelation.getTo());
            companies.add(from);
            companies.add(to);
            System.out.println("relation:"+from.getId()+"---"+to.getId());

            Map<String, Object> relationMap = new HashMap<>();

            relationMap.put("group", "edges");
            relationMap.put("classes", "HoldingRelation");

            Map<String, String> relDataMap = new HashMap<>();
            relDataMap.put("id", holdingRelation.getId() + "");
            relDataMap.put("source", from.getId()+"");
            relDataMap.put("target", to.getId()+"");
            relDataMap.put("name", holdingRelation.getDisplayName());
            relDataMap.put("holdRatio", holdingRelation.getHoldRatio());

            relationMap.put("data", relDataMap);

            list.add(relationMap);
        }
        for (Company company : companies) {
            System.out.println(company.getId()+":"+company.getName());
            Map<String, Object> nodeMap = new HashMap<>();
            nodeMap.put("group", "nodes");
            nodeMap.put("data", company);
            nodeMap.put("classes", "Company");
            list.add(nodeMap);

            if (company.getId() != root.getId()) {
                HoldingRelation holdingRelation = holdingRelationRepository.getBiggestHolder(company.getId());
                Entity biggestHolder=holdingRelation.getFrom();

                if (biggestHolder instanceof DGJ) {

                    Map<String, Object> holderMap = new HashMap<>();
                    holderMap.put("group", "nodes");
                    holderMap.put("data", biggestHolder);
                    holderMap.put("classes", biggestHolder.getClass().getName().split("\\.")[3]);
                    list.add(holderMap);

                    Map<String, Object> holdingRelationMap = new HashMap<>();

                    holdingRelationMap.put("group", "edges");
                    holdingRelationMap.put("classes", "HoldingRelation");

                    Map<String, String> relDataMap = new HashMap<>();
                    relDataMap.put("id", holdingRelation.getId() + "");
                    relDataMap.put("source", ((DGJ) biggestHolder).getId() + "");
                    relDataMap.put("target", company.getId() + "");
                    relDataMap.put("name", holdingRelation.getDisplayName());
                    relDataMap.put("holdRatio", holdingRelation.getHoldRatio());

                    holdingRelationMap.put("data", relDataMap);

                    list.add(holdingRelationMap);

                    System.out.println((biggestHolder));

                    System.out.println("relation:" + ((DGJ) biggestHolder).getId() + "---" + company.getId());
                }
            }
        }
        for(Map<String,Object> l:list){
            System.out.println(l);
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
            System.out.println("relation:"+from.getId()+"---"+to.getId());

            Map<String, Object> relationMap = new HashMap<>();

            relationMap.put("group", "edges");
            relationMap.put("classes", "HoldingRelation");

            Map<String, String> relDataMap = new HashMap<>();
            relDataMap.put("id", holdingRelation.getId() + "");
            relDataMap.put("source", from.getId()+"");
            relDataMap.put("target", to.getId()+"");
            relDataMap.put("name", holdingRelation.getDisplayName());
            relDataMap.put("holdRatio", holdingRelation.getHoldRatio());

            relationMap.put("data", relDataMap);

            list.add(relationMap);
        }
        for (Entity holder : holders) {
            System.out.println("holder:");
            System.out.println(holder);
            Map<String, Object> nodeMap = new HashMap<>();
            nodeMap.put("group", "nodes");
            nodeMap.put("data", holder);
            nodeMap.put("classes", holder.getClass().getName().split("\\.")[3]);
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
            relDataMap.put("source", from.getId()+"");
            relDataMap.put("target", to.getId()+"");
            relDataMap.put("name", holdingRelation.getDisplayName());
            relDataMap.put("holdRatio", holdingRelation.getHoldRatio());

            relationMap.put("data", relDataMap);

            list.add(relationMap);
        }
        for (Company company : companies) {
            System.out.println(company.getName());
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
        Company company=companyRepository.findByOrgName(".*"+companyName+".*");
        company.setSafety(calculateRisk(company));
        return company;
    }

    public List<Map<String,Object>> getNameList(String keyword){
        LinkedList<Company> companies=companyRepository.findCompaniesByKeyword(".*"+keyword+".*");
        List<Map<String ,Object>> res=new LinkedList<>();
        for(Company company:companies){
            Map<String,Object> map=new HashMap<>();
            map.put("id",company.getId()+"");
            map.put("name",company.getName());
            res.add(map);
        }
        return res;
    }


}
