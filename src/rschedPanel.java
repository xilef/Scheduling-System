import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

@SuppressWarnings( "serial" )
public class rschedPanel extends JPanel{
	int d = 0;
	int rmNo = 0;
	int rIndex = 0, rHolder = -1;
	static cellPanels rcells[][];
	JLabel days[] = new JLabel[6];
	JLabel times[] = new JLabel[13];
	JLabel label;
	JSeparator jo = new JSeparator();
	String day[] = { "MON" ,"TUE", "WED", "THU", "FRI", "SAT" };
	String time[] = { " 7 -  8"," 8 -  9"," 9 - 10","10 - 11","11 - 12","12 -  1"," 1 -  2"," 2 -  3"," 3 -  4"," 4 -  5"," 5 -  6"," 6 -  7"," 7 -  8" };
	String timeOut[] = { "7:00 -  8:00","8:00 -  9:00","9:00 - 10:00","10:00 - 11:00","11:00 - 12:00","12:00 -  1:00","1:00 - 2:00","2:00 - 3:00","3:00 - 4:00","4:00 - 5:00","5:00 - 6:00","6:00 - 7:00","7:00 - 8:00" };
	String allRooms[];
	String rSched[][][];
	double ctr = 0;
	boolean secnull[];
	/*
	 * Constructor
	 */
	public rschedPanel() {
		setLayout( null );
		setVisible( true );
		String roomlist[] = new String[0];
		
		try {
			roomlist = file_handle.getRoomlist( "" );
		} catch ( IOException e2 ) {
			e2.printStackTrace();
			return;
		}
		
		allRooms = roomlist;
		
		secnull = new boolean[allRooms.length];
		for (int mel = 0; mel < allRooms.length; mel++ ){
			secnull[mel] = false;
		}
	}
	
	public void seejo(boolean x){
		jo.setVisible( x );
	}
	
	public void seedays(boolean x){
		days[0].setVisible( x );
		days[1].setVisible( x );
		days[2].setVisible( x );
		days[3].setVisible( x );
		days[4].setVisible( x );
		days[5].setVisible( x );
	}
	
	public void seetime(boolean x){
		for ( int j = 0; j<13; j++){
			times[j].setVisible( x );
		}
	}
	
	public void seecells(boolean j){
		for (int x = 0; x < 13; x++) {
			for (int y = 0; y < d; y++) {
				rcells[x][y].setVisible( j );
			}
		}
	}
	
	public void placeCells(int z){
		if (z == -1)
			initializeCells();
		else{
			
			for (int x = 0; x < 13; x++) {
				for (int y = 0; y < d; y++) {
					if( rSched[rIndex][x][y]!= null ){
						StringTokenizer st = new StringTokenizer( rSched[rIndex][x][y],":" );
						if (st.hasMoreTokens())
							setCellData( x,y,allRooms[rIndex],st.nextToken(),st.nextToken());
						}
					else{
						rcells[x][y].settext("");
						rcells[x][y].setBorder(new TitledBorder(""));
					}
				}
			}
		}//else
	}
	
	public void initializeCells(){
		for (int x = 0; x < 13; x++) {
			for (int y = 0; y < d; y++) {
				rcells[x][y] = new cellPanels ("");
				rcells[x][y].setBorder(new TitledBorder("")); 
				addComponent( this, rcells[x][y], 80+(y*125),50+(x*63),105,55);
			}
		}
	}
	
	public void setCellData(int x, int y, String rm, String cCode, String section){
		if ( rm.length() == 0 )
			return;
		
		if (rm.length() > 0 && rm.charAt(0) == ' '){
			rm = rm.substring(1);
		}
		for(int z = 0; z < allRooms.length; z++ ){
			if (rm.equals(allRooms[z])){
				if (!secnull[z])
					secnull[z] = true;
				rIndex = z;
				z = allRooms.length;
			}
		}
		
		
		if ( rHolder==-1 )
			rHolder = rIndex;
		
		rSched[rIndex][x][y] = null;
		if ( cCode.equals("") || section.equals("") )
			rSched[rIndex][x][y] = null;
		else{
			rSched[rIndex][x][y] = cCode+":"+section;
			if(SchedulerBeta.isRTab)
				ctr++;

			String temp = Double.toString(((ctr / (13 * d)) * 100));
			temp = temp.substring(0, temp.indexOf('.') + 2);
			SchedulerBeta.roomRemarks.setText("Room Utility Rate = "+temp+ "%");
		}
		
		rcells[x][y].settext(cCode);
		rcells[x][y].setBorder(new TitledBorder(section));
		
		if (rHolder != rIndex){
			rHolder = rIndex;
			SchedulerBeta.roomRemarks.setText("");
		}
	}
	
	public void saveSched(String fname) throws IOException{
		BufferedWriter pw = new BufferedWriter ( new FileWriter ( fname, false ) );

		for (int x = 0; x < allRooms.length; x++){
			if (d==5){
				pw.write("Room: "+allRooms[x]+",TUE,WED,THU,FRI,SAT");
				pw.newLine();	
			}
			else{
				pw.write(x+1 + ") "+allRooms[x]+",MON,TUE,WED,THU,FRI,SAT");
				pw.newLine();
			}
			for(int z=0; z<13; z++){
				pw.write(timeOut[z]+", ");
				for (int y = 0; y < 5; y++){
					if (y!=4){
						if (rSched[x][z][y]==null)
							pw.write(", ");
						else
							pw.write(rSched[x][z][y]+", ");
						}
					else
						{
						if (rSched[x][z][y]==null)
							pw.newLine();
						else{
							pw.write(rSched[x][z][y]);
							pw.newLine();
							}
						}
				}
			}
			pw.newLine();
		}
		pw.close();
	}

/*	public void saveSched(String fname){
		for (int x = 0; x < allRooms.length; x++){
			if (d==5)
				System.out.print("Section: "+allRooms[x]+",TUE,WED,THU,FRI,SAT"+"\n");
			else
				System.out.print(x+1 + ") "+allRooms[x]+",MON,TUE,WED,THU,FRI,SAT"+"\n");
			for(int z=0; z<13; z++){
				System.out.print(timeOut[z]+",");
				for (int y = 0; y < 5; y++){
					if (y!=4){
						if (rSched[x][z][y]==null)
							System.out.print(",");
						else
							System.out.print(rSched[x][z][y]+",");
						}
					else
						{
						if (rSched[x][z][y]==null)
							System.out.print("\n");
						else
							System.out.print(rSched[x][z][y]+"\n");
						}
				}
			}
			System.out.print("\n");
		}
	}
	*/
	public void setRoom( String rName ){
		int x;
		if (rName.equals(""))
			placeCells(-1);
		else{
			for( x = 0; x < allRooms.length; x++ ){
				if (rName.equals(allRooms[x])){
					if (!secnull[x])
						secnull[x] = true;
					rIndex = x;
					x = allRooms.length;
				}
			}
			placeCells(rIndex);
			ctr = 0;
		}
	}
	
		
	public void startSched(String s, String m[]){
		d = Integer.parseInt(s);

		rSched = new String[allRooms.length][13][d];
		for ( int j = 0; j < 6; j++ ) {
			days[j] = new JLabel( day[j] );
		}
		
		for ( int j = 0; j<13; j++){
			times[j] = new JLabel( time[j] );
		}
		rcells = new cellPanels[13][d];
		if (d==6)
			{
			setPreferredSize(new Dimension(880,900));
			addComponent( this, jo, 80,35,745,1 );
			addComponent( this, days[0], 123,10, 100, 20 );
			addComponent( this, days[1], 248,10, 100, 20 );
			addComponent( this, days[2], 373,10, 100, 20 );
			addComponent( this, days[3], 498,10, 100, 20 );
			addComponent( this, days[4], 623,10, 100, 20 );
			addComponent( this, days[5], 749,10, 100, 20 );
			}
		else
			{
			setPreferredSize(new Dimension(685,900));
			addComponent( this, jo, 80,35,602,1 );
			addComponent( this, days[1], 113,10, 100, 20 );
			addComponent( this, days[2], 238,10, 100, 20 );
			addComponent( this, days[3], 363,10, 100, 20 );
			addComponent( this, days[4], 488,10, 100, 20 );
			addComponent( this, days[5], 613,10, 100, 20 );
			}
		for ( int j = 0; j<13; j++){
			addComponent( this, times[j], 15,50+(j*63),150,20);
		}
		initializeCells();
		seecells( false );
		seedays( false );
		seetime( false );
		seejo( false );
		
	}
	
	private static void addComponent( Container container, Component c, int x, int y, int width, int height ) {
		c.setBounds( x, y, width, height );
		container.add( c );
	}
	
	public static void main(String args[]) {
		pschedPanel a = new pschedPanel();
		JFrame b = new JFrame();
		a.startSched("5",null);
		a.setVisible(true);
		b.setContentPane(a);
		b.setVisible(true);
	}
	
}
