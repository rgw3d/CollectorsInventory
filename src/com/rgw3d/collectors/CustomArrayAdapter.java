package com.rgw3d.collectors;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

/**
 * TODO: document your custom view class.
 */
public class CustomArrayAdapter extends ArrayAdapter<ArrayList<String>>{
	
	// declaring our ArrayList of items
		private ArrayList<ArrayList<String>> objects;
		private Context context;
		private CollectionItem root;

		/* here we must override the constructor for ArrayAdapter
		* the only variable we care about now is ArrayList<Item> objects,
		* because it is the list of objects we want to display.
		*/
		public CustomArrayAdapter(Context context, int textViewResourceId, ArrayList<ArrayList<String>> objects) {
			super(context, textViewResourceId, objects);
			this.objects = objects;		
			this.context = context;
			this.root = ((ItemDetailActivity)context).getRoot();
			Log.d("root in array adapter: ",root.toString());
		}

		/*
		 * we are overriding the getView method here - this is what defines how each
		 * list item will look.
		 */
		public View getView(int position, View convertView, ViewGroup parent){

			// assign the view we are converting to a local variable
			View v = convertView;

			// first check to see if the view is null. if so, we have to inflate it.
			// to inflate it basically means to render, or show, the view.
			if (v == null) {
				LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.sample_custom_array_adapter, null);
			}

			/*
			 * Recall that the variable position is sent in as an argument to this method.
			 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
			 * iterates through the list we sent it)
			 * 
			 * Therefore, i refers to the current Item object.
			 */
			String i = objects.get(position).get(0);
			String o = objects.get(position).get(1);

			if (i != null && o!= null) {

				// This is how you obtain a reference to the TextViews.
				// These TextViews are created in the XML files we defined.

				TextView tv = (TextView) v.findViewById(R.id.text_view_title);
				// check to see if each individual textview is null.
				// if not, assign some text!
				if (tv != null){
					tv.setText(i);
					tv.setOnLongClickListener(new LongClickListener(position,0));
				}
				
				
				TextView tv2 = (TextView) v.findViewById(R.id.text_view_description);
				
				if(tv2 != null){
					tv2.setText(o);
					tv2.setOnLongClickListener(new LongClickListener(position,1));
				}
				
			}

			// the view must be returned to our activity
			return v;

		}
		
		public class LongClickListener implements View.OnLongClickListener{
			
			public int pos;
			public int isDescription;
			//public CustomArrayAdapter context;
			
			public LongClickListener(int pos, int isDescription){
				this.pos = pos;
				this.isDescription = isDescription;
				//this.context = context;
			}

			@Override
			public boolean onLongClick(View v) {
				// Do the stuff you want for the case when the row TextView is clicked
                // you may want to set as the tag for the TextView the position paremeter of the `getView` method and then retrieve it here
                //Integer realPosition = (Integer) v.getTag();
                // using realPosition , now you know the row where this TextView was clicked
            	//Toast.makeText(context, "Item "  + " was clicked", Toast.LENGTH_SHORT).show();
            	
            	TextView tv = (TextView)v;
            	AlertDialog.Builder alert = new AlertDialog.Builder(context);

            	if(isDescription ==0){
            		alert.setTitle("Edit Field: \n"+tv.getText());
            		
            	}
            	else if(isDescription ==1){
            		alert.setTitle("Edit Description: \n" + tv.getText());
            	}
            	
            	alert.setMessage("A blank description destroyes this field");
            	

            	// Set an EditText view to get user input 
            	final EditText input = new EditText(context);
            	input.setHint(tv.getText());
            	alert.setView(input);

            	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int whichButton) {
            		if(input.getText().toString().equals("")){
            			if(objects.size() >1)//only if there is more than one oject it will be removed
            				remove(objects.get(pos));
            		}
            		else{
            			Log.d("Orig Key Set: ", root.printDescriptionKeys());
	            		if(isDescription ==0){//change the title
	            			String toRemove = objects.get(pos).get(isDescription);
	            			objects.get(pos).remove(toRemove);
	            			objects.get(pos).add(0,input.getText().toString());
	            			
	            			root.addDescription(input.getText().toString(), objects.get(pos).get(1));//add new key and descript
	            			root.getDescription().remove(objects.get(pos).get(0));//remove key
	            			
	            			
	            			notifyDataSetChanged();
	            		}
	            		else if (isDescription ==1 ){
	            			String toRemove = objects.get(pos).get(isDescription);
	            			objects.get(pos).remove(toRemove);
	            			objects.get(pos).add(1,input.getText().toString());
	            			
	            			root.addDescription(objects.get(pos).get(0),input.getText().toString());//add new key and descript
	            			root.getDescription().remove(objects.get(pos).get(0));//remove key
	            			
	            			
	            			notifyDataSetChanged();
	            		}
	            		Log.d("Changed Key Set: ", root.printDescriptionKeys());
            		}
            		
            	  
            	  }
            	});

            	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            	  public void onClick(DialogInterface dialog, int whichButton) {
            	    // Canceled.
            	  }
            	});

            	alert.show();
            	
            	return true;
            }
			
		}
		
		@Override
		public void add(ArrayList<String> newData) {
		    objects.add(newData);
		    notifyDataSetChanged();
		}

		@Override
		public void insert(ArrayList<String> newData, int index) {
		    objects.add(index, newData);
		    notifyDataSetChanged();
		}

		@Override
		public void remove(ArrayList<String> newData) {
		    objects.remove(newData);
		    notifyDataSetChanged();
		}

		@Override
		public void clear() {
		    objects.clear();
		    notifyDataSetChanged();
		}
}

