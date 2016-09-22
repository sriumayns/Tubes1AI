public class Slot{
	private Course[] courseList;
	private int numberOfCourse;
	private boolean isFillable;
	
	public Slot() {
		courseList = new Course[10];
		for (int i=0; i<10; i++) {
			courseList[i] = MainClass.getEmptyCourse();
		}
		numberOfCourse = 0;
	}
	
	public Course getCourse(int index) {
		return courseList[index];
	}
	public int getNumberOfCourse() {
		return numberOfCourse;
	}
	public boolean isOpen() {
		return isFillable;
	}
	public void setCourse(Course course, int index) {
		courseList[index] = course;
	}
	public void setNumberOfCourse(int i) {
		numberOfCourse = i;
	}
	public void addNumberOfCourse() {
		numberOfCourse++;
	}
	public void substractNumberOfCourse() {
		numberOfCourse--;
	}
	public void openSlot() {
		isFillable = true;
	}
	public void lockSlot() {
		isFillable = false;
	}
	public void insertCourse(Course course) {
		if (numberOfCourse < 10) {
			courseList[numberOfCourse] = course;
			numberOfCourse++;
		}
		else {
			System.out.println("Space Full");
		}
	}
	
	public Course getLastInsertedCourse() {
		return courseList[numberOfCourse-1];
	}
	
	public Course getCourseWithIndex(int i) {
		if ((i <= numberOfCourse)&&(numberOfCourse > 0)) {
			return courseList[i];
		}
		else {
			System.out.println("Wrong index");
			return null;
		}
	}
	public Course getAndDeleteLastInsertedCourse() {
		Course deletedCourse = courseList[numberOfCourse-1];
		numberOfCourse--;
		return deletedCourse;
	}
	
	public Course getAndDeleteCourseWithIndex(int j) {
		if ((j <= numberOfCourse)&&(numberOfCourse > 0)) {
			Course deletedCourse = courseList[j];
			for (int i = j; i < numberOfCourse-1; i++) {
				courseList[j] = courseList[j+1];
			}
			return deletedCourse;
		}
		else {
			System.out.println("Wrong index");
			return null;
		}
	}

}
