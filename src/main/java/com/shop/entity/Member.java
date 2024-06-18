package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity // 엔티티
@Table(name = "member") // 테이블명
@Getter
@Setter
@ToString
public class Member extends BaseEntity{
    // 기본키 컬럼명 = member_id AI -> 데이터 저장시 1씩 증가
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    // 중복 허용 X
    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    private String phoneNum;

    // Enum -> 컴퓨터:숫자 우리:문자
    // 데이터베이스 문자 그대로 -> USER, ADMIN
    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto,
                                      PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setPhoneNum(memberFormDto.getPhoneNum());
        member.setRole(Role.ADMIN);
        return member;
    }
}
