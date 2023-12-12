package com.example.studentcounselingsystem.counseling.repository;

import com.example.studentcounselingsystem.counseling.entity.Counseling;
import org.springframework.data.jpa.domain.Specification;

public class CounselingSpecs {
    public static Specification<Counseling> withStudentId(final Integer id) {
        return (root, query, cb) -> cb.equal(root.get("studentId"), id);
    }
    public static Specification<Counseling> withCounselorId(final Integer id) {
        return (root, query, cb) -> cb.equal(root.get("counselorId"), id);
    }
    public static Specification<Counseling> withIsRead(final Boolean isRead) {
        return (root, query, cb) -> cb.equal(root.get("isRead"), isRead);
    }
    public static Specification<Counseling> withIsFeedback() {
        return (root, query, cb) -> cb.isNotNull(root.get("feedback"));
    }
}
