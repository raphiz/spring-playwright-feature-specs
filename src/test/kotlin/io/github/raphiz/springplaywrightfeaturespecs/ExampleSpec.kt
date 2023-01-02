package io.github.raphiz.springplaywrightfeaturespecs

import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat
import org.junit.jupiter.api.Test

@FeatureSpec
class ExampleSpec {

    @Test
    fun `they see the page for the created recipe`() = withPage {
        navigate("/")

        assertThat(locator("body")).containsText("Hello World")
    }

}