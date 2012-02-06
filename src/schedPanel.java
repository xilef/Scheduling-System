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
public class schedPanel extends JPanel{
	int d = 0;
	int rmNo = 0;
	int secIndex = 0;
	static cellPanels cells[][];
	JLabel days[] = new JLabel[6];
	JLabel times[] = new JLabel[13];
	JLabel label;
	JSeparator jo = new JSeparator();
	String day[] = { "MON" ,"TUE", "WED", "THU", "FRI", "SAT" };
	String time[] = { " 7 -  8"," 8 -  9"," 9 - 10","10 - 11","11 - 12","12 -  1"," 1 -  2"," 2 -  3"," 3 -  4"," 4 -  5"," 5 -  6"," 6 -  7"," 7 -  8" };
	String timeOut[] = { "7:00 -  8:00","8:00 -  9:00","9:00 - 10:00","10:00 - 11:00","11:00 - 12:00","12:00 -  1:00","1:00 - 2:00","2:00 - 3:00","3:00 - 4:00","4:00 - 5:00","5:00 - 6:00","6:00 - 7:00","7:00 - 8:00" };
	String allBlocks[];
	String sched[][][];
	String pWarning = "";
	boolean secnull[];
	/*
	 * Constructor
	 */
	public schedPanel() {
		setLayout( null );
		setVisible( true );
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
				cells[x][y].setVisible( j );
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public void placeCells(int z){
		if (z == -1)
			initializeCells();
		else{
			
			for (int x = 0; x < 13; x++) {
				for (int y = 0; y < d; y++) {
					if( sched[secIndex][x][y]!= null ){
						StringTokenizer st = new StringTokenizer( sched[secIndex][x][y],":" );
						if (st.hasMoreTokens()){
							String rm = st.nextToken();
							String cd = st.nextToken();
							String pf = st.nextToken();
							setCellData(x, y, rm, cd, pf, secIndex);
							
							for (int a = 0; a < SchedulerBeta.sc.unassigned.size(); a++) {
								String temp = SchedulerBeta.sc.unassigned.get(a);
								StringTokenizer s = new StringTokenizer(temp, ":");
								int section = Integer.valueOf(s.nextToken());
								String code = s.nextToken();
								if(section == secIndex){
									SchedulerBeta.bPanel.setTableData("Incomplete Assignment", "", code);
								}
							}
							}
						}
					else{
						cells[x][y].settext("");
						cells[x][y].setBorder(new TitledBorder(""));
					}
				}
			}
		}//else
	}
	
	public void initializeCells(){
		for (int x = 0; x < 13; x++) {
			for (int y = 0; y < d; y++) {
				cells[x][y] = new cellPanels ("");
				cells[x][y].setBorder(new TitledBorder("")); 
				addComponent( this, cells[x][y], 80+(y*125),50+(x*63),105,55);
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public void setCellData(int x, int y, String rm, String cCode, String prof, int secIndex){
		pWarning = prof;
		SchedulerBeta.bPanel.setTableData("0", prof, cCode);
		for (int a = 0; a < SchedulerBeta.sc.unassigned.size(); a++) {
			String temp = SchedulerBeta.sc.unassigned.get(a);
			StringTokenizer st = new StringTokenizer(temp, ":");
			int section = Integer.valueOf(st.nextToken());
			String code = st.nextToken();
			if(section == secIndex){
				SchedulerBeta.bPanel.setTableData("Incomplete Assignment", "", code);
			}
		}
		
		sched[secIndex][x][y] = null;
		if ( rm.equals("") || cCode.equals("") )
			sched[secIndex][x][y] = null;
		else {
			sched[secIndex][x][y] = rm+":"+cCode+":"+prof;
		}

		cells[x][y].settext(cCode);
		cells[x][y].setBorder(new TitledBorder("Rm: "+rm));
	}

	public void saveSched(String fname, int sem) throws IOException{
		BufferedWriter pw = new BufferedWriter ( new FileWriter ( fname, false ) );
		pw.write("Semester: "+sem+" SY: "+SchedulerBeta.sy+"\n");
		for (int x = 0; x < allBlocks.length; x++){
			if (d==5)
				pw.write("Section: "+allBlocks[x]+",TUE,WED,THU,FRI,SAT"+"\n");
			else
				pw.write("Section: "+allBlocks[x]+",MON,TUE,WED,THU,FRI,SAT"+"\n");
			for(int z=0; z<13; z++){
				pw.write(timeOut[z]+", ");
				for (int y = 0; y < 5; y++){
					if (y!=4){
						if (sched[x][z][y]==null)
							pw.write(", ");
						else
							pw.write(sched[x][z][y]+", ");
						}
					else
						{
						if (sched[x][z][y]==null)
							pw.write("\n");
						else
							pw.write(sched[x][z][y]+"\n");
						}
				}
			}
			pw.write("\n");
		}
		pw.close();
	}
	
	public void setSection( String secName ){
		int x;
		if (secName.equals(""))
			placeCells(-1);
		else{
			for( x = 0; x < allBlocks.length; x++ ){
				if (secName.equals(allBlocks[x])){
					if (!secnull[x])
						secnull[x] = true;
					secIndex = x;
					x = allBlocks.length;
				}
			}
			placeCells(secIndex);
		}
	}
	
	public void startSched(String s, String m[]){
		d = Integer.parseInt(s);
		allBlocks = m;
		secnull = new boolean[allBlocks.length];
		for (int mel = 0; mel < allBlocks.length; mel++ ){
			secnull[mel] = false;
		}
		
		sched = new String[allBlocks.length][13][d];
		for ( int j = 0; j < 6; j++ ) {
			days[j] = new JLabel( day[j] );
		}
		
		for ( int j = 0; j<13; j++){
			times[j] = new JLabel( time[j] );
		}
		cells = new cellPanels[13][d];
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
		schedPanel a = new schedPanel();
		JFrame b = new JFrame();
		a.startSched("5",null);
		a.setVisible(true);
		b.setContentPane(a);
		b.setVisible(true);
	}
	
}
