package com.rgw3d.collectors;

import com.rgw3d.collectors.CustomListArrayAdapter.LongClickListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


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
	
	public CollectionItem Root;
	
	public ArrayAdapter<CollectionItem> adapter;
	
	
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
		
		if (getArguments() != null && getArguments().containsKey(ARG_ITEM_HASH)) {
			Log.d("Starting List Fragment","It has the key ARG_ITEM_HASH");
			
			Root = CollectionItem.findChildObject(getArguments().getInt(ARG_ITEM_HASH),CollectionDataStorage.Base);
			if(Root == null){
				Log.e("Error in ItemDetailFragment", "mItem is null.  Hashcode sent was wrong");
			}
		}
		
		else{
			Log.d("Starting List Fragment", "it does not have the key");
			Root = CollectionDataStorage.Base;
		}
		
		//CustomListArrayAdapter adapter = new CustomListArrayAdapter(getActivity(),R.layout.custom_array_adapter_list_view,R.id.text_view_title,Root.getChildren());
		
		//setListAdapter(adapter);
		
		ArrayAdapter<CollectionItem> adapter = new ArrayAdapter<CollectionItem>(getActivity(),R.layout.custom_array_adapter_list_view,R.id.text_view_title,Root.getChildren());
		
		setListAdapter(adapter);
		
		this.adapter = adapter;
		
		Log.d("Starting List Fragment","Created!");
		
	}
	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		// Restore the previously serialized activated item position.
		if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
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
		mCallbacks.onItemSelected(Root.getChildren(position).isItem(),Root.getChildren(position).hashCode());
		//this calls the method from the Activity class above it
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

	public void addNewList() {
		
		Log.d("This was called","lol");
    	
    	AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());//make an Alert so the information can be edited

		alert.setTitle("Set Title");
		

    	// Set an EditText view to get user input 
    	final EditText input = new EditText(getActivity());
    	input.setHint("List Name");//create the variable EditText input and tell it to set the hint to be what was clicked on
    	
    	alert.setView(input);//display the edit text field

    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(!input.getText().toString().equals("")){
					CollectionItem child = new CollectionItem(Root,null,false,input.getText().toString(),null);
					child.initializeChildren();
					Root.addChildren(child);
					adapter.notifyDataSetChanged();
				}
				else {
					//do nothing, no text means nothing to create
				}
				
			}
		});
		alert.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//do nothing.  this is the cancel button
				
			}
		});
		
		alert.show();
	}
	
	public void addNewItem(){
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());//make an Alert so the information can be edited

		alert.setTitle("Set Name");
		

    	// Set an EditText view to get user input 
    	final EditText input = new EditText(getActivity());
    	input.setHint("Item Name");//create the variable EditText input and tell it to set the hint to be what was clicked on
    	
    	alert.setView(input);//display the edit text field

    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(!input.getText().toString().equals("")){
					CollectionItem child = new CollectionItem(Root,null,true,input.getText().toString(),null);
					child.initializeDescription();
					Root.addChildren(child);
					adapter.notifyDataSetChanged();
				}
				else {
					//do nothing, no text means nothing to create
				}
				
			}
		});
		alert.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//do nothing.  this is the cancel button
				
			}
		});
		
		alert.show();
	}
}
