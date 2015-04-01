# Locals--Event-Based-Social-Application-for-Android

The aim of the project is to develop an application which will take in the current location of the user and list the events happening around him. The application which we are proposing is called Locals! It can list all the events in the selected location as well as it can list only those events which fall under the user’s choice (for eg. Art, Music, etc.). The user can add other people using the application as his friends and everyone in the same circle will get notified if anyone is going for a particular event. This helps the user to improve his social life as well. In addition to this, the user can add his favorite events to a Favorite List which gets stored in the database.
After a stressful day at work, the user can use the app to unwind or add an upcoming event to his favorites so that he will not forget about it. It can help the user plan his free-time to do things that will make him happy, hence the tagline “Find your nearby Happiness!”
The app is very simple and handy to use. It can be used by all age groups. The app has a vast list of categories which caters to the interests of young and old alike. Parents can find fun events to take their kids to, or professionals can find conferences and workshops to attend while the young at heart can always find music, outdoor and holiday packages. The app is a one stop place for all age group users.
The app will use the “Eventful API” to perform all the functionalities as described above. The results will be parsed from this API depending on the user’s choices and the events will be displayed. For example,

http://api.eventful.com/json/events/search?app_key=pz2tGMjJcr4WhZT8&l=charlotte&c=music

Will generate a result of all music events happening in Charlotte.
This search can be modified accordingly by the user.


Key Features of the Locals app is as follows:

 The App has a login screen which allows users to login as well as new users to Sign Up for the App.
 The login authentication is performed by Parse.com
 The App allows the user to pick a location or it can use the current location of the user to set the geographic location.
 It displays all the events happening in 3 tabs namely Today, Upcoming and Categories.
 Today and Upcoming Tabs gives a list of ALL events happening close by.
 Category tab allows the user to pick his category of interest from a list of Art, Music, Conference, Education, Gallery, Holiday, etc. and only the events corresponding to that category will be displayed as a list view.
 Clicking on any item in the list view will give the user the option to either Add to Favorites or to View Details. By adding to Favorite, the event gets saved in Parse.com which maintains the database of all the favourite events pertaining to a user.
 Detail Activity gives the full details of the event. It also shows the event on Google Maps and also allows the user to buy tickets for the event. On Google Maps the route between the user’s location and the event’s location is displayed.
 The Detail Activity has a button called Going. On clicking this button all the user’s friends will receive a push notification indicating that the user is going to an event.
 By opening the Push Notification, the app will be launched to the Home Activity.
 The App also allows the user to view All Users using the App. The user can add other users as Friends. The names of friends of the friends of the user will be stored on Parse.com.
 The App also lets the user view his Friends.
 The Favorite Activity will have a list of all the user saved events. Clicking on any of the items on the favourite list will take the user to the Detail Activity.
 The App also comes with the logout option as well as automatic sign in if a user is already logged in.
