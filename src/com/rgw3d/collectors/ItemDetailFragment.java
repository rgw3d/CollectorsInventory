package com.rgw3d.collectors;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;


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
	
	public void addNewDescription(){
		ArrayList<String> newData = new ArrayList<String>();
		newData.add("Field Title");
		newData.add("Description");
		adapter.add(newData);
		
	}
	

}
	

