package com.rgw3d.collectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;


public class CollectionItem {
	/*
	 * In this class I want to be able to make objects that have 
	 * the ability to  either store more information, or to hold an undefined amount of
	 * information about the objects (of this type) below it.  
	 */
	

	private String Name = "Detail 1";
	private boolean IsItem = true;
	private ArrayList<CollectionItem> Children = null;
	private Map<String, ArrayList<String>> Description = null;
	private ArrayList<String> Keys = null;
	private CollectionItem Parent = null;
	public int Position=0;//something that might be implemented later to make sure that everything is displayed correctly
	
	//initialize the default values.
	public CollectionItem(CollectionItem parent, 
			ArrayList<CollectionItem> children, 
			boolean isItem, 
			String name, 
			Map<String, ArrayList<String>> description){
		Parent = parent;
		Children = children;
		IsItem = isItem;
		Name = name;
		Description = description;
		
		
	}
	
	public CollectionItem(){
		
	}
	public void addParent(CollectionItem parent){
		Parent = parent;
	}
	public void initializeChildren(){
		if(Children==null)
			Children = new ArrayList<CollectionItem>();
	}
	public void addChildren(CollectionItem child){
		Children.add(child);
		child.Position = Children.indexOf(child);
	}
	public boolean isItem(){
		return IsItem;
	}
	public void setIsItem(boolean x){
		IsItem = x;
	}
	public void setName(String name){
		Name = name;
	}
	public void setParent(CollectionItem parent){
		Parent = parent;
	}
	public void initializeDescription(){
		Description = new HashMap<String, ArrayList<String>>();
		Keys = new ArrayList<String>();
	}
	public void addNewFieldAndDescript(String field,String dscrpt){
		ArrayList<String> descriptions = new ArrayList<String>();
		descriptions.add(dscrpt);
		Description.put(field, descriptions);
		Keys.add(field);
	}
	public void addNewFieldAndDescript(String field,ArrayList<String> dscrpt){
		Description.put(field, dscrpt);
		Keys.add(field);
	}
	public void addDescription(String key, String dscrpt){
		if(Description.containsKey(key))
			Description.get(key).add(dscrpt);
	}
	public void changeDescription(String key, String dscrpt,int index){
		if(Description.containsKey(key))
			Description.get(key).set(index, dscrpt);
	}
	
	public ArrayList<String> getKeys(){
		return Keys;
	}
	
	public ArrayList<CollectionItem> getChildren(){
		return Children;
	}
	public CollectionItem getChildren(int indx){
		return Children.get(indx);
	}
	
	public CollectionItem getParent(){
		return Parent;
	}
	
	
	public Map<String,ArrayList<String>> getDescription(){
		
		return Description;
	}
	
	
	
	@Override
	public String toString(){
		return Name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((Children == null) ? 0 : Children.hashCode());
		result = prime * result
				+ ((Description == null) ? 0 : Description.hashCode());
		result = prime * result + (IsItem ? 1231 : 1237);
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + Position;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			return true;
		if (obj == null) 
			return false;
		if (!(obj instanceof CollectionItem)) 
			return false;
		CollectionItem other = (CollectionItem) obj;
		if (Children == null) {
			if (other.Children != null) {
				return false;
			}
		} else if (!Children.equals(other.Children)) {
			return false;
		}
		if (Description == null) {
			if (other.Description != null) {
				return false;
			}
		} else if (!Description.equals(other.Description)) {
			return false;
		}
		if (IsItem != other.IsItem) {
			return false;
		}
		if (Name == null) {
			if (other.Name != null) {
				return false;
			}
		} else if (!Name.equals(other.Name)) {
			return false;
		}
		if (Parent == null) {
			if (other.Parent != null) {
				return false;
			}
		} else if (!Parent.equals(other.Parent)) {
			return false;
		}
		if (Position != other.Position) {
			return false;
		}
		return true;
	}

	
	/**
	 * This method is designed to recursivly search through all of
	 * the CollectionItem items and find the correct item that was sent over
	 * via the hashcode parameter
	 * @param hashCode - specifies what object to choose
	 * @param root - this specifies what CollectionItem object is the root
	 * @return correct CollectionItem specified by hashcode
	 */
	public static CollectionItem findChildObject(int hashCode, CollectionItem root) {
		if(root.hashCode() == hashCode)
			return root;
		for(CollectionItem x: root.getChildren()){
			if(x.hashCode() == hashCode)
				return x;
			else if(!x.isItem()){
				CollectionItem tmp = findChildObject(hashCode,x);
				if(tmp!=null)
					return tmp;
			}
		}
		return null;
		
	}
	
	public static CollectionItem findParentObject(int hashCode, CollectionItem root){
		CollectionItem match = findChildObject(hashCode,root);
		if(match!=null)
			return match.getParent();
		else
			return null;
	}
	
	public ArrayList<String> getHTMLDescription(){
		ArrayList<String> stringDescript = new ArrayList<String>();
		for(String key: Description.keySet().toArray(new String[0])){//add each field
			Log.d("Detail Fragment","The key: "+key);
			String paragraphs = "";
			for(String descript: Description.get(key)){//add each description under the field
				Log.d("Detail Fragment","The descript: "+descript);
				
				paragraphs+="<small>"+descript+"</small> <br />";
			}
			paragraphs = paragraphs.substring(0, paragraphs.length()-7);
			stringDescript.add("<b>"+key+"</b> <br />" + paragraphs);
		}
		return stringDescript;
	}
	
	public ArrayList<ArrayList<String>> getKeyAndDescription(){
		String key[] = Description.keySet().toArray(new String[0]);
		Log.d("Key length","Key legnth: "+	key.length);
		ArrayList<ArrayList<String>> resultant = new ArrayList<ArrayList<String>>();
		for(int x = 0; x<key.length; x++){
			ArrayList<String> tmp = new ArrayList<String>();
			tmp.add(key[x]);
			resultant.add(tmp);
			resultant.get(x).add(getDescription(key[x]));
		}
		return resultant;
	}
	public String getDescription(String key){
		ArrayList<String> x = Description.get(key);
		String toReturn = "";
		for(String y: x){
			toReturn+=y+"\n";
		}
		return toReturn.substring(0,toReturn.length()-1);
	}
	public String printDescriptionKeys(){
		String resultant = "";
		for(String x: Description.keySet()){
			resultant.concat(x+"\n");
		}
		return resultant;
	}
	
	
	
	
	
}
