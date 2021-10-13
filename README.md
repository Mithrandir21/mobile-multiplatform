# demo
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
