package com.rgw3d.collectors;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ListFragment;
import android.util.Log;


/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
@SuppressLint("NewApi")
public class ItemDetailFragment extends ListFragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_HASH = "item_hash";

	/**
	 * The CollectionItem that is specified
	 */
	private CollectionItem root;
	
	private CustomDetailArrayAdapter adapter;
	
	final int REQUEST_IMAGE_CAPTURE = 1;
	
	public static final String IMAGE_FILE = "IMAGE_FILE";

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey(ARG_ITEM_HASH)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			root = CollectionItem.findChildObject(getArguments().getInt(ARG_ITEM_HASH),CollectionDataStorage.Base);
			if(root == null){
				Log.e("Error in ItemDetailFragment", "mItem is null.  Hashcode sent was wrong");
			}
			Log.d("Orig Key Set after root is created: ", root.printDescriptionKeys());
			
			adapter = new CustomDetailArrayAdapter(getActivity(),
			        android.R.layout.simple_list_item_activated_1, 
			        root.getKeyAndDescription());
			setListAdapter(adapter);
			
		}
	}
	
	public void addNewDescription(String title, String description){
		ArrayList<String> newData = new ArrayList<String>();
		newData.add(title);
		newData.add(description);
		adapter.add(newData);
		
	}

	public void addImage() {
		//this is where I will be handing the intnent and 
		
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    
	    if(takePictureIntent.resolveActivity(getActivity().getPackageManager())!= null){
	    	File photoFile = null;
	        try {
	            photoFile = createImageFile();
	        } catch (IOException ex) {
	            // Error occurred while creating the File
	        }
	        
	        if (photoFile != null) {
	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
	            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	        }
	    }
	    
		
	}
	
	String mCurrentPhotoPath;

	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES);
	    File image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    mCurrentPhotoPath = image.getAbsolutePath();
	    return image;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
	        addNewDescription(IMAGE_FILE,mCurrentPhotoPath);
	        runMediaScanner();
	        
	    }
	}
	
	private void runMediaScanner() {
	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    File f = new File(mCurrentPhotoPath);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    getActivity().sendBroadcast(mediaScanIntent);
	}
	
	
	

}
	

