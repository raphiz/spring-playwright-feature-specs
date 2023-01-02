package io.github.raphiz.springplaywrightfeaturespecs

import org.intellij.lang.annotations.Language
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
open class ExampleSpringBootApp

@RestController
open class ExampleController {
    @GetMapping("/")
    @ResponseBody
    @Language("HTML")
    fun index() = """
        <!doctype html>
        <html>
        <head>
             <title>Document</title>
        </head>
        <body>
            <h1>Hello World</h1>          
        </body>
        </html>
    """.trimIndent()
}