import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.table.TableModel;

public class AgeCalculator extends Thread 
{
	
	DefaultTableModel model;
	Calendar cal = new GregorianCalendar();
	JLabel label;
	String month;
	int year,month1,date;
	JFrame jf;
	Main m;
	AgeCalculator(Main m)
	{
		this.m=m;
	}
	
	public void run()
	{
	
		jf= new JFrame();
		jf.setLayout(new BorderLayout());
		jf.setTitle("Age Calculator Calandar");
		jf.setSize(400,300);
		jf.setDefaultLookAndFeelDecorated(true);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setVisible(true);
		//pack();
		jf.setLocation(450, 150);
		jf.setResizable(false);
		
		label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
 
		JButton b1 = new JButton("<-M");
		b1.addActionListener(new ActionListener() {
			
				public void actionPerformed(ActionEvent ae) 
				{
					cal.add(Calendar.MONTH, -1);
					updateMonth();
				}
		});
 
		JButton b2 = new JButton("M->");
		b2.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent ae) 
				{
					cal.add(Calendar.MONTH, +1);
					updateMonth();
				}
		});
		
		JButton b3 = new JButton("<-Y");
		b3.setBackground(Color.blue);
		b3.addActionListener(new ActionListener() {
			
				public void actionPerformed(ActionEvent ae) 
				{
					cal.add(Calendar.YEAR, -1);
					updateMonth();
				}
		});
 
		JButton b4 = new JButton("Y->");
		b4.setBackground(Color.blue);
		b4.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent ae) 
				{
					cal.add(Calendar.YEAR, +1);
					updateMonth();
				}
		});
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel buttonpanel1 = new JPanel();
		buttonpanel1.setLayout(new FlowLayout(FlowLayout.CENTER,10,30));
		buttonpanel1.add(b1);
		buttonpanel1.add(b3);
		
		JPanel buttonpanel2 = new JPanel();
		buttonpanel2.setLayout(new FlowLayout(FlowLayout.CENTER,10,30));
		buttonpanel2.add(b4);
		buttonpanel2.add(b2);
		
		panel.add(buttonpanel1,BorderLayout.WEST);
		panel.add(label,BorderLayout.CENTER);
		panel.add(buttonpanel2,BorderLayout.EAST);
 
 
		String [] columns = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
		model = new DefaultTableModel(null,columns){ 
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}};
		
		JTable table = new JTable(model);
		JScrollPane pane = new JScrollPane(table);
 
		jf.add(panel,BorderLayout.NORTH);
		jf.add(pane,BorderLayout.CENTER);
 
		updateMonth();
		
		
	
		table.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(final MouseEvent e) 
			{
				if (e.getClickCount() == 1) 
				{
				
					final JTable target = (JTable)e.getSource();
					final int row = target.getSelectedRow();
					final int column = target.getSelectedColumn();
					try{
						date = (int)target.getValueAt(row, column);
					}
					catch(Exception s){date =0;}
						
					if(date!=0)
					{
						//System.out.println(date+" "+month1+" "+year);
						String srt = date+"/"+month1+"/"+year;
						m.getResult(srt);
						jf.dispose();
					}
					
				}
			}
		});
        
	}
	
	
	void updateMonth() 
	{
		cal.set(Calendar.DAY_OF_MONTH, 1);
 
		month1 = cal.get(Calendar.MONTH);
		month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
		year = cal.get(Calendar.YEAR);
		label.setText(month + " " + year);
 
		int startDay = cal.get(Calendar.DAY_OF_WEEK);
		int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int weeks = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
 
		model.setRowCount(0);
		model.setRowCount(weeks);
 
		int i = startDay-1;
		for(int day=1;day<=numberOfDays;day++)
		{
			model.setValueAt(day, i/7 , i%7 );    
			i = i + 1;
		}
 
	}
}

//