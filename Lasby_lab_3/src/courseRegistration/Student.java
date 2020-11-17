package courseRegistration;

import java.util.ArrayList;

public class Student {

	private String studentName;
	private int studentId;
	private ArrayList<Registration> regList; // list of course sections
	private int MAX_COURSE_LOAD = 6;

	public Student(String studentName, int studentId) {
		setStudentName(studentName);
		setStudentId(studentId);
		setRegList(new ArrayList<Registration>());
	}

	public int getMAX_COURSE_LOAD() {
		return MAX_COURSE_LOAD;
	}

	public void setMAX_COURSE_LOAD(int mAX_COURSE_LOAD) {
		this.MAX_COURSE_LOAD = mAX_COURSE_LOAD;
	}

	@Override
	public String toString() {
		String response;
		response = String.format("Name: %s, ID Number: %d\n", this.studentName, this.studentId);
		for (Registration r : regList) {
			response.concat(r.toString());
		}
		response = response.concat("-".repeat(80));
		response = response.concat("\n");
		return response;
	}

	public String registerForCourse(CourseCat cat, String courseName, int courseNum, int section) throws Exception {
		String status = "";
		Course myCourse = cat.searchCat(courseName, courseNum);

		if (this.regList.size() >= MAX_COURSE_LOAD) {
			throw new Exception(
					String.format("Sorry, you are already enrolled in %d courses!\n", this.MAX_COURSE_LOAD));
		}

		if (myCourse == null) {
			throw new Exception(String.format("No Course named \"%s %d\"\n", courseName, courseNum));
			// no course found
		}

		if (!myCourse.offeringExists(section)) {
			throw new Exception(String.format("No Section %d in %s %d.\n", section, courseName, courseNum));
		}

		if (myCourse.getPreReq().size() > 0) {
			status = status.concat(String.format("Please note that %s %d has the following pre-requisites: \n",
					courseName, courseNum));

			for (Course prereq : myCourse.getPreReq()) {
				status = status.concat(String.format("%s %d\n", prereq.getCourseName(), prereq.getCourseNum()));
			}
		}

		Registration reg = new Registration();
		status = status.concat(reg.register(this, myCourse.getOffering(section)));
		return status;
	}

	public String removeCourse(CourseCat cat, String courseName, int courseNum, int section) throws Exception {
		String status = "";
		Course myCourse = cat.searchCat(courseName, courseNum);

		if (myCourse == null) {
			throw new Exception(String.format("No Course named \"%s %d\"\n", courseName, courseNum));
			// no course found
		}

		if (!myCourse.offeringExists(section)) {
			throw new Exception(String.format("No Section %d in %s %d.\n", section, courseName, courseNum));
		}

		Registration toBeDeleted = null;
		for (Registration r : this.regList) {
			if (r.getTheOffering().getTheCourse() == myCourse && r.getTheOffering().getSectionNum() == section) {
				toBeDeleted = r;
				break;
			}
		}

		if (toBeDeleted == null) {
			throw new Exception(String.format("%s (ID No.: %d) is not enrolled in %s %d Section %d!", this.studentName,
					this.studentId, myCourse.getCourseName(), myCourse.getCourseNum(), section));
		} else {
			this.regList.remove(toBeDeleted);
			toBeDeleted.getTheOffering().dropStudent(toBeDeleted);
			status = String.format("%s %d Section %d successfully dropped from %s's (ID No.: %d) schedule.\n",
					myCourse.getCourseName(), myCourse.getCourseNum(), section, this.studentName, this.studentId);
		}
		return status;

	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public ArrayList<Registration> getRegList() {
		return regList;
	}

	public void setRegList(ArrayList<Registration> regList) {
		this.regList = regList;
	}

	public void addRegistration(Registration registration) {
		regList.add(registration);
		registration.setTheStudent(this);
		return;
	}

	public void printCourses() {
		for (Registration r : regList) {
			System.out.print(r.toString());
		}
		return;
	}
}
