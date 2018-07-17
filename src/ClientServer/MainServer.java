package ClientServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MainServer {
	final static String docRoot = "/Users/abdelrahmanamrsalem/Networks/doc";
	final static String name = "MainServer"; 
	static Queue<Request> Requests = new LinkedList<Request>();
	static Socket connectionSocket;
	 static ArrayList<ClientInfo>clients =new ArrayList<ClientInfo>();
	  public static void main(String argv[]) throws Exception 
	    { 
	      ServerSocket welcomeSocket = new ServerSocket(6791); 
	      
	     
	      while(true) { 
	    	    Socket connectionSocket = welcomeSocket.accept(); 
	           TCPServerThread t =  new TCPServerThread(connectionSocket,name);
	           t.start();
	              } 
	    
	      
	      }
}
