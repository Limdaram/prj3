package com.soprj.sharedone_prj.service.member;

import com.soprj.sharedone_prj.domain.buyer.BuyerDto;
import com.soprj.sharedone_prj.domain.member.MemberDto;
import com.soprj.sharedone_prj.mapper.member.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<MemberDto> listMember() {
        return memberMapper.list();
    }

    public MemberDto getMember(String m_member_id) {
        return memberMapper.getMember(m_member_id);
    }


    public int updateEmail(MemberDto member) {
        return memberMapper.updateMemberEmail(member);
    }

    public int updatePassword(MemberDto member) {
        return memberMapper.updateMemberPassword(member);
    }

    public int addMember(MemberDto member) {
        return memberMapper.addMember(member);
    }


    public int register(MemberDto member) {
        String pw = member.getM_member_password();
        member.setM_member_password(passwordEncoder.encode(pw));

        int cnt = memberMapper.insert(member);
        return cnt;
    }

    public int remove(String m_member_id) {
        return memberMapper.removeById(m_member_id);
    }

    public MemberDto get(String m_member_id) {
        return memberMapper.select(m_member_id);
    }

    public int updatePasswordd(String m_member_id, String m_member_password) {
        return memberMapper.updatePasswordd(m_member_id, m_member_password);
    }

    public List<MemberDto> selectAuthority() {
        return memberMapper.selectAuthority();
    }

    public int updateGrade(MemberDto member) {
        return memberMapper.updateGrade(member);
    }
}
