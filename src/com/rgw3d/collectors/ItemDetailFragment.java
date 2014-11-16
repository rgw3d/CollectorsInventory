package com.rgw3d.collectors;

import com.rgw3d.collectors.dummy.DummyContent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.*;


/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemDetailFragment extends ListFragment implements OnItemClickListener, OnItemLongClickListener {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_HASH = "item_hash";

	/**
	 * The CollectionItem that is specified
	 */
	private CollectionItem root;
	private CustomArrayAdapter adapter;

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
			root = CollectionItem.findChildObject(getArguments().getInt(ARG_ITEM_HASH),DummyContent.x);
			if(root == null){
				Log.e("Error in ItemDetailFragment", "mItem is null.  Hashcode sent was wrong");
			}
			
			//adapter = new CustomArrayAdapter(getActivity(),
			 //       android.R.layout.simple_list_item_1, root.getHTMLDescription());
			adapter = new CustomArrayAdapter(getActivity(),
			       android.R.layout.simple_list_item_1, root.getHTMLDescription());
			
			setListAdapter(adapter);
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedState) {
	    super.onActivityCreated(savedState);
    	Log.d("Long Click","Okay so it should be creating the listener here");
    	getListView().setOnItemClickListener(this);
	    getListView().setOnItemLongClickListener(this);
	}
	
	public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long id) {
		Log.d("Long click","Wow it actually worked");
        
        return true;
    }
	
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		//super.onListItemClick(arg0, v, position, id);
		Toast.makeText(getActivity(), "Item " + position + " was clicked", Toast.LENGTH_SHORT).show();
		Log.d("Long Click", "There was a click");
	}
}

	
/*
new OnItemLongClickListener() {

	        public boolean onItemLongClick(AdapterView<?> arg0, View v,
	                int position, long id) {
	            
	            return true;
	        }
	    }



 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_item_detail,
				container, false);
		
		if(root != null) {
			Log.d("Detail Fragment","It is making the views");
			ListView lv = (ListView) rootView.findViewById(R.id.listView1);			
			adapter = new CustomArrayAdapter(getActivity(),
			        android.R.layout.simple_list_item_1, root.getHTMLDescription());
			
			lv.setAdapter(adapter);
		}

		return rootView;
	}
	*/
	
	

