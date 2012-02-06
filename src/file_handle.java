import java.io.*;
import java.util.StringTokenizer;

public class file_handle {
	public static void main (String args[] ) throws IOException {
		String temp[] = getSectionlist("" );
		int num;
		for (num = 0; num < temp.length; num++ ) {
			print( temp[num] );
		}
	}
	
	private static void print( String text ) {
		System.out.println( text );
	}
	/* 
	 *Counts the number of entries
	 */
	public static int getProflistCount() throws IOException {
		return getlistcount( "proflist.dat" );
	}
	public static int getRoomlistCount() throws IOException {
		return getlistcount( "roomlist.dat" );
	}
	public static int getCourselistCount() throws IOException {
		return getlistcount( "courselist.dat" );
	}
	public static int getSectionlistCount() throws IOException {
		return getlistcount( "sectionlist.dat" );
	}
	
	/*
	 *Returns the list of entries
	 */
	public static String[] getProflist ( String first ) throws IOException {
		return getlist( "proflist.dat", first, true );
	}
	public static String[] getRoomlist ( String first ) throws IOException {
		return getlist( "roomlist.dat", first, true );
	}
	public static String[] getCourselist ( String first ) throws IOException {
		return getlist( "courselist.dat", first, true );
	}
	public static String[] getSectionlist ( String first ) throws IOException {
		return getlist( "sectionlist.dat", first, true );
	}
	public static String[] getCSCourselist ( String first, int sem ) throws IOException {
		return getYearlist( first, sem, 0 );
	}
	public static String[] getITCourselist ( String first, int sem ) throws IOException {
		return getYearlist( first, sem, 4 );
	}
	public static String[] getISCourselist ( String first, int sem ) throws IOException {
		return getYearlist( first, sem, 8 );
	}

	/*
	 *Returns the row of the entry specified by String name
	 */
	public static String getCourseDetails ( String name ) throws IOException {
		return getDetails( name, "courselist.dat" );
	}
	public static String getProfDetails ( String name ) throws IOException {
		return getDetails( name, "proflist.dat" );
	}
	public static String getRoomDetails ( String name ) throws IOException {
		return getDetails( name, "roomlist.dat" );
	}
	
	/*
	 *For now it removes, but it should be disabling the entry specified by String name
	 */
	public static void removeProf( String name ) throws IOException {
		remove( name, "proflist.dat" );
	}
	public static void removeCourse( String name ) throws IOException {
		remove( name, "courselist.dat" );
	}
	public static void removeRoom( String name ) throws IOException {
		remove( name, "roomlist.dat" );
	}
	
	/*
	 *Sets the details of an entry
	 */
	public static void setProfDetails ( String profDetails[] ) throws IOException {
		/*String label[] = {
				"name=",
				"time start=",
				"time end=",
				"subjects="
		};*/
		
		setDetails( profDetails, "proflist.dat" );
	}
	public static void setCourseDetails ( String courseDetails[] ) throws IOException {
		/*String label[] = {
				"id=",
				"description=",
				"type=",
				"units="
		};*/
		
		setDetails( courseDetails, "courselist.dat" );
	}
	public static void setRoomDetails ( String roomDetails[] ) throws IOException {
		setDetails( roomDetails, "roomlist.dat" );
	}
	public static void setSections ( String sections[] ) throws IOException {
		setSections(sections, "sectionlist.dat");
	}
	
	public static void setCSCourses( String details[], int sem, int year ) throws IOException {
		setCourse( details, sem, year, 0 );
	}
	public static void setITCourses( String details[], int sem, int year ) throws IOException {
		setCourse( details, sem, year, 4 );
	}
	public static void setISCourses( String details[], int sem, int year ) throws IOException {
		setCourse( details, sem, year, 8 );
	}
	
	
	private static int lineCnt( File file ) throws IOException {
		int cnt = 0;
		BufferedReader inFile = new BufferedReader( 
	new FileReader( file ) );
		
		String line = inFile.readLine();
		while ( line != null )
		{
			cnt++;
			line = inFile.readLine();
		}
		
		inFile.close();
		return cnt;
	}
	
	private static String[] getYearlist( String first, int sem, int major ) throws IOException {
		String temp[];
		String list[] = new String[4];
		int num;
		StringTokenizer st;
		
		if ( sem == 1 )
			temp = getlist( "sem1courses.dat", first, false );
		else if ( sem == 2 )
			temp = getlist( "sem2courses.dat", first, false );
		else
			return list;
		
		for ( num = 0; num < 4 && temp.length > 0; num++ ) {
			st = new StringTokenizer( temp[num + major], "=" );
			if ( st.hasMoreTokens() ) {
				if ( st.hasMoreTokens() )
					st.nextToken();
				if ( st.hasMoreTokens() )
					list[num] = st.nextToken();
			}
		}
		return list;
	}
	private static String[] getlist( String fname, String first, boolean listonly ) throws IOException {
		File file = new File( fname );
		String list[];
		int num;
		
		if ( !file.exists() ) {
			BufferedWriter fw = new BufferedWriter( new FileWriter ( fname ) );
			fw.close();
		}
		BufferedReader inFile = new BufferedReader( 
		new FileReader( file ) );
		
		if ( first.equals("" )) {
			list = new String [lineCnt( file )];
			num = 0;
		}
		else {
			list = new String [lineCnt( file ) + 1];
			list[0] = first;
			num = 1;
		}
		
		String line = inFile.readLine();
		while ( line != null ) {
			list[num] = line.toString();
			num++;
			line = inFile.readLine();
		}
	
		inFile.close();
		
		if ( !fname.equals( "sectionlist.dat" ) && listonly ) {
			for ( num = 0; num < list.length; num++ ) {
				StringTokenizer st = new StringTokenizer( list[num], "," );
				list[num] = st.nextToken();
			}
		}
		return list;
	}
	private static int getlistcount( String fname ) throws IOException {
		File file = new File( fname );
		StringTokenizer st;
		int count = 0;
		
		if ( !file.exists() )
		{
			BufferedWriter fw = new BufferedWriter( new FileWriter ( fname ) );
			fw.close();
		}
		BufferedReader inFile = new BufferedReader(
		 new FileReader( file ) );
		
		String line = inFile.readLine();
		while ( line != null ) {
			if ( fname.equals( "sectionlist.dat" ) ) {
				st = new StringTokenizer( line, " " );
				while ( st.hasMoreTokens()) {
					count++;
					st.nextToken();
				}
			}
			else {
				count++;
			}
			line = inFile.readLine();
		}
	
		inFile.close();
		return count;
	}
	private static String getDetails( String name, String fname ) throws IOException {
		String details[] = getlist( fname, "", false );
		StringTokenizer st;
		int num;
		
		for ( num = 0; num < details.length; num++ ) {
			st = new StringTokenizer( details[num], "," );
			if ( name.equals( st.nextToken() ) ) {
				return details[num];
			}
		}
		return "";
	}
	
	private static void setSections( String sections[], String fname ) throws IOException {
		BufferedWriter pw = new BufferedWriter ( new FileWriter ( fname, false ) );
		int num;
		for ( num = 0; num < sections.length; num++ ) {
			pw.write( sections[num] );
			pw.newLine();
		}
		pw.close();
	}
	private static void setDetails( String details[], String fname ) throws IOException {
		BufferedWriter pw;
		int num, x;
		String list[] = getlist( fname, "", false );
		StringTokenizer st;
		
		for ( num = 0; num < list.length; num++ ) {
			st = new StringTokenizer( list[num], "," );
			
			if ( details[0].equals( st.nextToken() ) ) {
				list[num] = "";
				for ( x = 0; x < details.length; x++ ) {
					list[num] = list[num] + details[x] + ",";
				}
				
				pw = new BufferedWriter ( new FileWriter ( fname, false ) );
				for ( x = 0; x < list.length; x++ ) {
					pw.write( list[x] );
					pw.newLine();
				}
				pw.close();
				return;
			}
		}
		
		pw = new BufferedWriter( new FileWriter ( fname, true ) );
		
		for ( num = 0; num < details.length; num++ ) {
			pw.write( details[num] );
			if ( num + 1 < details.length ) {
				pw.write( ',' );
			}
		}
		pw.newLine();
		pw.close();
		return;
	}
	private static void setCourse( String details[], int sem, int year, int major ) throws IOException {
		String temp = "";
		String list[];
		String fname;
		StringTokenizer st;
		int num;
		BufferedWriter bw;
		
		if ( sem == 1 )
			fname = "sem1courses.dat";
		else if ( sem == 2 )
			fname = "sem2courses.dat";
		else
			return;
		
		list = getlist( fname, "", false );
		
		for ( num = 0; num < details.length; num++ ) {
			if ( !details[num].equals( "" ) ) {
				temp += details[num];
				if ( num + 1 < details.length )
					temp += ",";
			}
		}
		
		num = ( year - 1 ) + major;
		st = new StringTokenizer( list[num], "=" );
		list[num] = st.nextToken() + "=" + temp;
		
		bw = new BufferedWriter( new FileWriter ( fname, false ) );
		for ( num = 0; num < list.length; num++ ) {
			bw.write( list[num] );
			bw.newLine();
		}
		bw.close();
	}

	private static void remove( String name, String fname ) throws IOException{
		String list[] = getlist( fname, "", false );
		StringTokenizer st;
		BufferedWriter bw;
		int num, x;
		
		for ( num = 0; num < list.length; num++ ) {
			st = new StringTokenizer( list[num], "," );
			if ( name.equals( st.nextToken() ) ) {
				break;
			}
		}
		bw = new BufferedWriter( new FileWriter ( fname, false ) );
		for ( x = 0; x < list.length; x++ ) {
			if ( x != num ) {
				bw.write( list[x] );
				bw.newLine();
			}
		}
		bw.close();
	}
	
	
}