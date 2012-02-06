import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.awt.image.*;
 
@SuppressWarnings("serial")
public class PrintPreviewS extends JPanel implements ActionListener{
  JPanel PC;
  PageFormat PF = new PageFormat();
  double XSC = 1;
  double YSC = 1;
  BufferedImage pcImage;
 
  JPanel hold = new JPanel();
  PreviewPage prp;
 
  ButtonGroup pf = new ButtonGroup();
  JRadioButton pf1;
  JRadioButton pf2;
  JLabel xsl = new JLabel("X Scale:", JLabel.LEFT);
  JTextField xs = new JTextField("1");
  JLabel ysl = new JLabel("Y Scale:", JLabel.LEFT);
  JTextField ys = new JTextField("1");
  JButton preview = new JButton("PREVIEW");
  JButton print = new JButton("PRINT");
  Color bgColor = Color.white;
 
  int pcw, pch;
  double wh, hw;
  
  @SuppressWarnings("static-access")
public PrintPreviewS(JPanel pc){
    PC = pc;
    pcImage = new BufferedImage(pcw = PC.getWidth(), pch = PC.getHeight(),BufferedImage.TYPE_INT_ARGB);
    Graphics g = pcImage.createGraphics();
    PC.paint(g);
    g.dispose();
    wh = (double)pcw / (double)pch;
    hw = (double)pch / (double)pcw;
    
    PF.setOrientation(PF.PORTRAIT);
    prp = new PreviewPage();
 
    pf1 = new JRadioButton("Portrait");
    pf1.setActionCommand("1");
    pf1.setSelected(true);
    pf2 = new JRadioButton("Landscape");
    pf2.setActionCommand("2");
    pf.add(pf1);
    pf.add(pf2);
 
    preview.addActionListener(this);
    print.addActionListener(this);
 
    prp.setBackground(bgColor);
    hold.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    hold.setLayout(new GridBagLayout());
 
    GridBagConstraints c1 = new GridBagConstraints();
 
    c1.insets = new Insets(15, 45, 0, 5);
    c1 = buildConstraints(c1, 0, 0, 2, 1, 0.0, 0.0);
    hold.add(pf1, c1);
 
    c1.insets = new Insets(2, 45, 0, 5);
    c1 = buildConstraints(c1, 0, 1, 2, 1, 0.0, 0.0);
    hold.add(pf2, c1);
 
    c1.insets = new Insets(25, 5, 0, 5);
    c1 = buildConstraints(c1, 0, 2, 1, 1, 0.0, 0.0);
    hold.add(xsl, c1);
 
    c1.insets = new Insets(25, 5, 0, 35);
    c1 = buildConstraints(c1, 1, 2, 1, 1, 0.0, 0.0);
    hold.add(xs, c1);
 
    c1.insets = new Insets(5, 5, 0, 5);
    c1 = buildConstraints(c1, 0, 3, 1, 1, 0.0, 0.0);
    hold.add(ysl, c1);
 
    c1.insets = new Insets(15, 5, 0, 35);
    c1 = buildConstraints(c1, 1, 3, 1, 1, 0.0, 0.0);
    hold.add(ys, c1);
 
    c1.insets = new Insets(25, 35, 0, 35);
    c1 = buildConstraints(c1, 0, 6, 2, 1, 0.0, 0.0);
    hold.add(preview, c1);
 
    c1.insets = new Insets(5, 35, 25, 35);
    c1 = buildConstraints(c1, 0, 7, 2, 1, 0.0, 0.0);
    hold.add(print, c1);
 
    add(hold);
    add(prp);
  }
 
  GridBagConstraints buildConstraints(GridBagConstraints gbc, int gx, int gy,
   int gw, int gh, double wx, double wy){
    gbc.gridx = gx;
    gbc.gridy = gy;
    gbc.gridwidth = gw;
    gbc.gridheight = gh;
    gbc.weightx = wx;
    gbc.weighty = wy;
    gbc.fill = GridBagConstraints.BOTH;
    return gbc;
  }
 
  public class PreviewPage extends JPanel{
    int x1, y1, l1, h1, x2, y2;
    Image image;
    
    public PreviewPage(){
      setPreferredSize(new Dimension(460, 460));
      setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }
 
    @SuppressWarnings("static-access")
	public void paintComponent(Graphics g){
      super.paintComponent(g);
 
      // PORTRAIT
      if(PF.getOrientation() == PF.PORTRAIT){
        g.setColor(Color.black);
        g.drawRect(60, 10, 340, 440);
        
        x1 = (int)Math.rint(((double)PF.getImageableX() / 72) * 40);
        y1 = (int)Math.rint(((double)PF.getImageableY() / 72) * 40);
        l1 = (int)Math.rint(((double)PF.getImageableWidth() / 72) * 40);
        h1 = (int)Math.rint(((double)PF.getImageableHeight() / 72) * 40);
        g.setColor(Color.red);
        g.drawRect(x1 + 60, y1 + 10, l1, h1);
        setScales();
        x2 = (int)Math.rint((double)l1 / XSC);
        y2 = (int)Math.rint(((double)l1 * hw) / YSC);
        
        image = pcImage.getScaledInstance(x2, y2, Image.SCALE_AREA_AVERAGING);
        g.drawImage(image, x1 + 60, y1 + 10, this);
      }
      // LANDSCAPE
      else{
        g.setColor(Color.black);
        g.drawRect(10, 60, PC.getWidth(), PC.getHeight());
        x1 = (int)Math.rint(((double)PF.getImageableX() / 72) * 40);
        y1 = (int)Math.rint(((double)PF.getImageableY() / 72) * 40);
        l1 = (int)Math.rint(((double)PF.getImageableWidth() / 72) * 40);
        h1 = (int)Math.rint(((double)PF.getImageableHeight() / 72) * 40);
        g.setColor(Color.red);
        g.drawRect(x1 + 10, y1 + 60, l1, h1);
        setScales();
        x2 = (int)Math.rint((double)l1 / XSC);
        y2 = (int)Math.rint(((double)l1 * hw) / YSC);
        image = pcImage.getScaledInstance(x2, y2, Image.SCALE_AREA_AVERAGING);
        g.drawImage(image, x1/* + 10*/, y1/* + 60*/, this);
      }
    } 
  }
 
  public void actionPerformed(ActionEvent e){
    if(e.getSource() == preview){
      setProperties();
    }
    if(e.getSource() == print){
      doPrint();
    }
  }
 
  @SuppressWarnings("static-access")
public void setProperties(){
    if(pf1.isSelected()){
      PF.setOrientation(PF.PORTRAIT);
    }
    else if(pf2.isSelected()){
      PF.setOrientation(PF.LANDSCAPE);
    }
    setScales();
    prp.repaint();
  }
 
  public void setScales(){
    try{
      XSC = Double.parseDouble(xs.getText());
      YSC = Double.parseDouble(ys.getText());
    }
    catch (NumberFormatException e) {
    }
  }
 
  public void doPrint(){
    PrintThis();
  }
 
  public void PrintThis(){
    PrinterJob printerJob = PrinterJob.getPrinterJob();
    Book book = new Book();
    book.append(new PrintPage(), PF);
    printerJob.setPageable(book);
    boolean doPrint = printerJob.printDialog();
    if (doPrint) {
      try {
        printerJob.print();
      }
      catch (PrinterException exception) {
        System.err.println("Printing error: " + exception);
      }
    }
  }
 
  //public class PrintPage implements Printable{
  class PrintPage implements Printable{
 
    public int print(Graphics g, PageFormat format, int pageIndex) {
      Graphics2D g2D = (Graphics2D) g;
      g2D.translate(format.getImageableX (), format.getImageableY ());
      System.out.println("get i x " + format.getImageableX ());
      System.out.println("get i x " + format.getImageableY ());
      System.out.println("getx: " + format.getImageableWidth() );
      System.out.println("getx: " + format.getImageableHeight() );
      // scale to fill the page
      double dw = format.getImageableWidth();
      double dh = format.getImageableHeight();
      @SuppressWarnings("unused")
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      setScales();
      double xScale = dw / (1024 / XSC);
      double yScale = dh / (768 / YSC);
      double scale = Math.min(xScale, yScale);
      System.out.println("" + scale);
      g2D.scale( xScale, yScale);
      ((JPanel)PC).paint(g);
      return Printable.PAGE_EXISTS;
    }
 
    public void disableDoubleBuffering(Component c) {
      RepaintManager currentManager = RepaintManager.currentManager(c);
      currentManager.setDoubleBufferingEnabled(false);
    }
 
    public void enableDoubleBuffering(Component c) {
      RepaintManager currentManager = RepaintManager.currentManager(c);
      currentManager.setDoubleBufferingEnabled(true);
    }
  }
}
