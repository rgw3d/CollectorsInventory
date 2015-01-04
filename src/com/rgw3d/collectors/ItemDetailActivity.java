package com.rgw3d.collectors;



import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

/**
 * An activity representing a single Item detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link ItemListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link ItemDetailFragment}.
 */
@SuppressLint("NewApi")
public class ItemDetailActivity extends ActionBarActivity implements OnNavigationListener{

	private CollectionItem parentItem;
	private CollectionItem root;
	private String FragmentKeyWord = "FragmentKeyWord";
	public CollectionItem getRoot(){
		return root;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);

		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null &&getIntent()!= null && getIntent().hasExtra(ItemDetailFragment.ARG_ITEM_HASH) ) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			
			arguments.putInt(ItemDetailFragment.ARG_ITEM_HASH, 
					getIntent().getExtras()
					.getInt(ItemDetailFragment.ARG_ITEM_HASH));
			
			root = CollectionItem.findChildObject(getIntent().getExtras().getInt(ItemDetailFragment.ARG_ITEM_HASH), CollectionDataStorage.Base);
			parentItem = root.getParent();
			ItemDetailFragment fragment = new ItemDetailFragment();
			
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.item_detail_container, fragment,FragmentKeyWord).commit();
		}
		
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			Intent intent = new Intent(this,ItemListActivity.class);
			
			if(parentItem!=null){
				Log.d("Action bar Navegation","From detail to list");
				intent.putExtra(ItemListFragment.ARG_ITEM_HASH, parentItem.hashCode());
			}
			else
				Log.d("Action bar Navegation","it returned false, supposedly nothing to navegate too..");
			NavUtils.navigateUpTo(this,intent);
			return true;
		}
		else if(id == R.id.add_new_description){
			((ItemDetailFragment) getSupportFragmentManager().findFragmentByTag(FragmentKeyWord)).addNewDescription("Field Title", "Description");
		}
		else if(id == R.id.add_image){
			((ItemDetailFragment) getSupportFragmentManager().findFragmentByTag(FragmentKeyWord)).addImage();
		}
		return super.onOptionsItemSelected(item);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detailmenu, menu);
		return true;
	}

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		return false;
	}
	
}
