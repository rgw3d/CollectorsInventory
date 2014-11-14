package com.rgw3d.collectors;


import com.rgw3d.collectors.dummy.DummyContent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

/**
 * An activity representing a list of Items. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ItemDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details (if present) is a
 * {@link ItemDetailFragment}.
 * <p>
 * This activity also implements the required {@link ItemListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class ItemListActivity extends ActionBarActivity implements
		ItemListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	
	private CollectionItem Root = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);
		
		
			Bundle arguments = new Bundle();
			
			if(getIntent()!= null && getIntent().hasExtra(ItemListFragment.ARG_ITEM_HASH)){
				arguments.putInt(ItemListFragment.ARG_ITEM_HASH, 
					getIntent().getExtras()
					.getInt(ItemListFragment.ARG_ITEM_HASH));
				
				Root = CollectionItem.findChildObject(getIntent().getExtras().getInt(ItemListFragment.ARG_ITEM_HASH), DummyContent.x);
				if(Root == DummyContent.x)
					Root = null;
			}
			
			ItemListFragment fragment = new ItemListFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
				.add(R.id.item_list_container, fragment).commit();
		
			

		
		
		if (findViewById(R.id.item_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ItemListFragment) getSupportFragmentManager().findFragmentById(
					R.id.item_list)).setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
		
		//display the up arrow if applicable
		if(Root!= null){
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			Log.d("Action bar","it will display");
			Log.d("Action bar","Name of root object: "+Root.toString());
		}
		else{
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			Log.d("Action bar","Will not be displayed");
		}
			
	}
	
	

	/**
	 * Callback method from {@link ItemListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(boolean isItem, int hashCode) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putInt(ItemDetailFragment.ARG_ITEM_HASH, hashCode);
			ItemDetailFragment fragment = new ItemDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.item_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			
			if(isItem){
				Intent detailIntent = new Intent(this, ItemDetailActivity.class);
				detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_HASH, hashCode);  //this starts the new activity, and it sets the argument ID to the name
				//of the fragment that it wants to start
				startActivity(detailIntent);
			}
			else{//TODO: add implementaiton of going to another list
				Intent listIntent = new Intent(this, ItemListActivity.class);
				listIntent.putExtra(ItemListFragment.ARG_ITEM_HASH, hashCode);
				
				startActivity(listIntent);
			}
			
			
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			
			Intent intent = new Intent(this,ItemListActivity.class);
			Log.d("Action bar","about to test");
			if(Root!= null){
				Log.d("Action bar","putting in the hash");
				intent.putExtra(ItemListFragment.ARG_ITEM_HASH, Root.hashCode());
				NavUtils.navigateUpTo(this,intent);
				return true;
			}
			else{
				Log.d("Action bar","it returned false, nothing special to navegate to");
				return false;
			}
		}
		return super.onOptionsItemSelected(item);
	}
	
}
