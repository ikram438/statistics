package statistics;

import java.text.DecimalFormat;

import java.util.*;

import java.util.ArrayList;

import java.util.Scanner;

import statistics.Statistic;

public class Statistics {

  public static void main(String[] args) {

   Scanner sc=new Scanner(System.in);  

   DecimalFormat df= new DecimalFormat("0.00");

    Statistic obj=new Statistic();

    

  System.out.println("1:group data\n2:ungrouped data \n  with frequency\n3:ungrouped data :");

   String op= sc.next();

   

   

  if(op.equals("1"))

  {

   System.out.println("enter lower boundry of data \nsperated with coma:");

   obj.inputlower(sc.next());

   System.out.println("enter upper boundry of data \nsperated with coma:"); 

   obj.inputUpper(sc.next());

   System.out.println("enter frequency \nsperated with coma     :");  

   obj.inputfrequency(sc.next());  

   }//end if

   else if(op.equals("2"))

   {

    System.out.println("enter data sperated with coma     :"); 

   obj.inputUpper(sc.next());

   System.out.println("enter frequency data sperated with coma:");  

   obj.inputfrequency(sc.next());

   

   }//end else if

   else

   {

   

   System.out.println("enter data sperated with coma:"); 

   obj.inputUpper(sc.next());

   }//end else        

  

   System.out.println("mean = "+df.format(obj.mean()));

 

   System.out.println("median ="+df.format(obj.median()));

   

   System.out.println("mode = "+df.format(obj.mode()));

  

   System.out.println("variance = "+df.format(obj.variance()));

   

   System.out.println("standard deviation = "+df.format(obj.standardDeviation()));

 

 System.out.println("coefficient of variation = "+df.format(obj.coefficientOfVariation()));

   }

}

class Statistic{

private ArrayList<Integer>frequency;

private ArrayList<Integer> cumlFreq;

private ArrayList<Double> upper;

private ArrayList<Double> lower;

public Statistic()

{

  

  frequency = new ArrayList<Integer>();

  cumlFreq  = new ArrayList<Integer>();

  upper = new ArrayList<Double>();

  lower = new ArrayList<Double>();

}

private ArrayList<Double> stringToList(String strData)

{

ArrayList data=new ArrayList ();

   double value=0.0;

  while(true)

  {

  if(strData.indexOf(",")==-1)

  {

   value=Double.parseDouble(strData.substring(0,strData.length())); 

  data.add(value); 

  break;

  }

  else 

  {

 value=Double.parseDouble(strData.substring(0,strData.indexOf(",")));

  data.add(value); 

  strData=strData.substring(strData.indexOf(",")+1,strData.length());

  }   

  } 

 return data; 

}

public void inputUpper(String strData)

{

  this.upper=stringToList(strData);

}

public void inputlower(String strData)

{  

 this.lower=stringToList(strData);

}

public void inputfrequency(String strFreq)

{

  for(Double value:stringToList(strFreq))

  this.frequency.add(value.intValue());

}

public double mean()

{

if(lower.size()==0&& frequency.size()==0)

return meanUnGroup();

else

{

  double sum=0.0;

  int freq=0;

  ArrayList<Double>midpoint=new ArrayList<Double>();

  

  for(int i=0;i<upper.size();i++)

  {

  if(lower.size()>0)

    midpoint.add((upper.get(i)+lower.get(i))/2);

    

    freq+=frequency.get(i);

  }

  for(int i=0;i<upper.size();i++)

  {

  if(lower.size()==0)

  sum+=frequency.get(i)*upper.get(i);

  else

  sum+=frequency.get(i)*midpoint.get(i);

  }

  

  return sum/freq;

  }

}

private double meanUnGroup()

{

  double sum=0.0;

 

  for(int i=0;i<upper.size();i++)

  sum+=upper.get(i);

 

  return sum/upper.size();

}

private int cumlFreq_medIndex()

{

 

cumlFreq.add(frequency.get(0));

for(int i=1; i<frequency.size();i++)

  cumlFreq.add(frequency.get(i)+cumlFreq.get(i-1));

  

double med=  cumlFreq.get(cumlFreq.size()-1)/2.0;

int medFreqIndex=0;

for(int i=0;i<cumlFreq.size();i++)

{

  if(cumlFreq.get(i)>=med && med>=cumlFreq.get(i-1))

  {

  medFreqIndex=i;

  break;

  }

}

return medFreqIndex;

}

public double median()

{

  if(lower.size()==0&& frequency.size()==0)

return medianUnGroup();

else

{

int medFreqIndex=cumlFreq_medIndex();

if(lower.size()==0)

return upper.get(medFreqIndex);

else

{

double med=  cumlFreq.get(cumlFreq.size()-1)/2.0;

double h=upper.get(medFreqIndex)-lower.get(medFreqIndex);

return lower.get(medFreqIndex)+

(h/frequency.get(medFreqIndex))*

(med-cumlFreq.get(medFreqIndex-1));

}

}

}

private double medianUnGroup()

{

  ArrayList<Double> list= sortedList(upper);

  int size= list.size();

  if(size%2==1)

  return list.get(size/2);

  else

  return (list.get(size/2)+list.get((size/2)-1))/2;

}

public double mode()

{

  if(lower.size()==0&& frequency.size()==0)

return modeUnGroup();

else

{

int max=Collections.max(frequency);

int modeIndex=frequency.indexOf(max);

 double f1=0,f2=0;

 if(lower.size()==0)

return upper.get(modeIndex);

 

else

{

double h=upper.get(modeIndex)-lower.get(modeIndex); 

try{ 

 f1=frequency.get(modeIndex)-frequency.get(modeIndex-1);

}catch(Exception e){f1=frequency.get(modeIndex);}

try{

 f2=frequency.get(modeIndex)-frequency.get(modeIndex+1);

}catch(Exception e){f2=frequency.get(modeIndex);}

return lower.get(modeIndex)+(f1/(f1+f2))*h;

}

}

}

private double modeUnGroup()

{

  int m=0,n=m, modeIndex=0;

  for(int i=0;i<upper.size();i++)

  {

    m=0;

    for(int j=0;j<upper.size();j++)

    {

      if(upper.get(i).equals(upper.get(j)))

   m++;

  

    }

    if(m>n)

    {

    n=m;

    modeIndex=i;

    

    }

    }

  

  return upper.get(modeIndex);

}

private ArrayList<Double> sortedList(ArrayList<Double> list)

{

 int minIndex=0;

 double temp=0.0;

 

  for(int i=0;i<list.size();i++)

 {

  minIndex=i;

  

  for(int j=i;j<list.size();j++)

  {

  if(list.get(j)<list.get(minIndex))

    minIndex=j;

  }

  temp=list.get(i);

  list.set(i,list.get(minIndex));

  list.set(minIndex,temp);

}

  return list;  

}

public double variance()

{

if(lower.size()==0&& frequency.size()==0)

return varianceUnGroup();

else

{

  double sum=0.0;

  double sumPow=0.0;

  int n=0;

  ArrayList<Double>midpoint=new ArrayList<Double>();

   

  for(int i=0;i<upper.size();i++)

  {

  if(lower.size()>0)

    midpoint.add((upper.get(i)+lower.get(i))/2);

    

    n+=frequency.get(i);

  }// end for loop

    

  for(int i=0;i<upper.size();i++)

  {

  if(lower.size()==0)

  {

  sum+=frequency.get(i)*upper.get(i);

  sumPow+=frequency.get(i)*Math.pow(upper.get(i),2);

  }//end inner if

  else

 { 

 sum+=frequency.get(i)*midpoint.get(i);

  sumPow+=frequency.get(i)*Math.pow(midpoint.get(i),2);

  }//end inner else 

  }//end for loop

  

  return (sumPow/n)-(Math.pow(sum/n,2));

 }//end outer else

}

public double varianceUnGroup()

{

  double sumPow=0.0;

  double sum=0.0;

   

  for(int i=0;i<upper.size();i++)

  {

  sumPow+=Math.pow(upper.get(i),2);

  sum+=upper.get(i);

  }

  return (sumPow/upper.size())-(Math.pow(sum/upper.size(),2));

}

public double standardDeviation()

{

  return Math.sqrt(variance()) ; 

}

public double coefficientOfVariation()

{ 

  return standardDeviation()/mean()*100 ; 

}

}
