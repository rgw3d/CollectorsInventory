package com.rgw3d.collectors;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rgw3d.collectors.dummy.DummyContent;

/**
 * A list fragment representing a list of Items. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link ItemDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ItemListFragment extends ListFragment {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	public static String ARG_ITEM_HASH = "item_hash";
	
	public static CollectionItem Root;
	
	
	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(boolean isItem, int hashCode);
	}

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(boolean isItem, int hashCode) {
		}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Debug","It managed to get here");
		/*
		if (savedInstanceState.getInt(ARG_ITEM_HASH)!=0) {
			Log.d("Debug","It has the key ARG_ITEM_HASH");
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			Root = findChildObject(savedInstanceState.getInt(ARG_ITEM_HASH),DummyContent.x);
			Log.d("Debug",Root.toString());
			if(Root == null){
				Log.e("Error in ItemDetailFragment", "mItem is null.  Hashcode sent was wrong");
			}
			//mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
			//		ARG_ITEM_PREFIX));
		}
		*/
		//else{
			Log.d("Debug", "it does not have the key");
			Root = DummyContent.x;
		//}
			
		
		setListAdapter(new ArrayAdapter<CollectionItem>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, Root.getChildren()));
		
		
	}
	
	/**
	 * This method is designed to recursivly search through all of
	 * the CollectionItem items and find the correct item that was sent over
	 * via the hashcode parameter
	 * @param hashCode - specifies what object to choose
	 * @param root - this specifies what CollectionItem object is the root
	 * @return correct CollectionItem specified by hashcode
	 */
	private CollectionItem findChildObject(int hashCode, CollectionItem root) {
		
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
	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		
		
		//have it look at the current item on display, check its children and get their get(position) and from there use their id.  
		
		mCallbacks.onItemSelected(Root.getChildren(position).isItem(),Root.getChildren(position).hashCode());//this calls the method from the Activity class above it
		//DummyContent.ITEMS.get(position).id
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
}
