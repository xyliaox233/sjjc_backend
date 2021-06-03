package sjjc.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sjjc.backend.domain.Response;

@Controller
@ResponseBody
public class controller {
    private static final String pre="/api/chart";

    @Autowired
    service service;

    /**
     * 支持模糊搜索，
     * root：searchName对应根节点（limit 1）
     * children + biggestHolder（不包括Company）
     * @param searchName
     * @return
     */
    @GetMapping(pre+"/findRelatedCompanies/{searchName}")
    public Response findRelatedCompaniesByName(@PathVariable String searchName){
        System.out.println(searchName);
        return Response.builder().content(service.findRelatedCompanies(searchName)).status(0).build();
    }

    /**
     * 支持模糊搜索
     * 限制4层，holder包括Company、DGJ
     * @param searchName
     * @return
     */
    @GetMapping(pre+"/findUpCompanies/{searchName}")
    public Response getUpCompaniesByName(@PathVariable String searchName){
        System.out.println(searchName);
        return Response.builder().content(service.findUpCompanies(searchName)).status(0).build();
    }

    /**
     * 支持模糊搜索
     * @param companyName
     * @return
     */
    @GetMapping(pre+"/findRiskChart/{companyName}")
    public Response findRiskChartByName(@PathVariable String companyName){
        System.out.println(companyName);
        return Response.builder().content(service.findRiskChart(companyName)).status(0).build();
    }

    @GetMapping(pre+"/findDetail/{companyName}")
    public Response findDetail(@PathVariable String companyName){
        System.out.println(companyName);
        return Response.builder().content(service.findDetail(companyName)).status(0).build();
    }

    @GetMapping(pre+"/getNameList/{keyWord}")
    public Response getNameList(@PathVariable String keyWord){
        System.out.println(keyWord);
        return Response.builder().content(service.getNameList(keyWord)).status(0).build();
    }

}
