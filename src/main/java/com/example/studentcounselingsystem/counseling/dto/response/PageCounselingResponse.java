package com.example.studentcounselingsystem.counseling.dto.response;

import com.example.studentcounselingsystem.counseling.entity.Counseling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class PageCounselingResponse extends PageImpl<Counseling> {
    public PageCounselingResponse(Page<Counseling> counselingPage) {
        super(counselingPage.getContent(), counselingPage.getPageable(), counselingPage.getTotalPages());
    }
}
