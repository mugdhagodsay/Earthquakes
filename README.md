# Coding exercise - *Earthquakes*

**Earthquakes** is a simple Android application that reads JSON data from an earthquakes API and populates a ListView within the main activity.  Each earthquake occupies one item in the list. 

Submitted by: **Mugdha Kulkarni**

## User Stories

The following **required** functionality is completed:

* [x] Read JSON data from the Earthquakes API and populate ListView
* [x] Each earthquake occupies one item in the list and displays Magnitude, Depth and Date
* [x] Earthquakes with magnitude greater than 8 or equal to 8 are highlited with red text

The following **optional** features are implemented:

* [x] Clicking on an earthquake item takes the user to Earthquake details where a Map is renedered with a marker at the Latitude, Longitude where the earthquake occured. 
* [x] Display earthquake details in an infowindow on the Map
* [x] Check for network connectivity and if the network is online

## Test Cases
* Test Case 1: Verify that the Earthquakes app displays Network Error dialog when there is no network
* Test Case 2: Verify that when the HTTP Response code is other than 200, the app displays and error message and does not crash
* Test Case 3: Verify that is the JSON returned does not contain "earthquakes", appropriate message is indicated to the user
* Test Case 4: Verify that all the returned earthquakes are displayed in the listview, each earthquake is one item
* Test Case 5: Verify that for each earthquake Magnitude, Depth and Date are displayed in the listview
* Test Case 6: Verify that for Magnitude >= 8.0, property Magnitude is displayed in red
* Test case 7: Verify that clicking on the earthquake item, user is taken to a Map showing a marker corresponding to the lat,lng of where the earthquake occured

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/Z464nzH.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).




