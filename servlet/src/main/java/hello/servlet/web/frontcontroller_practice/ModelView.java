package hello.servlet.web.frontcontroller_practice;

import java.util.Map;
import lombok.Getter;

@Getter
public class ModelView {

    private Map<String, Object> model;
    private String viewName;


    public ModelView(String viewName) {
        this.viewName = viewName;
    }
}
