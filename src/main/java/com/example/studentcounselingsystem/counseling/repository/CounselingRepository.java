package com.example.studentcounselingsystem.counseling.repository;

import com.example.studentcounselingsystem.counseling.entity.Counseling;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CounselingRepository extends CrudRepository<Counseling, Long> {
    Counseling save(Counseling counseling);

//    @Query(value = "SELECT id, student_id, content, counselor_id, feedback, is_read, createdDate FROM counseling WHERE id = UUID_TO_BIN(:id, 1)", nativeQuery = true)
    Counseling findById(int id);

}
