package com.dhsoftware.android.FacebookNewsfeedSample.model;

import android.os.Bundle;

/**
 * My intention for spinning off the Graph API Request in an {@link android.os.AsyncTask AsyncTask} was to allow a potential Github fork/pull
 * of this project to use the same class for different Requests. To make it all simpler, I chose to represent all the information the
 * {@link com.dhsoftware.android.FacebookNewsfeedSample.tasks.FacebookGraphAPIRequestTask FacebookGraphAPIRequestTask} requires for every request
 * in a distinct class.
 * <br></br>
 * There are no "setters" here since immutable classes are always preferable to mutable classes since they're simpler and don't change state,
 * therefore being much easier to understand and debug.
 * <br></br>
 * <br></br>
 * User: Dinesh Harjani (email: goldrunner18725@gmail.com) (github: the7thgoldrunner) (Twitter: @dinesharjani)
 * <br></br>
 * Date: 4/23/13
 */
public class GraphAPIRequest {

   String mGraphPath;
   Bundle mParameters;

   public GraphAPIRequest(String graphPath, Bundle params) {
       mGraphPath = graphPath;
       mParameters = params;
   }

   public String getGraphPath() {
      return mGraphPath;
   }

   public Bundle getParameters() {
      return mParameters;
   }

}
