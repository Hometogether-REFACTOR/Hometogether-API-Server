package hometoogether.hometoogether.domain.front;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Controller
public class WebController implements ErrorController {

//    @GetMapping({"/test"})
//    public HashMap test() {
//        HashMap result = new HashMap();
//        result.put("message", "test입니다.");
//
//        return result;
//    }

    @GetMapping({"/hometoo", "/error"})
    public String home() {
        return "index.html";
    }
}
