import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

class Professors {
	String name;
	int time_start, time_end, units;
	int[][][] subjStat;
	Course c[];
	String sched[][];
	
	public Professors(String pname, int day) {
		name = "";
		time_start = 0;
		time_end = 0;
		units = 0;
		c = new Course[0];
		try {
			setDetails(pname, day);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void setDetails(String pname, int day) throws IOException {
		String details = file_handle.getProfDetails(pname);
		StringTokenizer st = new StringTokenizer(details, ",");
		StringTokenizer s;
		String temp;
		int num, num1;
		
		if (st.hasMoreTokens()) 
			name = st.nextToken();
		
		if (st.hasMoreTokens()) 
			time_start = Integer.parseInt(st.nextToken());
		
		if (st.hasMoreTokens()) 
			time_end = Integer.parseInt(st.nextToken());
		
		if (st.hasMoreTokens()) {
			temp = st.nextToken();
			s = new StringTokenizer(temp, ":");
			num = 0;
			while (s.hasMoreTokens()) {
				num++;
				s.nextToken();
			}
			c = new Course[num];
			s = new StringTokenizer(temp, ":");
			for (num = 0; num < c.length; num++) 
				c[num] = new Course(s.nextToken());
		}
		sched = new String[day][13];
		for (num = 0; num < day; num++) {
			for (num1 = 0; num1 < 13; num1++) 
				sched[num][num1] = "";
		}
	}
}
class Course {
	String description, id, program;
	int type, units, year, sem;
	boolean assigned;
	
	public Course(String sname) {
		id = "";
		description = "";
		type = 0;
		units = 0;
		sem = 0;
		assigned = false;
		try {
			setDetails(sname);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void setDetails(String sname) throws IOException {
		String details = file_handle.getCourseDetails(sname);
		StringTokenizer st = new StringTokenizer(details, ",");
		
		if (st.hasMoreTokens()) 
			id = st.nextToken();
		
		if (st.hasMoreTokens()) 
			description = st.nextToken();
		
		if (st.hasMoreTokens()) 
			type = Integer.parseInt(st.nextToken());
		
		if (st.hasMoreTokens()) 
			units = Integer.parseInt(st.nextToken());
		
		if (st.hasMoreTokens())
			sem = Integer.parseInt(st.nextToken());
	}
}
class Rooms {
	String name;
	int type;
	int time_start, time_end;
	Course c[];
	String sched[][];
	
	public Rooms(String rname, int day) {
		name = "";
		type = 0;
		try {
			setDetails(rname, day);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
	}
	public void setDetails(String rname, int day) throws IOException {
		String details = file_handle.getRoomDetails(rname);
		StringTokenizer st = new StringTokenizer(details, ",");
		int num, num1;
		String temp = "";
		if (st.hasMoreTokens()) 
			name = st.nextToken();
		
		if (st.hasMoreTokens()) 
			type = Integer.parseInt(st.nextToken());

 		if (st.hasMoreTokens()) 
			time_start = Integer.parseInt(st.nextToken());
		
		if (st.hasMoreTokens()) 
			time_end = Integer.parseInt(st.nextToken());
		
		if (st.hasMoreTokens())
 			temp = st.nextToken();
		String temp1 = temp;
 		st = new StringTokenizer(temp1, ":");
 		
 		num = 0;
 		while (st.hasMoreTokens()) {
 			num++;
 			st.nextToken();
 		}
 		
 		c = new Course[num];
 		st = new StringTokenizer(temp, ":");
 		for (num = 0; num < c.length; num++)
 			c[num] = new Course(st.nextToken());
 		
		
 		sched = new String[day][13];
 		for (num = 0; num < day; num++) {
			for (num1 = 0; num1 < 13; num1++) 
				sched[num][num1] = "";
		}
	}
	public boolean contains(String name) {
		int num;
		for (num = 0; num < c.length; num++) {
			if (c[num].id.equals(name))
				return true;
		}
		return false;
	}
}
class Sections {
	String name, major, block;
	int year;
	boolean done = false;
	Course c[];
	String sched[][];
	
	public Sections(String sname, int day, int sem) {
		name = "";
		major = "";
		block = "";
		year = 0;
		done = true;
		c = new Course[0];
		try {
			setDetails(sname, day, sem);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
 	public void setDetails(String sname, int day, int sem) throws IOException {
 		String list[] = new String[0];
 		String temp;
 		StringTokenizer st;
 		int num, num1;
 		name = sname;
 		year = sname.charAt(0) - 48;
 		
 		major = String.valueOf(sname.subSequence(1, 3));
 		if (sname.length() > 3)
 			block = String.valueOf(sname.charAt(3));
 		else
 			block = "A";
 		
 		if (major.equals("CS") || major.equals("cs")) {
 			list = file_handle.getCSCourselist("", sem);
 		}
 		else if (major.equals("IT") || major.equals("it")) {
 			list = file_handle.getITCourselist("", sem);
 		}
 		else {
 			list = file_handle.getISCourselist("", sem);
 		}
 		
 		st = new StringTokenizer(list[year - 1], "=");
 		temp = st.nextToken();
 		if (st.hasMoreTokens())
 			temp = st.nextToken();
 		st = new StringTokenizer(temp, ",");
 		
 		num = 0;
 		while (st.hasMoreTokens()) {
 			num++;
 			st.nextToken();
 		}
 		
 		c = new Course[num];
 		st = new StringTokenizer(temp, ",");
 		for (num = 0; num < c.length; num++)
 			c[num] = new Course(st.nextToken());
 		
 		sched = new String[day][13];
 		for (num = 0; num < day; num++) {
			for (num1 = 0; num1 < 13; num1++) 
				sched[num][num1] = "";
		}
	} 
}


public class automateSched {
	static int days;
	static int sem;
	static Professors p[];
	static Rooms r[];
	static Sections s[];
	static Course cr[];
	private static int rs = 4;
	public static String[][][] profStat;
	public static String[][][] subjStat;
	public static String[][][] subjs;
	public static int[][][] flag;
	public static int[][][] status;
	public static ArrayList <String> unassigned = new ArrayList <String> ();
	public static ArrayList <String> subjects = new ArrayList <String>();
	private static String[][] taught;
	private static int[] ts;
	public static int[] crsCt;
	public static int[] unitLecCt;
	public static int[] unitLabCt;
	
	public automateSched(int day, int sm) throws IOException {
		p = new Professors[file_handle.getProflistCount()];
		r = new Rooms[file_handle.getRoomlistCount()];
		s = new Sections[file_handle.getSectionlistCount()];
		cr = new Course[file_handle.getCourselistCount()];
		StringTokenizer st;
		String section;
		int num, line;
		
		String list[] = file_handle.getProflist("");
		for (num = 0; num < p.length; num++) 
			p[num] = new Professors(list[num], day);
		
		list = file_handle.getRoomlist("");
		for (num = 0; num < list.length; num++) 
			r[num] = new Rooms(list[num], day);
		
		line = 0;
		list = file_handle.getSectionlist("");
		st = new StringTokenizer(list[line]);
		for (num = 0; num < s.length; num++) {
			if (!st.hasMoreTokens()) {
				line++;
				st = new StringTokenizer(list[line]);
			}
			section = st.nextToken();
			s[num] = new Sections(section, day, sm);
		}
		
		list = file_handle.getCourselist("");
		for (num = 0; num < list.length; num++)
			cr[num] = new Course(list[num]);
		
		sem = sm;
		days = day;
		crsCt = new int[p.length];
		unitLecCt = new int[p.length];
		unitLabCt = new int[p.length];
		ts = new int[p.length];
		taught = new String[p.length][days];
		ipakita(r[0].c.length);
	}
	private static int getUnit(String name) {
		int num;
		for (num = 0; num < cr.length; num++) {
			if (cr[num].id.equals(name))
				return cr[num].units;
		}
		return -1;
	}
	private static void reload() throws IOException {
		p = new Professors[file_handle.getProflistCount()];
		r = new Rooms[file_handle.getRoomlistCount()];
		int num;
		String list[] = file_handle.getProflist("");
		for (num = 0; num < p.length; num++) 
			p[num] = new Professors(list[num], days);
		
		list = file_handle.getRoomlist("");
		for (num = 0; num < list.length; num++) 
			r[num] = new Rooms(list[num], days);
	}
	
	public static void NaghahatiNgMgaAralin(){
		String[][] subjLab = new String[s.length][10];
		String[][] subjLec = new String[s.length][10];
		String[][][] perDay = new String[s.length][days][7];
		String temp = new String();
		int[][] blgNgAralin = new int[s.length][days];
		int[][] unitLab = new int[s.length][6];
		int[][] unitLec = new int[s.length][10];
		int[] LabUnit = new int[s.length]; 
		int[] LecUnit = new int[s.length];
		int[] ct = new int[6];
		int[] basehan = new int[s.length];
		int[] sermon = new int[s.length];
		int pamilangNgKlase, pamilangNgAralin;			
		int base = 0, unts;
		
		for(pamilangNgKlase = 0; pamilangNgKlase < s.length; pamilangNgKlase++)
		{
			basehan[pamilangNgKlase] = 0;
			for(pamilangNgAralin = 0; pamilangNgAralin < s[pamilangNgKlase].c.length; pamilangNgAralin++)
			{	
				unts = s[pamilangNgKlase].c[pamilangNgAralin].type;
				if(unts != 2)
				{
					subjLab[pamilangNgKlase][basehan[pamilangNgKlase]] = s[pamilangNgKlase].c[pamilangNgAralin].id;
					unitLab[pamilangNgKlase][basehan[pamilangNgKlase]] = s[pamilangNgKlase].c[pamilangNgAralin].units;
					LabUnit[pamilangNgKlase] += unitLab[pamilangNgKlase][basehan[pamilangNgKlase]];
					basehan[pamilangNgKlase]++;
					temp = s[pamilangNgKlase].c[pamilangNgAralin].id;
					if(!subjects.contains(temp))
						subjects.add(temp);
				}
			}
		}
		for(pamilangNgKlase = 0; pamilangNgKlase < s.length; pamilangNgKlase++)
		{
			sermon[pamilangNgKlase] = 0;
			for(pamilangNgAralin = 0; pamilangNgAralin < s[pamilangNgKlase].c.length; pamilangNgAralin++)
			{
				if(s[pamilangNgKlase].c[pamilangNgAralin].type == 2)
				{
					subjLec[pamilangNgKlase][sermon[pamilangNgKlase]] = s[pamilangNgKlase].c[pamilangNgAralin].id;
					unitLec[pamilangNgKlase][sermon[pamilangNgKlase]] = s[pamilangNgKlase].c[pamilangNgAralin].units;
					LecUnit[pamilangNgKlase] += unitLec[pamilangNgKlase][sermon[pamilangNgKlase]];
					sermon[pamilangNgKlase]++;
					temp = s[pamilangNgKlase].c[pamilangNgAralin].id;
					if(!subjects.contains(temp))
						subjects.add(temp);
				}
			}
		}
		
		int hrs = 0, units = 0;
		int d = days;
		units = (basehan[base] * 3) + (sermon[base] * 3);

		if(units >= 10 && units <= 18)
			d -= 2;
		else if(units >= 19 && units <= 24)
			d -= 1;
		
		hrs = ((basehan[base] * 3) + (sermon[base] * 3)) / d;
		
		int x, loc, loc2, ctr;
		
		do //section
		{
			
			x = d - 1;
			loc2 = 0;
			for(int y = 0; y < 6; y++)
				ct[y] = 0;
			
			do
			{
				if(ct[x] <= 4)
				{
					ctr = 0;
					do
					{
						perDay[base][x][ct[x]] = subjLab[base][loc2];
						ct[x]++;
						blgNgAralin[base][x] = ct[x];
						ctr++;
					}while(ctr < 3);
					unitLab[base][loc2]--;
					if(unitLab[base][loc2] > 0)
						loc2--;
					x--;
				}
				else
					x--;
				loc2++;
			}while(loc2 < basehan[base]);
			
			x = 0;
			loc = 0;
			
			do
			{
				if(unitLec[base][loc] == 3)
				{
					if(ct[0] < hrs)
					{
						x = 0;
						ctr = 0;
						do
						{
							while(ct[x] >= 7)
							{
								x++;
								
								if(x >= d)
									x = 0;
							};
							perDay[base][x][ct[x]] = subjLec[base][loc];
							ct[x]++;
							ctr++;
							blgNgAralin[base][x] = ct[x];
							x++;
						}while(ctr < 3);
					}
					else
					{
						x = d - 1;
						ctr = 0;
						do
						{
							if(x < 0)
								x = d - 1;
							while(ct[x] >= 7)
							{
								x--;
							};
							perDay[base][x][ct[x]] = subjLec[base][loc];
							ct[x]++;
							blgNgAralin[base][x] = ct[x];
							ctr++;
							x--;
						}while(ctr < unitLec[base][loc]);
					}
				}
				else
				{
					x = d - 1;
					ctr = 0;
					do
					{
						if(x < 0)
							x = d - 1;
						while(ct[x] >= 7)
						{
							x--;
						};
						perDay[base][x][ct[x]] = subjLec[base][loc];
						ct[x]++;
						blgNgAralin[base][x] = ct[x];
						ctr++;
						x--;
					}while(ctr < unitLec[base][loc]);
				}
				loc++;
			}while(loc < sermon[base]);
			base++;
		}while(base < s.length);
		NaglalagayNgMgaAralin(perDay, base, blgNgAralin);
	}
	
	private static boolean checkHorizontal(int base, int timectr, int day) {
		int num;
		StringTokenizer st;
		String name = " ";
		
		for (num = 0; num < r.length; num++) {
			name = " ";
			st = new StringTokenizer(r[num].sched[day][timectr], ":");
			if (st.hasMoreTokens())
				name = st.nextToken();
			if (s[base].name.equals(name))
				return false;
		}
		return true;
	}
	private static int selectCourse(boolean[] done, int ct, int ctr) {
		int num;
		
		for (num = 0; num < ct; num++) {
			if (!done[(num + ctr) % ct])
				return ((num + ctr) % ct);
		}
		return -1;
	}
	private static boolean moreCourse(boolean[] done, int ct) {
		int num;
		
		for (num = 0; num < ct; num++)  {
			if (!done[num])
				return true;
		}
		return false;
	}
	private static int getCourse(String name, int base) {
		int num;
		
		for (num = 0; num < s[base].c.length; num++) {
			if (s[base].c[num].id.equals(name)) 
				return num;
		}
		return -1;
	}
	private static boolean assignCourse(int base, int cctr, int roomctr, int day, int timectr) {
		try {
			s[base].sched[day][timectr] = r[roomctr].name + ":" + s[base].c[cctr].id;
			r[roomctr].sched[day][timectr] = s[base].name + ":" + s[base].c[cctr].id;
		} catch(Exception e) {
			ipakita(e.getMessage());
			ipakita("base: " + base + " day: " + day + " timectr: " + timectr + " roomctr: " + roomctr);
			return false;
		}
		return true;
	}
	private static int checkDuplicate(boolean[] done, String[] perDay, int coursectr, int ct) {
		int num;
		
		for (num = 0; num < ct; num++) {
			if (!done[num] && perDay[coursectr].equals(perDay[num]))
				return num;
		}
		return -1;
	}
	@SuppressWarnings("static-access")
	private static boolean checkRoom(int base, int roomctr, int timectr, int day, int recommendedTime, int type, int cctr) {
		int time;
		int num;
		boolean valid;
		if (timectr == -1)
			time = 0;
		else
			time = timectr;
		if (recommendedTime > rs)
			recommendedTime = rs;
		if (timectr != -1) {
			if (!r[roomctr].sched[day][timectr].equals("")) 
				return true;
		}
		if (r[roomctr].type != 2)
			recommendedTime = 3;
		if (r[roomctr].c.length > 0) {
			if (!r[roomctr].contains(s[base].c[cctr].id))
				return true;
		}
		if (!(timectr >= r[roomctr].time_start && timectr <= r[roomctr].time_end))
			return true;
		for (; time < 13; time++) {
			valid = false;
			for (num = time; num < time + recommendedTime && time + recommendedTime <= 13; num++) {
				if ((num >= r[roomctr].time_start && num <= r[roomctr].time_end) && r[roomctr].sched[day][num].equals("") && checkHorizontal(base, num, day))
					valid = true;
				else {
					return true;
				}
			}
			if (valid)
				return false;
		}
		return true;
	}
	private static boolean checkAllRooms(boolean[] done) {
		int num;
		for (num = 0; num < done.length; num++) {
			if (!done[num])
				return true;
		}
		return false;
	}
	private static int selectRoom(int base, int type, int cctr, int timectr, int day, int recommendedTime, int prev) {
		int roomctr = -1;
		Random generator = new Random();
		boolean done[] = new boolean[r.length];
		do {
			do {
				roomctr = generator.nextInt(r.length);
				if (r[roomctr].type != type)
					done[roomctr] = true;
			} while (done[roomctr] && checkAllRooms(done));
			done[roomctr] = true;
		} while (checkRoom(base, roomctr, timectr, day, recommendedTime, type, cctr) && checkAllRooms(done));
		
		if (!checkRoom(base, roomctr, timectr, day, recommendedTime, type, cctr))
			return roomctr;
		else 
			return -1;
	}
	private static int getDay(boolean[] daydone, int day) {
		int num = -1;
		Random generator = new Random();
		do {
			num = generator.nextInt(day);
		} while (daydone[num]);
		
		return num;
	}
	private static boolean errdayCheck(boolean[] day) {
		int num;
		for (num = 0; num < day.length; num++) {
			if (!day[num])
				return false;
		}
		return true;
	}
	private static boolean[] copyContents (boolean[] daydone) {
		boolean[] temp = new boolean[daydone.length];
		int num;
		for (num = 0; num < temp.length; num++)
			temp[num] = daydone[num];
		
		return temp;
	}
	private static int getEarliestTime (int base, int cctr) {
		int timectr;
		int roomctr;
		int day;
		boolean valid = false;
		
		for (timectr = 0; timectr < 13; timectr++) {
			for (roomctr = 0; roomctr < r.length; roomctr++) {
				if (r[roomctr].type == 2) {
					for (day = 0; day < days; day++) {
						if (r[roomctr].sched[day][timectr].equals(""))
							valid = true;
						else {
							valid = false;
							break;
						}
					}
					if (valid)
						return --timectr;
				}
			}
			if (valid)
				return --timectr;
		}
		return -1;
	}
	private static void NaglalagayNgMgaAralin(String[][][] perDay, int base, int[][] ct) {
		int num, x, secnum;
		int day, dayctr;
		int coursectr, cctr, scoursectr;
		int prevroom;
		int roomctr;
		int timectr, temptimectr, starttime;
		int unassign = 0;
		boolean assign, exit;
		boolean[][][] done = new boolean[base][days][7];
		boolean[] daydone;
		boolean[] errday;
		ArrayList <Integer> sectionList = new ArrayList <Integer> ();
		ArrayList <Integer> tempList = new ArrayList <Integer> ();
		ArrayList <Integer> firstyear = new ArrayList <Integer> ();
		ArrayList <Integer> secondyear = new ArrayList <Integer> ();
		ArrayList <Integer> thirdyear = new ArrayList <Integer> ();
		ArrayList <Integer> fourthyear = new ArrayList <Integer> ();
		unassigned.clear();
		sectionList.clear();
		int year;
		String major;
		int crctr;
		try {
			reload();
		} catch (IOException e) {
		}
		for (num = 0; num < s.length; num++) {
			switch (s[num].year) {
			case 1: firstyear.add(num); break;
			case 2: secondyear.add(num); break;
			case 3: thirdyear.add(num); break;
			case 4: fourthyear.add(num); break;
			}
		}
		num = 0;
		crctr = 0;
		major = "";
		while (firstyear.size() > 0) {
			if (!major.equals("")) {
				if (num >= firstyear.size())
					num = 0;
				if (s[firstyear.get(num)].major.equals(major)) {
					if (crctr < 3) {
						tempList.add(firstyear.get(num));
						firstyear.remove(num);
						crctr++;
					}
					else {
						while (num < firstyear.size() && s[firstyear.get(num)].major.equals(major)) {
							num++;
							crctr = 0;
						}
					}
				}
				else {
					major = s[firstyear.get(num)].major;
					crctr = 0;
				}
			}
			else
				major = s[firstyear.get(num)].major;
		}
		num = 0;
		crctr = 0;
		major = "";
		while (secondyear.size() > 0) {
			if (!major.equals("")) {
				if (num >= secondyear.size())
					num = 0;
				if (s[secondyear.get(num)].major.equals(major)) {
					if (crctr < 3) {
						tempList.add(secondyear.get(num));
						secondyear.remove(num);
						crctr++;
					}
					else {
						while (num < secondyear.size() && s[secondyear.get(num)].major.equals(major)) {
							num++;
							crctr = 0;
						}
					}
				}
				else {
					major = s[secondyear.get(num)].major;
					crctr = 0;
				}
			}
			else
				major = s[secondyear.get(num)].major;
		}
		num = 0;
		crctr = 0;
		major = "";
		while (thirdyear.size() > 0) {
			if (!major.equals("")) {
				if (num >= thirdyear.size())
					num = 0;
				if (s[thirdyear.get(num)].major.equals(major)) {
					if (crctr < 3) {
						tempList.add(thirdyear.get(num));
						thirdyear.remove(num);
						crctr++;
					}
					else {
						while (num < thirdyear.size() && s[thirdyear.get(num)].major.equals(major)) {
							num++;
							crctr = 0;
						}
					}
				}
				else {
					major = s[thirdyear.get(num)].major;
					crctr = 0;
				}
			}
			else
				major = s[thirdyear.get(num)].major;
		}
		num = 0;
		crctr = 0;
		major = "";
		while (fourthyear.size() > 0) {
			if (!major.equals("")) {
				if (num >= fourthyear.size())
					num = 0;
				if (s[fourthyear.get(num)].major.equals(major)) {
					if (crctr < 3) {
						tempList.add(fourthyear.get(num));
						fourthyear.remove(num);
						crctr++;
					}
					else {
						while (num < fourthyear.size() && s[fourthyear.get(num)].major.equals(major)) {
							num++;
							crctr = 0;
						}
					}
				}
				else {
					major = s[fourthyear.get(num)].major;
					crctr = 0;
				}
			}
			else
				major = s[fourthyear.get(num)].major;
		}
		sectionList = tempList;
		for (num = 0; num < r.length; num++) {
			r[num].sched = new String[days][13];
			for (int z = 0; z < days; z++) {
				for (int y = 0; y < 13; y++)
					r[num].sched[z][y] = "";
			}
		}
		
		year = -1;
		major = "";
		crctr = 0;
		for (secnum = 0; secnum < base; secnum++) {
			num = sectionList.get(secnum);
			for (dayctr = 0; dayctr < days; dayctr++) {
				for (timectr = 0; timectr < 13; timectr++)
					s[num].sched[dayctr][timectr] = "";
			}
			if (s[num].year == year) {
				if (s[num].major.equals(major))
					crctr++;
				else {
					major = s[num].major;
					crctr = 0;
				}
			}
			else {
				year = s[num].year;
				major = s[num].major;
				crctr = 0;
			}
			daydone = new boolean[days];
			errday = new boolean[days];
			starttime = -1;
			for (dayctr = 0; dayctr < days; dayctr++) {
				int cons = ct[num][dayctr];
				day = getDay(daydone, days);
				ipakita(" ");
				ipakita("section: " + s[num].name + " day: " + day + " dayctr: " + dayctr);
				assign = false;
				exit = false;
				if (dayctr == 0) {
					coursectr = selectCourse(done[num][dayctr], ct[num][dayctr], crctr);
					cctr = getCourse(perDay[num][dayctr][coursectr], num);
					starttime = getEarliestTime (num, cctr);
				}
				timectr = starttime;
				roomctr = -1;
				prevroom = -1;
				scoursectr = 0;
				temptimectr = -1;
				while (moreCourse(done[num][dayctr], ct[num][dayctr])) {
					coursectr = selectCourse(done[num][dayctr], ct[num][dayctr], crctr);
					cctr = getCourse(perDay[num][dayctr][coursectr], num);
					
					if (s[num].c[cctr].type != 2) {
						boolean inc = false;
						while (timectr % 3 != 0) {
							timectr++;
							inc = true;
						}
						if (inc)
							timectr--;
					}
					
					temptimectr = timectr;
					while (roomctr < 0 || r[roomctr].type != s[num].c[cctr].type || timectr < 0) {
						assign = true;
						timectr++;
						if (timectr > 12 || (Math.abs(temptimectr - timectr) > 2) && temptimectr != -1) {
							errday[day] = true;
							rs--;
							if (rs < 1 || s[num].c[cctr].type != 2) {
								rs = 4;
								if (errdayCheck(errday)) {
									errday = copyContents(daydone);
									timectr = starttime;
									scoursectr = 0;
									unassign++;
									do {
										unassigned.add(String.valueOf(num + ":" + s[num].c[cctr].id + ":" + day));
										done[num][dayctr][coursectr] = true;
										cons--;
										coursectr = checkDuplicate(done[num][dayctr], perDay[num][dayctr], coursectr, ct[num][dayctr]);
										if (coursectr != -1) 
											cctr = getCourse(perDay[num][dayctr][coursectr], num);
									} while(coursectr != -1);
									coursectr = selectCourse(done[num][dayctr], ct[num][dayctr], crctr);
									if (coursectr != -1)
										cctr = getCourse(perDay[num][dayctr][coursectr], num);
									else {
										exit = true;
										break;
									}
								}
								else {
									scoursectr = 0;
									timectr = starttime;
									roomctr = -1;
									assign = false;
									break;
								}
							}
							else {
								scoursectr = 0;
								timectr = starttime;
								roomctr = -1;
							}
						}
						roomctr = selectRoom(num, s[num].c[cctr].type, cctr, timectr, day, cons, prevroom);
					}
					prevroom = roomctr;
					if (!assign)
						break;
					if (exit)
						break;
					while (coursectr != -1) {
						if (assignCourse(num, cctr, roomctr, day, timectr)) {
							cons--;
							done[num][dayctr][coursectr] = true;
							scoursectr++;
							timectr++;
							coursectr = checkDuplicate(done[num][dayctr], perDay[num][dayctr], coursectr, ct[num][dayctr]);
							if (coursectr != -1)
								cctr = getCourse(perDay[num][dayctr][coursectr], num);
						}
						else {
							timectr = starttime;
							scoursectr = 0;
							unassign++;
							do {
								unassigned.add(String.valueOf(num + ":" + s[num].c[cctr].id + ":" + day));
								done[num][dayctr][coursectr] = true;
								cons--;
								coursectr = checkDuplicate(done[num][dayctr], perDay[num][dayctr], coursectr, ct[num][dayctr]);
								if (coursectr != -1)
									cctr = getCourse(perDay[num][dayctr][coursectr], num);
							} while(coursectr != -1);
							coursectr = selectCourse(done[num][dayctr], ct[num][dayctr], crctr);
							if (coursectr != -1)
								cctr = getCourse(perDay[num][dayctr][coursectr], num);
							else {
								exit = true;
								break;
							}
						}
					}
					if (scoursectr > 3) {
						scoursectr = 0;
						timectr++;
						roomctr = -1;
					}
				}
				if (ct[num][dayctr] > 0 && assign) {
					errday[day] = true;
					daydone[day] = true;
					unassign = 0;
					for (x = 0; x < 13; x++)
						ipakita(x + ": " + s[num].sched[day][x]);
				}
				else if (ct[num][dayctr] > 0 && !assign) {
					dayctr--;
				}
			}
		}
		for (int a = 0; a < r.length; a++) {
			ipakita("room: " + r[a].name);
			for (int b = 0; b < days; b++) {
				ipakita("day: " + b);
				for (int c = 0; c < 13; c++) {
					ipakita(c + ": " + r[a].sched[b][c]);
				}
			}
		}
		ipakita("Unassigned (Try to reschedule): ");
		for (int a = 0; a < unassigned.size(); a++) {
			String temp = unassigned.get(a);
			StringTokenizer st = new StringTokenizer(temp, ":");
			int tempnum = Integer.valueOf(st.nextToken());
			String tempcoursectr = st.nextToken();
			int tempday = Integer.valueOf(st.nextToken());
			ipakita(a + ": " + tempnum + " " + s[tempnum].name + " " + tempcoursectr + " day: " + tempday);
		}
		ipakita("natapos nya!");
		startProf();
		naghahanap();
	}
	private static void startProf() {
		subjStat = new String[s.length][6][13];
		profStat = new String[p.length][6][13];
		subjs = new String[s.length][6][13];
		status = new int[s.length][6][13];
		flag = new int[p.length][6][13];
		for(int x = 0; x < s.length; x++)
		{
			for(int y = 0; y < days; y++)
			{
				for(int z = 0; z < 13; z++)
					{
					subjStat[x][y][z] = "";
					subjs[x][y][z] = s[x].sched[y][z];
					}
			}
		}
		for(int x = 0; x < p.length; x++)
		{
			for(int y = 0; y < days; y++)
			{
				for(int z = 0; z < 13; z++)
					profStat[x][y][z] = "";
			}
		}
	}
	
	private static void naghahanap() {
		int currSec = 0, currDay = 0, currTime = 0;
		StringTokenizer subj;
		String temp = null;
		do
		{
			if(currTime < 11 && currDay < days - 2)
			{
				if(currTime < 11 && currDay < days - 2 && subjStat[currSec][currDay][currTime] == "" && subjs[currSec][currDay][currTime] != "")
				{
					subj = new StringTokenizer(subjs[currSec][currDay][currTime], ":");
					if (subj.hasMoreTokens()) 
						{
						temp = subj.nextToken();
						temp = subj.nextToken();
						}
					traverse(temp);
					if(currTime > 11 && currDay < days - 1)
					{
						currDay++;
						currTime = 0;
					}
					else if( currDay >= days - 1 && currTime >= 11 )
					{
						currSec++;
						currTime = 0;
						currDay = 0;
					}
					else
						currTime++;
				}
				else
				{
					if(currTime > 11 && currDay < days - 1)
					{
						currDay++;
						currTime = 0;
					}
					else if ( currDay >= days - 1 && currTime >= 11 )
					{
						currSec++;
						currTime = 0;
						currDay = 0;
					}
					else
					{
						currTime++;
					}
				}
			}
			else
			{
				if(currTime > 11 && currDay < days - 1)
				{
					currDay++;
					currTime = 0;
				}
				else if( currDay >= days - 1 && currTime >= 11 )
				{
					currSec++;
					currTime = 0;
					currDay = 0;
				}
				else
					currTime++;
			}
		}while(currSec < s.length );
	}
	
	private static void traverse(String temp) {
		int[] dayLoc = new int[6];
		int[] timeLoc = new int[6];
		int currSec = 0, currDay = 0, currTime = 0, ctr = 0;
		int ct = 0, loc = 0, tms = 0;
		StringTokenizer subj;
		String save = null;
		
		do
		{
			subj = new StringTokenizer(subjs[currSec][currDay][currTime], ":");
			if(subjs[currSec][currDay][currTime] != "")
			{	
				if (subj.hasMoreTokens()) 
					{
					save = subj.nextToken();
					save = subj.nextToken();
					}
			}
			if(temp.equals(save))
			{
				loc = getCourse(save, currSec);
				
				if(2 != s[currSec].c[loc].type)
				{
					dayLoc[ct] = currDay;
					timeLoc[ct] = currTime;
					ct++;
					
					if(ct == (tms =(s[currSec].c[loc].units*3)))
					{
						mgaMagtuturo(currSec, dayLoc, timeLoc, tms);
						ct = 0;
						currSec++;
						currDay = 0;
						currTime = 0;
					}
				}
				else
				{
					if(2 == s[currSec].c[loc].type)
					{
						dayLoc[ct] = currDay;
						timeLoc[ct] = currTime;
						ct++;
						if(ct == (tms = s[currSec].c[loc].units))
						{
							mgaMagtuturo(currSec, dayLoc, timeLoc, tms);
							ct = 0;
							currSec++;
							currDay = 0;
							currTime = 0;
						}
					}
				}
				ctr++;
				if(currTime > 11 && currDay < days - 1)
				{
					currDay++;
					currTime = 0;
				}
				else if ( currDay >= days - 1 && currTime >= 11 )
				{
					currSec++;
					currTime = 0;
					currDay = 0;
				}
				else
					currTime++;
			}
			else
			{
				if(currTime > 11 && currDay < days - 1)
				{
					currDay++;
					currTime = 0;
				}
				else if ( currDay >= days - 1 && currTime >= 11 )
				{
					currSec++;
					currTime = 0;
					currDay = 0;
				}
				else
					currTime++;
			}
		save = "";
		}while(currSec < s.length);
	}
	
	private static void mgaMagtuturo(int secLoc, int[] dayLoc, int[] timeLoc, int tms) {
		int a = 0, gU = 0;
		StringTokenizer subj;
		String temp = null;
		subj = new StringTokenizer(subjs[secLoc][dayLoc[0]][timeLoc[0]], ":");
		if (subj.hasMoreTokens()) 
			{
			temp = subj.nextToken();
			temp = subj.nextToken();
			}
		int y = 0, ct = 0;	

		do
		{
			for(int x = 0; x < p[y].c.length; x++)
			{
				if(crsCt[y] < 3)
				{
					if((unitLecCt[y]+unitLabCt[y]) < 24)
					{
						ct = 0;
						do
						{
							if(profStat[y][dayLoc[ct]][timeLoc[ct]] == "")
							{
								if(p[y].c[x].id.equals(temp))
								{
									if(p[y].time_start - 1 <= timeLoc[ct] && p[y].time_end - 1 >= timeLoc[ct])
									{
										if(ct == tms - 1)
										{
											int cons = 0, stat = 0;
											do
											{
												if(timeLoc[cons] == 0)
												{
													if(profStat[y][dayLoc[cons]][timeLoc[cons]+1] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]+2] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]+3] == "")
													{
														stat = 1;
														profStat[y][dayLoc[cons]][timeLoc[cons]] = "1";
													}
													else
													{
														stat = 0;
														int reset = 0;
														do{
															profStat[y][dayLoc[reset]][timeLoc[reset]] = "";
															reset++;
														}while(reset < cons);
														cons = tms;
													}	
												}
												else if(timeLoc[cons] == 1)
												{
													if(profStat[y][dayLoc[cons]][timeLoc[cons]-1] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]+1] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]+2] == "")
													{
														stat = 1;
														profStat[y][dayLoc[cons]][timeLoc[cons]] = "1";
													}
													else
													{
														stat = 0;
														int reset = 0;
														do{
															profStat[y][dayLoc[reset]][timeLoc[reset]] = "";
															reset++;
														}while(reset < cons);
														cons = tms;
													}	
												}
												else if(timeLoc[cons] == 2)
												{
													if(profStat[y][dayLoc[cons]][timeLoc[cons]-2] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]-1] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]+1] == "")
													{
														stat = 1;
														profStat[y][dayLoc[cons]][timeLoc[cons]] = "1";
													}
													else
													{
														stat = 0;
														int reset = 0;
														do{
															profStat[y][dayLoc[reset]][timeLoc[reset]] = "";
															reset++;
														}while(reset < cons);
														cons = tms;
													}	
												}
												else
												{
													if(profStat[y][dayLoc[cons]][timeLoc[cons]-1] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]-2] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]-3] == "")
													{
														stat = 1;
														profStat[y][dayLoc[cons]][timeLoc[cons]] = "1";
													}
													else
													{
														stat = 0;
														int reset = 0;
														do{
															profStat[y][dayLoc[reset]][timeLoc[reset]] = "";
															reset++;
														}while(reset < cons);
														cons = tms;
													}
												}
												cons++;
											}while(cons < tms);
											
											if(stat == 1)
											{
												int jrf = 0;
												gU = getUnit(temp);
												do
												{
													subjStat[secLoc][dayLoc[jrf]][timeLoc[jrf]] = p[y].name; 
													status[secLoc][dayLoc[jrf]][timeLoc[jrf]] = 0; 
													subjs[secLoc][dayLoc[jrf]][timeLoc[jrf]] = "";
													profStat[y][dayLoc[jrf]][timeLoc[jrf]] = s[secLoc].name+":"+temp;
													flag[y][dayLoc[jrf]][timeLoc[jrf]] = 0;
													int s = 0, ctr = 0;
													if(taught[y][ctr] != null)
													{
														do{
															if(!temp.equals(taught[y][ctr]))
															{
																s++;
																if(s == ts[y])
																{
																	crsCt[y]++;
																	taught[y][ts[y]] = temp;
																	ts[y]++;
																}
															}
															else
																ctr = ts[y];
														ctr++;
														}while(ctr < ts[y]);
													}
													else
													{
														crsCt[y]++;
														taught[y][ts[y]] = temp;
														ts[y]++;
													}
													if(jrf == 0)
													{
														if(gU == 3)
															unitLabCt[y]++;
														else
															unitLecCt[y] += 3;
													}
													jrf++;
													x = p[y].c.length;
													a = 1;
												}while(jrf < tms);
											}
										}
									}
									else
										ct = tms;
								}
							ct++;
							}
							else
								ct = tms;
						}while(ct < tms);
					}
				}
			}
			y++;
			if(a == 1)
				y = p.length;
		}while(y < p.length);
		y = 0;
		ct = 0;
		if(subjStat[secLoc][dayLoc[ct]][timeLoc[ct]] == "")
		{
			do
			{
				for(int x = 0; x < p[y].c.length; x++)
				{
					if(crsCt[y] >= 3 ||	(unitLecCt[y]+unitLabCt[y]) >= 24)
					{
						ct = 0;
						do
						{
							if(profStat[y][dayLoc[ct]][timeLoc[ct]] == "")
							{
								if(p[y].c[x].id.equals(temp))
								{
									if(p[y].time_start - 1 <= timeLoc[ct] && p[y].time_end - 1 >= timeLoc[ct])
									{
										if(ct == tms - 1)
										{
											int cons = 0, stat = 0;
											do
											{
												if(timeLoc[cons] == 0)
												{
													if(profStat[y][dayLoc[cons]][timeLoc[cons]+1] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]+2] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]+3] == "")
													{
														stat = 1;
														profStat[y][dayLoc[cons]][timeLoc[cons]] = "1";
													}
													else
													{
														stat = 0;
														int reset = 0;
														do{
															profStat[y][dayLoc[reset]][timeLoc[reset]] = "";
															reset++;
														}while(reset < cons);
														cons = tms;
													}	
												}
												else if(timeLoc[cons] == 1)
												{
													if(profStat[y][dayLoc[cons]][timeLoc[cons]-1] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]+1] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]+2] == "")
													{
														stat = 1;
														profStat[y][dayLoc[cons]][timeLoc[cons]] = "1";
													}
													else
													{
														stat = 0;
														int reset = 0;
														do{
															profStat[y][dayLoc[reset]][timeLoc[reset]] = "";
															reset++;
														}while(reset < cons);
														cons = tms;
													}	
												}
												else if(timeLoc[cons] == 2)
												{
													if(profStat[y][dayLoc[cons]][timeLoc[cons]-2] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]-1] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]+1] == "")
													{
														stat = 1;
														profStat[y][dayLoc[cons]][timeLoc[cons]] = "1";
													}
													else
													{
														stat = 0;
														int reset = 0;
														do{
															profStat[y][dayLoc[reset]][timeLoc[reset]] = "";
															reset++;
														}while(reset < cons);
														cons = tms;
													}	
												}
												else
												{
													if(profStat[y][dayLoc[cons]][timeLoc[cons]-1] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]-2] == "" || profStat[y][dayLoc[cons]][timeLoc[cons]-3] == "")
													{
														stat = 1;
														profStat[y][dayLoc[cons]][timeLoc[cons]] = "1";
													}
													else
													{
														stat = 0;
														int reset = 0;
														do{
															profStat[y][dayLoc[reset]][timeLoc[reset]] = "";
															reset++;
														}while(reset < cons);
														cons = tms;
													}
												}
												cons++;
											}while(cons < tms);
											if(stat == 1)
											{
												int jrf = 0;
												gU = getUnit(temp);
												do
												{
													subjStat[secLoc][dayLoc[jrf]][timeLoc[jrf]] = p[y].name; 
													status[secLoc][dayLoc[jrf]][timeLoc[jrf]] = 1; 
													profStat[y][dayLoc[jrf]][timeLoc[jrf]] = s[secLoc].name+":"+temp; 
													flag[y][dayLoc[jrf]][timeLoc[jrf]] = 1;
													int s = 0, ctr = 0;
													if(taught[y][ctr] != null)
													{
														do{
															if(!temp.equals(taught[y][ctr]))
															{
																s++;
																if(s == ts[y])
																{
																	crsCt[y]++;
																	taught[y][ts[y]] = temp;
																	ts[y]++;
																}
															}
															else
																ctr = ts[y];
														ctr++;
														}while(ctr < ts[y]);
													}
													else
													{
														crsCt[y]++;
														taught[y][ts[y]] = temp;
														ts[y]++;
													}
													if(jrf == 0)
													{
														if(gU == 3)
															unitLabCt[y]++;
														else
															unitLecCt[y] += 3;
													}
													jrf++;
													x = p[y].c.length;
													a = 1;
												}while(jrf < tms);
											}
										}
									}
									else
										ct = tms;
								}
							ct++;
							}
							else	
								ct = tms;
						}while(ct < tms);
					}
				}
				y++;
				if(a == 1)
					y = p.length;
			}while(y < p.length);
		}
	}
	
	private static void ipakita(Object text) {
		System.out.println(text);
	}
	@SuppressWarnings("static-access")
	public static void main(String args[]) {
		try {
			automateSched test = new automateSched(5, 2);
			test.NaghahatiNgMgaAralin();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		for(int x = 0; x < p.length; x++)
		{
			for(int b = 0; b < days; b++)
			{
				for(int z = 0; z < 13; z++)
				{
					if(profStat[x][b][z] != "")
					{
						ipakita("subj: "+profStat[x][b][z]+" prof: "+x+" day: "+b+" time: "+z);
					}
				}
			}
		}
		for (int num = 0; num < r.length; num++) {
			ipakita("room: " + r[num].name);
			for (int day = 0; day < days; day++) {
				ipakita("day: " + day);
				for (int x = 0; x < 13; x++) {
					ipakita(x + ": " + r[num].sched[day][x]);
				}
			}
		}
		ipakita("natapos nya!");
		System.exit(0);
	}
}
