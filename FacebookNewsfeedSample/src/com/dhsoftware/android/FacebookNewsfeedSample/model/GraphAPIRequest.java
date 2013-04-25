package com.dhsoftware.android.FacebookNewsfeedSample.model;

import android.os.Bundle;

/**
 * My intention for spinning off the Graph API Request in an {@link android.os.AsyncTask AsyncTask} was to allow a potential Github fork/pull
 * of this project to use the same class for different Requests. To make it all simpler, I chose to represent all the information the
 * {@link com.dhsoftware.android.FacebookNewsfeedSample.tasks.FacebookGraphAPIRequestTask FacebookGraphAPIRequestTask} requires for every request
 * in a distinct class.
 * <br></br>
 * <br></br>
 * User: Dinesh Harjani (email: goldrunner18725@gmail.com) (github: the7thgoldrunner) (Twitter: @dinesharjani)
 * <br></br>
 * Date: 4/23/13
 */
public class GraphAPIRequest {

   String mGraphPath;
   Bundle mParameters;

   public String getGraphPath() {
      return mGraphPath;
   }

   public void setGraphPath(String graphPath) {
      mGraphPath = graphPath;
   }

   public Bundle getParameters() {
      return mParameters;
   }

   public void setParameters(Bundle parameters) {
      mParameters = parameters;
   }
}
