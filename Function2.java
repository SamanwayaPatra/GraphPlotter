import java.io.*;
class Function2
{
public double f(double x,double t)
{
return sffsfsfsf.f(x,t);
}
public Function2(String eqn){
String mtd="class sffsfsfsf{private static double e=Math.E,E=Math.E,pi=Math.PI,Pi=Math.PI,pI=Math.PI,PI=Math.PI;private static double log(double x){return Math.log(x);}private static double sin(double x){return Math.sin(x);}private static double cos(double x){return Math.cos(x);}private static double tan(double x){return Math.tan(x);}static double f(double x,double t){return "+eqn+";}}";
try{
FileOutputStream fout=new FileOutputStream("sffsfsf.java");
BufferedOutputStream bout=new BufferedOutputStream(fout);
for(int i=0;i<mtd.length();i++)
bout.write(mtd.charAt(i));
bout.close();
fout.close();
Process p=Runtime.getRuntime().exec("javac sffsfsf.java");
p.waitFor();
}catch(Exception e){return;}
}
}