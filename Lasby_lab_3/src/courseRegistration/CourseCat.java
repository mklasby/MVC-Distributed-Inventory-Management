package courseRegistration;

import java.util.ArrayList;

public class CourseCat {

	private ArrayList<Course> courseList;

	public CourseCat() {
		courseList = loadFromDB();
	}

	private static ArrayList<Course> loadFromDB() {
		// In real life course would be loaded for the database or at least some sort of
		// file
		// on disk.

		// So imagine this is being loaded from the database!

		ArrayList<Course> imaginaryDB = new ArrayList<Course>();

		imaginaryDB.add(new Course("ENGG", 233));
		imaginaryDB.add(new Course("ENSF", 607));
		imaginaryDB.add(new Course("PHYS", 259));
		imaginaryDB.add(new Course("ENSF", 593));
		imaginaryDB.add(new Course("ENSF", 592));
		imaginaryDB.add(new Course("ABCD", 101));
		imaginaryDB.add(new Course("XYZ", 101));
		return imaginaryDB;
	}

	public ArrayList<Course> searchCat(String courseName) {
		ArrayList<Course> foundCourses = new ArrayList<Course>();
		char[] query = courseName.toLowerCase().toCharArray();
		int hits;

		for (int i = query.length; i > 0; i--) {
			for (Course c : this.courseList) {
				char[] thisName = c.getCourseName().toLowerCase().toCharArray();
				hits = 0;
				for (int j = 0; j < i; j++) {
					if (j >= thisName.length) {
						break;
					} else if (query[j] == thisName[j]) {
						hits++;
					}
				}

				if (hits == i) {
					if (foundCourses.contains(c)) {
						break;
					} else {
						foundCourses.add(c);
					}
				}
			}
		}
		return foundCourses;
	}

	public Course searchCat(String courseName, int courseNum) {
		for (Course c : courseList) {
			if (c.getCourseNum() == courseNum && c.getCourseName().toLowerCase().equals(courseName.toLowerCase())) {
				return c;
			}
		}
		return null;
	}

	public void createOffering(Course theCourse, int secNum, int secCap) {
		if (theCourse != null) {
			Offering theOffering = new Offering(secNum, secCap);
			theCourse.addOffering(theOffering);
			theOffering.setTheCourse(theCourse);
		}
	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}

	public void displayCourses() {
		for (Course c : this.courseList) {
			System.out.print(c.toString());
		}
	}

	public Course getCourse(String name, int id) {
		for (Course c : this.courseList) {
			if (c.getCourseName().toLowerCase().equals(name.toLowerCase()) && c.getCourseNum() == id) {
				return c;
			}
		}
		return null;
	}

}
