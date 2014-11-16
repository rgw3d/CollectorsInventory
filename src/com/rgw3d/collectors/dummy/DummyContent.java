package com.rgw3d.collectors.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rgw3d.collectors.CollectionItem;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {
	
	public static CollectionItem x = new CollectionItem();
	static{
		x.setName("Top Level");
		x.setIsItem(false);
		x.initializeChildren();
		
			CollectionItem child1 = new CollectionItem(x,null,true,"Item 1",null);
			child1.initializeDescription();
			child1.addNewFieldAndDescript("Field 1", "The description1");
			child1.addDescription("Field 1", "WOW");
			child1.addNewFieldAndDescript("Field 2", "The description1");
			child1.addNewFieldAndDescript("Field 3", "The description1");
			child1.addNewFieldAndDescript("Field 4", "The description1");
			child1.addNewFieldAndDescript("Field 5", "The description1");
			child1.addNewFieldAndDescript("Field 6", "The description1");
			child1.addNewFieldAndDescript("Field 7", "The description1");
			
			
			CollectionItem child2 = new CollectionItem(x,null,true,"Item 2",null);
			child2.initializeDescription();
			child2.addNewFieldAndDescript("Field 2", "The description2");
			
			
			CollectionItem child3 = new CollectionItem(x,null,true,"Item 3",null);
			child3.initializeDescription();
			child3.addNewFieldAndDescript("Field 1", "The description1");
			child3.addDescription("Field 1", "WOW");
			child3.addNewFieldAndDescript("Field 2", "The description1");
			child3.addNewFieldAndDescript("Field 3", "The description1");
			child3.addNewFieldAndDescript("Field 4", "The description1");
			child3.addNewFieldAndDescript("Field 5", "The description1");
			child3.addNewFieldAndDescript("Field 6", "The description1");
			child3.addNewFieldAndDescript("Field 7", "The description1");
			
			CollectionItem child4 = new CollectionItem(x,null,false,"List 1",null);
			child4.initializeChildren();
			
				CollectionItem baby1 = new CollectionItem(child4,null,true,"Sub Item 1",null);
				baby1.initializeDescription();
				baby1.addNewFieldAndDescript("Field 1", "The lame description for this object");
				baby1.addDescription("Field 1", "WOW a secondary description");
				baby1.addNewFieldAndDescript("Field 2", "The description 3");
				baby1.addNewFieldAndDescript("Field 3", "The description 4");
				baby1.addNewFieldAndDescript("Field 4", "aisjflk;asjdf adsf");
				baby1.addNewFieldAndDescript("Field 5", "The description 5asldfkj asdfj ");
				baby1.addNewFieldAndDescript("Field 6", "The description 7 aslkdjflaks;djf");
				baby1.addNewFieldAndDescript("Field 7", "The description 8 al;kjf");
				
				CollectionItem baby2 = new CollectionItem(child4,null,true,"Sub Item 2",null);
				baby2.initializeDescription();
				baby2.addNewFieldAndDescript("Field 1", "A description of this field");
				baby2.addDescription("Field 1", "WOW WOW WOW ");
				baby2.addNewFieldAndDescript("Field 2", "The description 2");
				
				CollectionItem baby3 = new CollectionItem(child4,null,false,"Sub List 1",null);
				baby3.initializeChildren();
					
					CollectionItem babyChild1 = new CollectionItem(baby3,null,true,"Sub Sub Item 1",null);
					babyChild1.initializeDescription();
					babyChild1.addNewFieldAndDescript("Field 1", "A description of this field");
					babyChild1.addDescription("Field 1", "WOW WOW WOW ");
					babyChild1.addNewFieldAndDescript("Field 2", "The description 2");
					
					CollectionItem babyChild2 = new CollectionItem(baby3,null,true,"Sub Sub Item 2",null);
					babyChild2.initializeDescription();
					babyChild2.addNewFieldAndDescript("Field 1", "A description of this field");
					babyChild2.addDescription("Field 1", "WOW WOW WOW ");
					babyChild2.addNewFieldAndDescript("Field 2", "The description 2");
				
				baby3.addChildren(babyChild1);
				baby3.addChildren(babyChild2);
					
			
			child4.addChildren(baby1);
			child4.addChildren(baby2);
			child4.addChildren(baby3);
		
		x.addChildren(child1);
		x.addChildren(child2);
		x.addChildren(child3);
		x.addChildren(child4);
		
	}
	
	
	

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

	static {
		// Add 3 sample items.
		addItem(new DummyItem("1", "Item 1"));
		addItem(new DummyItem("2", "Item 2"));
		addItem(new DummyItem("3", "Item 3"));
	}

	private static void addItem(DummyItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class DummyItem {
		public String id;
		public String content;

		public DummyItem(String id, String content) {
			this.id = id;
			this.content = content;
		}

		@Override
		public String toString() {
			return content;
		}
	}
}
