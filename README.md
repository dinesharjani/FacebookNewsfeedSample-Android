# FacebookNewsSample-Android

## Introduction

![Screenshot](https://github.com/the7thgoldrunner/facebooknewsfeedsample-android/raw/master/images/facebooknewsfeed-android-holo.png)

It's the year 2013. Facebook's Android SDK was first announced in May 2010 at Google I/O, and I know it because I was there going through all the stands. Yet, here we are and there is still not a single Sample Code Project, not even from Facebook itself, for you to access the Graph API and pull down a user's Newsfeed.

Until now, of course.

## Description

This simple Project is targeted at Android 2.3 and upwards, and uses Facebook's latest Android SDK to display a user's Newsfeed. I'm not yet into the Maven vibe (although I know what it does) so I added the SDK directly into this project. I also wanted a fancy way for you to refresh the Newsfeed, so I went to [Chris Banes' Pull-to-Refresh project] (https://github.com/chrisbanes/Android-PullToRefresh) and added it, too.

To test it, you simply tap on the "Login" button at the bottom, introduce your Facebook credentials, it'll ask permission to read your Newsfeed, and a couple of seconds later (it works asynchronously, but I left out a visual indicator) you'll see it pop onscreen. If your own Newsfeed is very busy, you can Pull-to-Refresh  a couple of seconds later and see the new items pop right at the top. No, it doesn't include any [fancy Gap Detection code inside] (http://dinesharjani.com/post/48561548010/week-3-gap-technology-part-1).

My recommendations to anyone interested in this code: please, please, please, have a look at the [official Facebook Documentation] (https://developers.facebook.com/). In a couple of ways I consider it a mess because it doesn't focus on what's most important to you as a 3rd-party developer looking to access the Graph API, but if you go through all of it calmly, and take time to play with their tools, specially their [Graph Explorer] (https://developers.facebook.com/tools/explorer), you'll be able to better appreciate how this code works and what you can do with it to suit your own needs.

## Instructions

There are a couple of things you need to go through to get this code working for you.

* First: I wrote this project in [IntelliJ-Idea] (http://www.jetbrains.com/idea/), [not Eclipse] (http://www.eclipse.org/). Your first order of business would be to get the Project to run and not show up any errors.

* Second: Once the Project runs, you'll try to Log in and find that it doesn't totally work. That's because you need to go to Facebook and [create your own App in their console] (https://developers.facebook.com/apps). [This link should provide you with all the help you might need.] (https://developers.facebook.com/docs/getting-started/facebook-sdk-for-android/3.0/)

## Other stuff

Keep in mind this code is for sample purposes. Even so, I made use of Fragments and split the code into sub-packages just like I do in production to make it all tidy and it's well documented. Right now I don't envision expanding this Sample with more features (posting to Facebook, or adding ActionBarSherlock, for instance), but if you want to do it before I do, feel free to do so and by all means get in touch with me.

## Acknowledgements

* [Facebook Official Documentation] (https://developers.facebook.com/)
* [Chris Banes' Pull-to-Refresh Github Project] (https://github.com/chrisbanes/Android-PullToRefresh)

## Contact
Dinesh Harjani
Email: [goldrunner18725@gmail.com] (mailto:goldrunner18725@gmail.com)
Twitter: [@dinesharjani] (https://twitter.com/dinesharjani)

## License

Copyright 2013 Dinesh Harjani

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License.
