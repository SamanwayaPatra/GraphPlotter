import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class Graph implements ActionListener,MouseMotionListener,MouseWheelListener,Runnable
{
JTextField jtf,crd1,crd2,crd3;
PaintPan pp;
Thread thr;
boolean b=true;
public static void main(String args[])
{
SwingUtilities.invokeLater(new Runnable(){public void run(){new Graph();}});
}
Graph()
{
JFrame jfr=new JFrame("Graph Ploter");
jfr.setExtendedState(JFrame.MAXIMIZED_BOTH);
jfr.setLayout(new BorderLayout());
jfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
JPanel tmp=new JPanel();
tmp.setLayout(new GridLayout(1,2));
JPanel scp=new JPanel();
scp.setLayout(new GridLayout(1,3));
jtf=new JTextField(1000);
jtf.addActionListener(this);
crd1=new JTextField(1000);
crd2=new JTextField(1000);
crd3=new JTextField(1000);
crd1.setEditable(false);
crd2.setEditable(false);
crd3.setEditable(false);
scp.add(crd1);
scp.add(crd2);
scp.add(crd3);
tmp.add(jtf);
tmp.add(scp);
jfr.add(tmp,BorderLayout.NORTH);
pp=new PaintPan(jtf,this);
pp.addMouseMotionListener(this);
pp.addMouseWheelListener(this);
jfr.add(pp,BorderLayout.CENTER);
jfr.setVisible(true);
thr=new Thread(this);
}
public void run()
{
while(true)
pp.repaint();
}
public void mouseWheelMoved(MouseWheelEvent e)
{
int nt=e.getWheelRotation();
double mtp=Math.pow(1.1,-nt);
pp.oX=(int)(pp.mx+(pp.oX-pp.mx)*mtp);
pp.oY=(int)(pp.my+(pp.oY-pp.my)*mtp);
pp.scale*=mtp;
pp.repaint();
}
public void actionPerformed(ActionEvent ae)
{
if(b)
thr.start();
b=false;
}
public void mouseDragged(MouseEvent me){
int a=me.getX(),b=me.getY();
pp.oX+=a-pp.mx;pp.oY+=b-pp.my;
pp.mx=a;pp.my=b;
pp.repaint();
}
public void mouseMoved(MouseEvent me)
{
double dig=Math.pow(10,Math.round(Math.log10(pp.scale)));
pp.mx=me.getX();
pp.my=me.getY();
long m1=Math.round((pp.mx-pp.oX)/pp.scale*dig),m2=Math.round((pp.oY-pp.my)/pp.scale*dig);
double mm1=m1/dig,mm2=m2/dig;
crd1.setText(((mm1==(int)mm1)?(int)mm1:mm1)+"");
crd2.setText(((mm2==(int)mm2)?(int)mm2:mm2)+"");
}
}
class PaintPan extends JPanel
{
String pre="";
Function2 fn;
Insets ins;
int oX=0,oY=0,mx=0,my=0;
double scale=100;
JTextField jtf;
Graph grp;
long intm=0;
PaintPan(JTextField tf,Graph g)
{
jtf=tf;
oX=getWidth()/2;
oY=getHeight()/2;
grp=g;
}
protected void paintComponent(Graphics g)
{
super.paintComponent(g);
ins=getInsets();
int height=getHeight(),width=getWidth();
g.setColor(new Color(0,255,0));
g.drawLine(oX,-10,oX,height+10);
g.drawLine(-10,oY,width+10,oY);
g.setColor(new Color(255,0,0));
String eqn=jtf.getText();
if(eqn.equals(""))
return;
if(!pre.equals(eqn)){
fn=new Function2(eqn);
pre=eqn;
intm=System.nanoTime();
}
int prX=0,prY=0;boolean b=false,otr=false;
double ct=(System.nanoTime()-intm)/(1e+9);
grp.crd3.setText(""+ct);
for(int i=-10;i<width+10;i++)
{
double y=fn.f((i-oX)/scale,ct);
if(i==-10 || b){
prX=i;prY=(int)Math.round(oY-y*scale);if(!b)continue;b=false;}
if(Double.isInfinite(y) || Double.isNaN(y))
{b=true;continue;}
if(Math.abs(y-(oY-prY)/scale)<80)
g.drawLine(prX,prY,i,(int)Math.round(oY-y*scale));
if(otr && !((oY-y*scale)<-10 ||(oY-y*scale)>height+10))
otr=false;
if(((oY-y*scale)<-10 ||(oY-y*scale)>height+10) && otr)
{prX=i;prY=(int)Math.round(oY-y*scale);continue;}
otr=(oY-y*scale)<-10 ||(oY-y*scale)>height+10;
if(Double.isFinite(y) && !Double.isNaN(y))
g.drawLine(prX,prY,i,(int)Math.round(oY-y*scale));
prX=i;prY=(int)Math.round(oY-y*scale);
}
}
}