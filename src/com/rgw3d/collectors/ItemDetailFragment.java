package com.rgw3d.collectors;

import java.util.ArrayList;
import java.util.Map;

import com.rgw3d.collectors.dummy.DummyContent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_HASH = "item_hash";

	/**
	 * The CollectionItem that is specified
	 */
	private CollectionItem mItem;
	
	public CollectionItem getmItem(){
		return mItem;
	}

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
			mItem = CollectionItem.findChildObject(getArguments().getInt(ARG_ITEM_HASH),DummyContent.x);
			if(mItem == null){
				Log.e("Error in ItemDetailFragment", "mItem is null.  Hashcode sent was wrong");
			}
			//mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
			//		ARG_ITEM_PREFIX));
		}
	}

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_item_detail,
				container, false);
		
		if(mItem != null) {
			Log.d("Detail Fragment","It is making the views");
			ListView lv = (ListView) rootView.findViewById(R.id.listView1);
			
			ArrayList<String> stringDescript = new ArrayList<String>();//to be passed to arrayAdapter
			
			Map<String, ArrayList<String>> Description = mItem.getDescription();
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
			CustomArrayAdapter adapter = new CustomArrayAdapter(getActivity(),
			        android.R.layout.simple_list_item_1, stringDescript);
			
			lv.setAdapter(adapter);
		}

		return rootView;
	}
}
