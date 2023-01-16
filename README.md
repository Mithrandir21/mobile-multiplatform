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
[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=_mobile-multiplatform)](https://sonarcloud.io/summary/new_code?id=_mobile-multiplatform)


[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=_mobile-multiplatform&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=_mobile-multiplatform)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=_mobile-multiplatform&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=_mobile-multiplatform)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=_mobile-multiplatform&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=_mobile-multiplatform)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=_mobile-multiplatform&metric=bugs)](https://sonarcloud.io/summary/new_code?id=_mobile-multiplatform)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=_mobile-multiplatform&metric=coverage)](https://sonarcloud.io/summary/new_code?id=_mobile-multiplatform)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=_mobile-multiplatform&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=_mobile-multiplatform)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=_mobile-multiplatform&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=_mobile-multiplatform)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=_mobile-multiplatform&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=_mobile-multiplatform)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=_mobile-multiplatform&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=_mobile-multiplatform)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=_mobile-multiplatform&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=_mobile-multiplatform)

Analysis of the codebase, including finding known issues, bugs, security issues, unit test code coverage, and much more.


## BitRise CI/CD
[![Build Status](https://app.bitrise.io/app/f700d6d913604895/status.svg?token=aPoJf3BJ4OCF-PeAzQQc0Q&branch=main)](https://app.bitrise.io/app/f700d6d913604895)

Continuous Integration and Continuous Delivery (CI/CD) pipelines for builds, testing, integration and releases. 
