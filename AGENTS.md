# Repository Guidelines

This Maven-based Java 8 calculator evaluates infix expressions, so contributions should preserve deterministic parsing, precise error reporting, and file-based workflows.

## Project Structure & Module Organization
Source lives under `src/main/java/org/example/calculator`, split into `core` (calculation engine), `core/parser` (tokenizer, AST, nodes), and `io` (FileReader/FileWriter). CLI wiring is in `App.java`. Inputs such as `src/main/resources/input.txt` feed sample expressions. Tests mirror the package layout inside `src/test/java`. Build descriptors (`pom.xml`) and design notes (`DESIGN.md`) sit at the repo root; keep new assets under `src/main/resources` unless a tool requires another location.

## Build, Test, and Development Commands
Use Maven for every workflow:
```bash
mvn clean compile        # recompile with a fresh target directory
mvn test                 # run all JUnit 5 tests
mvn package              # create the runnable JAR in target/
java -cp target/classes org.example.calculator.App input.txt output.txt
```
When iterating, prefer `mvn -q test` to keep logs concise, and reference files via relative paths from the repo root.

## Coding Style & Naming Conventions
Follow the established four-space indentation, braces on their own line, and UTF-8 source files. Package names remain lowercase (`org.example.calculator...`), classes/interfaces use PascalCase (`TokenParser`), methods camelCase, and constants SCREAMING_SNAKE_CASE. Favor descriptive exceptions (see `CalculatorException`). Keep public APIs documented with Javadoc-style comments and avoid adding new logging frameworks; rely on `System.out` for CLI messaging unless Maven profiles demand otherwise.

## Testing Guidelines
JUnit 5 (Surefire includes `**/*Test.java`) powers the suite. Place unit specs beside the class under test (`core/parser/ASTParserTest`). Name tests after behavior, e.g., `shouldHandleDecimalDivision`. Cover operator precedence, parenthesis errors, and batch workflows before merging. Run `mvn test` prior to every commit; add parameterized tests when reproducing file-driven bugs. If a feature depends on sample resources, add fixtures under `src/test/resources`.

## Commit & Pull Request Guidelines
History currently uses short imperative messages (e.g., `init`); keep summaries ≤50 chars with detail in the body when needed (`feat: support exponent token`). Every PR should describe motivation, reference related issues, list `mvn test` results, and include sample CLI commands or screenshots when IO changes affect UX. Highlight parser or tokenizer edge cases covered by the change and tag reviewers familiar with the touched module.
