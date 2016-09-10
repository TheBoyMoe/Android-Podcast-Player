Android Podcast Player App
===============================
 
Demo app allows the user to explore and search iTunes for their favourite podcasts.

The app is a learning exercise in the implementation of the following:
- Designed with a phone layout in mind using activities/fragments
- Activity/fragment communication via interfaces
- SearchView and QuerySuggestionProvider
- Google's ExoPlayer
- Download and parse xml from iTunes using a combination of Retrofit, okhttp, simplexmlframework and JSoup.

Pre-requisites
--------------

- Min Android SDK supported v16

Getting Started
---------------

This sample uses the Gradle build system. To build this project, use the
"gradlew build" command or use "Import Project" in Android Studio.

Screenshots
-----------

![Phone](screencasts/phone-sequence-one.gif "Interacting with the app on a tablet")

![Phone](screencasts/phone-sequence-two.gif "Interacting with the app on a phone")

Credit
------
The project uses the following 3rd party libraries:
- Glide Image downloading and caching library (https://github.com/bumptech/glide)
- Chiu-Ki Chan's AutoRecyclerView (http://blog.sqisland.com/2014/12/recyclerview-autofit-grid.html)
- Timber Android logging library by Jake Wharton (https://github.com/JakeWharton/timber)
- Facebook's Stetho debugging library (https://github.com/facebook/stetho)
- Brian Wernick's ExoMedia library(https://github.com/brianwernick/ExoMedia) and demo app (the episode fragment and supporting files are based on the library's demo app)
- Square's Picasso image downloading and caching library (https://github.com/square/picasso)
- Square's Retrofit (https://github.com/square/retrofit) and okhttp (https://github.com/square/okhttp) library's
- JSoup, by Jonathan Hedley (https://github.com/jhy/jsoup)

MIT License
-----------

Copyright (c) [2016] [William Fero]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.