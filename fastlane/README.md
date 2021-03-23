fastlane documentation
================
# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```
xcode-select --install
```

Install _fastlane_ using
```
[sudo] gem install fastlane -NV
```
or alternatively using `brew cask install fastlane`

# Available Actions
## Android
### android basic_checks
```
fastlane android basic_checks
```
Checks for WIP PRs
### android checks_and_distribution
```
fastlane android checks_and_distribution
```
Checks for non-wip PRs
### android start_release_candidate
```
fastlane android start_release_candidate
```
Start release candidate
### android start_hotfix
```
fastlane android start_hotfix
```
Start hotfix
### android beta_stg_release
```
fastlane android beta_stg_release
```
Build and Submit a new version StgRelease to App Distribution
### android deploy
```
fastlane android deploy
```
Deploy a new version to the Google Play
### android ktlint
```
fastlane android ktlint
```
Runs code linter
### android unit_tests
```
fastlane android unit_tests
```
Runs all unit tests
### android distribute_app
```
fastlane android distribute_app
```
Firebase App Distribution

----

This README.md is auto-generated and will be re-generated every time [fastlane](https://fastlane.tools) is run.
More information about fastlane can be found on [fastlane.tools](https://fastlane.tools).
The documentation of fastlane can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
