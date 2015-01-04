package com.rgw3d.collectors;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TODO: document your custom view class.
 */
@SuppressLint("NewApi")
public class CustomDetailArrayAdapter extends ArrayAdapter<ArrayList<String>> {
	
	// declaring our ArrayList of items
		private ArrayList<ArrayList<String>> objects;
		private Context context;
		private CollectionItem rootItem;
		private final int THUMBSIZE = 504;

		/* here we must override the constructor for ArrayAdapter
		* the only variable we care about now is ArrayList<Item> objects,
		* because it is the list of objects we want to display.
		*/
		public CustomDetailArrayAdapter(Context context, int textViewResourceId, ArrayList<ArrayList<String>> objects) {
			super(context, textViewResourceId, objects);
			this.objects = objects;
			this.context = context;
			this.rootItem = CollectionItem.findChildObject(((ItemDetailActivity)context).getRoot().hashCode(),CollectionDataStorage.Base);
			Log.d("root in array adapter: ",rootItem.toString());
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
			
			if(v!= null){
				v.setOnLongClickListener(new LongClickViewListener(position));
			}

			/*
			 * Recall that the variable position is sent in as an argument to this method.
			 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
			 * iterates through the list we sent it)
			 * 
			 * Therefore, i refers to the current Item object.
			 */
			final String i = objects.get(position).get(0);
			final String o = objects.get(position).get(1);

			if (i != null && o!= null) {
				
				if(i.equals(ItemDetailFragment.IMAGE_FILE)){//we have an image!
					Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(o), THUMBSIZE, THUMBSIZE);//grab thumbnail
					ImageView iv = (ImageView) v.findViewById(R.id.imageView1);
					
					if(iv != null){
						iv.setImageBitmap(ThumbImage);
						
						iv.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								//context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("image:/"+o)));
								
								Intent intent = new Intent();
								intent.setAction(Intent.ACTION_VIEW);
								intent.setDataAndType(Uri.parse("file://" + o), "image/*");
								context.startActivity(intent);
								
								
							}
						});
						
					}
				}
				else {//we have text
					

					// These TextViews are created in the XML files we defined.
	
					TextView tv = (TextView) v.findViewById(R.id.text_view_title);
					// check to see if each individual textview is null.
					// if not, assign some text!
					if (tv != null){
						tv.setText(i);
						tv.setOnLongClickListener(new LongClickTextListener(position,0));
					}
					
					
					TextView tv2 = (TextView) v.findViewById(R.id.text_view_description);
					
					if(tv2 != null){
						tv2.setText(o);
						tv2.setOnLongClickListener(new LongClickTextListener(position,1));
					}
				}
				
			}

			// the view must be returned to our activity
			return v;
		}
		
		public class LongClickViewListener implements View.OnLongClickListener, OnClickListener{
			int position;
			public LongClickViewListener(int position){
				this.position = position;
			}
			
			public boolean onLongClick(View v){
				
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				
				alert.setTitle("Options");
				
				String [] options = {"Delete","Option1","Option 2"};
				
				alert.setItems(options, new LongClickViewListener(position));
				
				alert.show();
				
				return true;
			}

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(which == 0){
					//delete the view
					ArrayList<String> toRemove = new ArrayList<String>();
					toRemove.add(objects.get(position).get(0));
					toRemove.add(objects.get(position).get(1));
					remove(toRemove);
				}
				
			}
		}
		
		/**
		 * This is where the long click listener for the Text Displays methods are implemented.  onLongClick() is the method
		 * of most importance
		 * 
		 * This class also defines an alert
		 * @author rgw3d
		 */
		public class LongClickTextListener implements View.OnLongClickListener{
			
			public int pos;
			public int isDescription;
			//public CustomArrayAdapter context;
			
			public LongClickTextListener(int pos, int isDescription){
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
            	
            	AlertDialog.Builder alert = new AlertDialog.Builder(context);//make an Alert so the information can be edited

            	if(isDescription == 0){
            		alert.setTitle("Edit Field: \n"+tv.getText());
            	}
            	
            	else if(isDescription == 1){
            		alert.setTitle("Edit Description: \n" + tv.getText());
            	}

            	// Set an EditText view to get user input 
            	final EditText input = new EditText(context);
            	input.setHint(tv.getText());//create the variable EditText input and tell it to set the hint to be what was clicked on
            	
            	alert.setView(input);//display the edit text field

            	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {//here we are defining what happens if the "Ok" button is pressed
            	public void onClick(DialogInterface dialog, int whichButton) {//the changes need to be seen here and in the root object
            		if(input.getText().toString().equals("")){
            			//do nothing
            		}
            		else{
            			Log.d("Orig Key Set: ", rootItem.printDescriptionKeys());
	            		if(isDescription == 0){//change the title/key
	            			changeKey( pos,input.getText().toString());
	            		}
	            		else if (isDescription ==1 ){//change the description
	            			changeDescription(pos,input.getText().toString());
	            		}
	            		Log.d("Changed Key Set: ", rootItem.printDescriptionKeys());
            		}
            	  
            	  }
            	});

            	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {//define the cancel activity
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
		    rootItem.addNewFieldAndDescript(newData.get(0), newData.get(1));
		    CollectionDataStorage.saveCycle();
		    notifyDataSetChanged();
		}

		@Override
		public void insert(ArrayList<String> newData, int index) {
		    objects.add(index, newData);
		    CollectionDataStorage.saveCycle();
		    notifyDataSetChanged();
		}

		@Override
		public void remove(ArrayList<String> newData) {
		    objects.remove(newData);
		    rootItem.removeDescription(newData.get(0));
		    CollectionDataStorage.saveCycle();
		    notifyDataSetChanged();
		}
		
		/**
		 * 
		 * @param index which field/description pair in the objects array
		 */
		public void changeDescription( int index, String newData){
			String oldData = objects.get(index).get(1);
			objects.get(index).remove(oldData);
			objects.get(index).add(newData);
			rootItem.changeDescription(objects.get(index).get(0),objects.get(index).get(1));//send the key and the description
			CollectionDataStorage.saveCycle();
			notifyDataSetChanged();
		}
		
		public void changeKey( int index, String newKey){
			String descriptionData = objects.get(index).get(1);//get the description( this data does not change)
			String oldKey = objects.get(index).get(0);
			objects.get(index).remove(oldKey);//remove the old title in the local object
			objects.get(index).add(0, newKey);//add the new key to the local object
			rootItem.changeKey(oldKey,newKey);
			CollectionDataStorage.saveCycle();
			notifyDataSetChanged();
			
		}
		

		@Override
		public void clear() {
		    objects.clear();
		    CollectionDataStorage.saveCycle();
		    notifyDataSetChanged();
		}

		
		
		
}

