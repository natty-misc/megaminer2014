package cz.mm14.src;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
	 
	public class Updater extends JPanel implements ActionListener {

		private static final long serialVersionUID = -9006340799547012944L;
		
		public static JProgressBar progressBar;
	    public static JButton dlB;
	    public static JButton playB;
	    public static JButton exitB;
	    public static JButton delB;
	    public static JTextArea out;
	    
	    static Thread t;
	    
		static Process p;
		public static long timeStart;

		public static boolean isGameRunning;

		public Task task;
	 
	    public Updater() {
	        super(new BorderLayout());
	 
	        playB = new JButton("Play");
	        playB.addActionListener(new PlayB());
	        
	        dlB = new JButton("Update");
	        dlB.addActionListener(this);
	        
	        exitB = new JButton("Exit");
	        exitB.addActionListener(this);
	 
	        delB = new JButton("Delete");
	        delB.addActionListener(this);
	        
	        progressBar = new JProgressBar(0, 100);
	        progressBar.setValue(0);
	        progressBar.setStringPainted(true);
	 
	        out = new JTextArea(20, 45);
	        out.setMargin(new Insets(5,5,5,5));
	        out.setEditable(false);
	 
	        JPanel panel = new JPanel();

	        panel.add(exitB);
	        panel.add(playB);
	        panel.add(dlB);
	        panel.add(delB);
	        
        	out.append("Welcome to the MegaMiner2014 launcher.\n");
        	out.append("Press \"Play\" to play the game.\n");
        	out.append("Press \"Update\" to update the game.\n");
        	out.append("Press \"Delete\" to delete the game.\n");
        	out.append("\n");
	        
	        if(!new File("Game/").exists() || new File("Game/").list().length == 0)
	        {
	        	out.setText("");
	        	out.append("Welcome to the MegaMiner2014 launcher.\n");
	        	out.append("Press \"Download\" to download the game.\n");
	        	playB.setEnabled(false);
	        	delB.setEnabled(false);
	        	dlB.setText("Download");
	        }
	        
	        add(progressBar, BorderLayout.PAGE_START);
	        add(panel, BorderLayout.PAGE_END);
	        add(new JScrollPane(out), BorderLayout.CENTER);
	        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	 
	    }
	 
	    @Override
	    public void actionPerformed(ActionEvent event) {
	    	JButton jb = (JButton) event.getSource();
	    	
	    	if(jb.getText().equals("Update")||jb.getText().equals("Download"))
	    	{
	    	delB.setVisible(false);
	        dlB.setVisible(false);
	        playB.setVisible(false);
	        exitB.setVisible(false);
	        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	        
	        task = new Task(this, "download");
	        task.execute();
	    	}
	    	
	    	if(jb.getText().equals("Delete"))
	    	{
	    	exitB.setVisible(false);
	    	delB.setVisible(false);
	        dlB.setVisible(false);
	        playB.setVisible(false);
	        
	        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	        
	        task = new Task(this, "delete");
	        task.execute();
	    	}
	    	
	    	if(jb.getText().equals("Exit"))
	    	{
	        System.exit(0);
	    	}
	    }
	 
	    private static void initGUI() {
	    	
	        JFrame frame = new JFrame("MegaMiner Launcher");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	        JComponent cp = new Updater();
	        cp.setOpaque(true);
	        
	        frame.setContentPane(cp);
	        frame.setSize(400, 300);
	        frame.setLocation(200,100);
	        
	        frame.pack();
	        frame.setVisible(true);
	    }
	 
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                initGUI();
	            }
	        });
	        
	        class GameListenThread extends Thread implements Runnable
	        {
	        	public void run()
	        	{	        		
	        		try 
	        		{
						createConsoleListener();
					} 
	        		catch (InterruptedException e) 
	        		{
	        			out.append(e.getStackTrace().toString()+"\n");
					}
	        	}
	        }
	        t = new GameListenThread();
	    }
	    
	     static boolean isRunning(Process process) {
	        try {
	            process.exitValue();
	            return false;
	        } catch (Exception e) {
	            return true;
	        }
	    }
	    
	    public static void createConsoleListener() throws InterruptedException
	    {
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("UTF-8")));;

	              	while(true)
	                {
	                	while(!isGameRunning)
	                    {    
	                		
	                    }
	                	
	                    	try 
	                    	{
	                    		if(p!=null)
	                    		{
	                    		PlayB.toConsole(reader);
	                    		}
	        				} 
	                    	catch (IOException e) 
	                    	{
	        					e.printStackTrace();
	        				}
	                }	        
	    }
	    
	    public static void runGame() throws IOException
	    {   		    		
			timeStart = System.currentTimeMillis();
			Runtime rt = Runtime.getRuntime();
			
			rt.exec("cmd @echo off");
			p = rt.exec("cmd /c java -jar MegaMiner2014.jar", null, new File(new File("").getAbsolutePath().concat("\\Game\\")));
						
			isGameRunning = true;
			if(!t.isAlive())
			{
			Updater.out.setText("");
			t.start();
			}
	    }
	}
