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
	
	private CollectionItem Parent = null;
	//public
	private ArrayList<CollectionItem> Children = null;
	private boolean IsItem = true;
	private String Name = "Detail 1";
	private Map<String, ArrayList<String>> Description = null;
	public int Position=0;
	
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
	public void initializeDescription(){
		Description = new HashMap<String, ArrayList<String>>();
	}
	public void addNewFieldAndDescript(String field,String dscrpt){
		ArrayList<String> descriptions = new ArrayList<String>();
		descriptions.add(dscrpt);
		Description.put(field, descriptions);
	}
	public void addNewFieldAndDescript(String field,ArrayList<String> dscrpt){
		Description.put(field, dscrpt);
	}
	public void addDescription(String key, String dscrpt){
		if(Description.containsKey(key))
			Description.get(key).add(dscrpt);
	}
	public void changeDescription(String key, String dscrpt,int index){
		if(Description.containsKey(key))
			Description.get(key).set(index, dscrpt);
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
		Log.d("Find Child Objects","Root Name: "+root.toString());
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
	
	
	
}
