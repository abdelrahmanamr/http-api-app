package ClientServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class TCPServerThread extends Thread {
	Socket socket;
	String serverName;
	String senderName;
	String url;
	String acceptedForm;
	String connection;
	String message;
	String version;

	public TCPServerThread(Socket s, String serverName) {
		this.socket = s;
		this.serverName = serverName;
	}

	public void run() {
		String clientSentence;
		String capitalizedSentence;
		try {
			while (true) {
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));

				clientSentence = inFromClient.readLine();
				if (clientSentence != null) {
					
					if (clientSentence.split(",")[1].equals("0")) {
						MainServer.clients.add(new ClientInfo(
										clientSentence.split(",")[0],
										this.socket));
								System.out
										.println("Connection to server done Sucessfully");
						// --------------------------------------------------------------------------------------------------------
					} 
						// -----------------------------------------------------------------------------------------------------------------
						else {
							// code for sending regular messages till the end of
							// the page
							// Example of a request received from client: Abdelrahman,GET,www.google.com/car,HTTP 1.1,Postman,text,closed
							System.out.println(clientSentence.split(",")[1]+" "+clientSentence.split(",")[2]+" "+clientSentence.split(",")[3]);
							System.out.println(clientSentence.split(",")[4]);
							System.out.println(clientSentence.split(",")[5]);
							System.out.println(clientSentence.split(",")[6]);
							System.out.println();
							System.out.println("the following are the request's name of the sender in order ");
							senderName = clientSentence.split(",")[0];
							url = clientSentence.split(",")[2];
							acceptedForm= clientSentence.split(",")[5];
							connection = clientSentence.split(",")[6];
							version =clientSentence.split(",")[3];
							for(int i = 0;i<MainServer.clients.size();i++){
								if(this.senderName.toLowerCase().equals(MainServer.clients.get(i).name.toLowerCase())){
									MainServer.Requests.add(new Request(MainServer.clients.get(i), url, acceptedForm, connection,version));
								}
							}
							
							Iterator<Request> i =MainServer.Requests.iterator();
							while(i.hasNext()){
								System.out.println(i.next().client.name);
							}
							
							while (!MainServer.Requests.isEmpty()) {
								
			 					try {
			 						Request req  = MainServer.Requests.poll();
			 						String path = MainServer.docRoot+req.url;
			 						File file = new File(path);
			 						String message ="";
			 						String format = req.url.split("/")[req.url.split("/").length-1].split("\\.")[req.url.split("/")[req.url.split("/").length-1].split("\\.").length-1];
			 						String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
			 						if(file.exists()&& format.toLowerCase().equals(req.acceptedFormat.toLowerCase())){
			 							FileInputStream fis = new FileInputStream(path);
			 							byte b[] = new byte[(int) file.length()];
			 							System.out.println(b.length);
			 							message = "200 OK"+","+req.version+","+timeStamp+","+req.acceptedFormat+","+req.connection+","+b.length;
			 							System.out.println(message);
			 							DataOutputStream outToClient = 
				 				   		          new DataOutputStream(req.client.socket.getOutputStream());
				 						outToClient.writeBytes(message+'\n');
				 						outToClient = new DataOutputStream(req.client.socket.getOutputStream());
			 							fis.read(b, 0,b.length);
			 							try {
											TimeUnit.SECONDS.sleep(2);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
			 							outToClient.write(b, 0, b.length);
			 						}
			 						else{
			 							message = "404 Not Found"+","+req.version+","+timeStamp+","+req.acceptedFormat+","+req.connection;
			 							DataOutputStream outToClient = 
				 				   		          new DataOutputStream(req.client.socket.getOutputStream());
				 						outToClient.writeBytes(message+'\n');
				 						outToClient.flush();
			 						}
			 						
			 					} catch (IOException e) {
			 						
			 						e.printStackTrace();
			 					}
			 					}
							
					    
						}
					}
				}
		
		}catch (IOException e) {
			e.printStackTrace();
		}

	}
}
