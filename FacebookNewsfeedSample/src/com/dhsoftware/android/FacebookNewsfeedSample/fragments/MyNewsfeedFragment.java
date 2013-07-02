package com.dhsoftware.android.FacebookNewsfeedSample.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.TextView;
import com.dhsoftware.android.FacebookNewsfeedSample.R;
import com.dhsoftware.android.FacebookNewsfeedSample.adapters.NewsfeedItemAdapter;
import com.dhsoftware.android.FacebookNewsfeedSample.model.GraphAPIRequest;
import com.dhsoftware.android.FacebookNewsfeedSample.model.IRequestCallback;
import com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed.INewsfeedItem;
import com.dhsoftware.android.FacebookNewsfeedSample.tasks.FacebookGraphAPIRequestTask;
import com.facebook.*;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is the meat of this sample. This {@link android.support.v4.app.Fragment Fragment} takes care of logging in to Facebook, asking for the
 * necessary permissions, showing the Facebook user's Newsfeed, and allowing him/her to log out. Please keep in mind that this sample is
 * best understood AFTER you've given a look to the Facebook API and their Documentation. I went through it myself to get this sample done and
 * I know navigating it can be a little confusing, but trust me, it's worth it. Once you understand how to query the Facebook API you'll better
 * understand the tools this sample code provides and how you might want to adapt them for your own needs.
 * Please, feel free to contact me if you have any doubts.
 * <br></br>
 * <br></br>
 * User: Dinesh Harjani (email: goldrunner18725@gmail.com) (github: the7thgoldrunner) (Twitter: @dinesharjani)
 * <br></br>
 * Date: 4/23/13
 */
public class MyNewsfeedFragment extends Fragment implements IRequestCallback {

   /**
    * The ID for the menu option to logout the user.
    */
   private static final int __MENU_LOGOUT_OPTION__ = Menu.FIRST;

   // Views
   private TextView mUsername;
   /**
    * This is a very versatile {@link android.view.View View} made by Facebook. It asynchronously downloads a User's profile picture.
    */
   private ProfilePictureView mUserProfilePicture;
   /**
    * We're going to rely on this {@link com.facebook.widget.LoginButton LoginButton} a lot. It'll log us with the necessary permissions and we'll
    * also use it to log us out.
    */
   private LoginButton mUserLoginButton;
   /**
    * A custom {@link android.widget.ListView ListView} made by Chris Banes implementing the coveted "Pull-to-Refresh" feature.
    */
   private PullToRefreshListView mUserNewsfeed;

   // Data Members
   /**
    * A {@link com.facebook.UiLifecycleHelper UIlifecycleHelper} is a class provided by the Facebook SDK, and what it basically does is notify you
    * of any changes in your Facebook Session: when it's opened, if the user logs out, if your access token times out (which is not covered in this
    * sample), etc. You use it by calling its {@link android.support.v4.app.FragmentActivity Activity}/{@link android.support.v4.app.Fragment Fragment}
    * mirror lifecycle methods, and by giving it a {@link com.facebook.Session.StatusCallback StatusCallback} implementation on {@link java.lang.Object Object} creation.
    * It'll take care of calling you when anything interesting happens.
    */
   private UiLifecycleHelper mUiLifecycleHelper;
   /**
    * This variable will hold the information of the logged in user. We need it to show the user's profile picture and his name.
    * As a class, {@link com.facebook.model.GraphUser GraphUser} is Facebook's clever way of relieving us 3rd-party developers from having to interact
    * with all kinds of JSON properties. Check out my own {@link com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed.INewsfeedItem INewsfeedItem} documentation
    * or Facebook's for more.
    */
   private GraphUser mUser;
   private boolean mActiveSession;
   /**
    * A small variable keeping track of whether the Fragment is visible or not, so if our Facebook Session changes we don't update the UI unless
    * the Fragment is visible. (Anyways, we always refresh the UI when {@link android.support.v4.app.Fragment#onResume onResume()} is called.)
    */
   private boolean mIsResumed;
   private NewsfeedItemAdapter mAdapter;
   /**
    * This variable holds the code that'll be called whenever a change in our Facebook Session takes place.
    */
   private Session.StatusCallback mStatusCallback;
   /**
    * We're using Chris Banes' "Pull-to-refresh" implementation. This variable holds the code called when the user pulls down the {@link com.handmark.pulltorefresh.library.PullToRefreshListView ListView}.
    */
   private PullToRefreshListView.OnRefreshListener mRefreshListener;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      // if we forget this line, we won't have a Menu!
      setHasOptionsMenu(true);
      initDataMembersConfig(savedInstanceState);
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.mynewsfeed_fragment, viewGroup);
      initUIConfig((ViewGroup) view);
      return view;
   }

   /**
    * Basically, this code initializes all our data members BEFORE we link our View objects.
    * @param savedInstanceState  the same {@link android.os.Bundle Bundle} we get in {@link #onCreate(android.os.Bundle)}.
    */
   private void initDataMembersConfig(Bundle savedInstanceState) {
      // set our Session change callback call
      mStatusCallback = new Session.StatusCallback() {
         @Override
         public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
         }
      };
      mUiLifecycleHelper = new UiLifecycleHelper(getActivity(), mStatusCallback);
      // lifecycle mirror method
      mUiLifecycleHelper.onCreate(savedInstanceState);
      // new adapter for our ListView
      mAdapter = new NewsfeedItemAdapter(getActivity());
      // code that's called when user pulls-to-refresh
      mRefreshListener = new PullToRefreshBase.OnRefreshListener() {
         @Override
         public void onRefresh(PullToRefreshBase refreshView) {
            refreshView.setRefreshing(true);
            requestNewsfeed();
         }
      };
   }

   /**
    * A method which initializes all of our members pointing to layout {@link android.view.View View}s.
    * @param viewGroup  The {@link android.view.ViewGroup ViewGroup} representing this {@link android.support.v4.app.Fragment Fragment}'s screen.
    */
   private void initUIConfig(ViewGroup viewGroup) {
      mUsername = (TextView)viewGroup.findViewById(R.id.myNewsFeedFragment_userName);
      mUserProfilePicture = (ProfilePictureView) viewGroup.findViewById(R.id.com_dhsoftware_android_myNewsfeedFragment_userProfilePicture);
      mUserLoginButton = (LoginButton) viewGroup.findViewById(R.id.com_dhsoftware_android_myNewsFeedFragment_userLoginButton);
      // we need this permission to access the user's Newsfeed
      mUserLoginButton.setReadPermissions(Arrays.asList("read_stream"));
      mUserNewsfeed = (PullToRefreshListView) viewGroup.findViewById(R.id.myNewsfeedFragment_userNewsfeed);
      mUserNewsfeed.setAdapter(mAdapter);
      mUserNewsfeed.setOnRefreshListener(mRefreshListener);
   }

   /*
      Lifecycle methods
    */

   public void onResume() {
      super.onResume();
      mUiLifecycleHelper.onResume();
      mIsResumed = true;
      updateUI();
   }

   public void onPause() {
      super.onPause();
      mUiLifecycleHelper.onPause();
      mIsResumed = false;
   }

   @Override
   public void onDestroy() {
      super.onDestroy();
      mUiLifecycleHelper.onDestroy();
   }

   @Override
   public void onSaveInstanceState(Bundle outState) {
      super.onSaveInstanceState(outState);
      mUiLifecycleHelper.onSaveInstanceState(outState);
   }

   /*
    * Menu handling
    */

   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      super.onCreateOptionsMenu(menu, inflater);
      Session facebookSession = Session.getActiveSession();
      // if we've got a session, we'll enable the option to logout
      if ((facebookSession != null) && (facebookSession.isOpened())) {
         menu.add(0, __MENU_LOGOUT_OPTION__, 0, R.string.com_dhsoftware_android_logout);
      }
      else {
         // if we don't we can't logout if we aren't properly logged in, can we?
         menu.clear();
      }
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case __MENU_LOGOUT_OPTION__:
         // the Login button switches to "Logout" if our Session's active, so all we've got to do to
         // Logout is click it programmatically

         // Reflection: only use the appropriate method call depending on current Android release
         // we're running on
         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
             // callOnClick is a new method added in API 15
             mUserLoginButton.callOnClick();
         }
         else {
             // performClick is the classic method to make a button trigger its listeners
             mUserLoginButton.performClick();
         }
         return true;
      default:
         break;
      }
      // if it's not one of our options, we let Android handle them
      return super.onOptionsItemSelected(item);
   }

   /**
    * We handle almost everything UI-wise in this method.
    */
   private void updateUI() {
      // get Facebook Session
      Session facebookSession = Session.getActiveSession();
      mActiveSession = (facebookSession != null) && (facebookSession.isOpened());
      if (mActiveSession && mUser != null) {
         // if we have an active session, we can show the user's profile picture, display his/her name
         mUsername.setVisibility(View.VISIBLE);
         mUsername.setText(mUser.getFirstName() + " " + mUser.getLastName() + "'s Newsfeed");
         mUserProfilePicture.setVisibility(View.VISIBLE);
         mUserProfilePicture.setProfileId(mUser.getId());
         mUserLoginButton.setVisibility(View.GONE);

         // and prompt the system to review our Menu so it's displayed
         setMenuVisibility(true);
      }
      else {
         // we can't show any information unless the user is logged in
         mUsername.setVisibility(View.GONE);
         mUsername.setText(null);
         mUserProfilePicture.setVisibility(View.GONE);
         mUserProfilePicture.setProfileId(null);
         mUserLoginButton.setVisibility(View.VISIBLE);
         // we also need to clear any information we may have in the Adapter
         mAdapter.clear();
         mAdapter.notifyDataSetChanged();
         // and prompt the system to make our Menu disappear
         setMenuVisibility(false);
      }

   }

   /**
    * Thanks to the UILifecycleHelper, this method will be called whenever our Facebook Session changes.
    */
   private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
      // Only make changes if the Fragment is visible
      if (mIsResumed) {
         if (state.isOpened()) {
            if (mUser == null) {
               // if we don't have a user, we need to get it
               final Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
                  @Override
                  public void onCompleted(GraphUser user, Response response) {
                     // If the response is successful
                     if ((session == Session.getActiveSession()) && (user != null)) {
                        // we've got a user and we can update the UI
                        MyNewsfeedFragment.this.mUser = user;
                        updateUI();
                        requestNewsfeed();
                     }
                     else {
                        // something is wrong
                        // handle errors or potential loss of Facebook Session here
                     }
                  }
               });
               // performing this request will not block our UI Thread
               request.executeAsync();
            }
            else {
               // we need to get our Newsfeed
               requestNewsfeed();
            }

         }
         else {
            // either we have the user or our session's state has changed
            // in either case, we need to update our UI immediately to reflect this
            updateUI();
         }
      }
   }

   /**
    * This method handles calling our {@link com.dhsoftware.android.FacebookNewsfeedSample.tasks.FacebookGraphAPIRequestTask FacebookGraphAPIRequestTask} to
    * request our user's Newsfeed off Facebook's API.
    */
   private void requestNewsfeed() {
      GraphAPIRequest request = new GraphAPIRequest();
      // newsfeed base address
      request.setGraphPath("/me/home");
      Bundle params = new Bundle();
      // the specific fields we're interested in
      params.putString("fields", "id,from,name,message,caption,description,created_time,updated_time,type,status_type,via,source,picture, application");
      request.setParameters(params);
      // if we already have some items downloaded
      if (mAdapter.getCount() > 0) {
         // we'll only request the new ones
         params.putString("since", mAdapter.getItem(mAdapter.getCount() - 1).getUpdated_Time());
      }

      // make a new request
      FacebookGraphAPIRequestTask requestTask = new FacebookGraphAPIRequestTask();
      // set ourselves to be called
      requestTask.setCallback(this);
      requestTask.execute(request);
   }

   /**
    * This method is called by the {@link com.dhsoftware.android.FacebookNewsfeedSample.tasks.FacebookGraphAPIRequestTask FacebookGraphAPIRequestTask} when it's finished
    * taking care of all our API Requests.
    * @param obj An {@link java.util.ArrayList ArrayList} with the downloaded {@link com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed.INewsfeedItem INewsfeedItem}s.
    */
   @Override
   public void requestCompleted(Object obj) {
      mUserNewsfeed.onRefreshComplete();
      // if we still have a working (open) session
      if (Session.getActiveSession().isOpened()) {
         ArrayList<INewsfeedItem> newItems = (ArrayList<INewsfeedItem>) obj;
         // add the new items to our adapter
         for (INewsfeedItem item : newItems) {
            mAdapter.add(item);
         }
      }
      // no need to notifyDataSetChanged() our Adapter,
      // because we're adding the items directly to it.
   }
}
