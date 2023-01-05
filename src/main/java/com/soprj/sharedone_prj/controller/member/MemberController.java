package com.soprj.sharedone_prj.controller.member;

import com.soprj.sharedone_prj.domain.buyer.BuyerDto;
import com.soprj.sharedone_prj.domain.member.MemberDto;
import com.soprj.sharedone_prj.service.member.EmailServiceImpl;
import com.soprj.sharedone_prj.service.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("list")
    public void list(Model model) {
        List<MemberDto> list = memberService.listMember();

        model.addAttribute("memberList", list);
    }

    @PostMapping("list")
    public String modifyy(MemberDto member, RedirectAttributes rttr) {

        return "redirect:/member/list";
    }

    @GetMapping("get")
    public void get(@RequestParam(name = "m_member_id") String m_member_id, Model model) {
        MemberDto member = memberService.get(m_member_id);
        model.addAttribute("member", member);
    }

    @PostMapping("get")
    public String getto(MemberDto member, RedirectAttributes rttr) {

        return "redirect:/member/list";
    }

    @RequestMapping("register")
    public void register() {

    }

    @PostMapping("register")
    public String register(MemberDto member,
                           RedirectAttributes rttr) throws Exception {

        String cnttt = emailService.sendSimpleMessage(member.getM_member_email());
        String password = passwordEncoder.encode(emailService.ePw);
        member.setM_member_password(password);

        int cnt = memberService.register(member);

        return "redirect:/member/manage";
    }

    @PostMapping("modify")
    public String modify(String m_member_id, String oldPassword,
                         String m_member_password, String m_member_email, RedirectAttributes rttr) {

        System.out.println(m_member_id);
        System.out.println(m_member_email);

        String password = memberService.getPassword(m_member_id);
        if(oldPassword != null) {
            if (passwordEncoder.matches(oldPassword, password)) {
                System.out.println("야호");
                int cnt = memberService.updatePasswordd(m_member_id, m_member_password);
            }
        }
        if (m_member_email != memberService.getEmail(m_member_id) && m_member_email != null) {
            System.out.println("이게 왜...?");
            int cnt = memberService.updateEmail(m_member_id, m_member_email);
        }

        rttr.addAttribute("id", m_member_id);

        return "redirect:/member/modify?m_member_id={id}";
    }

    @GetMapping("modify")
    public void modify(String m_member_id, Model model) {
        MemberDto member = memberService.get(m_member_id);

        model.addAttribute("member", member);
    }

    @PostMapping("remove")
    public String remove(String m_member_id, RedirectAttributes rttr) {

        System.out.println(m_member_id);
        int cnt = memberService.remove(m_member_id);

        return "redirect:/member/manage";
    }

    @GetMapping("login")
    public void loginGET(String error, String logout) {
        log.info("login get............");
        log.info("logout: " + logout);

        if (logout != null) {
            log.info("user logout..........");
        }
    }

    @GetMapping("manage")
    public void manage(Model model) {
        List<MemberDto> list = memberService.listMember();
        List<MemberDto> authority = memberService.selectAuthority();

        model.addAttribute("memberList", list);
        model.addAttribute("authority", authority);

    }

    @PostMapping("manage")
    public String manageModify(MemberDto member, RedirectAttributes rttr) {


        int cnt = memberService.updateGrade(member);

        return "redirect:/member/manage";
    }

    @GetMapping("resetPassword")
    public String resetPassword(@RequestParam(name = "m_member_id") String m_member_id, RedirectAttributes rttr) throws Exception {

        int cnt = memberService.resetPassword(m_member_id);
        String email = memberService.getEmail(m_member_id);
        String cnttt = emailService.sendSimpleMessage(email);

        return "redirect:/member/manage";
    }


}
