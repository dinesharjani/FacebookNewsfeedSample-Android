package com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed;

import com.facebook.model.GraphObject;

/**
 *
 * If you'd like to know through whom a {@link com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed.INewsfeedItem INewsfeedItem} was posted,
 * you can do so through this proxy for the "via" property. Keep in mind this may be null for some items.
 * <br></br>
 * <br></br>
 * User: Dinesh Harjani (email: goldrunner18725@gmail.com) (github: the7thgoldrunner) (Twitter: @dinesharjani)
 * <br></br>
 * Date: 4/23/13
 */
public interface INewsfeedItemVia extends GraphObject {
   String getID();
   String getName();
}
