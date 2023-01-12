# Demonstration App
#### App explanations
- Architecture: MVVM
- Storage: Room + SharedPreferences
- Separation of code into logical modules (see [MODULES.md](MODULES.md))
- Domain layer separation from API
- Unit testing of standalone logic
- Instrumentation testing of end-to-end journeys

#### Usage
- App can used as standalone app
- Deep link can used to access specific Album.<br>
Deep link schema: 'demo://album/[albumID]'

##### List of improvements
- Instrumentation testing tooling should be improved to allow less boilerplate code and more generalisation and abstraction, allowing faster writing of tests.
- App designs should be done by actual designer.
- Themes and styles could be created for specific elements to allow faster and more uniform styling of the UI elements.
- Pagination could be used for loading items from Room database.


# External Tools
## Code Quality
[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=Mithrandir21_demo)](https://sonarcloud.io/summary/new_code?id=Mithrandir21_demo)


[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Mithrandir21_demo&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Mithrandir21_demo)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Mithrandir21_demo&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=Mithrandir21_demo)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Mithrandir21_demo&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=Mithrandir21_demo)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Mithrandir21_demo&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Mithrandir21_demo)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Mithrandir21_demo&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Mithrandir21_demo)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Mithrandir21_demo&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=Mithrandir21_demo)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Mithrandir21_demo&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=Mithrandir21_demo)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Mithrandir21_demo&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Mithrandir21_demo)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Mithrandir21_demo&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=Mithrandir21_demo)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Mithrandir21_demo&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=Mithrandir21_demo)

Analysis of the codebase, including finding known issues, bugs, security issues, unit test code coverage, and much more.


## BitRise CI/CD
[![Build Status](https://app.bitrise.io/app/00529106b19e4a01/status.svg?token=e7EdZA2tcsiVLmp_ILyJJQ&branch=main)](https://app.bitrise.io/app/00529106b19e4a01)

Continuous Integration and Continuous Delivery (CI/CD) pipelines for builds, testing, integration and releases. 
