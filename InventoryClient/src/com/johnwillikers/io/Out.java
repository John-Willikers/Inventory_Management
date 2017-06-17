package com.johnwillikers.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.johnwillikers.Core;

public class Out {
	
	/*
	 * Written to keep me from adding so much every-time I needed to print. - John <3 
	 */
	public static void print(File file, String string)throws IOException{
		PrintWriter pr = new PrintWriter(file);
		pr.println(string);
		pr.close();
	}
	
	/*
	 * This function is in charge of saving both Items and SoldItems. It checks to see if soldPrice == Core.saveItemCode
	 * if it is it will save the information as an Item and anything else as a SoldItem. I.e use Core.saveItemCode if you
	 * intend to save an Item and not a SoldItem. - John <3
	 */
	public static boolean saveItem(String id, String name, String desc, String paidDate, String soldDate, int paidPrice, int soldPrice){
		//Saving Item
		if(soldPrice == 20000000 || soldDate == "00/00/0000"){
			File save = new File(Core.itemsDir.toString() + "/" + id + ".json");
			if(save.exists())
				save.delete();
			try{
				JSONObject item = new JSONObject().put("id", id).put("name", name).put("desc", desc).put("paidDate", paidDate).put("paidPrice", paidPrice);
				print(save, item.toString());
				return true;
			}catch(IOException e){
				return false;
			}
		//Saving SoldItem
		}else{
			File save = new File(Core.soldItemsDir.toString() + "/" + id + ".json");
			if(save.exists())
				save.delete();
			try{
				JSONObject item = new JSONObject().put("id", id).put("name", name).put("desc", desc).put("paidDate", paidDate).put("soldDate", soldDate).put("paidPrice", paidPrice).put("soldPrice", "soldPrice");
				print(save, item.toString());
				return true;
			}catch(IOException e){
				return false;
			}
		}
	}
	
	/*
	 * Written to save the settings file. Returns true on success false on failure
	 */
	public static boolean saveSettings(){
		JSONObject settings = new JSONObject();
		try{
			print(Core.settingsFile, settings.toString());
			return true;
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}catch(JSONException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean appendItemsFile(String id){
		if(!Core.itemsFile.exists()){
			JSONArray items = new JSONArray().put(id);
			JSONObject itemsFile = new JSONObject().put("Items", items);
			try {
				print(Core.itemsFile, itemsFile.toString());
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}else{
			try {
				JSONObject itemsFile = In.readItem(Core.itemsFile);
				JSONArray items = itemsFile.getJSONArray("Items");
				items.put(id);
				itemsFile.put("Items", items);
				print(Core.itemsFile, itemsFile.toString());
				return true;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public static boolean appendSoldItemsFile(String id){
		if(!Core.soldItemsFile.exists()){
			JSONArray items = new JSONArray().put(id);
			JSONObject itemsFile = new JSONObject().put("Items", items);
			try {
				print(Core.itemsFile, itemsFile.toString());
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}else{
			try {
				JSONObject itemsFile = In.readItem(Core.itemsFile);
				JSONArray items = itemsFile.getJSONArray("Items");
				items.put(id);
				itemsFile.put("Items", items);
				Core.soldItemsFile.delete();
				print(Core.soldItemsFile, itemsFile.toString());
				return true;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
}
