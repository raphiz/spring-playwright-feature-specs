package io.github.raphiz.springplaywrightfeaturespecs

import com.microsoft.playwright.Browser
import org.springframework.core.NamedThreadLocal

class BrowserContext(
    val browser: Browser,
    val port: Int,
) {
    companion object {
        private val browserContextThreadLocal = NamedThreadLocal<BrowserContext>("browserContext")
        var browserContext: BrowserContext
            get() = browserContextThreadLocal.get()
            internal set(ctx) = browserContextThreadLocal.set(ctx)
    }
}