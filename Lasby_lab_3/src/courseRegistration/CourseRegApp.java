package courseRegistration;
//This is my "FrontEnd!"

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CourseRegApp {
	private ArrayList<Student> students = new ArrayList<Student>();
	private BufferedReader br;
	private CourseCat cat;

	CourseRegApp() {
		br = new BufferedReader(new InputStreamReader(System.in));
	}

	public CourseCat getCat() {
		return this.cat;
	}

	public void setCat(CourseCat cat) {
		this.cat = cat;
	}

	public ArrayList<Student> getStudents() {
		return this.students;
	}

	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	/**
	 * Main app entry point
	 */
	public void run() {
		this.clear();
		this.displayMenu();
		this.getMenuChoice();
	}

	/**
	 * Displays menu options.
	 */
	private void displayMenu() {
		System.out.printf("~UofC Student Registration System~\n%s\n\n", ("-".repeat(80)));
		System.out.print("1.  Search catalogue courses\n2.  Add course to student courses\n"
				+ "3.  Remove course from student courses\n4.  View all courses in catalogue\n"
				+ "5.  View all courses taken by student\n6.  Display Student List\n7.  Quit App\n>>> ");
	}

	/**
	 * Displays menu return message and listens for user input to return control to
	 * app.run
	 */
	private void returnToMenu() {
		System.out.printf("%s\nPress any key to return to the main menu\n", "-".repeat(80));
		String input = this.getString();
		if (input != null) {
			return;
		}
	}

	/**
	 * Listens for user input at menu and orchestrates basic input sanitization
	 */
	private int getMenuChoice() {
		String s = null;
		int input = 0;
		String inputError = "ERROR! PLEASE SELECT FROM ONE OF THE MENU CHOICES ABOVE\n>>> ";
		while (true) {
			try {
				s = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				input = Integer.parseInt(s);
			} catch (Exception e) {
				System.out.print(inputError);
				s = null;
				continue;
			}

			switch (input) {
				case 1:
					this.searchCatalogue();
					break;
				case 2:
					this.addCourseToStudent(this.getStudent());
					break;
				case 3:
					this.removeCourseFromStudent(this.getStudent());
					break;
				case 4:
					this.viewAllCoursesInCatalogue();
					break;
				case 5:
					this.viewAllCoursesForStudent(this.getStudent());
					break;
				case 6:
					this.displayStudents();
					break;
				case 7:
					System.exit(0);
					break;
				default:
					System.out.print(inputError);
					s = null;
			}
			this.returnToMenu();
			this.clear();
			this.displayMenu();
		}
	}

	private void displayStudents() {
		for (Student s : this.students) {
			System.out.print(s.toString());
		}
		return;
	}

	/**
	 * Gets course instance based on searched parameters
	 * 
	 */
	private void searchCatalogue() {
		String name = "";
		int num = -1;
		System.out.print("Please enter the course name:\n>>> ");
		name = this.getString();
		System.out.print("Please enter the course number. Enter \"0\" to search for any course number:\n>>> ");
		num = this.getInt();

		if (num == 0) { // no num searched
			ArrayList<Course> courses = this.cat.searchCat(name);
			if (courses.size() == 0) {
				System.out.printf("No courses matching course name query: \"%s\"\n", name);
				return;
			}
			System.out.print("Search Results:\n");
			for (Course c : courses) {
				System.out.print(c.toString());
			}
			return;
		} else { // num search, return exact match
			Course course = this.cat.searchCat(name, num);
			if (course == null) {
				System.out.printf("ERROR! No courses matching query: \"%s %d\"\n", name, num);
				return;
			} else {
				System.out.print("Search Result:\n");
				System.out.print(course.toString());
				return;
			}
		}
	}

	private void addCourseToStudent(Student student) {
		if (student == null) {
			return;
		} else {
			this.printStudentFoundMessage(student);
			String courseName;
			int courseNum;
			int sectionNum;
			System.out.print("Please enter the course name:\n>>> ");
			courseName = this.getString();
			System.out.print("Please enter the course number:\n>>> ");
			courseNum = this.getInt();
			System.out.print("Please enter the section number:\n>>> ");
			sectionNum = this.getInt();

			String status;
			try {
				status = student.registerForCourse(this.cat, courseName, courseNum, sectionNum);
			} catch (Exception e) {
				System.out.print(e.getMessage());
				return;
			}
			System.out.print(status);

			return;

		}
	}

	private void removeCourseFromStudent(Student student) {
		if (student == null) {
			return;
		} else {
			this.printStudentFoundMessage(student);
			String courseName;
			int courseNum;
			int sectionNum;
			System.out.print("Please enter the course name:\n>>> ");
			courseName = this.getString();
			System.out.print("Please enter the course number:\n>>> ");
			courseNum = this.getInt();
			System.out.print("Please enter the section number:\n>>> ");
			sectionNum = this.getInt();
			try {
				String status = student.removeCourse(this.cat, courseName, courseNum, sectionNum);
				System.out.print(status);
				return;
			} catch (Exception e) {
				System.out.print(e.getMessage());
				System.out.print("\n");
				return;
			}
		}
	}

	private void viewAllCoursesForStudent(Student student) {
		if (student == null) {
			return;
		} else {
			System.out.printf("Success, found student %s (ID Number: %d)\n", student.getStudentName(),
					student.getStudentId());
			System.out.printf("%s's courses: \n", student.getStudentName());
			student.printCourses();
			return;
		}
	}

	private void viewAllCoursesInCatalogue() {
		System.out.print("Courses Available in Catalogue: \n");
		this.cat.displayCourses();
		return;
	}

	private void printStudentFoundMessage(Student s) {
		System.out.printf("Success, found student %s (ID Number: %d)\n", s.getStudentName(), s.getStudentId());
	}

	/**
	 * Gets student based on ID search
	 * 
	 * @return Student found by search or null if no results found
	 */
	private Student getStudent() {
		System.out.print("Please enter the Student's ID:\n>>> ");
		int id = this.getInt();
		for (Student s : this.students) {
			if (s.getStudentId() == id) {
				return s;
			}
		}
		System.out.printf("ERROR! No Student found matching ID: %d\n", id);
		return null;
	}

	/**
	 * Helper function to request an integer from user until good input is provided
	 * 
	 * @return: int - the int provided by user
	 */
	private int getInt() {
		String s = null;
		int input = -1;
		while (input == -1) {
			try {
				s = br.readLine();
				input = Integer.parseInt(s);
			} catch (Exception e) {
				System.out.print("ERROR! PLEASE ENTER AN INTEGER > -1\n>>> ");
			}
		}
		return input;
	}

	/**
	 * Helper function to request a String from user until good input is received.
	 * 
	 * @return: String - string provided by user.
	 */
	private String getString() {
		String s = null;
		boolean badInput = true;
		while (badInput) {
			try {
				s = br.readLine();
			} catch (IOException e) {
				System.out.print("ERROR! Please try again\n>>> ");
				continue;
			}
			badInput = false;
		}
		return s;
	}

	/**
	 * Simulates clearing the CLI by printing an ANSI clear screen on unix systems
	 * and 50 new lines on microsoft systems. TODO: Test on Microsoft OS, use system
	 * properties to check OS?
	 */
	private void clear() {
		String osName = System.getProperty("os.name");
		if (osName.contains("Windows") || osName.contains("windows")) {
			System.out.printf("%s", "\n".repeat(50));// cheap hack for windows OS'
		} else {
			System.out.print("\033[H\033[2J"); // ANSI escape code for clear Screen + home
		}
	}

	public static void main(String[] args) {

		CourseCat cat = new CourseCat(); // This loads the courses from our "DB"
		CourseRegApp app = new CourseRegApp();
		app.setCat(cat);

		ArrayList<Student> students = new ArrayList<Student>();
		students.add(new Student("Sara", 1));
		students.add(new Student("Joe", 2));
		students.add(new Student("Mike", 3));
		students.add(new Student("Bob", 4));
		students.add(new Student("Jenny", 5));
		students.add(new Student("Stan", 6));
		students.add(new Student("Karen", 7));
		students.add(new Student("Burak", 8));
		students.add(new Student("Larry", 9));
		students.add(new Student("Frank", 10));
		students.add(new Student("Conner", 11));
		students.add(new Student("Lizzy", 12));

		app.setStudents(students);

		for (Course c : app.getCat().getCourseList()) {
			if (c.getCourseNum() == 607) {
				ArrayList<Course> preReq = new ArrayList<Course>();
				preReq.add(cat.getCourse("ENSF", 593));
				preReq.add(cat.getCourse("ENSF", 592));
				c.setPreReq(preReq);
			}
			cat.createOffering(c, 1, 200);
			cat.createOffering(c, 2, 150);
		}

		app.run();
	}
}