package com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed;

import com.facebook.model.GraphObject;

/**
 *
 * We want to display exactly who posted every {@link com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed.INewsfeedItem INewsfeedItem}, so we
 * require another Proxy to access their ID (to download their profile picture) and their Name.
 * <br></br>
 * <br></br>
 * User: Dinesh Harjani (email: goldrunner18725@gmail.com) (github: the7thgoldrunner) (Twitter: @dinesharjani)
 * <br></br>
 * Date: 4/23/13
 */
public interface INewsfeedItemFrom extends GraphObject {
   String getID();
   String getName();
}
