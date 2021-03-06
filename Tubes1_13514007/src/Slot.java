public class Slot{
	private Course[] courseList;
	private int numberOfCourse;
	private boolean isFillable;
	
	public Slot() {
		courseList = new Course[20];
		for (int i=0; i<20; i++) {
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
		if (numberOfCourse < 20) {
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
	/*
		Mengembalikan id dari course
	*/
	public int getLastInsertedCourseId() {
		//return courseList[numberOfCourse-1].getId();
		int[] leastConstrainedIndex =  new int[numberOfCourse];
		int value;
		int hasilDiv;
		for (int i = 0; i < numberOfCourse; i++) {
			value = 0;
			if (!courseList[i].getRoomConstraint().equals("-")) {
				value += 7;
			}
			value += courseList[i].getTotalCredit();
			value += courseList[i].getDayConstraint().length;
			hasilDiv = (10-(courseList[i].getEndHourConstraint()-courseList[i].getStartHourConstraint()))/2;
			value += hasilDiv;

		}
		int id = 0;
		for (int i = 0; i < numberOfCourse; i++) {
			if (leastConstrainedIndex[id]>leastConstrainedIndex[i]) {
				i = id;
			}
		}
		return courseList[id].getId();
	}
	/*
		Mengambil dan menghampus course dari slot berdasarkan id
	*/
	public Course getAndDeleteCourseById(int courseId) {
		int i = 0;
		boolean found = false;
		while ((i < numberOfCourse)&&(!found)) {
			if (courseList[i].getId() == courseId) {
				found = true;
			}
			else {
				i++;
			}
		}
		Course course;
		Course emptyCourse = new Course();
		if (i == numberOfCourse) {
			course = new Course();
			return course;
		}
		else {
			course = courseList[i];
		}
		for (int j = i; j < numberOfCourse - 1; j++) {
			courseList[j] = courseList[j+1];
		}
		courseList[numberOfCourse] = emptyCourse;
		numberOfCourse--;
		return course;
	}

}
