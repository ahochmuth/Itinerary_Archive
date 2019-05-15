# Sprint 2 - *t12* - *Team Spirit*

## Goal

### A map and itinerary!
### Sprint Leader: *Nick ODell*

## Definition of Done

* Version in pom.xml should be `<version>2.0.0</version>` for your final build for deployment.
* Increment release `v2.0` created on GitHub with appropriate version number and name.
* Increment deployed for testing and demonstration on SPRINT2 assignment.
* Sprint Review and Restrospectives completed (team/sprint2.md).


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

* *#79 User: The calculator data shouldn't go away when units change.*
* *#77 User: Show me a map and itinerary for my trip*
* *#15 User: I may need distances in other units of measure*
* *#78 User: Enter latitudes and longitudes in the calculator using degree-minute-second and other formats.*

Key planning decisions for this sprint include considering

## Metrics

| Statistic | # Planned | # Completed |
| --- | ---: | ---: |
| Epics        | 4         | 1       |
| Tasks        | 18        | 17      |
| Story Points | 23        | 24      |

## Scrums

| Date | Person | Tasks closed  | Tasks in progress | Impediments |
| :--- | :---   | :---          | :---              | :--- |
| 2/13 | MP     |               |     #93           |  MP: Learn TIP v2 |
|      | SN     |               |     #94           | SN: Nothing |
|      | AH     |               |     #95           | AH: Learn TIP v2, Learn GSON |
|      | NO     |               |     #84           | NO: Learn some React |
| 2/15 | MP     | #93           | #97 #102          |  |
|      | SN     | #94           | #98               |  |
|      | AH     | ...           | #95               |  |
|      | NO     | #84           | #104 #83          |  |
| 2/18 | MP     | #102 #105     | #107              |  |
|      | SN     |               | #98               |  |
|      | AH     | #94           | #95               |  |
|      | NO     | #104          | #83               |  |
| 2/20 | MP     |               | #107              |  |
|      | SN     | #98           | #109              |  |
|      | AH     | #95 #110      | testing           |  |
|      | NO     |               | #83               |  |
| 2/22 | MP     | #107          | #116              |  |
|      | SN     |               | #109              |  |
|      | AH     | #112          | #115              |  |
|      | NO     |               | #83 #117          |  |
| 2/25 | MP     |               | #116              |  |
|      | SN     | #109          | #86               |  |
|      | AH     | #115 #112     |                   |  |
|      | NO     | #83           | #117              |  |
| 2/27 | MP     | #85           | #127              |  |
|      | SN     |               | #86               |  |
|      | AH     |               | #117 #114         |  |
|      | NO     |               | #117 #129         |  |

<!---
| .... | MP     | ...           | ...               |  |
|      | SN     | ...           | ...               |  |
|      | AH     | ...           | ...               |  |
|      | NO     | ...           | ...               |  |
-->


## Review (focus on solution and technology)

In this sprint, we moved from an application that could only calculate distance between two points to one that can import a file full of destinations, show the path, and calculate distances along each leg of the journey.

#### Completed epics in Sprint Backlog

These Epics were completed.

* *User: Enter latitudes and longitudes in the calculator using degree-minute-second and other formats*: We parse the dms coordinates on the client side, then send those to the server. We use a package called coordinate-parser.

#### Incomplete epics in Sprint Backlog

These Epics were not completed.

* *User: Show me a map and itinerary for my trip: we didn't finish showing leg and cumulative distances. We didn't finish showing only details the user wants to see.*
* *User: The calculator data shouldn't go away when units change.*
* *User: I may need distances in other units of measure*
* *User: Let me change my itinerary*
* *User: I want to know where I am on the map*
* *User: It would be nice to see a map with the calculator.*
* *User: Let me choose from different map backgrounds.*

#### What went well

We managed to fix the early problems we had in getting the specification and types wrong in TIPDistance. We learned how to problem-solve in React, and use debugging tools to speed up our changes.

We started early on the project. When we were halfway through the sprint, we had about half of our story points done.

#### Problems encountered and resolutions

We encountered problems getting new packages to work, which we solved by forcing a `npm install`. We had problems with TIPDistance, which we solved by talking to classmates who had already solved those problems.

## Retrospective (focus on people, process, tools)

In this sprint, we had meetings three times a week where we talked about where were and what we were struggling with. We also met in the computer science building, usually 2-3 times a week in pairs.

#### What we changed this sprint

Our changes for this sprint included greater use of Slack and GitHub Issues to communicate with our team.

#### What we did well

We problem-solved colaboratively by asking for help from other teams, and using Piazza to solve a problem more quickly than we could have by googling for solutions.

#### What we need to work on

We could improve our ability to test and prevent regressions, and our ability to create tests of the REST API. We lost points on the `/api/itinerary` test because we didn't realize that our REST API 404'd.

Despite our early start on the project, when we were coming into the home stretch, we found that some of the epics had been inappropriately estimated, and that old bugs sapped our productivity. We found that although we had about half of our story points done at the halfway point, we were not *half done*.

#### What we will change next sprint

We want to get major functionality, like the `/api/itinerary` test done sooner, so that we can test it better. (And check the obvious problems!)

We want to more accurately estimate our work done and work remaining.

#### Helpful classmates

 * **Joseph Gelfand**: gave hint about types required for TIPDistance, and did additional interop testing.
