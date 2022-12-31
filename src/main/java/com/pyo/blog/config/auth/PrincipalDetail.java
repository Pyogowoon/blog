package com.pyo.blog.config.auth;

import com.pyo.blog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가되면
//UserDetails 타입의 오브젝트를 스프링 시큐리티의 고유한 세션저장소에 저장
public class PrincipalDetail implements UserDetails {

    private User user;  //콤포지션 (객체를 품고있는것)

    public PrincipalDetail(User user){
        this.user= user;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

        //계정이 만료되지 않았는지 리턴한다 (true = 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겨있는지 아닌지 리턴한다. (true = 잠겨있지않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료되지 않았는지 리턴 (true = 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화(사용가능)인지 리턴 (true = 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    //계정이 갖고있는 권한 목록을 리턴한다. (권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 우리는 한개만있음)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();
//        collectors.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return "ROLE_"+user.getRole(); // ROLE_USER로 리턴 (스프링 규칙임 prefix 붙여줘야함)
//            }
//        });
        //자바에서는 함수만 넘길 수 없기떄문에 람다식 써도됌
        collectors.add(()->{return "ROLE_"+user.getRole();});
        return collectors;
    }
}
