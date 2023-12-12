package com.example.studentcounselingsystem.counseling.repository;

import com.example.studentcounselingsystem.counseling.entity.Counseling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CounselingRepository extends CrudRepository<Counseling, Long>, JpaSpecificationExecutor<Counseling> {
    Counseling save(Counseling counseling);

//    @Query(value = "SELECT id, student_id, content, counselor_id, feedback, is_read, createdDate FROM counseling WHERE id = UUID_TO_BIN(:id, 1)", nativeQuery = true)
    Counseling findById(int id);
    Page<Counseling> findAll(Specification<Counseling> counselingSpecification, Pageable pageable);
}
