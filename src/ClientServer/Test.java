package ClientServer;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
     File x = new File("/Users/abdelrahmanamrsalem/Networks/doc/text1.txt");
     System.out.println(x.exists());
     
//     Queue<String> y = new LinkedList<String>();
//     y.add("abdo");
//     y.add("omar");
//     System.out.println(y.size());
//     String abdo = y.poll();
//     System.out.println(abdo);
//     System.out.println(y.size());
//     System.out.println(y.poll());
     String s = "path/extensible.JPEG";
     System.out.println(s.split("/")[s.split("/").length-1]);
     String s1 = s.split("/")[s.split("/").length-1];
     System.out.println(s1.split("\\.")[s1.split("\\.").length-1]);
	}

}
