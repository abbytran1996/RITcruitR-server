# RecruitRs

A cross-playform mobile ap for quickly processing job applicants/applications


### Description

A cross-platform mobile application made to aid _Students_ and _Recruiters_ in the
  job application/fulfillment process. Meant to expedite and simplify the process of applying
  to/filling a job posting, the application allows each user type to quickly process
  a multitude of applicants/job offers based on user preferences in multiple phases.

In the last phase, the system connects a reduced and meaningful subset of the interested
  Students and Recruiters, providing them with a meaningful basis of interaction
  in as little time and with as much context as possible.


### Background

This project is sponsored by [Jim Bondi](https://www.rit.edu/emcs/oce/staff#staff581)
  from the office of [Co-Op & Career Services](https://www.rit.edu/emcs/oce/)
  at RIT, and was/is being developed as part of the RIT Software Engineering
  Senior Project (SWEN-561, SWEN-562) coursework required for graduation.


### How to Run

1. update the `spring.datasource.url` settings within the `resources/application.properties` to point at the hosted/desired database
2. update the `spring.datasource.` `username` and `password` to match the determined credentials.
3. run `gradlew build` to run the server against this database
4. if desired, see the `client` repository for instructions on how to run that.


### Contributing

1. Start progress on the highest priority JIRA story assigned to you in the sprint (e.g. `REC-123`)
2. On your local machine, switch to the `master` branch and update from origin `git checkout master && git pull origin master`
3. Create a branch named after the ticket id (`git checkout -b REC-123`)
4. Develop your feature/bugfix as normal, committing often with the format `REC-123: [verb] [object phrase]`
5. When development is complete, publish the branch (`git push origin REC-123`)
6. Create a pull request into the currently active `feature_name` branch for that iteration, following the auto-generated 
   instructions in the template.
7. Merge the pull request into `feature_name` after it has been reviewed, approved, and tested.
8. At the completion of each iteration (every 2 weeks), a pull request will be
   made from `feature_name` into `master` marking the next release.
