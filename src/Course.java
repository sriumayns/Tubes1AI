public class Course {
	private String courseName;
	private String roomConstraint;
	private int startHourConstraint;
	private int endHourConstraint;
	private int totalCredit;
	private int[] dayConstraint;
	private int nSlotAvaliable;
	private int id;
	
	/*
		Constructor
	*/
	public Course() {
		courseName = "EMPTY";
		roomConstraint = "EMPTY";
		startHourConstraint = 0;
		endHourConstraint = 0;
		totalCredit = 0;
		dayConstraint = new int[] {0};
		nSlotAvaliable = 0;
		id = 0;
	}
	
	/*
		Constructor
	*/
	public Course(String courseNameInput, String roomConstraintInput, int startHourConstraintInput,int endHourConstraintInput, int totalCreditInput, int[] dayConstraintInput, int idInput) {
		courseName = courseNameInput;
		roomConstraint = roomConstraintInput;
		startHourConstraint = startHourConstraintInput;
		endHourConstraint = endHourConstraintInput;
		totalCredit = totalCreditInput;
		dayConstraint = dayConstraintInput;
		nSlotAvaliable = 0;
		id = idInput;
	}
	
	/*
		Mengembalikan id course
	*/
	public int getId(){
		return id;
	}
	/*
		Mengembalikan nama course
	*/
	public String getCourseName() {
		return courseName;
	}

	/*
		Mengembalikan nama room constraint. Jika tidak ada contraint maka mengembalikan "-".
	*/
	public String getRoomConstraint() {
		return roomConstraint;
	}

	/*
		Mengembalikan jam awal (7-17)
	*/
	public int getStartHourConstraint() {
		return startHourConstraint;
	}

	/*
		Mengembalikan jam ahir (7-17)
	*/
	public int getEndHourConstraint() {
		return endHourConstraint;
	}

	/*
		Mengembalikan jumlah sks
	*/
	public int getTotalCredit() {
		return totalCredit;
	}

	/*
		Mengembalikan hari yang bisa diisi
	*/
	public int[] getDayConstraint() {
		return dayConstraint;
	}

	/*
		Mengembalikan jumlah slot yang dapat diisi dalam satu schedule board
	*/
	public int getNSlotAvaliable() {
		return nSlotAvaliable;
	}
	
	public void setId(int idInput){
		id = idInput;
	}

	/*
		Merubah nama course
	*/
	public void setCourseName(String courseNameInput) {
		courseName = courseNameInput;
	}

	/*
		Merubah room constraint. Merubah '-' jika tidak ada constraint.
	*/
	public void setRoomConstraint(String roomConstraintInput) {
		roomConstraint = roomConstraintInput;
		int i = 0;
		if(roomConstraintInput.equals("-")){
			while(i < FileReaderMachine.getRoomSize()){
				countAvaliableInRoom(FileReaderMachine.getRoomAtIdx(i));
				i++;
			}
		}else{
			countAvaliableInRoom(FileReaderMachine.getRoomByName(roomConstraintInput));
		}
	}

	/*
		Menghitung jumlah slot yang dapat diisi dalam satu slot.
	*/
	private void countAvaliableInRoom(Room room){
		int i = 0;
		int j = 0;
		int[] roomDayConstraint = room.getAvailableDay();
		Schedule schedule = FileReaderMachine.getScheduleByRoomName(room.getRoomName());
		while((i < roomDayConstraint.length) && (j < dayConstraint.length)){
			if(roomDayConstraint[i] > dayConstraint[j]){
				j++;
			}else if(roomDayConstraint[i] < dayConstraint[j]){
				i++;
			}else{
				for(int k = startHourConstraint;k <= endHourConstraint - totalCredit + 1;k++){
					if(schedule.isScheduleOpen(roomDayConstraint[i],k)){
						nSlotAvaliable++;
					}
				}
				i++;
				j++;
			}
		}
	}

	/*
		Merubah jam yang dapat dimulai.
	*/
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

	/*
		Merubah jam yang dapat diisi paling lama.
	*/
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

	/*
		Merubah sks.
	*/
	public void setTotalCredit(String totalCreditInput) {
		totalCredit = Integer.parseInt(totalCreditInput);
	}

	/*
		Merubah hari yang dapat digunakan. 
	*/
	public void setDayConstraint(String dayConstraintInput) {
		String[] availableDayParsed = dayConstraintInput.split(",");
		dayConstraint = new int[availableDayParsed.length] ;
		for (int i =0; i < availableDayParsed.length; i++) {
			dayConstraint[i] = Integer.parseInt(availableDayParsed[i]);
		}
	}

	public boolean isValid(int day,int hour, String roomName){
		boolean valid = false;

		for(int i = 0;i < dayConstraint.length;i++){
			if(dayConstraint[i] == day){
				valid = true;
			}
		}

		if(valid && (startHourConstraint <= hour) && (endHourConstraint <= hour)){
			valid = true;
		}else{
			valid = false;
		}

		if(roomConstraint.equals("-")){
			valid = true;
		}else if(roomConstraint.equals(roomName)){
			valid = true;
		}else{
			valid = false;
		}

		return valid;
	}
}
