package courseRegistration;

public class Registration {

	private Student theStudent;
	private Offering theOffering;
	private char grade;

	public String register(Student theStudent, Offering theOffering) {
		setTheStudent(theStudent);
		setTheOffering(theOffering);
		return addRegistration();
	}

	private String addRegistration() {
		String status = "";
		theStudent.addRegistration(this);
		status += theOffering.addRegistration(this);
		return status;
	}

	public Student getTheStudent() {
		return theStudent;
	}

	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}

	public Offering getTheOffering() {
		return theOffering;
	}

	public void setTheOffering(Offering theOffering) {
		this.theOffering = theOffering;
	}

	public char getGrade() {
		return grade;
	}

	public void setGrade(char grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return String.format("%s %d Section No.: %d\n", theOffering.getTheCourse().getCourseName(),
				theOffering.getTheCourse().getCourseNum(), theOffering.getSectionNum());
	}

}
