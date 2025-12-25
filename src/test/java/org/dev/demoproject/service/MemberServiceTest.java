package org.dev.demoproject.service;

import org.dev.demoproject.entity.Member;
import org.dev.demoproject.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMember_savesAndReturns() {
        Member m = new Member("John", "Doe", "john@example.com", "123");
        when(memberRepository.save(any(Member.class))).thenAnswer(i -> {
            Member arg = i.getArgument(0);
            arg.setId(1L);
            return arg;
        });

        Member created = memberService.createMember(m);

        assertNotNull(created);
        assertEquals(1L, created.getId());
        verify(memberRepository, times(1)).save(m);
    }

    @Test
    void getMemberById_existing_returnsMember() {
        Member m = new Member("A","B","a@b.com","111");
        m.setId(5L);
        when(memberRepository.findById(5L)).thenReturn(Optional.of(m));

        Member found = memberService.getMemberById(5L);

        assertEquals(5L, found.getId());
    }

    @Test
    void getMemberById_notFound_throws() {
        when(memberRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> memberService.getMemberById(99L));
    }

    @Test
    void deleteMember_existing_deletes() {
        Member m = new Member("X","Y","x@y.com","000");
        m.setId(2L);
        when(memberRepository.findById(2L)).thenReturn(Optional.of(m));
        doNothing().when(memberRepository).deleteById(2L);

        boolean res = memberService.deleteMember(2L);

        assertTrue(res);
        verify(memberRepository, times(1)).deleteById(2L);
    }

    @Test
    void deleteMember_notFound_throws() {
        when(memberRepository.findById(77L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> memberService.deleteMember(77L));
    }

    @Test
    void updateMember_existing_updatesFields() {
        Member existing = new Member("OldFirst","OldLast","o@o.com","9");
        existing.setId(3L);
        when(memberRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(memberRepository.save(any(Member.class))).thenAnswer(i -> i.getArgument(0));

        Member update = new Member("NewFirst","NewLast","n@n.com","8");

        Member out = memberService.updateMember(3L, update);

        // Expect the fields to be updated to values from 'update'
        assertEquals("NewFirst", out.getFirstName());
        assertEquals("NewLast", out.getLastName());
        assertEquals("n@n.com", out.getEmail());
        assertEquals("8", out.getPhone());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

}

