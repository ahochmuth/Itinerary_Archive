| Inspection | Details |
| ----- | ----- |
| Subject | *Calculator.js* |
| Meeting | *April 15, 2019* |
| Checklist | *http://users.csc.calpoly.edu/~jdalbey/301/Forms/CodeReviewChecklistJava.pdf http://www.cs.toronto.edu/~sme/CSC444F/handouts/java_checklist.pdf https://dzone.com/articles/java-code-review-checklist https://javarevisited.blogspot.com/2011/09/code-review-checklist-best-practice.html* |

### Roles

| Name | Preparation Time |
| ---- | ---- |
| Andre Hochmuth - End User/Maintainability | 45 minutes  |
| Simon Nardos -  | 30 min |
| Mike Popesh -  Style/Complexity | 50 min |
| Nick ODell - Tester | 30 minutes |

### Problems found

| file:line | problem | hi/med/low | who found | github#  |
| --- | --- | :---: | :---: | --- |
| Calculator.js  | Ran through Sprint1 distance api requests and other selected corner cases. No Issues. | low | Andre  | n/a |
| Calculator.js  | Custom Units are not implemented. | low | Andre | #43 |
| Calculator.js  | We may want to take away the header for the calculator.js that says to choose units from the options page. | low | Andre | no |
| Calculator.js:10 | Could possible put error styling in tripcowebstyle instead. | l | Simon | no |
| Calculator.js:85 | Reformat origin and destination pane for visual appeal | l | Simon | |
| Calculator.js:Mult. | Possibly add comments to explain methods | l | Simon | #316 |
| 73 | capitalizedCoordinate is not necessary  | low | Mike |  #318 |
| 95 | more discriptive function name for createDistance()  | low | Mike | #318 |
| 106/164 | coordinateValid() and validateCoordinates() functions have similar names but do different things | low | Mike |   #317 |
| 115 | coordinateParse() could use coordinateValid() to check coorinates | low | Mike |  |
| 130 | is there a purpose for the valueCopy variable? Do we need a null check for argument? | low | Mike | #318 |
| 147 | We don't care what the response code is, except that it was successful. We should modify `processRestfulAPI` to add success field to returned object. Simplifies both Calculator, FindAddDialog, and Itinerary. | med | Nick | |
| 23 | We have a lot of binded functions. We can't use arrow functions in the body of a class, but why not? I've seen other projects do that. Is our babel misconfigured? | low | Nick | n/a |
| 188 | This would be easier to express using the spread operator, but babel won't let me, and I'm not sure why. | low | Nick | n/a |
| 179 | Stop putting requestVersion: 4 in every file. Instead, have one copy of the request version, which we change once every sprint. | med | Nick | #319 |
| any | We should try using typechecking. When we were converting Itinerary to put places as a prop instead of as state, we had a number of bugs that would have been caught by typechecking the props. | low | Nick | #320 |
| 95 | We should prefix functions that create JSX with `display`, like we do for itinerary. It makes the purpose of the function more clear. e.g. `displayDistance` not `createDistance` | med | Nick | #317 |
