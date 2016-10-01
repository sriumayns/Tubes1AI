public class Slot{
	private Course[] courseList;
	private int numberOfCourse;
	private boolean isFillable;
	
	public Slot() {
		courseList = new Course[10];
		for (int i=0; i<10; i++) {
			courseList[i] = new Course();
		}
		numberOfCourse = 0;
		isFillable = false;
		
	}
	
	/*
		Mengembalikan Course berdasarkan index
	*/
	public Course getCourse(int index) {
		return courseList[index];
	}


	/*
	public Course getCourseById(int id) {
		int i = 0;
		boolean found = false;
		while ((i < numberOfCourse)&&(!found)) {
			if (courseList[i].getId() == id) {
				found = true;
			}

			i++;
		}

		if (i < numberOfCourse) {
			return courseList[i-1];
		}
		else {
			Course emptyCourse = new Course();
			return emptyCourse;
		}

	}
	*/
	

	/*
		Mengembalikan jumlah Course
	*/
	public int getNumberOfCourse() {
		return numberOfCourse;
	}

	/*
		Mengembalikan true jika slot dapat diisi
	*/
	public boolean isOpen() {
		return isFillable;
	}

	/*
		Merubah course pada slot pada index tertentu
	*/
	public void setCourse(Course course, int index) {
		courseList[index] = course;
		numberOfCourse++;
	}

	/*
		Merubah slot menjadi dapat diisi
	*/
	public void openSlot() {
		isFillable = true;
	}

	/*
		Merubah slot menjadi tidak dapat diisi
	*/
	public void lockSlot() {
		isFillable = false;
	}

	/*
		Menginsertkan Course pada ahir index
	*/
	public void insertCourse(Course course) {
		if (numberOfCourse < 10) {
			courseList[numberOfCourse] = course;
			numberOfCourse++;
		}
		else {
			System.out.println("Space Full");
		}
	}
	
	/*
		Mengembalikan Course pada index slot yang terahir
	*/
	public Course getLastInsertedCourse() {
		return courseList[numberOfCourse-1];
	}
	
	/*
		Mengembalikan Course pada index tertentu
	*/
	public Course getCourseWithIndex(int i) {
		if ((i <= numberOfCourse)&&(numberOfCourse > 0)) {
			return courseList[i];
		}
		else {
			System.out.println("Wrong index");
			return null;
		}
	}

	/*
		Mendelete Course pada index terahir. Dan mengembalikan Course nya
	*/
	public Course getAndDeleteLastInsertedCourse() {
		Course deletedCourse = courseList[numberOfCourse-1];
		numberOfCourse--;
		return deletedCourse;
	}
	
	/*
		Mendelete Course pada index tertentu dan mengembalikan Course yang di delete
	*/
	public Course getAndDeleteCourseWithIndex(int j) {
		if ((j <= numberOfCourse)&&(numberOfCourse > 0)) {
			Course deletedCourse = courseList[j];
			for (int i = j; i < numberOfCourse-1; i++) {
				courseList[i] = courseList[i+1];
			}
			numberOfCourse--;
			return deletedCourse;
		}
		else {
			System.out.println("Wrong index");
			return null;
		}
	}


	/*
		Mengembalikan konflik dalam satu slot
	*/
	public int getNumberOfConflict() {
		return (numberOfCourse*(numberOfCourse-1))/2;
	}

}
