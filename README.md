# Coding exercise - *Earthquakes*

**Earthquakes** is a simple Android application that reads JSON data from an earthquakes API and populates a ListView within the main activity.  Each earthquake occupies one item in the list. 

Submitted by: **Mugdha Kulkarni**

Time spent: **10** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] Read JSON data from the Earthquakes API and populate ListView
* [x] Each earthquake occupies one item in the list and displays Magnitude, Depth and Date
* [x] Earthquakes with magnitude greater than 8 or equal to 8 are highlited with red text

The following **optional** features are implemented:

* [x] Clicking on an earthquake item takes the user to Earthquake details where a Map is renedered with a marker at the Latitude, Longitude where the earthquake occured. 
* [x] Display earthquake details in an infowindow on the Map
* [x] Check for network connectivity and if the network is online

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/Z464nzH.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Test Cases
* Verify that the Earthquakes app displays Network Error dialog when there is no network
* Verify that when the HTTP Response code is other than 200, the app displays and error message and does not crash
* Verify that is the JSON returned does not contain "earthquakes", appropriate message is indicated to the user
* Verify that all the returned earthquakes are displayed in the listview, each earthquake is one item
* Verify that for each earthquake Magnitude, Depth and Date are displayed in the listview
* Verify that for Magnitude >= 8.0, property Magnitude is displayed in red
* Verify that clicking on the earthquake item, user is taken to a Map showing a marker corresponding to the lat,lng of where the earthquake occured
## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

