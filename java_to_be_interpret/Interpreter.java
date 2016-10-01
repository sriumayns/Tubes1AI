import java.util.*;

class Interpreter{
	public static class MasterRoom{
		private String namaRuangan;
		private int startHour, endHour;
		private ArrayList<Boolean> availableDay = new ArrayList<Boolean>(5);
		private ArrayList<ArrayList<String>> jadwal = new ArrayList<ArrayList<String>>(5);

		public MasterRoom(){
			for(int i = 0; i < 5; i++)
				jadwal.add(i, new ArrayList<String>());
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

		public void addJadwal(int index, String alVal){
			jadwal.get(index).add(alVal);
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

		public ArrayList<ArrayList<String>> getJadwal(){
			return jadwal;
		}

		// keluaran di cetak dalam bentuk JSON String
		public void printAll(){

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

			System.out.print("{");
			System.out.print("	\"nama_ruangan\": \"" + getNamaRuangan() + "\",");
			System.out.print("	\"hour\": {");
			System.out.print("		\"start\": \"" +  getStartHour() + "\",");
			System.out.print("		\"end\": \"" + getEndHour() + "\"");
			System.out.print("	},");
			System.out.print("	\"available\": " + getAvailableDay() + ",");
			System.out.print("	\"jadwal\": []");
			System.out.print("}");
			// System.out.println();
			// System.out.println(getJadwal());
		}
	}

	public static void main(String[] args){
		MasterRoom mp;
		mp = new MasterRoom();
		mp.setNamaRuangan("IF3110");
		mp.setStartEndHour(9, 15);
		mp.setAvailableDay(true, true, true ,false, false);
		mp.printAll();
	}
}