package com.rgw3d.collectors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class CollectionDataStorage {
	
	public static final String FILENAME = "collectionData.txt";
	public static final String COLLECTION_ITEM_TAG ="CollectionItem";
	public static final String ATTRIBUTE_TAG_NAME = "name";
	public static final String IS_ITEM_TAG = "IsItem";
	public static final String CHILDREN_TAG = "Children";
	public static final String DESCRIPTION_TAG = "Description";
	public static final String DESCRIPTION_FIELD_TAG = "DescriptionField";
	
	public static CollectionItem Base = new CollectionItem();
	
	static{
		//deleteData();
		if(savedData()){
			 loadData();
			 Log.d("Data:","Loading Data");
		}
		else{
			Log.d("Data:","No saved data");
		
		Base.setName("Top Level");
		Base.setIsItem(false);
		Base.initializeChildren();
		
			CollectionItem child1 = new CollectionItem(Base,null,true,"Item 1",null);
			child1.initializeDescription();
			child1.addNewFieldAndDescript("Field 1", "The description1");
			child1.addDescription("Field 1", "WOW");
			child1.addNewFieldAndDescript("Field 2", "The description1");
			child1.addNewFieldAndDescript("Field 3", "The description1");
			child1.addNewFieldAndDescript("Field 4", "The description1");
			child1.addNewFieldAndDescript("Field 5", "The description1");
			child1.addNewFieldAndDescript("Field 6", "The description1");
			child1.addNewFieldAndDescript("Field 7", "The description1");
			
			
			CollectionItem child2 = new CollectionItem(Base,null,true,"Item 2",null);
			child2.initializeDescription();
			child2.addNewFieldAndDescript("Field 2", "The description2");
			
			
			CollectionItem child3 = new CollectionItem(Base,null,true,"Item 3",null);
			child3.initializeDescription();
			child3.addNewFieldAndDescript("Field 1", "The description1");
			child3.addDescription("Field 1", "WOW");
			child3.addNewFieldAndDescript("Field 2", "The description1");
			child3.addNewFieldAndDescript("Field 3", "The description1");
			child3.addNewFieldAndDescript("Field 4", "The description1");
			child3.addNewFieldAndDescript("Field 5", "The description1");
			child3.addNewFieldAndDescript("Field 6", "The description1");
			child3.addNewFieldAndDescript("Field 7", "The description1");
			
			CollectionItem child4 = new CollectionItem(Base,null,false,"List 1",null);
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
		
		Base.addChildren(child1);
		Base.addChildren(child2);
		Base.addChildren(child3);
		Base.addChildren(child4);
		
		
		saveData();
		
		
		}
		printData();
		
	}
	
	public static void saveData(){
	    try {
	    		Log.d("Data","Starting Save");
	    		FileOutputStream fileOutputStream  = CollectorsInventory.getContext().openFileOutput(FILENAME,Context.MODE_APPEND);
			    XmlSerializer serializer = Xml.newSerializer();
			    serializer.setOutput(fileOutputStream, "UTF-8");
			    serializer.startDocument(null, Boolean.valueOf(true));
			    serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
	
			    addCollectionItemsTags(serializer, Base.getChildren(),COLLECTION_ITEM_TAG);
			    
			    serializer.endDocument();
			    serializer.flush();
			    fileOutputStream.close();
		     
		} catch (IllegalArgumentException | IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	    Log.d("Data","Save Complete");
	}

	public static void addCollectionItemsTags(XmlSerializer serializer, ArrayList<CollectionItem> collectionItemList, String name){
		
		for(CollectionItem i: collectionItemList){
	    	try {
				serializer.startTag(null, name);
				serializer.attribute(null, ATTRIBUTE_TAG_NAME, i.toString());
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
			serializer.startTag(null, IS_ITEM_TAG);
			serializer.text(""+i.isItem());
			serializer.endTag(null, IS_ITEM_TAG);
		} catch (IllegalArgumentException | IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addChildrenTag(XmlSerializer serializer, CollectionItem i){
		try {
			serializer.startTag(null, CHILDREN_TAG);
			if(i.getChildren()!= null){
				addCollectionItemsTags(serializer,i.getChildren(),COLLECTION_ITEM_TAG);
			}
			serializer.endTag(null, CHILDREN_TAG);
		} catch (IllegalArgumentException | IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addDescriptionTag(XmlSerializer serializer,CollectionItem i) {
		try {
			serializer.startTag(null, DESCRIPTION_TAG);
			if(i.isItem()){
				for(String field: i.getKeys()){
					serializer.startTag(null, DESCRIPTION_FIELD_TAG);
					serializer.attribute(null, ATTRIBUTE_TAG_NAME, field);
					Log.d("Get Description Error: ","What is returned: "+i.getDescription(field));
					serializer.text(i.getDescription(field));
					serializer.endTag(null, DESCRIPTION_FIELD_TAG);
				}
			}
			
			serializer.endTag(null, DESCRIPTION_TAG);
		} catch (IllegalArgumentException | IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void loadData(){
		
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
	        XmlPullParser xpp = factory.newPullParser();
	        File myXML = getFile();
	        FileInputStream fis = new FileInputStream(myXML);
	        xpp.setInput(fis,null);
	        
	        xpp.nextTag();
	        xpp.require(XmlPullParser.START_TAG, null, COLLECTION_ITEM_TAG);
	        
	        while(xpp.getName().equals(COLLECTION_ITEM_TAG)) {
	        		
	        		Base.setName("Top Level");
	        		Base.setIsItem(false);//init the base object
	        		Base.initializeChildren();
	        		
	        		loadCollectionItemTags(xpp, new CollectionItem(), Base);
	        		xpp.nextTag();//get the next collectionItem tag
	        }
	        
		} catch (XmlPullParserException | IOException e) {
			Log.d("XmlPullParserError","this is not a start tag, or it does not have the name");
			e.printStackTrace();
			
		}
		Context context = CollectorsInventory.getContext();
		CharSequence text = "Load Complete";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
	public static void loadCollectionItemTags(XmlPullParser xpp, CollectionItem child, CollectionItem parent){
		parent.addChildren(child);//add child
		child.setParent(parent);//set parent
		String name = xpp.getAttributeValue(null, ATTRIBUTE_TAG_NAME);
		child.setName(name);
		loadIsItemTag(xpp,child);
		loadChildrenTag(xpp,child);
		loadDescriptionTag(xpp,child);
		Log.d("XmlPullParser", "Completed one cycle.  Name of parent: "+parent.toString());
		try {
			xpp.nextTag();
			xpp.require(XmlPullParser.END_TAG, null, COLLECTION_ITEM_TAG);
		} catch (XmlPullParserException | IOException e) {
			Log.d("XmlPullParserError","next failed");
			e.printStackTrace();
		}//go to the end of this <collectionItem> tag
		
		
		
		
	}
	public static void loadIsItemTag(XmlPullParser xpp, CollectionItem child){
		try {
			xpp.nextTag();//get the isItem tag
			xpp.require(XmlPullParser.START_TAG, null, IS_ITEM_TAG);
			xpp.next();
			String value = xpp.getText();
			if(value.equals("true"))
				child.setIsItem(true);
			else
				child.setIsItem(false);
			
			int state = xpp.next();
			if(state==XmlPullParser.TEXT){
				xpp.next();
				xpp.require(XmlPullParser.END_TAG, null, IS_ITEM_TAG);
			}
			else{
				xpp.require(XmlPullParser.END_TAG, null, IS_ITEM_TAG);
			}
			//xpp.next();//UNECESSARY, BUT NECESSARY BECAUSE OF BUG THAT DOES NOT ADVANCE THE PARSER
			//xpp.nextTag();//move to the end tag
			//xpp.require(XmlPullParser.END_TAG, null, IS_ITEM_TAG);
			
		} catch (XmlPullParserException | IOException e) {
			Log.d("XmlPullParserError","this is not a start tag, or it does not have the name on the load is item");
    		Log.d("XmlPullParserError","Name: "+xpp.getName());
			e.printStackTrace();
			
    		
		}
	}
	public static void loadChildrenTag(XmlPullParser xpp, CollectionItem child){
		try {
			xpp.nextTag();//get the ChildrenTag
			xpp.require(XmlPullParser.START_TAG, null, CHILDREN_TAG);
			while(xpp.nextTag() == XmlPullParser.START_TAG && xpp.getName().equals(COLLECTION_ITEM_TAG)){
			//if(xpp.nextTag() == XmlPullParser.START_TAG){//this means that there are children.  now recursion
        		child.initializeChildren();
        		CollectionItem childChild =  new CollectionItem();
				loadCollectionItemTags(xpp, childChild, child);//Dont need to add to the parent
			}
			
			xpp.require(XmlPullParser.END_TAG, null, CHILDREN_TAG);
			
		} catch (XmlPullParserException | IOException e) {
			Log.d("XmlPullParserError","this is not a start tag, or it does not have the name. on the children tag");
    		Log.d("XmlPullParserError","Name: "+xpp.getName());
			e.printStackTrace();
			
		} 
	}
	
	public static void loadDescriptionTag(XmlPullParser xpp, CollectionItem child) {
		try {
			xpp.nextTag();
			xpp.require(XmlPullParser.START_TAG, null, DESCRIPTION_TAG);
			if(xpp.nextTag() == XmlPullParser.START_TAG && xpp.getName().equals(DESCRIPTION_FIELD_TAG)){
				child.initializeDescription();
				while(!xpp.getName().equals(DESCRIPTION_TAG)){
					String fieldName = xpp.getAttributeValue(null, ATTRIBUTE_TAG_NAME);
					xpp.next();
					String fieldText = xpp.getText();
					child.addNewFieldAndDescript(fieldName, fieldText);
					Log.d("Text Field Added", "Added this field: "+fieldName+" with this tag: "+fieldText);
					xpp.nextTag();//its the end of the DESCRIPTION_FIELD_TAG
					xpp.nextTag();//get either the next DESCRIPTION_FIELD_TAG or the the end of the DESCRIPTION_TAG
				}
			}
		} catch (XmlPullParserException | IOException e) {
			Log.d("XmlPullParserError","this is not a start tag, or it does not have the name on the load description");
    		Log.d("XmlPullParserError","Name: "+xpp.getName());
			e.printStackTrace();
			
		}
		
	}
	
	
	public static boolean deleteData(){
		File file = getFile();
		boolean deleted = file.delete();
		//boolean x = CollectorsInventory.getContext().deleteFile(FILENAME);
		Log.d("did it delete the file",deleted+"");
		return deleted;
	}
	
	public static File getFile(){
		return CollectorsInventory.getContext().getFileStreamPath(FILENAME);
	}
	
	/**
	 * Returns true if the file exists
	 * @return true if the file specified by FileName exists
	 */
	public static boolean savedData(){
		Log.d("The file path", CollectorsInventory.getContext().getFilesDir().toString());
		File file = getFile();
		return file.exists();
		
	}
	
	public static void printData() {
		Log.d("File: ","Look below");
		BufferedReader reader = null;
		try {
			if(savedData()){
				
				File file = getFile();
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
