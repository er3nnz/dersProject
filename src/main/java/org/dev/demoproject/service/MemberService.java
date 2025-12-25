package org.dev.demoproject.service;

import org.dev.demoproject.entity.Member;
import org.dev.demoproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public boolean deleteMember(Long id) {
        memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Böyle bir member yok."));
        memberRepository.deleteById(id);
        return true;
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Böyle bir member yok."));
    }

    public Member updateMember(Long id, Member member) {
        Member member1 = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Böyle bir member yok."));
        member1.setFirstName(member1.getFirstName());
        member1.setLastName(member.getLastName());
        member1.setEmail(member.getEmail());
        member1.setPhone(member.getPhone());
        return memberRepository.save(member1);
    }

}
