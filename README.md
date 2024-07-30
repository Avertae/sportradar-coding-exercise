# Live Football World Cup Score Board

## Requirements
Library should be able to keep leaderboard state and to provide means to update its state for the following cases:
1. Create Match
  - default score is 0:0
2. Update Match
  - new absolute score
3. Delete Match
4. Get Summary
  - returns list of matches ordered by:
    1. total score descending (sum of Home Team Score and Away Team Score)
    2. start timestamp descending

## Constraints
* simple library implementation
* TDD approach
* in-memory storage

## Entities
1. Match
   - Home team
   - Away team
   - Home team score
   - Away team score
   - start timestamp
2. Summary
   - ordered list of Matches
   - generation timestamp

## Assumptions and edge cases
1. Don't allow to start match, if one of teams is already on score board

