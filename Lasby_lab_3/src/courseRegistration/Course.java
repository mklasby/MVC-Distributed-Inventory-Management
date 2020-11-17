package courseRegistration;

import java.util.ArrayList;

public class Course {

	private String courseName;
	private int courseNum;
	private ArrayList<Course> preReq;
	private ArrayList<Offering> offeringList;

	public Course(String courseName, int courseNum) {
		setCourseName(courseName);
		setCourseNum(courseNum);

		// This is not composition, because we are not constructing
		// an Offering Object(i.e. not calling the constructor of Offering).
		// We are simply constructing an array
		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<Offering>();
	}

	// Adding a single offering to a course
	public void addOffering(Offering theOffering) {
		offeringList.add(theOffering);

	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}

	public ArrayList<Course> getPreReq() {
		return preReq;
	}

	public void setPreReq(ArrayList<Course> preReq) {
		this.preReq = preReq;
	}

	public ArrayList<Offering> getOfferingList() {
		return offeringList;
	}

	public void setOfferingList(ArrayList<Offering> offeringList) {
		this.offeringList = offeringList;
	}

	public boolean offeringExists(int sectionNum) {
		for (Offering o : this.getOfferingList()) {
			if (o.getSectionNum() == sectionNum) {
				return true;
			}
		}
		return false;
	}

	public Offering getOffering(int section) {
		for (Offering o : this.getOfferingList()) {
			if (o.getSectionNum() == section) {
				return o;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		String st = "";
		st += "\n";
		st += courseName + " " + courseNum;
		st += "\n";
		for (Offering offering : offeringList)
			st += offering; // I need to have a toString in Offering
		st += "\n";
		return st;
	}

}
