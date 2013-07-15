package com.dhsoftware.android.FacebookNewsfeedSample.tasks;

import android.os.AsyncTask;
import com.dhsoftware.android.FacebookNewsfeedSample.model.GraphAPIRequest;
import com.dhsoftware.android.FacebookNewsfeedSample.model.IRequestCallback;
import com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed.INewsfeedItem;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This distinct {@link android.os.AsyncTask AsyncTask} can be used to perform any sort of Graph API Request on Facebook.
 * All you've got to do is set a {@link com.dhsoftware.android.FacebookNewsfeedSample.model.IRequestCallback IRequestCallback} Interface so you're
 * notified of when your tasks are completed, and you'll receive an {@link java.util.ArrayList ArrayList} full of {@link com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed.INewsfeedItem INewsfeedItem}s
 * for you tu use. Of course, you may also change and update this code in any way that suits your use of the Graph API. I just wanted to make it
 * as simple as possible to show a user's Newsfeed onscreen.
 * <br></br>
 * <br></br>
 * User: Dinesh Harjani (email: goldrunner18725@gmail.com) (github: the7thgoldrunner) (Twitter: @dinesharjani)
 * <br></br>
 * Date: 4/23/13
 */
public class FacebookGraphAPIRequestTask extends AsyncTask<GraphAPIRequest, Integer, Void> {

   private ArrayList<INewsfeedItem> mItems;

   private IRequestCallback mCallback;

   public IRequestCallback getCallback() {
      return mCallback;
   }

   public void setCallback(IRequestCallback callback) {
      mCallback = callback;
   }

   /**
    * We "open" the given JSONObject and add its items to our internal list.
    */
   private void processResponse(JSONObject data) {
      try {
         JSONArray dataArray = (JSONArray)data.get("data");
         GraphObjectList<INewsfeedItem> newsFeedItems = GraphObject.Factory.createList(dataArray, INewsfeedItem.class);
         for (INewsfeedItem newsfeedItem : newsFeedItems) {
            mItems.add(newsfeedItem);
         }
      }
      catch (JSONException jsonException) {
         jsonException.printStackTrace();
      }
   }

   /**
    * This is the method performing all of this Tasks' work. It loops through all of the {@link com.dhsoftware.android.FacebookNewsfeedSample.model.GraphAPIRequest GraphAPIRequest}s and
    * adds all the downloaded items into a list.
    */
   @Override
   protected Void doInBackground(GraphAPIRequest... params) {
      mItems = new ArrayList<INewsfeedItem>();
      final Session session = Session.getActiveSession();
      for (GraphAPIRequest request : params) {
         Request graphApiRequest = Request.newGraphPathRequest(session, request.getGraphPath(), null);
         graphApiRequest.setParameters(request.getParameters());
         // this call blocks the calling thread, but in this case it's OK, since we're already in a background thread
         // in this way, you can see both uses of making requests to the Facebook API
         Response response = graphApiRequest.executeAndWait();
         // we could also check here that our Session's valid; which is recommended in Facebook's own samples
         // in this sample's case, I check it in MyNewsfeedFragment just before adding the downloaded items to the Adapter
         if (response != null) {
            // if we did get a response, we
            processResponse(response.getGraphObject().getInnerJSONObject());
         }

      }

      // We sort all items we've downloaded from newest to oldest,
      // in this way, the items will fill in naturally to how we display
      // Newsfeed items.
      Collections.sort(mItems, new Comparator<INewsfeedItem>() {
         @Override
         public int compare(INewsfeedItem lhs, INewsfeedItem rhs) {
            DateTime lhsDateTime = new DateTime(lhs.getCreated_Time());
            DateTime rhsDateTime = new DateTime(rhs.getCreated_Time());
            return (lhsDateTime.compareTo(rhsDateTime));
         }
      });

      // this is not the method returning the objects,
      // since we're still running in a background thread
      return null;
   }

   /**
    * This is where we give back the caller the items we've downloaded off the Facebook Graph API.
    */
   @Override
   protected void onPostExecute(Void object) {
      if (mCallback != null) {
         mCallback.requestCompleted(mItems);
      }
   }
}
