# Project 2 - *NYTimesSearch*

**Name of your app** is an android app that allows a user to search for articles on web using simple filters. The app utilizes [New York Times Search API](http://developer.nytimes.com/docs/read/article_search_api_v2).

Time spent: **28** hours spent in total

## User Stories

The following **required** functionality is completed:

* [ **DONE**] User can **search for news article** by specifying a query and launching a search. Search displays a grid of image results from the New York Times Search API.
* [**DONE**] User can click on "settings" which allows selection of **advanced search options** to filter results
* [ **DONE**] User can configure advanced search filters such as:
  * [ **DONE**] Begin Date (using a date picker)
  * [**DONE**] News desk values (Arts, Fashion & Style, Sports)
  * [**DONE**] Sort order (oldest or newest)
* [**DONE**] Subsequent searches have any filters applied to the search results
* [**DONE**] User can tap on any article in results to view the contents in an embedded browser.
* [**DONE**] User can **scroll down to see more articles**. The maximum number of articles is limited by the API search.

The following **optional** features are implemented:

* [**DONE**] Implements robust error handling, [check if internet is available](Checked for Network)(http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [**DONE**] Used the **ActionBar SearchView** or custom layout as the query box instead of an EditText
* [**DONE**] User can **share an article link** to their friends or email it to themselves
* [ **DONE**] Replaced Filter Settings Activity with a lightweight modal overlay
* [**DONE**] Improved the user interface and experiment with image assets and/or styling and coloring

The following **bonus** features are implemented:

* [ **DONE**] Use the [RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) with the `StaggeredGridLayoutManager` to display improve the grid of image results
* [ **DONE**] Replace all icon drawables and other static image assets with [vector drawables](http://guides.codepath.com/android/Drawables#vector-drawables) where appropriate.
* [ **DONE**] Replace Picasso with [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering.
* [ ] Uses [retrolambda expressions](http://guides.codepath.com/android/Lambda-Expressions) to cleanup event handling blocks.
* [**DONE** ] Leverages the popular [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data.
* [ **DONE**] Leverages the [Retrofit networking library](http://guides.codepath.com/android/Consuming-APIs-with-Retrofit) to access the New York Times API.
* [ ] For different news articles that only have text or only have images, use [Heterogenous Layouts](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) with RecyclerView
* [ ] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [ ] Leverages the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into layout templates.

The following **additional** features are implemented:

 - Implemented Additional Styling. 
 - Landing Page Features Top Stories.
 
 

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://github.com/dsatija/New-York-Times-Article-Search/blob/master/nyt_2.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />
<img src='https://github.com/dsatija/New-York-Times-Article-Search/blob/master/nyt_3.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />


GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Progress bar with recycler view pagination was hard to implement,not able to do it.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android
- [Retrofit](https://square.github.io/retrofit/) - HTTP Calls to Rest Api
- [Chrome Custom tabs]( https://developer.chrome.com/multidevice/android/customtabs/) - Replacing Web View
- [GSON](https://square.github.io/retrofit/)

## License

    Copyright 2016 Disha

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
