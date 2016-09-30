public class Room {
	private String roomName;
	private int startHour;
	private int endHour;
	private int[] availableDay;
	
	/*
		Constructor
	*/
	public Room() {
		roomName = "EMPTY";
		startHour = 0;
		endHour = 0;
		availableDay = new int[] {0};
	}
	
	/*
		Constructor
	*/
	public Room(String roomNameInput,int startHourInput,int endHourInput, int[] availableDayInput) {
		roomName = roomNameInput;
		startHour = startHourInput;
		endHour = endHourInput;
		availableDay = availableDayInput;
	}
	
	/*
		Mengembalikan nama room
	*/
	public String getRoomName() {
		return roomName;
	}

	/*
		Mengembalikan Start Hour
	*/
	public int getStartHour() {
		return startHour;
	}

	/*
		Mengembalikan end hour
	*/
	public int getEndHour() {
		return endHour;
	}

	/*
		Mengembalikan hari yang dapat diisi
	*/
	public int[] getAvailableDay()  {
		return availableDay;
	}
	
	/*
		Mengganti nama room
	*/
	public void setRoomName(String roomNameInput) {
		roomName = roomNameInput;
	}

	/*
		Merubah start hour
	*/
	public void setStartHour(String startHourString) {
		if (startHourString.equals("07.00")) {
			startHour = 7;
		}
		else if (startHourString.equals("08.00")) {
			startHour = 8;
		}
		else if (startHourString.equals("09.00")) {
			startHour = 9;
		}
		else if (startHourString.equals("10.00")) {
			startHour = 10;
		}
		else if (startHourString.equals("11.00")) {
			startHour = 11;
		}
		else if (startHourString.equals("12.00")) {
			startHour = 12;
		}
		else if (startHourString.equals("13.00")) {
			startHour = 13;
		}
		else if (startHourString.equals("14.00")) {
			startHour = 14;
		}
		else if (startHourString.equals("15.00")) {
			startHour = 15;
		}
		else if (startHourString.equals("16.00")) {
			startHour = 16;
		}
		else if (startHourString.equals("17.00")) {
			startHour = 17;
		}
	}

	/*
		Merubah End Hour
	*/
	public void setEndHour(String endHourString) {
		if (endHourString.equals("07.00")) {
			endHour = 7;
		}
		else if (endHourString.equals("08.00")) {
			endHour = 8;
		}
		else if (endHourString.equals("09.00")) {
			endHour = 9;
		}
		else if (endHourString.equals("10.00")) {
			endHour = 10;
		}
		else if (endHourString.equals("11.00")) {
			endHour = 11;
		}
		else if (endHourString.equals("12.00")) {
			endHour = 12;
		}
		else if (endHourString.equals("13.00")) {
			endHour = 13;
		}
		else if (endHourString.equals("14.00")) {
			endHour = 14;
		}
		else if (endHourString.equals("15.00")) {
			endHour = 15;
		}
		else if (endHourString.equals("16.00")) {
			endHour = 16;
		}
		else if (endHourString.equals("17.00")) {
			endHour = 17;
		}
	}

	/*
		Merubah hari apa saja yang bisa diisi
	*/
	public void setAvailableDay(String availableDayInputString)  {
		String[] availableDayParsed = availableDayInputString.split(",");
		availableDay = new int[availableDayParsed.length] ;
		for (int i =0; i < availableDayParsed.length; i++) {
			availableDay[i] = Integer.parseInt(availableDayParsed[i]);
		}
	}

	/*
		Mengecek apakah room ini bisa diisi pada hari tertentu (1-5)
	*/
	public boolean isContainAvailableDay(int n) {
		int i =0;
		while ((i< availableDay.length)&&(availableDay[i] != n)) {
			i++;
		}
		if (i == availableDay.length) {
			return false;
		}
		else {
			return true;
		}
	}
}
