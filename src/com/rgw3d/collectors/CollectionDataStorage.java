package com.rgw3d.collectors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.content.Context;
import android.provider.DocumentsContract.Root;
import android.util.Log;
import android.util.Xml;

public class CollectionDataStorage {
	
	public static String FileName = "collectionData.txt";
	
	public static CollectionItem base = new CollectionItem();
	static{
		readData();
		//if(savedData()){
			 
		//}
		//else{
		
		base.setName("Top Level");
		base.setIsItem(false);
		base.initializeChildren();
		
			CollectionItem child1 = new CollectionItem(base,null,true,"Item 1",null);
			child1.initializeDescription();
			child1.addNewFieldAndDescript("Field 1", "The description1");
			child1.addDescription("Field 1", "WOW");
			child1.addNewFieldAndDescript("Field 2", "The description1");
			child1.addNewFieldAndDescript("Field 3", "The description1");
			child1.addNewFieldAndDescript("Field 4", "The description1");
			child1.addNewFieldAndDescript("Field 5", "The description1");
			child1.addNewFieldAndDescript("Field 6", "The description1");
			child1.addNewFieldAndDescript("Field 7", "The description1");
			
			
			CollectionItem child2 = new CollectionItem(base,null,true,"Item 2",null);
			child2.initializeDescription();
			child2.addNewFieldAndDescript("Field 2", "The description2");
			
			
			CollectionItem child3 = new CollectionItem(base,null,true,"Item 3",null);
			child3.initializeDescription();
			child3.addNewFieldAndDescript("Field 1", "The description1");
			child3.addDescription("Field 1", "WOW");
			child3.addNewFieldAndDescript("Field 2", "The description1");
			child3.addNewFieldAndDescript("Field 3", "The description1");
			child3.addNewFieldAndDescript("Field 4", "The description1");
			child3.addNewFieldAndDescript("Field 5", "The description1");
			child3.addNewFieldAndDescript("Field 6", "The description1");
			child3.addNewFieldAndDescript("Field 7", "The description1");
			
			CollectionItem child4 = new CollectionItem(base,null,false,"List 1",null);
			child4.initializeChildren();
			
				CollectionItem baby1 = new CollectionItem(child4,null,true,"Sub Item 1",null);
				baby1.initializeDescription();
				baby1.addNewFieldAndDescript("Field 1", "The lame description for this object");
				baby1.addDescription("Field 1", "WOW a secondary description");
				baby1.addNewFieldAndDescript("Field 2", "The description 3");
				baby1.addNewFieldAndDescript("Field 3", "The description 4");
				baby1.addNewFieldAndDescript("Field 4", "aisjflk;asjdf adsf");
				baby1.addNewFieldAndDescript("Field 5", "The description 5asldfkj asdfj ");
				baby1.addNewFieldAndDescript("Field 6", "The description 7 aslkdjflaks;djf");
				baby1.addNewFieldAndDescript("Field 7", "The description 8 al;kjf");
				
				CollectionItem baby2 = new CollectionItem(child4,null,true,"Sub Item 2",null);
				baby2.initializeDescription();
				baby2.addNewFieldAndDescript("Field 1", "A description of this field");
				baby2.addDescription("Field 1", "WOW WOW WOW ");
				baby2.addNewFieldAndDescript("Field 2", "The description 2");
				
				CollectionItem baby3 = new CollectionItem(child4,null,false,"Sub List 1",null);
				baby3.initializeChildren();
					
					CollectionItem babyChild1 = new CollectionItem(baby3,null,true,"Sub Sub Item 1",null);
					babyChild1.initializeDescription();
					babyChild1.addNewFieldAndDescript("Field 1", "A description of this field");
					babyChild1.addDescription("Field 1", "WOW WOW WOW ");
					babyChild1.addNewFieldAndDescript("Field 2", "The description 2");
					
					CollectionItem babyChild2 = new CollectionItem(baby3,null,true,"Sub Sub Item 2",null);
					babyChild2.initializeDescription();
					babyChild2.addNewFieldAndDescript("Field 1", "A description of this field");
					babyChild2.addDescription("Field 1", "WOW WOW WOW ");
					babyChild2.addNewFieldAndDescript("Field 2", "The description 2");
				
				baby3.addChildren(babyChild1);
				baby3.addChildren(babyChild2);
					
			
			child4.addChildren(baby1);
			child4.addChildren(baby2);
			child4.addChildren(baby3);
		
		base.addChildren(child1);
		base.addChildren(child2);
		base.addChildren(child3);
		base.addChildren(child4);
		
		saveData();
		
		///}
		
	}
	
	public static void saveData(){

		    FileOutputStream fileOutputStream;       

	    try {
				fileOutputStream = CollectorsInventory.getContext().openFileOutput(FileName,Context.MODE_APPEND);
			    XmlSerializer serializer = Xml.newSerializer();
			    serializer.setOutput(fileOutputStream, "UTF-8");
			    serializer.startDocument(null, Boolean.valueOf(true));
			    serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
	
			    serializer.startTag(null, "root");
			    addCollectionItemsTags(serializer, base.getChildren(),"CollectionItemBase");
			    serializer.endTag(null, "root");
			    serializer.endDocument();
			    serializer.flush();
			    fileOutputStream.close();
		     
		} catch (IllegalArgumentException | IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public static void addCollectionItemsTags(XmlSerializer serializer, ArrayList<CollectionItem> collectionItemList, String name){
		
		for(CollectionItem i: collectionItemList){
	    	try {
				serializer.startTag(null, name);
				serializer.attribute(null, "name", i.toString());
		    	addIsItemTag(serializer,i);
		    	addChildrenTag(serializer,i);
		    	addDescriptionTag(serializer,i);
		    	serializer.endTag(null, name);
		    
			} catch (IllegalArgumentException | IllegalStateException
					| IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	

	public static void addIsItemTag(XmlSerializer serializer, CollectionItem i){
		try {
			serializer.startTag(null, "isItem");
			serializer.text(""+i.isItem());
			serializer.endTag(null, "isItem");
		} catch (IllegalArgumentException | IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addChildrenTag(XmlSerializer serializer, CollectionItem i){
		try {
			serializer.startTag(null, "children");
			if(i.getChildren()!= null){
				addCollectionItemsTags(serializer,i.getChildren(),"CollectionItem");
			}
			else{
				serializer.text("null");
			}
			serializer.endTag(null, "children");
		} catch (IllegalArgumentException | IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addDescriptionTag(XmlSerializer serializer,CollectionItem i) {
		try {
			serializer.startTag(null, "description");
			if(i.isItem()){
				for(String field: i.getKeys()){
					serializer.startTag(null, "field");
					serializer.attribute(null, "fieldName", field);
					serializer.text(i.getDescription(field));
					serializer.endTag(null, "field");
				}
			}
			else
				serializer.text("null");
			
			serializer.endTag(null, "description");
		} catch (IllegalArgumentException | IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Returns true if the file exists
	 * @return true if the file specified by FileName exists
	 */
	public static boolean savedData(){
		Log.d("The file path", CollectorsInventory.getContext().getFilesDir().toString());
		File file = CollectorsInventory.getContext().getFileStreamPath(FileName);
		return file.exists();
		
	}
	
	private static void readData() {
		Log.d("File: ","Look below");
		BufferedReader reader = null;
		try {
			if(savedData()){
				
				File file = new File (CollectorsInventory.getContext().getFileStreamPath(FileName).toString());
				
				
					reader = new BufferedReader(new FileReader(file));
				
				
				String text = null;
	
					while ((text = reader.readLine()) != null) {
				        Log.d("File: ",text);
					}
					
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	

}
