# GitHub Users

<p align=center>
<img src="https://github.com/piotrsulej/GitHubUsers/blob/master/right-to-left.png" height="300" /><img src="https://github.com/piotrsulej/GitHubUsers/blob/master/filtering.png" height="300" /><img src="https://github.com/piotrsulej/GitHubUsers/blob/master/error-handling.png" height="200" />
</p>

## Features
* Application will display list of the first 30 GitHub users and 3 of their repositories
* Application will download users list only once and save it to database for future usage
* You can filter the list by typing part of user's login
* You can filter the list by typing part of user's repository name
* Application works correctly on RTL layouts

## Modules
* **app** - dependency configuration for production application
* **app-mock** - dependency configuration for mock application that can be used for automated user interface testing
* **users** - contains all the code related to users list
* **users-mock** - contains all the code needed for for automated user interface testing of users list

## Overview of dependencies
### Dependency injection
* https://github.com/google/dagger
### Asynchronicity
* https://github.com/ReactiveX/RxJava
### Images loading
* https://github.com/bumptech/glide
### Network operations
* https://github.com/square/retrofit
### Database
* https://developer.android.com/jetpack/androidx/releases/room
### Testing
* https://github.com/junit-team/junit4
* https://github.com/nhaarman/mockito-kotlin
* https://github.com/joel-costigliola/assertj-core
### JSON (de)serialization
* https://github.com/google/gson
### User Interface
* https://developer.android.com/jetpack/androidx/releases/appcompat
* https://developer.android.com/jetpack/androidx/releases/constraintlayout
* https://github.com/sockeqwe/AdapterDelegates

## Ideas for future improvements
* Users screen is inside separate module - this gives the possibility to reuse it in more applications, e.g. with different implementation of model, view or presentation layer.
* Automated user interface tests can be created for **app-mock** for fast regression testing.
* `pl.sulej.utilities` package could be placed inside separate module and shared between different features.
