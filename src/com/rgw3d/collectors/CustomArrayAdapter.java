package com.rgw3d.collectors;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * TODO: document your custom view class.
 */
public class CustomArrayAdapter extends ArrayAdapter<String>{
	
	// declaring our ArrayList of items
		private ArrayList<String> objects;
		private Context context;

		/* here we must override the constructor for ArrayAdapter
		* the only variable we care about now is ArrayList<Item> objects,
		* because it is the list of objects we want to display.
		*/
		public CustomArrayAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
			super(context, textViewResourceId, objects);
			this.objects = objects;
			this.context = context;
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
			String i = objects.get(position);

			if (i != null) {

				// This is how you obtain a reference to the TextViews.
				// These TextViews are created in the XML files we defined.

				TextView tv = (TextView) v.findViewById(R.id.text_view);

				// check to see if each individual textview is null.
				// if not, assign some text!
				if (tv != null){
					tv.setText(Html.fromHtml(i));
					 tv.setOnLongClickListener(new OnLongClickListener(){

			                @Override 
			                public boolean onLongClick(View v) {
			                    // Do the stuff you want for the case when the row TextView is clicked
			                    // you may want to set as the tag for the TextView the position paremeter of the `getView` method and then retrieve it here
			                    //Integer realPosition = (Integer) v.getTag();
			                    // using realPosition , now you know the row where this TextView was clicked
			                	//Toast.makeText(context, "Item "  + " was clicked", Toast.LENGTH_SHORT).show();
			                	TextView tv = (TextView)v;
			                	AlertDialog.Builder alert = new AlertDialog.Builder(context);

			                	alert.setTitle("Edit Field"+tv.getText());
			                	alert.setMessage("A blank description destroyes this field");

			                	// Set an EditText view to get user input 
			                	final EditText input = new EditText(context);
			                	input.setHint(tv.getText());
			                	alert.setView(input);

			                	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			                	public void onClick(DialogInterface dialog, int whichButton) {

			                	  // Do something with value!
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
					 });
				}
			}

			// the view must be returned to our activity
			return v;

		}
}

