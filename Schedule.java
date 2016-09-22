// Filename: Schedule.java
// Author: Faza Thirafi (13514033)

import java.io.*;
import java.util.*;

public class Schedule {

	private String[][] courseTable = new String[6][12];

	public Schedule() {
		int i,j;
		for (i = 0;i<12;i++) {
			courseTable[i][0] = "xxx";
		}
		for (j = 0;j<6;j++) {
			courseTable[0][j] = "xxx";
		}

		for (i = 1; i<6; i++) {
			for (j = 0; j<11; j++) {
				courseTable[i][j] = "aaa";
			}
		}
	}

	public void setCourse(int day, int time, String inp) {
		courseTable[day][time] = inp;
	}

	public String getCourse(int day,int time) {
		return courseTable[day][time];
	}

}