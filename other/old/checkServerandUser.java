import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Your code here!
        
      String s="RCPT <SP> TO: <SP> < <user name>@<server IP> > <CRLF>";
      String r;
s = s.substring(s.indexOf("@") +2); // this is for server
//s = s.substring(s.indexOf("@") -10); this is for username
r = s.substring(0, s.indexOf(">"));

System.out.println(r);


    }
    
    
     public boolean serverChecker(String a){
    
    
    
    
             String r;
                a = a.substring(a.indexOf("@") + 2);
            r = a.substring(0, a.indexOf(">"));
          if(r=="ourdomainserver)"){
              return true;
          }else{
              System.out.println("554 Relay access denied.");
              return false;
          }
}



     public boolean userChecker(String a){
    
    
    
    
             String r;
                a = a.substring(a.indexOf("@") + 2);
            r = a.substring(0, a.indexOf(">"));
          if(r=="usernamelist"){
              return true;
          }else{
              System.out.println("554 Relay access denied.");
              return false;
          }
}



}
