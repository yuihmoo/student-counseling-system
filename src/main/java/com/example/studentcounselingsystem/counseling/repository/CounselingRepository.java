package com.example.studentcounselingsystem.counseling.repository;

import com.example.studentcounselingsystem.counseling.entity.Counseling;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CounselingRepository extends CrudRepository<Counseling, Long> {
    Counseling save(Counseling counseling);
    Counseling findById(UUID id);

}
