import java.util.*;

class Interpreter{
	public static class BeRoom{
		private Boolean strict;
		private String namaRuangan;
		private int startHour, endHour;
		private ArrayList<Boolean> availableDay = new ArrayList<Boolean>(5);
		private ArrayList<ArrayList<String>> jadwal = new ArrayList<ArrayList<String>>(5);

		public BeRoom(Boolean st){
			strict = st;
			for(int i = 0; i < 5; i++){
				jadwal.add(i, new ArrayList<String>());
				for(int j = 0; j <= 17; j++){
					jadwal.get(i).add("");
				}
			}
		}

		public void setNamaRuangan(String str){
			namaRuangan = str;
		}

		public void setStartEndHour(int a, int b){
			startHour = a;
			endHour = b;
		}

		public void setAvailableDay(boolean a, boolean b, boolean c, boolean d, boolean e){
			availableDay.add(0, a);
			availableDay.add(1, b);
			availableDay.add(2, c);
			availableDay.add(3, d);
			availableDay.add(4, e);
		}

		public void addJadwal(int index, int jam, String alVal){
			if((0 <= index && index <= 4) || !strict)
				if(availableDay.get(index) || !strict)
					if((7 <= jam  && jam <= 17) || !strict)
						if((getStartHour() <= jam && jam <= getEndHour()) || !strict){
							if(jadwal.get(index).get(jam) != "")
								jadwal.get(index).set(jam, jadwal.get(index).get(jam) + "<br/>" + alVal);
							else
								jadwal.get(index).set(jam, alVal);
						}
		}


		public String getNamaRuangan(){
			return namaRuangan;
		}

		public int getStartHour(){
			return startHour;
		}

		public int getEndHour(){
			return endHour;
		}

		public ArrayList<Boolean> getAvailableDay(){
			return availableDay;
		}

		public ArrayList<String> getJadwal(int i){
			return jadwal.get(i);
		}

		public String printJadwal(int k){
			String output = "";
			output += "[";
			for(int i = 0; i <= 17; i++)
				if(i != 17)
					output += "\"" + jadwal.get(k).get(i) + "\", ";
				else
					output += "\"" + jadwal.get(k).get(i) + "\"";

			output += "]";

			return output;
		}

		// keluaran di cetak dalam bentuk JSON String
		public String getOutput(){

		      // array(
		      //   'nama_ruangan' => 'Labdas II Informatika',
		      //   'hour' => array(
		      //     'start' => 7,
		      //     'end' => 13
		      //   ),
		      //   'available' => array(2, 4, 5),
		      //   'jadwal' => array(
		      //     '2' => array(),
		      //     '4' => array(),
		      //     '5' => array()
		      //   )
		      // )
			String output = "";
			output += "{";
			output += "\"nama_ruangan\": \"" + getNamaRuangan() + "\",";
			output += "\"hour\": {";
			output += "\"start\": \"" +  getStartHour() + "\",";
			output += "\"end\": \"" + getEndHour() + "\"";
			output += "},";
			output += "\"available\": " + getAvailableDay() + ",";
			output += "\"jadwal\": [";
			output += printJadwal(0);
			output += ", ";
			output += printJadwal(1);
			output += ", ";
			output += printJadwal(2);
			output += ", ";
			output += printJadwal(3);
			output += ", ";
			output += printJadwal(4);
			output += "]";
			output += "}";

			return output;
		}
	}

	public static class MasterRoom{
		private ArrayList<BeRoom> arrRoom;
		public MasterRoom(){
			arrRoom = new ArrayList<BeRoom>();
		}

		public void addRoom(BeRoom br){
			arrRoom.add(br);
		}

		public BeRoom getRoom(int index){
			return arrRoom.get(index);
		}

		public String getAllOutput(){
			String output = "";
			output += "[";
			for(int i = 0; i < arrRoom.size(); i++){
				output += getRoom(i).getOutput();
				if(i != (arrRoom.size() - 1))
					output += ", ";
			}
			output += "]";

			return output;
		}
	}

	public static void main(String[] args){
		MasterRoom mr = new MasterRoom();

		mr.addRoom(new BeRoom(true));
		BeRoom mp;
		mp = mr.getRoom(0);
		mp.setNamaRuangan("Labdas II Informatika");
		mp.setStartEndHour(7, 15);
		mp.setAvailableDay(true, true, true ,false, false);
		mp.addJadwal(0, 7, "IF3110");
		mp.addJadwal(0, 8, "IF3110");
		mp.addJadwal(0, 11, "IF4070");
		mp.addJadwal(0, 12, "IF4070");

		mr.addRoom(new BeRoom(true));
		mp = mr.getRoom(1);
		mp.setNamaRuangan("7602");
		mp.setStartEndHour(9, 17);
		mp.setAvailableDay(true, true, false, true, false);
		mp.addJadwal(3, 9, "IF3110");
		mp.addJadwal(3, 10, "IF3110");
		mp.addJadwal(1, 15, "IF4070");
		mp.addJadwal(1, 16, "IF4070");
		mp.addJadwal(1, 15, "IF1220");
		mp.addJadwal(1, 16, "IF1220");

		System.out.println(mr.getAllOutput());
	}
}