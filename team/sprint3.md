# Sprint 3 - *t12* - *Smells Like Team Spirit*

## Goal

### Shorter trips to more places!
### Sprint Leader: *Andre Hochmuth*

## Definition of Done

* Version in pom.xml should be `<version>3.0.0</version>` for your final build for deployment.
* Increment release `v3.0` created on GitHub with appropriate version number and name.
* Increment `server-3.0.jar` deployed for testing and demonstration on SPRINT3 assignment.
* Sprint Review, Restrospective, and Metrics completed (team/sprint3.md).


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
* Code Coverage above 40%
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


## Plan

This sprint will complete the following Epics.

* *#77 User: Show me a map and itinerary for my trip.*
* *#150 User: Data shouldn't go away when I change tabs.*
* *#151 User: Make my trip shorter*
* *#149 User: Let me change my itinerary*

***Our first task before beginning any epics is to update TIP API and increment all version calls, etc. 
This Sprint we will be prioritizing finishing the itinerary related epics from Sprint2 before moving on to new epics. Thus, epics #77 and #150 are our first priority. 
We then picked the new epics #151 and #149 which are both features that will be added to the itinerary, with priority given to #151 due to it's simplicity. 
We have mapped out what we want our itinerary page to look like at the end of this Sprint. We have also included a sketch planning out our itinerary display with all the features that will be added.***

![/t12/team/ItineraryDiagram.png]()  
![/t12/team/add_mockup.jpg]()



## Metrics

| Statistic | # Planned | # Completed |
| --- | ---: | ---: |
| Epics | *4* | *2* |
| Tasks |  *35*   | *32* | 
| Story Points |  *44*  | *42* | 


## Scrums

| Date | Tasks closed  | Tasks in progress | Impediments |
| :--- | :--- | :--- | :--- |
| *3/6* | *n/a* | *#170, #152, #153, #174* | *none* | 
| *3/8* | *#152, #153, #174* | *#164, #168* | *none* | 
| *3/11* | *#164, #168, #155, #172* | *#170* | *none* | 
| *3/13* | *CANCELED DUE TO WEATHER* | 
| *3/15* | *#176, #171* | *#146, #161* | *Andre (Leader) Absent* | 
|*Spring Break*|
| *3/25* | *#148, #185, #186, #188, #147, #159, #160, #190, #191, #192, #197, #158, #189, #199, #87, #151, #163, #187, #203, #195* | *#207, #156, #223* | *Simon absent* | 
| *3/27* | *#223* | *#207, #156* | *Simon Absent* |

## Review (focus on solution and technology)

In this sprint, we added the optimization feature to our itinerary tab. Then we finished adding other features to the itinerary tab to make it more useful and user-friendly. We also came close to making sure data stays when changing tabs, but did not quite complete this epic.

#### Completed epics in Sprint Backlog 

These Epics were completed.

* *#77 User: Show me a map and itinerary for my trip.*
* *#151 User: Make my trip shorter*

#### Incomplete epics in Sprint Backlog 

These Epics were not completed.

* *#150 User: Data shouldn't go away when I change tabs.*
* *#149 User: Let me change my itinerary*

Note: We were one task away from completing each of these epics.


#### What went well

- The team made consistent commits and progress, even over Spring Break. 
- We were able to complete much of the work before the final week, allowing us more time for bug fixes.


#### Problems encountered and resolutions

- In the case of one issue, the assignee had prior commitments and could not finish their task. Our resolution was to reassign to someone else if possible, but since it was very late we just left the final task of the epic for the next Sprint. 
- Difficult or vague details of implementation were experienced in this Sprint. The resolution was to post to Piazza and get an answer from an instructor/TA.


## Retrospective (focus on people, process, tools)

In this sprint, we had meetings three times a week where we talked about where were and what we were struggling with. Most of the work was done remotely.

#### What we changed this sprint

Our changes for this sprint included ...
- Giving ourselves time to focus on testing and bug fixes over the last week.
- We planned in advance for Spring Break. 
- We met less in the computer science building to work in groups.

#### What we did well

We achieved a fairly consistent burndown chart, with a majority of the work being done over Spring Break which was as planned.

We contributed to Piazza a few times this Sprint.

#### What we need to work on

We could still improve our strategy to include things slightly more in advance to allow a few days to make sure we have everything completed and for testing/bug fixes. 

#### What we will change next sprint 

We will try to assign more of the work/tasks at the beginning of the Sprint. This will only serve as a rough guideline to follow and will hopefully make the workloads more equal over the course of the Sprint.
