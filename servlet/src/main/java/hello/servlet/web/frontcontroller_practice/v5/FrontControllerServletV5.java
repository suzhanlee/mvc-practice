package hello.servlet.web.frontcontroller_practice.v5;

import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller_practice.ModelView;
import hello.servlet.web.frontcontroller_practice.MyView;
import hello.servlet.web.frontcontroller_practice.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller_practice.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller_practice.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller_practice.v5.adaptor.ControllerV3HandlerAdaptor;
import hello.servlet.web.frontcontroller_practice.v5.adaptor.ControllerV4HandlerAdaptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")

public class FrontControllerServletV5 {

    private Map<String, Object> handlerMappingMap = new HashMap<>();
    private List<MyHandlerAdaptor> handlerAdaptors = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdaptor();
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form",
            new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());

        handlerMappingMap.put("/front-controller/v5/v3/members/new-form",
            new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
    }

    private void initHandlerAdaptor() {
        handlerAdaptors.add(new ControllerV4HandlerAdaptor());
        handlerAdaptors.add(new ControllerV3HandlerAdaptor());
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdaptor adaptor = getHandlerAdaptor(handler);

        ModelView mv = adaptor.handle(request, response, handler);

        MyView view = viewResolver(mv.getViewName());
        view.render(mv.getModel(), request, response);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdaptor getHandlerAdaptor(Object handler) {
        for (MyHandlerAdaptor adaptor : handlerAdaptors) {
            if (adaptor.supports(handler)) {
                return adaptor;
            }
        }
        throw new IllegalArgumentException("handlerAdaptor를 찾을 수 없습니다!");
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
