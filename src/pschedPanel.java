import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;


@SuppressWarnings( "serial" )
public class pschedPanel extends JPanel{
	int d = 0;
	int rmNo = 0;
	int pIndex = 0;
	static cellPanels pcells[][];
	JLabel days[] = new JLabel[6];
	JLabel times[] = new JLabel[13];
	JLabel label;
	JSeparator jo = new JSeparator();
	String day[] = { "MON" ,"TUE", "WED", "THU", "FRI", "SAT" };
	String time[] = { " 7 -  8"," 8 -  9"," 9 - 10","10 - 11","11 - 12","12 -  1"," 1 -  2"," 2 -  3"," 3 -  4"," 4 -  5"," 5 -  6"," 6 -  7"," 7 -  8" };
	String timeOut[] = { "7:00 -  8:00","8:00 -  9:00","9:00 - 10:00","10:00 - 11:00","11:00 - 12:00","12:00 -  1:00","1:00 - 2:00","2:00 - 3:00","3:00 - 4:00","4:00 - 5:00","5:00 - 6:00","6:00 - 7:00","7:00 - 8:00" };
	String allProf[];
	String psched[][][];
	int hourCtr = 0, pHolder = -1, ctr = 0, loadCtr = 0;
	boolean first = true;
	ArrayList <String> sect = new ArrayList<String>();
	ArrayList <String> subj = new ArrayList<String>();
	ArrayList <String> load = new ArrayList<String>();
	/*
	 * Constructor
	 */
	public pschedPanel() {
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
				pcells[x][y].setVisible( j );
			}
		}
	}
	
	public void placeCells(int z){
		if (z == -1)
			initializeCells();
		else{
			
			for (int x = 0; x < 13; x++) {
				for (int y = 0; y < d; y++) {
					if( psched[pIndex][x][y]!= null ){
						StringTokenizer st = new StringTokenizer( psched[pIndex][x][y],":" );
						if (st.hasMoreTokens())
							setCellData( x,y,st.nextToken(),st.nextToken(),st.nextToken(), allProf[pIndex]);
						}
					else{
						pcells[x][y].settext("");
						pcells[x][y].setBorder(new TitledBorder(""));
					}
				}
			}
		}//else
	}
	
	public void initializeCells(){
		for (int x = 0; x < 13; x++) {
			for (int y = 0; y < d; y++) {
				pcells[x][y] = new cellPanels ("");
				pcells[x][y].setBorder(new TitledBorder("")); 
				addComponent( this, pcells[x][y], 80+(y*125),50+(x*63),105,55);
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public void setCellData(int x, int y, String rm, String cCode, String section, String prof/*, boolean flag*/){
		if (prof.equals("...") || prof.equals(""))
			return;
		for(int z = 0; z < allProf.length; z++ ){
			if (prof.equals(allProf[z])){
				pIndex = z;
				z = allProf.length;
			}
		}
		if (pHolder == -1)
			pHolder = pIndex;
		
		psched[pIndex][x][y] = null;
		if ( rm.equals("") || cCode.equals("") )
			psched[pIndex][x][y] = null;
		else{
			String temp = section + " ("+cCode+") ";
			hourCtr++;
			if (!sect.contains(temp))
				sect.add(temp);
			if (!subj.contains(cCode))
				subj.add(cCode);
			psched[pIndex][x][y] = rm+":"+cCode+":"+section;
		}
		pcells[x][y].settext(section+" : "+cCode);
		pcells[x][y].setBorder(new TitledBorder("Rm: "+rm));
		
		SchedulerBeta.profRemarks.setText("Sections handled:\n");
		int z = 0;
		while(z<sect.size()){
			SchedulerBeta.profRemarks.append("\t> " + sect.get(z) +"\n");
			z++;
		}
		SchedulerBeta.profRemarks.append("Subject/s taught:\n");
		z = 0;
		while(z<subj.size()){
			SchedulerBeta.profRemarks.append("\t> " + subj.get(z) +"\n");
			z++;
		}
		
		if (SchedulerBeta.isTab){
			ctr++;
		}
		if ( ctr != hourCtr){
			SchedulerBeta.profRemarks.append("Total hours of teaching per week (Units Handled):\n"+"\t"+(ctr)+"\n");
			loadCtr = ctr - 24;
			SchedulerBeta.sc.unitLecCt[pIndex] = ctr;
		}
		else{
			SchedulerBeta.profRemarks.append("Total hours of teaching per week (Units Handled):\n"+"\t"+(hourCtr)+"\n");
			loadCtr = hourCtr - 24;
			SchedulerBeta.sc.unitLecCt[pIndex] = hourCtr;
		}
		if (loadCtr > 0)
			SchedulerBeta.profRemarks.append("Overload:\n\t>"+loadCtr+" units overload.");
		
		SchedulerBeta.sc.crsCt[pIndex] = subj.size();
		
		z = 0;
		while(z<load.size()){
			SchedulerBeta.profRemarks.append("\t> "+load.get(z)+" \n");
			z++;
		}
		
		if (pHolder != pIndex){
			pHolder = pIndex;
			hourCtr=0;
			subj.clear();
			sect.clear();
			load.clear();
			SchedulerBeta.profRemarks.setText("");
		}
	}

	public void saveSched(String fname) throws IOException{
		BufferedWriter pw = new BufferedWriter ( new FileWriter ( fname, false ) );

		for (int x = 0; x < allProf.length; x++){
			if (d==5){
				pw.write("Name: "+allProf[x]+",TUE,WED,THU,FRI,SAT");
				pw.newLine();
			}
			else{
				pw.write(x+1 + ") "+allProf[x]+",MON,TUE,WED,THU,FRI,SAT");
				pw.newLine();
			}
			for(int z=0; z<13; z++){
				pw.write(timeOut[z]+", ");
				for (int y = 0; y < 5; y++){
					if (y!=4){
						if (psched[x][z][y]==null)
							pw.write(", ");
						else
							pw.write(psched[x][z][y]+", ");
						}
					else
						{
						if (psched[x][z][y]==null)
							pw.newLine();
						else{
							pw.write(psched[x][z][y]);
							pw.newLine();
						}
						}
				}
			}
			pw.newLine();
		}
		pw.close();
	}
	
	
	public void setProf( String pName ){
		//initializeCells();
		
			
		int x;
		if (pName.equals(""))
			placeCells(-1);
		else{
			for( x = 0; x < allProf.length; x++ ){
				if (pName.equals(allProf[x])){
					pIndex = x;
					x = allProf.length;
				}
			}
			
		}
		ctr = 0;
		placeCells(pIndex);
		SchedulerBeta.profRemarks.setCaretPosition(0);
		//S51.printTool.setEnabled( true );
		//S51.printDoc.setEnabled( true );
/*		if( S51.isTab )
			S51.isTab = false;*/
	}
	
	public void startSched(String s, String m[]){
		d = Integer.parseInt(s);
		allProf = m;

		psched = new String[automateSched.p.length][13][d];
		for ( int j = 0; j < 6; j++ ) {
			days[j] = new JLabel( day[j] );
		}
		
		for ( int j = 0; j<13; j++){
			times[j] = new JLabel( time[j] );
		}
		pcells = new cellPanels[13][d];
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
