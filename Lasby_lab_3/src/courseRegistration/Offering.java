package courseRegistration;

import java.util.ArrayList;

public class Offering {

	private int sectionNum;
	private int sectionCap;
	private Course theCourse;
	private static int MIN_CAP = 8;

	private ArrayList<Registration> studentList; // student list

	public Offering(int sectionNum, int sectionCap) {
		setSectionNum(sectionNum);
		setSectionCap(sectionCap);
		studentList = new ArrayList<Registration>();
	}

	public String addRegistration(Registration reg) {
		String status = "";
		this.studentList.add(reg);
		reg.setTheOffering(this);
		if (studentList.size() < MIN_CAP) {
			status = status.concat(String.format(
					"Warning! Only %d students enrolled in this section. %d students required to ensure that section will be run.\n",
					studentList.size(), MIN_CAP));
		}

		status += String.format("Successfully added %s to section number %d of %s %d.\n",
				reg.getTheStudent().getStudentName(), this.sectionNum, this.theCourse.getCourseName(),
				this.theCourse.getCourseNum());
		return status;
	}

	public int getSectionNum() {
		return sectionNum;
	}

	public void setSectionNum(int sectionNum) {
		this.sectionNum = sectionNum;
	}

	public int getSectionCap() {
		return sectionCap;
	}

	public void setSectionCap(int sectionCap) {
		this.sectionCap = sectionCap;
	}

	public Course getTheCourse() {
		return theCourse;
	}

	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}

	public ArrayList<Registration> getStudentList() {
		return studentList;
	}

	public void setStudentList(ArrayList<Registration> studentList) {
		if (studentList.size() < MIN_CAP) {
			System.out.printf(
					"WARNING! Only %d students enrolled in %s. At least %s must be enrolled in this offering. Please add more students to ensure this offering will be run.\n");
		}
		this.studentList = studentList;
		return;
	}

	public boolean hasMinEnrollment() {
		if (this.getStudentList().size() < MIN_CAP) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String st = "";
		st += "Section Number: " + sectionNum + ", Section cap: " + sectionCap + "\n\n";
		st += "Students in this section are:\n\n";
		for (Registration r : studentList) {
			/// I need to get this from student list
			st += r.getTheStudent().getStudentName()
					+ String.format(" (ID No.: %d), ", r.getTheStudent().getStudentId()) + "Grade: " + r.getGrade();
			st += "\n\n";
		}
		return st;
	}

	public boolean isOpen() {
		if (this.getStudentList().size() < this.sectionCap) {
			return true;
		}
		return false;
	}

	public void dropStudent(Registration r) throws Exception {
		try {
			this.studentList.remove(r);
		} catch (Exception e) {
			throw new Exception("ERROR! Student not enrolled in offering!\n");
		}
	}
}