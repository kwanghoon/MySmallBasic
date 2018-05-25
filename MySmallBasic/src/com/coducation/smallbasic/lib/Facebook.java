package com.coducation.smallbasic.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import com.coducation.smallbasic.ArrayV;
import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;
import com.restfb.BinaryAttachment;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.User;

public class Facebook {
	static String Accesstoken = new String();
	public static Value SaveToken(ArrayList<Value> args) {
		if (args.get(0) instanceof DoubleV) {

			Accesstoken = args.get(0).toString();

		} else if (args.get(0) instanceof StrV) {

			Accesstoken = ((StrV) args.get(0)).getValue();
		}
		return new StrV(Accesstoken);
	}

//Get your personal informaton on Facebook		
	public static Value GetPersonalInformation(ArrayList<Value> args) {
		FacebookClient facebookClient = new DefaultFacebookClient(Accesstoken);
		User user = facebookClient.fetchObject("me", User.class,
	            Parameter.with("fields",
	                    "id, name, email, first_name, last_name, birthday"));
		ArrayV myinfor = new ArrayV();
		myinfor.put("FirstName", new StrV(user.getFirstName()));
		myinfor.put("LastName", new StrV(user.getLastName()));
		myinfor.put("FullName", new StrV(user.getName()));		
		myinfor.put("Id", new StrV(user.getId()));
		myinfor.put("Email", new StrV(user.getEmail()));
		myinfor.put("Birthday", new StrV(user.getBirthday()));
		return myinfor;
	}
	public static void put(ArrayV arr1, String index, String index1, String index2, Value v1, Value v2) {
		ArrayV arr = new ArrayV();
		arr.put(index1, v1);
		arr.put(index2, v2);
		arr1.put(index, arr);
	}

	//Get your List Friend on Facebook 	
	public static Value GetFriendlist(ArrayList<Value> args) {
		FacebookClient facebookClient = new DefaultFacebookClient(Accesstoken);
		Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class, 
				Parameter.with("fields", "id,first_name,last_name,name,gender"));
		ArrayV record_arrayv = new ArrayV();
		int i ;
		String j;
//		total number of people in list friend
		record_arrayv.put("0", new StrV(myFriends.getData().size()));
		
		for(i=1; i<=myFriends.getData().size(); i++) {
			j = Integer.toString(i);
        	put(record_arrayv, j,  "NAME", "ID",
        			new StrV(myFriends.getData().get(i-1).getName()), new StrV(myFriends.getData().get(i-1).getId()));
        }
	    return record_arrayv;
	}	

	//Post a status on Facebook	
	public static Value PostStatus(ArrayList<Value> args) {		
		String str_arg = new String();
		if(str_arg == null){
			return new StrV("false");
		}
		else if (args.get(0) instanceof DoubleV) {

			str_arg = args.get(0).toString();

		} else if (args.get(0) instanceof StrV) {

			str_arg = ((StrV) args.get(0)).getValue();
		
		}else
			
			throw new InterpretException("Not String Value for Status" + str_arg);
		
		FacebookClient facebookClient = new DefaultFacebookClient(Accesstoken);
		FacebookType response = facebookClient.publish("me/feed", FacebookType.class, 
					 Parameter.with("message", str_arg));
		String id = "fb.com/"+ response.getId();
	    return new StrV(id);
	}

	//Post a photo on Facebook	
	public static Value PostPhoto(ArrayList<Value> args) throws FileNotFoundException {
//		Parameters of function
		String namephoto = new String();
		String caption = new String();
		String filePhoto = new String();
//		
			if(args.size() != 3) throw new InterpretException("Error in # of Arguments: " + args.size());
			
			if (args.get(0) instanceof DoubleV) {

				filePhoto = args.get(0).toString();

			} else if (args.get(0) instanceof StrV) {

				filePhoto = ((StrV) args.get(0)).getValue();
			}else
				throw new InterpretException(" Error for FilePhoto " + filePhoto);
			
			
			if (args.get(1) instanceof DoubleV) {

				namephoto = args.get(1).toString();

			} else if (args.get(1) instanceof StrV) {

				namephoto = ((StrV) args.get(1)).getValue();
			}else
				
				throw new InterpretException(" Error for Photo's Name" + namephoto);
			 
			if (args.get(2) instanceof DoubleV) {

				caption = args.get(2).toString();

			} else if (args.get(2) instanceof StrV) {

				caption = ((StrV) args.get(2)).getValue();
			
			}else
				
				throw new InterpretException(" Error for Caption" + caption);
		
		FacebookClient facebookClient = new DefaultFacebookClient(Accesstoken);
		FileInputStream fis = new FileInputStream(new File(filePhoto+namephoto));
		FacebookType response = facebookClient.publish("me/photos", FacebookType.class,
					 BinaryAttachment.with(namephoto, fis), Parameter.with("message", caption));
		String id = "fb.com/"+ response.getId();
	    return new StrV(id);
	}
}
