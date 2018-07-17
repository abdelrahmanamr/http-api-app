package ClientServer;

public class Request {
ClientInfo client;
String url;
String acceptedFormat;
String connection;
String version;

public Request(ClientInfo client,String url,String acceptedFormat,String connection,String version){
	this.client = client;
	this.url = url;
	this.acceptedFormat = acceptedFormat;
	this.connection = connection;
	this.version = version;
}
}
