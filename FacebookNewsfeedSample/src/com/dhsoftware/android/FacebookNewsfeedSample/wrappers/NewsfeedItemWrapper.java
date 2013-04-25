package com.dhsoftware.android.FacebookNewsfeedSample.wrappers;

import android.view.View;
import android.widget.TextView;
import com.dhsoftware.android.FacebookNewsfeedSample.R;
import com.dhsoftware.android.FacebookNewsfeedSample.model.newsfeed.INewsfeedItem;
import com.facebook.widget.ProfilePictureView;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * Keeping in line with the ViewHolder pattern that is standard in all good Android code, this is the "Holder" representing
 * each row in the Newsfeed {@link android.widget.ListView ListView}.
 * <br></br>
 * <br></br>
 * User: Dinesh Harjani (email: goldrunner18725@gmail.com) (github: the7thgoldrunner) (Twitter: @dinesharjani)
 * <br></br>
 * Date: 4/23/13
 */
public class NewsfeedItemWrapper {

   private final View mRow;
   private ProfilePictureView mUserProfilePicture;
   private TextView mUsername;
   private TextView mTime;
   private TextView mContent;
   private TextView mVia;
   private DateTimeFormatter mDateTimeFormatter;
   private StringBuilder mStringBuilder;


   public NewsfeedItemWrapper(final View row) {
      this.mRow = row;
      mDateTimeFormatter = DateTimeFormat.forPattern("HH:mm");
      mStringBuilder = new StringBuilder();
   }

   public void setForItem(INewsfeedItem item) {
      // the "From" field in an Item is the ID of the user who posted the item
      getUserProfilePicture().setProfileId(item.getFrom().getID());
      getUsername().setText(item.getFrom().getName());
      getTime().setText(mDateTimeFormatter.print(new DateTime((item.getCreated_Time()))));

      mStringBuilder.setLength(0);
      if (item.getMessage() != null) {
         mStringBuilder.append(item.getMessage() + "\n");
      }

      if (item.getDescription() != null) {
         mStringBuilder.append(item.getDescription() + "\n");
      }
      if (item.getCaption() != null) {
         mStringBuilder.append(item.getCaption() + "\n");
      }
      if (item.getStory() != null) {
         mStringBuilder.append(item.getStory() + "\n");
      }
      getContent().setText(mStringBuilder.toString());

      // show the Person/Page through whom this item was posted
      /*
      if (item.getVia() != null) {
         getVia().setVisibility(View.VISIBLE);
         getVia().setText("via " + item.getVia().getName());
      }
      else {
         getVia().setVisibility(View.GONE);
      }
      */

      // show the App through which this item was posted
      if (item.getApplication() != null) {
         getVia().setVisibility(View.VISIBLE);
         getVia().setText("via " + item.getApplication().getName());
      }
      else {
         getVia().setVisibility(View.GONE);
      }
   }

   public ProfilePictureView getUserProfilePicture() {
      if (mUserProfilePicture == null) {
         mUserProfilePicture = (ProfilePictureView) mRow.findViewById(R.id.com_dhsoftware_android_newsFeedItem_userProfilePicture);
      }
      return mUserProfilePicture;
   }

   public TextView getUsername() {
      if (mUsername == null) {
         mUsername = (TextView) mRow.findViewById(R.id.com_dhsoftware_android_newsFeedItem_userName);
      }
      return mUsername;
   }

   public TextView getTime() {
      if (mTime == null) {
         mTime = (TextView) mRow.findViewById(R.id.com_dhsoftware_android_newsFeedItem_time);
      }
      return mTime;
   }

   public TextView getContent() {
      if (mContent == null) {
         mContent = (TextView) mRow.findViewById(R.id.com_dhsoftware_android_newsFeedItem_content);
      }
      return mContent;
   }

   public TextView getVia() {
      if (mVia == null) {
         mVia = (TextView) mRow.findViewById(R.id.com_dhsoftware_android_newsFeedItem_via);
      }
      return mVia;
   }

}
