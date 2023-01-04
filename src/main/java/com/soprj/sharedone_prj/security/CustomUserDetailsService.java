package com.soprj.sharedone_prj.security;

import com.soprj.sharedone_prj.domain.member.MemberDto;
import com.soprj.sharedone_prj.domain.member.MemberLoginDto;
import com.soprj.sharedone_prj.mapper.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberMapper memberMapper;

//    public CustomUserDetailsService(){
//        this.passwordEncoder = new BCryptPasswordEncoder();
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        MemberDto memberDto = memberMapper.getMember(username);
        MemberLoginDto memberLoginDto = memberMapper.getMemberLogin(username);

        if(memberDto == null){
            return null;
        }

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        if(memberLoginDto.getM_authority_grade() != null){
            for(String auth : memberLoginDto.getM_authority_grade()){
                authorityList.add(new SimpleGrantedAuthority(auth));
            }
        }

        User user = new User(memberDto.getM_member_id(), memberDto.getM_member_password(), authorityList);

        return user;

    }
}
