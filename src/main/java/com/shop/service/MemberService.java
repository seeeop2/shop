package com.shop.service;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@Transactional  //비즈니스 로직을 담당하는 서비스 계층 클래스에 트랜젝션 선언. 로직 처리 중 에러 발생시, 변경된 데이터를 데이터 로직 수행 이전으로 콜백
@RequiredArgsConstructor    /*Bean을 주입하는 방법으로 @Autowired or 필드 주입(Setter 주입) or 생성자 주입...
                                @RequiredArgsConstructor은 final이나 @NonNUll이 붙은 필드에 생성자를 생성한다.
                                Bean은 생성자가 1개이고, 생성자가 파라미터 타입이 Bean으로 등록이 가능하다면
                                @Autowired 없이 의존성 주입이 가능하다. */
public class MemberService implements UserDetailsService {   //UserDetailsService를 구현

    private final MemberRepository memberRepository;    //@RequiredArgsConstructor을 이용한 생성자 주입

    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member){    // 이미 가입된 회원의 경우 IllegalStateException 예외 발생
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { //login할 유저의 email 필요

        Member member = memberRepository.findByEmail(email);

        if(member == null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()   //User객체를 반환. User객체를 생성하기 위해 회원의 email,pw,role을 파라미터로 넘겨 준다.
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

}