package io.github.raphiz.springplaywrightfeaturespecs

import com.microsoft.playwright.Browser.NewContextOptions
import com.microsoft.playwright.Page

fun withPage(newContextOptions: NewContextOptions? = null, block: Page.() -> Unit): Unit =
    with(BrowserContext.browserContext) {
        browser.newContext(
            newContextOptions ?: NewContextOptions().setBaseURL("http://localhost:${port}")
        ).use { context ->
            val page = context.newPage()
            page.setDefaultTimeout(500.0)
            page.block()
        }
    }
