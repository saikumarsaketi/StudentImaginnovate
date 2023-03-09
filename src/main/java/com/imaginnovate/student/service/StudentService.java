package com.imaginnovate.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imaginnovate.student.model.StudentModel;
import com.imaginnovate.student.repository.StudentRepository;

@Service
public class StudentService {

	
	@Autowired
	private StudentRepository studentRepo;
	
	
	public StudentModel insertStudent(StudentModel studentModel) {
		
		return studentRepo.save(studentModel);
	}
	
	public StudentModel updateStudent(StudentModel studentModel) {
		
		StudentModel studModel = studentRepo.findById( studentModel.getStudentId( ) ).orElse( null );
		studModel.setAverage(studentModel.getAverage());
		studModel.setDob(studentModel.getDob());
		studModel.setFirstName(studentModel.getFirstName());
		studModel.setGender(studentModel.getGender());
		studModel.setLastName(studentModel.getLastName());
		studModel.setMarks1(studentModel.getMarks1());
		studModel.setMarks2(studentModel.getMarks2());
		studModel.setMarks3(studentModel.getMarks3());
		studModel.setResult(studentModel.getResult());
		studModel.setSection(studentModel.getSection());
		studModel.setStudentId(studentModel.getStudentId());
		studModel.setTotal(studentModel.getTotal());
		
		
		return studentRepo.save(studModel);
	}
}
