# Xtrem TDD workshop
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=les-tontons-crafters_xtrem-tdd-0525&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=les-tontons-crafters_xtrem-tdd-0525)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=les-tontons-crafters_xtrem-tdd-0525&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=les-tontons-crafters_xtrem-tdd-0525)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=les-tontons-crafters_xtrem-tdd-0525&metric=coverage)](https://sonarcloud.io/summary/new_code?id=les-tontons-crafters_xtrem-tdd-0525)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=les-tontons-crafters_xtrem-tdd-0525&metric=bugs)](https://sonarcloud.io/summary/new_code?id=les-tontons-crafters_xtrem-tdd-0525)

[![CodeScene Average Code Health](https://codescene.io/projects/64234/status-badges/average-code-health)](https://codescene.io/projects/64234)
[![CodeScene System Mastery](https://codescene.io/projects/64234/status-badges/system-mastery)](https://codescene.io/projects/64234)
[![CodeScene general](https://codescene.io/images/analyzed-by-codescene-badge.svg)](https://codescene.io/projects/64234)

You have probably already heard or practiced Test-Driven Development (TDD) but have you already tried it in an Xtrem way?

What do we mean by Xtrem?
We propose to practice TDD on a kata using mob programming and introducing different constraints that you will pick randomly. We expect you to find smart ways to overcome those constraints.

Those constraints can be of different types : Design, Testing, Practice, Architecture.

Here are some example of constraints that we have documented on our website :
- Use TCR workflow (Test && Commit || Revert)
- Use a Test DataBuilder
- Check your dependency freshness with libyear
- Write only pure functions
- Make at least 2 refactorings after a passing test
- Write your next test using Approval Testing approach
- Check the quality of your tests with Mutation Testing
- and much more ...

By overcoming those constraints you will learn new ways of designing your code that you will be able to use in your day-to-day.

![Welcome](docs/img/xtrem-tdd-logo.png)

#### Prerequisites
You need to install the runtime environment for the language you prefer use:

- C# : [.NET 6](https://dotnet.microsoft.com/en-us/download/dotnet/6.0)
- java : [SDK 17](https://www.oracle.com/java/technologies/downloads/)
- Scala 2 : `scala 2.13.8`

#### Workshop structure
We have structured this workshop using the [4C model](https://www.bowperson.com/2017/11/reposting-a-quick-guide-to-the-4cs-map/) :

- `Connection` : Help learners make connections with the topic of the workshop
- `Concepts` : Direct instruction, lecture or presentation part
- `Concrete Practice` : Learners actively practice a new skill using the new information
- `Conclusion` :  Learners summarize what they have learned

## Workshop
- [Connection](docs/connection.md)
- [Concepts](docs/concepts.md)
- [Concrete Practice](docs/concrete-practice.md)
- [Conclusion](docs/conclusion.md)

## Escape Game
In addition to this repository, we also provide an escape game based on cards that you can use to facilitate a 2/3 days training.

Cards and solution are available [here](https://github.com/les-tontons-crafters/xtrem-tdd-escape-game).

![xtrem tdd cards game](https://raw.githubusercontent.com/les-tontons-crafters/xtrem-tdd-escape-game/main/img/cover-xtrem-tdd.png)

## Video
We have been invited by [Valentina Cupać](https://valentinacupac.com/) to demonstrate part of this kata in her [Tech Excellence meetup](https://www.meetup.com/techexcellence/). The replay is available [here](https://www.youtube.com/live/yxO7YHkB83I?feature=share).

[![Replay Xtrem TDD on Youtube](docs/img/video.png)](https://www.youtube.com/live/yxO7YHkB83I?feature=share)
