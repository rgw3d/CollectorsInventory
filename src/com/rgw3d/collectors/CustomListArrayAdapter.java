package com.rgw3d.collectors;

import java.util.ArrayList;
import java.util.List;

import com.rgw3d.collectors.CustomDetailArrayAdapter.LongClickTextListener;
import com.rgw3d.collectors.CustomDetailArrayAdapter.LongClickViewListener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class CustomListArrayAdapter extends ArrayAdapter<CollectionItem>{
	
	private ArrayList<CollectionItem> objects;
	private Context context;
	private CollectionItem rootItem;
	private int resource;
	private int textViewResourceId;

	public CustomListArrayAdapter(Context context, int resource,
			int textViewResourceId, ArrayList<CollectionItem> objects) {
		super(context, resource, textViewResourceId, objects);
		
		this.objects = objects;
		this.context = context;
		this.rootItem = CollectionItem.findChildObject(((ItemListActivity)context).getRoot().hashCode(),CollectionDataStorage.Base);
		this.resource = resource;
		this.textViewResourceId = textViewResourceId;
		Log.d("root in array adapter: ",rootItem.toString());
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent){

		// assign the view we are converting to a local variable
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		// to inflate it basically means to render, or show, the view.
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(resource, null);
		}
		
		if(v!= null){
			v.setOnLongClickListener(new LongClickListener(position,true));
			/*v.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					((ItemListFragment)context).onListItemClick(position);
					
				}
				
			}
			*/
		}
		
		String i = objects.get(position).toString();

		if (i != null) {
			TextView tv = (TextView) v.findViewById(textViewResourceId);
			// check to see if each individual textview is null.
			// if not, assign some text!
			if (tv != null){
				tv.setText(i);
				tv.setOnLongClickListener(new LongClickListener(position,false));
			}
		}
		
		return v;
	}
	
	public class LongClickListener implements View.OnLongClickListener, OnClickListener{
		int position;
		boolean isViewListener = false;
		EditText input = null;
		public LongClickListener(int position, boolean isViewListener){
			this.position = position;
			this.isViewListener = isViewListener;
		}
		public LongClickListener(int position, EditText input){
			this.position = position;
			this.isViewListener = false;
			this.input = input;
		}
		
		@Override
		public boolean onLongClick(View v) {
			if(isViewListener){
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				
				alert.setTitle("Options");
				
				String [] options = {"Delete","Option1","Option 2"};
				
				alert.setItems(options, new LongClickListener(position, true));
				
				alert.show();
				
				return true;
			}
			else{
				TextView tv = (TextView)v;
            	
            	AlertDialog.Builder alert = new AlertDialog.Builder(context);//make an Alert so the information can be edited

        		alert.setTitle("Edit Title: \n" + tv.getText());

            	// Set an EditText view to get user input 
            	final EditText input = new EditText(context);
            	input.setHint(tv.getText());//create the variable EditText input and tell it to set the hint to be what was clicked on
            	
            	alert.setView(input);//display the edit text field

            	alert.setPositiveButton("Ok", new LongClickListener(position, input));
    			alert.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//do nothing.  this is the cancel button
						
					}
				});
    			return true;
			}
		}
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if(isViewListener){
				if(which == 0)
				{
					remove(objects.get(position));
				}
			
			}
			else{
				if(input!= null && !input.getText().equals("")){
					//change the name of this object
					changeName(objects.get(position),input.getText().toString(),position);
				}
			}
			
		}
	}
	
	public class LongClickTextListener{
		public LongClickTextListener(){
			
		}
	}
	
	@Override
	public void remove(CollectionItem item){
		objects.remove(item);
		rootItem.removeChildren(item);
		CollectionDataStorage.saveCycle();
	    notifyDataSetChanged();
	}
	
	public void changeName(CollectionItem item, String newName,int position){
		item.setName(newName);
		rootItem.getChildren().get(position).setName(newName);
		CollectionDataStorage.saveCycle();
	    notifyDataSetChanged();
	}

}
