import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.Period;
import java.util.*;
 

public class Main extends JFrame implements ActionListener 
{
	JButton selectAge;
	JTextField dateSelected,todaysDate,age;
	Font fo1;
	//static String date;
	static boolean stopFlag = false;
	AgeCalculator child;
	
	Main()
	{
		setLayout(new GridLayout(4,1));
		//setLayout(new FlowLayout(FlowLayout.CENTER,10,30));
		selectAge = new JButton("Select Your Birthday");
		selectAge.addActionListener(this);
		selectAge.setBackground(Color.red);
		
		fo1 =new Font("Times New Roman",Font.BOLD,30);
		
		dateSelected = new JTextField(" ",30);
		dateSelected.setFont(fo1);
		dateSelected.setEditable(false);
		
		todaysDate = new JTextField(" ",30);
		todaysDate.setFont(fo1);
		todaysDate.setEditable(false);
		
		age = new JTextField(" ",30);
		age.setFont(fo1);
		age.setEditable(false);
		
		add(selectAge);
		add(dateSelected);
		add(todaysDate);
		add(age);
		
		age.setVisible(false);
		dateSelected.setVisible(false);
		todaysDate.setVisible(false);
		
		setTitle("Age Calculator");
		setSize(600,600);
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()== selectAge)
		{
			//date="";
			child = new AgeCalculator(this);
			child.start();
			try
			{
				child.join();
			}
			catch(Exception efs){System.out.println("ERRor");}		
			
		}	
	}
	public String calculate(int[] bd , int[] td)
	{
		LocalDate birthdate =  LocalDate.of(bd[2], bd[1], bd[0]);
		LocalDate todayDate =	LocalDate.of(td[2], td[1], td[0]);
		
		
		
	//	System.out.println(birthdate+" "+todayDate);
		
		Period p = Period.between(birthdate, todayDate);
 
		//System.out.println(p.getDays());
		//System.out.println(p.getMonths());
		//System.out.println(p.getYears());
		
		int ad[] = new int[3];
		
		ad[0]=p.getDays();
		ad[1]=p.getMonths();
		ad[2]=p.getYears();
		
		if(ad[0]>=0&& ad[1]>=0 && ad[2]>=0)
		{
			if(ad[0]==0 && ad[1]==0)
			{
				//String t = "<html>The quick <font color=#A62A2A>brown</font> fox."; JOptionPane.showMessageDialog(null, t);
				String wish="<html><font color=#A62A2A>On your special day</font>, \nI wish you good luck. I hope this wonderful day will fill up your heart with joy and blessings. \nHave a fantastic birthday, celebrate the happiness on every day of your life. Happy Birthday!! \n\n from-HitRo.";
				JOptionPane.showMessageDialog(this,wish,"1/2p BirthDay 2 You",JOptionPane.PLAIN_MESSAGE);
			}
			return  "Year :" + ad[2]+" Month :"+ad[1]+" Day :"+ad[0];
		}
		else
		{
			return "You Still Not Born";
		}
		
		
	}
	
	public void getResult(String data)
	{
			if(data.equals(""))
			{
				System.out.println("Please select a date");
			}
			else
			{
				Date d1 = new Date();
			//	String todayd = DateFormat.getDateInstance(DateFormat.SHORT).format(d1).toString();
				
				String splitedBd[] = data.split("\\/+");
			//	String splitedTd[] = todayd.split("\\/+");
				
				 int[] td=new int[3];
			//	int i=0;
			//	for(String str:splitedTd)
			//	{
			//		td[i]=Integer.parseInt(str);
			//		i++;
			//	}
				
				int[] bd=new int[splitedBd.length];
				int i=0;
				for(String str:splitedBd)
				{
					bd[i]=Integer.parseInt(str);
					i++;
				}
				
				Calendar cal = new GregorianCalendar();
				cal.setTime(d1);
				td[2]= cal.get(Calendar.YEAR);
				td[1]= cal.get(Calendar.MONTH)+1;
				td[0]= cal.get(Calendar.DATE);
				//td[2] = year ;
				bd[1] += 1;
				data = bd[0]+"/"+bd[1]+"/"+bd[2];
				dateSelected.setText("Your BirthDay :"+ data);
				data = calculate(bd,td);
				age.setText("Your age :"+ data);
				
				//todaysDate.setText("Todays Date : "+DateFormat.getDateInstance(DateFormat.SHORT).format(d1));
				todaysDate.setText("Todays Date : " + td[0]+"/"+td[1]+"/"+td[2]);
				todaysDate.setVisible(true);
				age.setVisible(true);
				dateSelected.setVisible(true);
				
			}
	}
}



/* private static Age calculateAge(int bd[])
   {
	   LocalDate birthdate =  LocalDate.of(bd[2], bd[1], bd[0]);
      int years = 0;
      int months = 0;
      int days = 0;
 
      Calendar birthDay = Calendar.getInstance();
      birthDay.setTimeInMillis(birthDate.getTime());
	  
      long currentTime = System.currentTimeMillis();
      Calendar now = Calendar.getInstance();
      now.setTimeInMillis(currentTime);
      
	  //Get difference between years
      years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
      int currMonth = now.get(Calendar.MONTH) + 1;
      int birthMonth = birthDay.get(Calendar.MONTH) + 1;
	  
      //Get difference between months
      months = currMonth - birthMonth;
      //if month difference is in negative then reduce years by one and calculate the number of months.
      if (months < 0)
      {
         years--;
         months = 12 - birthMonth + currMonth;
         if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
            months--;
      } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
      {
         years--;
         months = 11;
      }
      //Calculate the days
      if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
         days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
      else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
      {
         int today = now.get(Calendar.DAY_OF_MONTH);
         now.add(Calendar.MONTH, -1);
         days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
      } else
      {
         days = 0;
         if (months == 12)
         {
            years++;
            months = 0;
         }
      }
     
      return "Year :" + years+" Month :"+months+" Day :"+days;
   }
   
   */