package ClientServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class ClientSend extends Thread{
	boolean request;
	String Clientname;
	DataOutputStream outToServer;
	String sentence;
	Socket clientSocket;
	boolean inputFromuser;
	public ClientSend(String Clientname,DataOutputStream outToServer,Socket clientSocket){
		this.request=true;
		this.Clientname = Clientname;
		this.outToServer= outToServer;
		this.clientSocket = clientSocket;
		this.inputFromuser = false;
	}
	
	public void updatemessage(String message){
		this.sentence = message;
		this.inputFromuser= true;
	}
public void run(){
	while(true){
        if(request){
	         try {
				outToServer.writeBytes(Clientname+",0"+'\n');
			} catch (IOException e) {
				e.printStackTrace();
			}
	         System.out.println("hello");
	         request=false;
	        }
        else{
             
        	if(inputFromuser){
              try {
            	
				outToServer.writeBytes(Clientname+","+sentence+'\n');
			} catch (IOException e) {
				e.printStackTrace();
			} 
              System.out.println(Clientname+","+sentence+'\n'); 
              inputFromuser = false;
              if(sentence.toUpperCase().equals("BYE")){
              try {
				clientSocket.close();
			} catch (IOException e) {
				break;
			} 
              
              }
                         
          } 
        }
        //break;
    	}
}
}
