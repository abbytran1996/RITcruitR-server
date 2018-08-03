# Matching Subsystem

Matching is the core part of the system responsible for initially connecting 
  recruiters and students by populating the student's initial match list.
  
### When are they calculated?

There are multiple triggers for calculating new matches:

- a student updates their skills (or initially sets them after registering)
- a company creates a new job posting or updates its requirements  

### How are they determined

There are four types of considerations going into the calculation of a given match, 
set by the jobPosting or by the student:

|Job or Student|code representation|components|match criteria|weight| 
|:----|:----|:---|:----|:----|
|JOB|`jobPosting.requiredSkils`|`Set<Skill>`|percentageSetIntersection|`REQUIRED_SKILLS_WEIGHT`|
|JOB|`jobPosting.recommendedSkills`|`Set<Skill>`|percentageSetIntersection|`job.getRecommendedSkillsWeight()`|
|JOB|`jobPosting.jobFilters`|

- `jobPosting.requiredSkills`
- `jobPosting.`

