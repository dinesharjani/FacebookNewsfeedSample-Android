package com.dhsoftware.android.FacebookNewsfeedSample.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.dhsoftware.android.FacebookNewsfeedSample.R;
import com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed.INewsfeedItem;
import com.dhsoftware.android.FacebookNewsfeedSample.wrappers.NewsfeedItemWrapper;

import java.util.List;

/**
 *
 * To make code in the {@link com.dhsoftware.android.FacebookNewsfeedSample.fragments.MyNewsfeedFragment MyNewsfeedFragment} as simple as possible, I made
 * an adapter to show all the {@link com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed.INewsfeedItem INewsfeedItem}s we get through the API.
 * Keep in mind we're not using a separate {@link java.util.ArrayList ArrayList} to hold the data; in this sample we're actually adding the data
 * directly into the adapter. Look at the {@link com.dhsoftware.android.FacebookNewsfeedSample.fragments.MyNewsfeedFragment MyNewsfeedFragment} code.
 * <br></br>
 * <br></br>
 * User: Dinesh Harjani (email: goldrunner18725@gmail.com) (github: the7thgoldrunner) (Twitter: @dinesharjani)
 * <br></br>
 * Date: 4/23/13
 */
public class NewsfeedItemAdapter extends ArrayAdapter<INewsfeedItem> {

   private LayoutInflater mLayoutInflater;

   public NewsfeedItemAdapter(Context context) {
      super(context, R.layout.newsfeed_listviewitem);
      init();
   }

   public NewsfeedItemAdapter(Context context, List<INewsfeedItem> items) {
      super (context, R.layout.newsfeed_listviewitem, items);
      init();
   }

   private void init() {
      mLayoutInflater = LayoutInflater.from(getContext());
   }

   /**
    * I overrode this method so the Newsfeed would show up right: keeping newest items at the top. For that,
    * all I had to do was invert access to the items in the {@link android.widget.Adapter Adapter}.
    * @param position the requested item's position
    * @return the {@link com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed.INewsfeedItem INewsfeedItem} at the naturally-inverted position.
    */
   @Override
   public INewsfeedItem getItem(int position) {
      return super.getItem(getCount() - position - 1);
   }

   /**
    * This is a simple implementation following the ViewHolder pattern.
    */
   @Override
   public View getView(int position, View convertView, ViewGroup parent) {

      View row = convertView;
      NewsfeedItemWrapper wrapper;

      if (row == null) {
         row = mLayoutInflater.inflate(R.layout.newsfeed_listviewitem, null);
         wrapper = new NewsfeedItemWrapper(row);
         row.setTag(wrapper);
      }
      else {
         wrapper = (NewsfeedItemWrapper) row.getTag();
      }

      wrapper.setForItem(getItem(position));

      return row;
   }

}
