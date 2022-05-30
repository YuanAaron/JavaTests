package cn.coderap.rest;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/greeting")
    @ResponseStatus(HttpStatus.OK) //仅用于演示状态码可以修改
    public String greeting() {
        return "Hello World!";
    }

    @PostMapping("/greeting")
    public String greeting1(@RequestParam String name, @RequestBody Profile profile) {
        return "Hello World!" + " " + name + " " + profile.gender + " " + profile.idNo;
    }

    @PutMapping ("/greeting/{name}")
    public String greeting2(@PathVariable String name) {
        return "Hello World!" + name;
    }

    @Data
    static class Profile {
        private String gender;
        private String idNo;
    }

}
