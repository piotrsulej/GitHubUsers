# GitHub Users

Example Android application. Application code developed by [@piotrsulej](https://github.com/piotrsulej), automated interface tests developed by [@BartoszDybczak](https://github.com/BartoszDybczak).

<p align=center>
<img src="https://github.com/piotrsulej/GitHubUsers/blob/master/right-to-left.png" height="300" /><img src="https://github.com/piotrsulej/GitHubUsers/blob/master/filtering.png" height="300" /><img src="https://github.com/piotrsulej/GitHubUsers/blob/master/error-handling.png" height="200" />
</p>

## Features
* Application will display list of the first 30 GitHub users and 3 of their repositories
* Application will download users list only once and save it to database for future usage
* You can filter the list by typing part of user's login
* You can filter the list by typing part of user's repository name

## Project structure
* **users** - contains all the code related to users list.
* **users-mock** - contains all the code needed for for automated user interface testing of users list. It contains dependency configuration for users lists with model layer depending on static mock data from JSON assets instead of network call.
* **app** - dependency configuration for production application.
* **app-mock** - having *users* and *users-mock* as a separate modules gives the possibility to reuse it in more applications, e.g. with different implementation of model, view or presentation layer. This mock version used for automated tests is an example of how this approach could be implemented. It uses *users-mock* module and dependency configuration that is provided there to make automated interface tests independent from GitHub API.
* **app-res** - directory used by both *app* and *app-mock* to store shared resources.

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
* https://developer.android.com/training/testing/espresso
### JSON (de)serialization
* https://github.com/google/gson
### User Interface
* https://developer.android.com/jetpack/androidx/releases/appcompat
* https://developer.android.com/jetpack/androidx/releases/constraintlayout
* https://github.com/sockeqwe/AdapterDelegates
