package io.github.raphiz.springplaywrightfeaturespecs

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Playwright
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class PlaywrightExtension : BeforeAllCallback, BeforeEachCallback,
    ExtensionContext.Store.CloseableResource {
    override fun beforeAll(context: ExtensionContext) {
        if (!started) {
            started = true
            launchBrowser()
            registerShutdownHook(context)
        }
    }

    private fun launchBrowser() {
        playwright = Playwright.create()
        browser = playwright!!.chromium().launch(
            BrowserType.LaunchOptions()
        )
    }

    override fun beforeEach(context: ExtensionContext) {
        val springContext = SpringExtension.getApplicationContext(context)
        val port = springContext.environment.getProperty("local.server.port")!!.toInt()

        BrowserContext.browserContext = BrowserContext(browser!!, port)
    }

    private fun registerShutdownHook(context: ExtensionContext) {
        context.root.getStore(ExtensionContext.Namespace.GLOBAL)
            .put(PlaywrightExtension::class.qualifiedName, this)
    }

    override fun close() {
        browser?.close()
        playwright?.close()
    }

    companion object {
        private var started = false
        private var browser: Browser? = null
        private var playwright: Playwright? = null
    }

}