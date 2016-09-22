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
		System.out.println("cek");
		int i,j;
		slotTable = new Slot[6][18];
		for (i = 1; i<6; i++) {
			for (j = 7; j<18; j++) {
				slotTable[i][j] = new Slot();
				if (room.isContainAvailableDay(i)) {
					if ((j >= room.getStartHour())&&(j< room.getEndHour())) {
						slotTable[i][j].openSlot();
					}
				}
				else {
					slotTable[i][j].lockSlot();
				} 
			}
		}
	}
	public void printSchedule() {
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
	
	public void insertCourseToSchedule(int day, int time, Course course) {
		slotTable[day][time].insertCourse(course);
	}
	
	public void moveLastInsertedCourse(Room initialRoom, Room finalRoom, int initialDay, int finalDay, int initialHour, int finalHour) {
		Course movedCourse = slotTable[initialDay][initialHour].getAndDeleteLastInsertedCourse();
		slotTable[finalDay][finalHour].insertCourse(movedCourse);
	}

}
