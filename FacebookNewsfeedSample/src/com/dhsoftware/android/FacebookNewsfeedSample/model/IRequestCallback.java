package com.dhsoftware.android.FacebookNewsfeedSample.model;

/**
 *
 * I wanted to keep the {@link com.dhsoftware.android.FacebookNewsfeedSample.tasks.FacebookGraphAPIRequestTask FacebookGraphAPIRequestTask} which does the heavy
 * lifting of going to Facebook and asking for the data separate from the {@link android.app.Activity Activity} or {@link android.support.v4.app.Fragment Fragment}
 * using it, so I came up with this interface to solve the problem. For those coming from iOS/Objectve-C, this would be the Delegate's Protocol.
 * <br></br>
 * <br></br>
 * User: Dinesh Harjani (email: goldrunner18725@gmail.com) (github: the7thgoldrunner) (Twitter: @dinesharjani)
 * <br></br>
 * Date: 4/23/13
 */
public interface IRequestCallback {

   void requestCompleted(Object obj);

}
