// Filename: Schedule.java
// Author: Faza Thirafi (13514033)

import java.io.*;
import java.util.*;

public class Schedule {
	private Slot[][] slotTable;
	//slotTable[Hari][Jam]
	private Room room;

	
	public Schedule(Room roomInput) {
		room = roomInput;
		int i,j;
		slotTable = new Slot[6][18];
		for (i = 1; i<6; i++) {
			for (j = 7; j<18; j++) {
				slotTable[i][j] = new Slot();
				if (room.isContainAvailableDay(i)) {
					if ((j >= room.getStartHour())&&(j< room.getEndHour())) {
						slotTable[i][j].openSlot();
					}
					else {
						slotTable[i][j].lockSlot();
					}
				}
				else {
					slotTable[i][j].lockSlot();
				} 
			}
		}
	}


	/*
		Untuk mendapatkan Room dari Schedule
	*/
	public Room getRoom() {
		return room;
	}
	
	/*
		Mengecek apakah slot bisa isi atau tidak, berdasarkan konstrain

	*/
	public boolean isScheduleOpen(int day, int hour){
		return slotTable[day][hour].isOpen();
	}
	

	/*
		Untuk debuging lock Slot
	*/
	public void printScheduleLock() {
		System.out.println(room.getRoomName());
		System.out.println(room.getStartHour());
		System.out.println(room.getEndHour());
		System.out.println(room.getAvailableDay());
		
		for (int i =1; i < 6; i++) {
			for (int j = 7; j < 18; j++) {
				if (slotTable[i][j].isOpen()) {
					System.out.print(1);
				}
				else {
					System.out.print(0);
				}
			}
			System.out.println();
		}
	}


	/*
		Digunakan untuk menambahkan course ke slot
	*/
	public void insertCourseToSchedule(int day, int time, Course course) {
		slotTable[day][time].insertCourse(course);
	}
	
	/*	
	public void moveLastInsertedCourse(Room initialRoom, Room finalRoom, int initialDay, int finalDay, int initialHour, int finalHour) {
		Course movedCourse = slotTable[initialDay][initialHour].getAndDeleteLastInsertedCourse();
		slotTable[finalDay][finalHour].insertCourse(movedCourse);
	}*/
	
	/*
		Untuk mengeprint schedule satu ruangan
	*/
	public void printSchedule() {
		System.out.println("===============================================================");
		System.out.println("             Room Name: "+room.getRoomName());
		System.out.println("             Start Hour: "+room.getStartHour()+"   End Hour: "+room.getEndHour());
		System.out.print("             Available Day: ");
		int dayAvailable[] = room.getAvailableDay();
		for (int j =0; j < dayAvailable.length; j++) {
			System.out.print(dayAvailable[j]+" ");
		}
		System.out.println();
		System.out.println("===============================================================");
		System.out.println("  |   Monday  |   Tuesday | Wednesday |  Thursday |   Friday  |");
		System.out.println("===============================================================");
		for (int hour = 7; hour<18; hour++) {
			if (hour < 10) {
				System.out.print(hour+" |");
			}
			else {
				System.out.print(hour+"|");
			}
			for (int day = 1; day <6;day++) {
				if (slotTable[day][hour].getNumberOfCourse() > 0) {
					for (int i=0; i< slotTable[day][hour].getNumberOfCourse() ; i++) {
						System.out.print(slotTable[day][hour].getCourseWithIndex(i).getCourseName());
					}
					if (slotTable[day][hour].getNumberOfCourse() == 1) {
						System.out.print("     |");
					}
					else {
						System.out.print("|");
					}
				}
				else {
					System.out.print("           |");
				}
			}
			System.out.println();
		}
		System.out.println("---------------------------------------------------------------");
	}
	
	/*
		Untuk menghitung konflik dalam slot
	*/
	public int getConflict(int day, int hour) {
		return slotTable[day][hour].getNumberOfConflict();
	}
	

	/*
		Untuk mencari konflik yang ada dalam ruangan itu yang pertama didapat. Searching dilakukan dari jam dulu.
		output:
		int [0] = hari(1-7)
		int [1] = jam(7-17)

		jika tidak ditemukan maka hasilnya: 
		int[0] = 0;
		int[1] = 0; 
	*/
	public int[] getConflictSlot() {
		int day = 1;
		int hour = 7;
		boolean found = false;
		int[] result = new int[2];
		//result[0] = day
		//result[1] = hour;
		while ((day < 6)&&(!found)) {
			hour = 7;
			while ((hour < 18)&&(!found)) {
				if (slotTable[day][hour].getNumberOfCourse() > 1) {
					found = true;
				}
			hour++;
			}
		day++;
		}
		if ((day==6)&&(hour==18)) {
			result[0] = 0;
			result[1] = 0;
		}
		else {
			result[0] = day-1;
			result[1] = hour-1;
		}
		return result;
	}
	
	public Course getAndDeleteLastInsertedCourseFromSLot(int day, int hour) {
		return slotTable[day][hour].getAndDeleteLastInsertedCourse();
	}

}
