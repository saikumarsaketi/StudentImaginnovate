package com.imaginnovate.student.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.imaginnovate.student.model.StudentModel;
import com.imaginnovate.student.service.StudentService;

import java.time.Period;

@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;

	/*
	 * this method to insert or create student record
	 */
	@PostMapping(value = "/createStudent")
	public ResponseEntity<Object> createStudent(@RequestBody StudentModel studentModel) {

		try {

			String error = validationStudentData(studentModel);
			if (error != null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
			} else {
				studentModel.setTotal(result(studentModel));
				studentModel.setAverage(average(studentModel));
				studentModel.setResult(findResult(studentModel));

				return new ResponseEntity<>(studentService.insertStudent(studentModel), HttpStatus.OK);
			}

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/*
	 * this method to update student record
	 */
	@PutMapping(value = "/updateStudent")
	public ResponseEntity<Object> updateStudent(@RequestBody StudentModel studentModel) {

		try {

			String error = validationStudentData(studentModel);
			if (error != null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
			} else {
				studentModel.setTotal(result(studentModel));
				studentModel.setAverage(average(studentModel));
				studentModel.setResult(findResult(studentModel));

				return new ResponseEntity<>(studentService.updateStudent(studentModel), HttpStatus.OK);
			}

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	private String findResult(StudentModel studentModel) {

		if (studentModel.getMarks1() >= 35 && studentModel.getMarks2() >= 35 && studentModel.getMarks3() >= 35) {

			return "Pass";
		} else {
			return "Fail";
		}
	}

	private int result(StudentModel studentModel) {

		int marks[] = new int[] { studentModel.getMarks1(), studentModel.getMarks2(), studentModel.getMarks3() };
		int total = 0;
		for (int i = 0; i < marks.length; i++) {
			total = total + marks[i];
		}

		return total;
	}

	private float average(StudentModel studentModel) {

		int marks[] = new int[] { studentModel.getMarks1(), studentModel.getMarks2(), studentModel.getMarks3() };
		int average = 0;
		for (int i = 0; i < marks.length; i++) {
			average += marks[i];
		}

		return average / (float) marks.length;
	}

	private String validationStudentData(StudentModel studentModel) {

		String error = null;

		if (studentModel.getFirstName() == null || StringUtils.isEmpty(studentModel.getFirstName())) {
			error = "First Name is Mandatory";
		} else if (studentModel.getFirstName().length() < 3) {
			error = "First Name length minimum should be 3";
		} else if (studentModel.getLastName() == null || StringUtils.isEmpty(studentModel.getLastName())) {
			error = "Last Name is Mandatory";
		} else if (studentModel.getLastName().length() < 3) {
			error = "Last Name length minimum should be 3";
		} else if (studentModel.getDob() == null || StringUtils.isEmpty(studentModel.getDob())) {
			error = "DOB is Mandatory";
		} else if (calculateAge(convertStringtoLocalDate(studentModel.getDob())) < 15
				|| calculateAge(convertStringtoLocalDate(studentModel.getDob())) > 20) {
			error = "Age should be between 15 to 20";
		} else if (studentModel.getMarks1() >= 0 && studentModel.getMarks1() <= 100) {
			error = "Marks1 SHould be between 0 and 100";
		} else if (studentModel.getMarks2() >= 0 && studentModel.getMarks2() <= 100) {
			error = "Marks2 SHould be between 0 and 100";
		} else if (studentModel.getMarks3() >= 0 && studentModel.getMarks3() <= 100) {
			error = "Marks3 SHould be between 0 and 100";
		} else if (studentModel.getSection() != null && (studentModel.getSection().equals("A")
				|| studentModel.getSection().equals("B") || studentModel.getSection().equals("C"))) {
			error = "Section Should be A, B or C";
		} else if (studentModel.getGender() != null
				&& (studentModel.getGender().equals("M") || studentModel.getGender().equals("F"))) {
			error = "Gender Should be M or F";
		}

		return error;

	}

	private LocalDate convertStringtoLocalDate(String dob) {
		LocalDate localDate = LocalDate.parse(dob);
		return localDate;
	}

	// the method calculates the age
	public static int calculateAge(LocalDate dob) {
		// creating an instance of the LocalDate class and invoking the now() method
		// now() method obtains the current date from the system clock in the default
		// time zone
		LocalDate curDate = LocalDate.now();
		// calculates the amount of time between two dates and returns the years
		if ((dob != null) && (curDate != null)) {
			return Period.between(dob, curDate).getYears();
		} else {
			return 0;
		}
	}
}
