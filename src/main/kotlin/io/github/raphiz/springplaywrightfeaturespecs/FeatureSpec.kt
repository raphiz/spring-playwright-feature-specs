package io.github.raphiz.springplaywrightfeaturespecs

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringWithPlaywrightExtension::class)
annotation class FeatureSpec
