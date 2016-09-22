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

}
