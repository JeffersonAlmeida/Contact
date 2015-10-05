# Simple Contact Android App

##	Justification	of chosen	libraries

[com.google.code.gson:gson:2.3.1](https://github.com/google/gson)

Gson is a Java library that can be used to convert Java Objects into their JSON representation.
It can also be used to convert a JSON string to an equivalent Java object. 

[com.jakewharton:butterknife:7.0.1](http://jakewharton.github.io/butterknife/)

Field and method binding for Android views which uses annotation processing to generate boilerplate code for you.
Eliminate findViewById calls by using @Bind on fields.

[junit:junit:4.12](http://junit.org/)

JUnit is a simple framework to write repeatable tests.

[com.amulyakhare:com.amulyakhare.textdrawable:1.0.1](https://github.com/amulyakhare/TextDrawable)

This light-weight library provides images with letter/text like the Gmail app. 
It extends the Drawable class thus can be used with existing/custom/network ImageView classes.

[com.squareup.picasso:picasso:2.5.2](http://square.github.io/picasso/)

A powerful image downloading and caching library for Android

[commons-io:commons-io:2.4](https://commons.apache.org/proper/commons-io/)

Commons IO is a library of utilities to assist with developing IO functionality.

## Short	description	about	how	to	generate	your	app

Open the project in android studio and hit the play button.

## General	explanation	about	your	code	and	app
 
 * It is a simple contact app:
     * Add, Edit, Create and Remove Contacts.
     * You can set name, email, bio and a photo for a contact
 * It is prepared to run in small and big screens.
 
##	What	should	you	change	on	your	implementation	if	you	have	more	time

* Implement events manager for contact deletion and contacts removal

* Handle no internet access  ( Http response errors )

* Implement ListView Animation/Effects

* Implement Group Deletion of Contacts

* Add more automated tests

* Delete confirmations
