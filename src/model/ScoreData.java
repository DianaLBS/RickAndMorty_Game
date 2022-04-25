package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.Gson;

public class ScoreData{
	public static  ArrayList<User> scoreBoard;

	public ScoreData() {

		scoreBoard = new ArrayList<>();
	}

	public void addUsername(User user) {
		scoreBoard.add(user);
	}
	
	//public void print() {
		//for (User u: scoreBoard) {
			//	System.out.println(u.getUserName()+" "+u.getScore());
		//}
	//}
	
	public static void saveJSON() {
		try {
			Gson gson = new Gson();
			String json = gson.toJson(scoreBoard);

			File file = new File("scoreBoard.json");
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(json.getBytes());
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadJSON() {
		try {
			FileInputStream fis = new FileInputStream(new File("scoreBoard.json"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			String json = "";
			String line;
			while ((line = reader.readLine()) != null) {
				json += line;
			}
			Gson gson = new Gson();
			User[] data = gson.fromJson(json, User[].class);
			for (User u : data) {
				scoreBoard.add(u);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int searchUser(String username) {
	    
		int low=0;
		int high=scoreBoard.size()-1;
	    while (low <= high) {
	    	int midPos = (low + high) / 2;
		    String midUser = scoreBoard.get(midPos).getUserName();
		    int compare = username.compareTo(midUser);

		    if (compare == 0) {
		        return midPos;
		    }
		    if (compare < 0) {
		        high = midPos - 1;
		    } else {
		        low = midPos + 1;
		    }
	    }
	    return -1;
	}
	
	/*public int searchUser(String username) {
		int low=0;
		int high=scoreBoard.size()-1;
	    if (low > high) {
	        return -1;
	    }
	    int midPos = (low + high) / 2;
	    String midUser = scoreBoard.get(midPos).getUserName();
	 
	    int compare = username.compareTo(midUser);

	    if (compare == 0) {
	        return midPos;
	    }
	 
	    if (compare < 0) {
	        high = midPos - 1;
	        return searchUser(username);
	    } else {
	        low = midPos + 1;
	        return searchUser(username);
	    }
	}*/
}
