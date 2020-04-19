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
* https://developer.android.com/topic/libraries/architecture/room
### Testing
* https://github.com/junit-team/junit4
* https://github.com/nhaarman/mockito-kotlin
* https://github.com/joel-costigliola/assertj-core
### User Interface
* https://developer.android.com/jetpack/androidx
* https://github.com/sockeqwe/AdapterDelegates

## Ideas for future improvements
* Users screen is inside separate module - this gives the possibility to reuse it in more applications, e.g. with different implementation of model, view or presentation layer.
* Mock version for automated user interface tests could be created and it should use prepared mocked data in order to be able to test user interface without dependency on real GitHub API.
* `pl.sulej.utilities` package could be placed inside separate module and shared between different features.
