package org.fub;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements TestAPI {

    @Override
    public String setup() {
        return "Server up and running..........";
    }
}
