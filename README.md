# JavaFX Quiz Game (End Assignment)

This is a JavaFX Quiz Game implementing **Factory**, **Singleton**, **Observer (via JavaFX bindings)**, and **MVC** patterns.

## How to run (IntelliJ 2025.2, Java 21 LTS)
1. Open the project as a Maven project.
2. Ensure SDK is set to **Java 21**.
3. Run with Maven target or the `MainApp` run configuration:  
   ```bash
   mvn clean javafx:run
   ```

## Features
- Load quiz JSON (Appendix 1 compatible) via **Menu → Load Quiz**.
- **Start Quiz** enabled only after loading data.
- Enter **player name** before first question.
- **Sequential questions**, no back navigation; only question pane updates.
- **Per-question countdown** with `Timeline` and progress bar (UI stays responsive).
- **Live score** bound to model (Observer pattern).
- **Save results** as `[quizId]-results.json` (append if exists) in the `results/` folder.
- Robust error handling and **JUnit tests** for parsing and scoring.

## Credentials
No login required.

## Folder to find results
Generated under `results/` in the project root.

## UML (simplified)
```
+-----------------+        +-------------------+
| Question        |<>----- | ChoiceQuestion    |
| - id:String     |        | - choices:List<>  |
| - text:String   |        | - correct:String  |
| - timeLimit:int |        +-------------------+
| +scoreFor(...)  |
+-----------------+        +-------------------+
        ^                  | BooleanQuestion   |
        |                  | - correct:boolean |
        |                  +-------------------+
        |
+------------------+
| QuestionFactory  |
+------------------+

+------------------+        (Singleton)
| GameManager      |<-------------------+
| - playerName     | IntegerProperty score (Observable)
| - currentIndex   |
| - score          |
+------------------+

Controllers (MVC):
MenuController, GameController, ResultsController
```

## Notes
- Code is formatted and follows Java conventions.
- No static global state; only `GameManager.getInstance()` is a controlled singleton.

