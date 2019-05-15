# Sprint 5 - *t12* - *Team Spirit*

## Goal

### A Beautiful User Experience!
### Sprint Leader: Andre Hochmuth

## Definition of Done

* Version in pom.xml should be `<version>5.0.0</version>` for your final build for deployment.
* Increment release `v5.0` created on GitHub with appropriate version number and name.
* Increment `server-5.0.jar` deployed for testing and demonstration on SPRINT5 assignment.
* Sprint Review, Restrospective, and Metrics completed (team/sprint5.md).


## Policies

#### Mobile First Design!
* Design for mobile, tablet, laptop, desktop (in that order).
* Use ReactStrap for a consistent interface (no HTML, CSS, style, etc.).
* Must adhere to the TripCo Interchange Protocol (TIP) for interoperability and testing.
#### Clean Code
* Code Climate maintainability of A or B.
* Code adheres to Google style guides for Java and JavaScript.
#### Test Driven Development
* Write method headers, unit tests, and code in that order.
* Unit tests are fully automated.
* Code Coverage above 50%
#### Configuration Management
* Always check for new changes in master to resolve merge conflicts locally before committing them.
* All changes are built and tested before they are committed.
* All commits include a task/issue number.
* All commits include tests for the added or modified code.
* All tests pass.
#### Continuous Integration / Delivery 
* Master is never broken.  If broken, it is fixed immediately.
* Continuous integration successfully builds and tests all pull requests for master branch.
* All Java dependencies in pom.xml.  Do not load external libraries in your repo. 
* Each team member must complete Interop with another team and file an issue in the **class** repo with the results.
  * title is your team number and your name, 
  * labels should include Interop and the Team that you tested with, 
  * description should include a list of tests performed, noting any failures that occurred.


## Plan

This sprint will complete the following Epics.

* *#29 I want to know where I am on the map: map should mark the location of the user*
* *#237 Let me plan trips world wide: Database allows the user to search world wide*
* *#338 Make the application easier to use: Make application UI user friendly*
* *#339 Give me the shortest trips possible: implement 3-opt*
* *#346 Could the planning be a bit faster?: implement optimizations with concurrency*

***Our plan includes the last few epics that are relevant at this stage of our project. These epics are mainly associated with user experience upgrades, since our application is functional when we finish the worldwide database searching feature. 
Our primary goal is to not put code up that fails code climate tests, which significantly reduced our ci score in sprint 4. Our secondary goal is to make sure our code is all clean and ready to be deployed or given to our client with the assumption that other programmers will continue working on it. Below are some images of planning done for the worldwide database searching feature.***

![IMG1](https://github.com/csucs314s19/t12/raw/master/team/Sprint5_Image1.png)
![IMG2](https://github.com/csucs314s19/t12/raw/master/team/Sprint5_Image2.png)

## Metrics

| Statistic | # Planned | # Completed |
| --- | ---: | ---: |
| Epics | *5* | *3* |
| Tasks |  *20*   | *16* | 
| Story Points |  *21*  | *16* | 


## Scrums

| Date | Tasks closed  | Tasks in progress | Impediments |
| :--- | :--- | :--- | :--- |
| *April 22* | *n/a* | *344, 340, 341, 165* | *none* |
| *April 24* | *n/a* | *344, 340, 341, 165* | *none* | 
| *April 26* | *n/a* | *344, 340, 341, 349, 165* | *none* |
| *April 29* | *165* | *344, 340, 341* | *none* |
| *May 1* | *341, 165* | *349, 344, 320, 353, 344* | *none* |
| *May 3* | *353, 344* | *349, 360, 359, 320, 361, 370* | *none* |
| *May 6* | *361, 370* | *349, 360, 359, 320* | *none* |
| *May 8* | *319, 340, 341, 344, 349, 353, 359* | *342, 372* | *none* |


## Review (focus on solution and technology)

In this sprint, we focused on improving the user experience. This involved cleaning up a few of the past created features as well as improving visual appearances. We added the current location functionality to allow the user to view their own location, and then decided that we should delete the homepage and have the itinerary be the homepage, since it includes a map showing the current location already; and will allow the user to get straight into creating their itinerary. We also added functionality to add locations worldwide

#### Completed epics in Sprint Backlog 

These Epics were completed.

* *#29 I want to know where I am on the map: map should mark the location of the user*
* *#237 Let me plan trips world wide: Database allows the user to search world wide*
* *#338 Make the application easier to use: Make application UI user friendly*

#### Incomplete epics in Sprint Backlog 

These Epics were not completed.

* *#339 Give me the shortest trips possible: implement 3-opt*
* *#346 Could the planning be a bit faster?: implement optimizations with concurrency*

#### What went well

The team definitely reached the performing stage during this Sprint. We essentially came up with epics to complete at the beginning of the plan, and then each researched tools and implemented them. Thus, our team has become a self-sufficient entity with people performing in their general areas of expertise. This sprint definitely seemed very smooth.

#### Problems encountered and resolutions

We failed our plan because we had not created tasks to accompany the epics. Instead, we added tasks this sprint as we discovered new tools and implementation details that would satisfy the requirements of the epic. I think this way of doing things can end up being bad in certain situations, but due to our smooth flowing team environment this sprint, we were able to effectively implement the tasks to complete the epics all in fair time. 


## Retrospective (focus on people, process, tools)

This sprint, we definitely did a good job of coming up with tasks on our own. We were able to play with the website, and then intuitively come up with fixes that would improve the user experience. This is extremely important in most workplaces. The tools we used were usually chosen for specific tasks.

#### What we changed this sprint

This sprint we mainly became more self-sufficient. Thus, we also all collaborated to solve issues and push things out much more quickly and efficiently. Thus, we had hit the performing stage for this sprint.

#### What we did well

Our efficiency to check each other and push out tasks was very good this sprint. We also were very good about giving feedback on certain updates to the deployment. 

#### What we need to work on

We could be doing more work earlier on in the sprint to allow for more time at the end of the sprint for testing. 

#### What we will change next sprint 

We will plan more of our tasks out and get a solidified plan as early as possible, so that we can estimate total work at the beginning, rather than as we go.
