package com.dhsoftware.android.FacebookNewsfeedSample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.dhsoftware.android.FacebookNewsfeedSample.R;
import com.dhsoftware.android.FacebookNewsfeedSample.fragments.MyNewsfeedFragment;
import com.facebook.Session;

/**
 *
 * I could've written everything into this {@link android.support.v4.app.FragmentActivity Activity} rather than using {@link android.support.v4.app.Fragment Fragment}s,
 * but then again, {@link android.support.v4.app.Fragment Fragment}s are here to stay and so I make them a part of every Android app I develop.
 * <br></br>
 * <br></br>
 * User: Dinesh Harjani (email: goldrunner18725@gmail.com) (github: the7thgoldrunner) (Twitter: @dinesharjani)
 * <br></br>
 * Date: 4/23/13
 */
public class MyNewsfeedActivity extends FragmentActivity {

   private static final String __NEWSFEED_FRAGMENT_TAG__ = MyNewsfeedFragment.class.getCanonicalName();
   private MyNewsfeedFragment mNewsfeedFragment;

   /**
    * Called when the activity is first created.
    */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.mynewsfeed_activity);

       if (savedInstanceState != null) {
           mNewsfeedFragment = (MyNewsfeedFragment) getSupportFragmentManager().findFragmentByTag(__NEWSFEED_FRAGMENT_TAG__);
       } else {
           mNewsfeedFragment = new MyNewsfeedFragment();
           getSupportFragmentManager().beginTransaction().add(R.id.com_dhsoftware_android_myNewsfeedActivity_containerView, mNewsfeedFragment, __NEWSFEED_FRAGMENT_TAG__).commit();
       }
   }

   /**
    * This is necessary so Facebook SDK can perform Login for us using their own Activity.
    * Putting this code in the {@link com.dhsoftware.android.FacebookNewsfeedSample.fragments.MyNewsfeedFragment MyNewsfeedFragment} wouldn't work.
    */
   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
   }

}
