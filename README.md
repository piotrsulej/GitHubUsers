# GitHub Users

# Features
* Application will display list of the first 30 GitHub users
* You can expand any user to see their list of repositories
* Application will download users list only once on startup
* Application will download list of repositories for specific user only once after you will click on "more" icon
* You can filter the list by typing part of user's login
* You can filter the list by typing part of user's repository name if it was already downloaded
* Application works correctly on RTL layouts

# Overview of dependencies
* Dependency injection - https://github.com/google/dagger
* Asynchronicity - https://github.com/ReactiveX/RxJava
* Images loading - https://github.com/bumptech/glide
* Network operations - https://github.com/square/retrofit
* Testing - https://github.com/junit-team/junit4, https://github.com/nhaarman/mockito-kotlin, https://github.com/joel-costigliola/assertj-core
* User Interface - https://developer.android.com/jetpack/androidx, https://github.com/sockeqwe/AdapterDelegates

# Ideas for future improvements
* Users screen is inside separate module - this gives the possibility to reuse it in more applications, e.g. with different implementation of model layer. One of the applications could use implementation that would save downloaded data to database (Room Persistence Library could be used for this), and other one could still use the same behavior as now.
* Mock version for automated user interface tests could be created and it should use prepared mocked data in order to be able to test user interface without dependency on real GitHub API.
* pl.sulej.utilities package could be placed inside separate module and shared between different features.
