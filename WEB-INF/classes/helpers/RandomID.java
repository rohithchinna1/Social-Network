package helpers;

import java.util.UUID;

public class RandomID {
	public static String GenerateId(){
		String id="";		
		id = UUID.randomUUID().toString();		
		return id;
	}
	
	public static String GeneratePassword(){
		String password="";
		String[] id = GenerateId().split("-");
		password=id[0];		
		return password;
	}
}
