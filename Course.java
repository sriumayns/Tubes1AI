public class Course {
	private String courseName;
	private String roomConstraint;
	private int startHourConstraint;
	private int endHourConstraint;
	private int totalCredit;
	private int[] dayConstraint;
	
	public Course() {
		courseName = "EMPTY";
		roomConstraint = "EMPTY";
		startHourConstraint = 0;
		endHourConstraint = 0;
		totalCredit = 0;
		dayConstraint = new int[] {0};
	}
	
	public Course(String courseNameInput, String roomConstraintInput, int startHourConstraintInput,int endHourConstraintInput, int totalCreditInput, int[] dayConstraintInput) {
		courseName = courseNameInput;
		roomConstraint = roomConstraintInput;
		startHourConstraint = startHourConstraintInput;
		endHourConstraint = endHourConstraintInput;
		totalCredit = totalCreditInput;
		dayConstraint = dayConstraintInput;
	}
	
	public String getCourseName() {
		return courseName;
	}
	public String getRoomConstraint() {
		return roomConstraint;
	}
	public int getStartHourConstraint() {
		return startHourConstraint;
	}
	public int getEndHourConstraint() {
		return endHourConstraint;
	}
	public int getTotalCredit() {
		return totalCredit;
	}
	public int[] getDayConstraint() {
		return dayConstraint;
	}
	
	public void setCourseName(String courseNameInput) {
		courseName = courseNameInput;
	}
	public void setRoomConstraint(String roomConstraintInput) {
		roomConstraint = roomConstraintInput;
	}
	public void setStartHourConstraint(String startHourString) {
		if (startHourString.equals("07.00")) {
			startHourConstraint = 7;
		}
		else if (startHourString.equals("08.00")) {
			startHourConstraint = 8;
		}
		else if (startHourString.equals("09.00")) {
			startHourConstraint = 9;
		}
		else if (startHourString.equals("10.00")) {
			startHourConstraint = 10;
		}
		else if (startHourString.equals("11.00")) {
			startHourConstraint = 11;
		}
		else if (startHourString.equals("12.00")) {
			startHourConstraint = 12;
		}
		else if (startHourString.equals("13.00")) {
			startHourConstraint = 13;
		}
		else if (startHourString.equals("14.00")) {
			startHourConstraint = 14;
		}
		else if (startHourString.equals("15.00")) {
			startHourConstraint = 15;
		}
		else if (startHourString.equals("16.00")) {
			startHourConstraint = 16;
		}
		else if (startHourString.equals("17.00")) {
			startHourConstraint = 17;
		}
	}
	public void setEndHourConstraint(String endHourString) {
		if (endHourString.equals("07.00")) {
			endHourConstraint = 7;
		}
		else if (endHourString.equals("08.00")) {
			endHourConstraint = 8;
		}
		else if (endHourString.equals("09.00")) {
			endHourConstraint = 9;
		}
		else if (endHourString.equals("10.00")) {
			endHourConstraint = 10;
		}
		else if (endHourString.equals("11.00")) {
			endHourConstraint = 11;
		}
		else if (endHourString.equals("12.00")) {
			endHourConstraint = 12;
		}
		else if (endHourString.equals("13.00")) {
			endHourConstraint = 13;
		}
		else if (endHourString.equals("14.00")) {
			endHourConstraint = 14;
		}
		else if (endHourString.equals("15.00")) {
			endHourConstraint = 15;
		}
		else if (endHourString.equals("16.00")) {
			endHourConstraint = 16;
		}
		else if (endHourString.equals("17.00")) {
			endHourConstraint = 17;
		}
	}
	public void setTotalCredit(String totalCreditInput) {
		totalCredit = Integer.parseInt(totalCreditInput);
	}
	public void setDayConstraint(String dayConstraintInput) {
		String[] availableDayParsed = dayConstraintInput.split(",");
		dayConstraint = new int[availableDayParsed.length] ;
		for (int i =0; i < availableDayParsed.length; i++) {
			dayConstraint[i] = Integer.parseInt(availableDayParsed[i]);
		}
	}
}
