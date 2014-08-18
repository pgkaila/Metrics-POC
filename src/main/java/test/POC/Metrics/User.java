package test.POC.Metrics;

import org.springframework.stereotype.Component;

@Component
public class User {
	
	String username = "Piyush";
	String pass = "test";
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	

}
