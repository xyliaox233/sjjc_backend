package sjjc.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sjjc.backend.domain.Response;

@Controller
@ResponseBody
public class controller {
    @Autowired
    service service;

    @GetMapping("/load")
    public Response loadCsv(){
        return Response.builder().content("Success").status(0).build();
    }
}
