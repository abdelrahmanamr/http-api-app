package ClientServer;

	import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*; 
import java.net.*; 
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
	class TCPClient extends JFrame implements ActionListener{ 
	  // all things concerning the request
		String message;
		static String sentence;
		static String Clientname;
		static DataOutputStream outToServer;
		
		JPanel req = new JPanel();
		JPanel req1 = new JPanel();
		JPanel req2 = new JPanel();
		JPanel req3 = new JPanel();
		JPanel req4 = new JPanel();
		
		
	    JTextField Method1= new JTextField();
		JLabel Method2 = new JLabel();
		
		static JTextField Url1 = new JTextField();
		JLabel Url2 = new JLabel();
		
		JTextField Version1 = new JTextField();
		JLabel Version2 = new JLabel();
		
		JTextField Host1 = new JTextField();
		JLabel Host2 = new JLabel();
		
		JTextField Acceptedformat1 = new JTextField();
		JLabel Acceptedformat2 = new JLabel();
		
		JTextField Connection1 = new JTextField();
		JLabel Connection2 = new JLabel();
		
		JButton send1 = new JButton();
		JLabel send2 = new JLabel();
		
		// all things concerning response
		JPanel res = new JPanel();
		
		static JTextArea Response1= new JTextArea();
		JLabel Response2 = new JLabel();
		
		static JLabel ClientName = new JLabel();
		
		
		public TCPClient(){
			
			this.setTitle("Mini Postman");
			 this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			 this.setLayout(new GridLayout(1,2));
			 this.setSize(800,600);
			 
			 req.setLayout(new GridLayout(5,1));
			 req1.setLayout(new GridLayout(2,3));
			 req2.setLayout(new GridLayout(2,1));
			 req3.setLayout(new GridLayout(2,1));
			 req4.setLayout(new GridLayout(2,1));
			 
			 Method1.setSize(10,50);
			 Method2.setSize(50,50);
			 Method2.setText("METHOD:");
			 
			 Url1.setSize(10,50);
			 Url2.setSize(50,50);
			 Url2.setText("URL:");
			 
			 Version1.setSize(10,50);
			 Version2.setSize(50,50);
			 Version2.setText("VERSION:");
			 
			 req1.add(Method2);
			 req1.add(Url2);
			 req1.add(Version2);
			 
			 req1.add(Method1);
			 req1.add(Url1);
			 req1.add(Version1);
			 
			 
			 Host1.setSize(10,50);
			 Host2.setSize(50,50);
			 Host2.setText("Host:");
			 
			 req2.add(Host2);
			 req2.add(Host1);
			 
			 Acceptedformat1.setSize(10,50);
			 Acceptedformat2.setSize(50,50);
			 Acceptedformat2.setText("Acceptedformat:");
			 
			 req3.add(Acceptedformat2);
			 req3.add(Acceptedformat1);
			 
			 Connection1.setSize(10,50);
			 Connection2.setSize(50,50);
			 Connection2.setText("Connection:");
			 
			 req4.add(Connection2);
			 req4.add(Connection1);
			 
			 send1.setSize(10,20);
			 send2.setSize(50,50);
			 send2.setText("Send");
			 send1.add(send2);
			 send1.addActionListener(this);
			 
			 req.add(req1);
			 req.add(req2);
			 req.add(req3);
			 req.add(req4);
			 req.add(send1);
		//---------------------------------------------
			 
			 res.setLayout(new GridLayout(3,1));
			 Response1.setSize(50,50);
			 Response2.setSize(10,50);
			 Response2.setText("Response:");
			 Response1.setEditable(false);
			 ClientName.setSize(10,50);
			 
			 res.add(ClientName);
			 res.add(Response2);
			 res.add(Response1);
			 
			 this.add(req);
			 this.add(res);
			 
			 this.repaint();
			 this.revalidate();
			 this.setVisible(true);
		}
	 
	    public static void main(String argv[]) throws Exception 
	    {
	    	 Clientname=JOptionPane.showInputDialog("Enter Your Name Please");
	    	 ClientName.setText(Clientname);
	    	 Socket clientSocket =
		        		new Socket("abdelrahmans-MacBook-Air.local", 6791);
	    	  outToServer = 
   		          new DataOutputStream(clientSocket.getOutputStream()); 
	    	  boolean request = true;
	    	  if(request){
			         try {
						outToServer.writeBytes(Clientname+",0"+'\n');
					} catch (IOException e) {
						e.printStackTrace();
					}
			         System.out.println("hello");
			         request=false;
			        }
	 		
	    	  new TCPClient();
	 		Thread receive = new Thread(new Runnable() {
	 			boolean flag = true;
	 			public void run() {
	 				
	 				while (flag) {
	 					 BufferedReader inFromServer;
	 					try {
	 						inFromServer = new BufferedReader(new
	 		 						InputStreamReader(clientSocket.getInputStream()));
	 						String modifiedSentence = inFromServer.readLine();
	 						System.out.println(modifiedSentence); 
	 						String status = modifiedSentence.split(",")[0];
	 						if(status.equals("200 OK")||status.equals("404 Not Found")){
	 						String version = modifiedSentence.split(",")[1];
	 						String timeStamp = modifiedSentence.split(",")[2];
	 						String format = modifiedSentence.split(",")[3];
	 						String connection  = modifiedSentence.split(",")[4];
	 						String resp ="Status: " +status+"       "+"Version: "+version+'\n'+"TimeStamp: "+timeStamp+'\n'+"Format: "+format+'\n'+"Connection: "+connection;
	 						if(status.equals("200 OK")){
	 						byte []b = new byte[Integer.parseInt(modifiedSentence.split(",")[5])];
	 						InputStream is = clientSocket.getInputStream();
	 						is.read(b,0,b.length);
	 						FileOutputStream fos = new FileOutputStream("/Users/abdelrahmanamrsalem/Networks"+Url1.getText());
	 						fos.write(b, 0, b.length);
	 						
	 						}
	 						if(connection.toLowerCase().equals("close")){
	 							Response1.setText(resp +'\n'+"Connection Closed Correctly");
	 							clientSocket.close();
	 							flag = false;
	 						}
	 						else{
	 							Response1.setText(resp);
	 						}
	 						}
	 					}
	 						 catch (IOException e) {
	 						// TODO Auto-generated catch block
	 						e.printStackTrace();
	 					} 
	 				}
	 			}

	 		});
	 		 receive.start();
	    	 //getMembersList();
	    	
	    }

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			sentence = Method1.getText()+","+Url1.getText()+","+Version1.getText()+","+Host1.getText()+","+Acceptedformat1.getText()+","+Connection1.getText();
			try {
					outToServer.writeBytes(Clientname+","+sentence+'\n');
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
	              System.out.println(Clientname+","+sentence); 
		}
	    
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
