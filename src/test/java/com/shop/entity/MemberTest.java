package com.shop.entity;

import com.shop.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "gildong", roles = "USER")
    public void auditingTest(){
        Member member = new Member();
        memberRepository.save(member);

        em.flush();
        em.clear();

        Member member1 = memberRepository.findById(member.getId())
                .orElseThrow(EntityNotFoundException::new);
        System.out.println("register time : "+ member1.getRegTime());
        System.out.println("update time : "+ member1.getUpdateTime());
        System.out.println("create member : "+ member1.getCreatedBy());
        System.out.println("modify member : "+ member1.getModifiedBy());
    }
}