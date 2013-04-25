package com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed;

import com.facebook.model.GraphObject;

/**
 *
 * A simple proxy Interface to access the supplied information of the Facebook App
 * through which a {@link com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed.INewsfeedItem INewsfeedItem} was posted.
 * Keep in mind this may be null in some items.
 * <br></br>
 * <br></br>
 * User: Dinesh Harjani (email: goldrunner18725@gmail.com) (github: the7thgoldrunner) (Twitter: @dinesharjani)
 * <br></br>
 * Date: 4/23/13
 */
public interface INewsfeedItemApplication extends GraphObject {
   String getID();
   String getName();
   String getNamespace();
}
