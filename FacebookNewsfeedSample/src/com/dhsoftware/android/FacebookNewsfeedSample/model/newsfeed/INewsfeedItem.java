package com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed;

import com.facebook.model.GraphObject;

/**
 *
 * This is the proxy Interface which allows us to strongly-type against the JSON Objects returned by the
 * Facebook SDK. You will be wondering why there's no specific implementation of this Interface: that's what
 * proxy means. A method in the {@link com.facebook.model.GraphObject GraphObject} class will take care of
 * translating from this Interface's method names to the downloaded JSON's properties. Yes, this means
 * if you mess up the name of the "getter" this magical Facebook SDK method will throw you an Exception,
 * but if you do it'll save you a tooooooooonnnnn of code (no need to make a specific class translating
 * JSON properties to Java Objects).
 * Note: as you can see, you can "chain" these proxies: {@link com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed.INewsfeedItemApplication INewsfeedItemApplication},
 * {@link com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed.INewsfeedItemFrom INewsfeedItemFrom}, {@link com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed.INewsfeedItemVia}.
 * * <br></br>
 * <br></br>
 * User: Dinesh Harjani (email: goldrunner18725@gmail.com) (github: the7thgoldrunner) (Twitter: @dinesharjani)
 * <br></br>
 * Date: 4/23/13
 */
public interface INewsfeedItem extends GraphObject {

   String getID();
   String getName();
   INewsfeedItem getFrom();
   String getMessage();
   String getType();
   INewsfeedItemVia getVia();
   INewsfeedItemApplication getApplication();
   String getCaption();
   String getDescription();
   String getSource();
   String getStatusType();
   String getStory();
   String getPicture();
   String getCreated_Time();
   String getUpdated_Time();

}
