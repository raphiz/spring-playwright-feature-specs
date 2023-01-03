# Spring Playwright Feature Specs

Spring Playwright Feature Specs is a **small, opinionated library to write readable feature specs for your Spring web applications in Kotlin**.

It's just a bit of setup code and syntactic sugar.

This style of testing is inspired by Ruby's [Capybara with RSpec](https://thoughtbot.com/blog/how-we-test-rails-applications#feature-specs). Read more about testing with [feature specs](https://thoughtbot.com/blog/how-we-test-rails-applications#feature-specs) in the [Testing Rails book](https://books.thoughtbot.com/books/testing-rails.html).

## Example Usage

Annotate your test class with `@FeatureSpec`. This will add the `@SpringBootTest` and enable the jUnit 5 `SpringWithPlaywrightExtension`, which will prepare a real browser (chromium) for the tests.

Each test method (annotated with jUnits `@Test`) uses the `withPage` helper function. This function opens up a new [browser context](https://playwright.dev/java/docs/api/class-browsercontext) for each test and provides access to playwrights [Page interface](https://playwright.dev/java/docs/api/class-page).

```kotlin
@FeatureSpec
class UserCreatesRecipe {

    @Test
    fun `they see the page for the created recipe`() = withPage {
        val recipeTitle = "Chicken Masala"

        navigate("/")

        getByText("Create new Recipe").click()

        getByLabel("Recipe Title").fill(recipeTitle)

        getByText("Submit!").click()

        assertThat(locator("body")).containsText(recipeTitle)
    }

    @Test
    fun `they see a useful error message when the form is invalid`() = withPage {
        navigate("/")

        getByText("Create new Recipe").click()

        getByText("Submit!").click()

        assertThat(locator("body")).containsText("title cannot be blank")
    }

}
```

[Here is a full minimal working example](https://github.com/raphiz/spring-playwright-feature-specs/blob/main/src/test/kotlin/io/github/raphiz/springplaywrightfeaturespecs/ExampleSpec.kt).

## Install

Just add `spring-playwright-feature-specs` as a test dependency to you project.

When using Gradle, I highly recommend to create a separate [test suite](https://docs.gradle.org/current/userguide/jvm_test_suite_plugin.html) for feature spec tests.

**Gradle Kotlin DSL**
```kotlin
testImplementation("io.github.raphiz:spring-playwright-feature-specs:v0.1.0")
```

**Gradle**
```groovy
testImplementation 'io.github.raphiz:spring-playwright-feature-specs:v0.1.0'
```


**Maven**
<details><p>

```xml
<dependency>
  <groupId>io.github.raphiz</groupId>
  <artifactId>spring-playwright-feature-specs</artifactId>
  <version>v0.1.0</version>
</dependency>
```

</p></details>

## Configuration

The scope of this project is very small and not intended to be configurable.

If you have different needs, consider implementing your own version of the `withPage` function or the `@FeatureSpec` annotation. If you need even more, it's probably best to integrate this code into your own code base.

## Contribution

If you have any suggestions, feedback or questions, feel free to open a [GitHub issue](https://github.com/raphiz/spring-playwright-feature-specs/issues). 