package com.imaginnovate.student.repository;

import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imaginnovate.student.model.StudentModel;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Long>{

	
}
