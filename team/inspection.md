# Inspection - Team *T12* 
 
| Inspection | Details |
| ----- | ----- |
| Subject | *NearestNeighbor.java* |
| Meeting | *04/08/2019, 3:00PM MST, Stadium 1205* |
| Checklist | *http://users.csc.calpoly.edu/~jdalbey/301/Forms/CodeReviewChecklistJava.pdf http://www.cs.toronto.edu/~sme/CSC444F/handouts/java_checklist.pdf https://dzone.com/articles/java-code-review-checklist https://javarevisited.blogspot.com/2011/09/code-review-checklist-best-practice.html* |

### Roles

| Name | Role | Preparation Time |
| ---- | ---- | ---- |
|Andre Hochmuth  | End-User | 45 min |
|Nick ODell  | Tester | 30 min |
|Simon Nardos| Moderator | 40 min |
|Mike Popesh| Maintainability | 40 min |

### Problems found

We inspected the NearestNeighbors.java file from the server side.

| file:line | problem | hi/med/low | who found | github#  |
| --- | --- | :---: | :---: | --- |
| 72 | variable name 'size' | low | Mike Popesh | #278 |
| 102 | argument name 'l' | low | Mike Popesh   | #278 |
| 112 | variable name 'ret' | low | Mike Popesh | #278 |
| 187 | variable tourDistance should equal routeDistance | low | Mike Popesh | #278 |
| 200 | remove TODO comment | low | Mike Popesh | #285 |
| 202 | remove comment | low | Mike Popesh | #285 | 
| 225 | variable name 'temp' | low | Mike Popesh | #278 |
| mult | Add javadoc comments to all functions | low | Mike Popesh | Only to ones that need explanation |
| 72 | diff. name for size? | l | Simon | #278 |
| 98 | maybe diff. param for input list | l | Simon | #278 |
| 119 | comments for param would be nice | l | Simon | #278 |
| 139 | "                          " | l | Simon | #278 |
| 181 | "                          " | l | Simon | #278 |
| 200 | "                          " | l | Simon | #278 |
| 223 | "                          " | l | Simon | #278 |
| 71  | nearestNeighbor loop can be simplified to only have one exit point by adding a check above the loop that bails out if doing a 0-place NN, so that there is only one condition on the loop. This would have fewer branches in the loop, and therefore be easier to test | med | Nick ODell | #280 |
| 147 | optimizeTour is too complex to effectively test. We could reduce complexity by changing twoOPT to return a list of optimized tours, and creating a new method that picks the best tour from among all of the availible tours. That would allow us to get rid of the loop in optimizeTour. | med | Nick ODell |  #280 |
| 181 | inconsistent naming: twoOPT vs twoOptImprove | low | Nick ODell | #280 |
| 202 | "//possibly not right" It's definitely not right. I talked to another team. We need to make dis() wrap around when looking at the edge between the first and last place. We're missing optimizations that we shouldn't. | high | Nick ODell | #285 |
| 200 | Why is there a TODO here? | med | Nick ODell | #285 |
| 78 | Rather than represent tours with a List of Integers, maybe we should make an object to do it. A List of List of Integers is incomprehensible, but a List of Tours is just fine. | med | Nick ODell | #280 |
