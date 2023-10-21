package hello.servlet.web.frontcontroller_practice.v3;

import hello.servlet.web.frontcontroller_practice.ModelView;
import java.util.Map;

public interface ControllerV3 {

    ModelView process(Map<String, String> paramMap);

}
