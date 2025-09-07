package ideau.controlePatrimonioAPI.controller.implementation;
/*import org.springframework.stereotype.Controller;*/
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello-world")
public class HelloWorldController {
    @GetMapping("/hello")
    public String helloWorld(){
        return "Teste Hello World";
    }
}
