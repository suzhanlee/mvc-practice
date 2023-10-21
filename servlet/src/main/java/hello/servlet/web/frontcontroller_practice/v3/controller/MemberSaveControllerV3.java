package hello.servlet.web.frontcontroller_practice.v3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller_practice.ModelView;
import hello.servlet.web.frontcontroller_practice.v3.ControllerV3;
import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        String name = paramMap.get("name");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(name, age);
        memberRepository.save(member);

        ModelView mv = new ModelView("save");
        mv.getModel().put("member", member);
        return mv;
    }
}
