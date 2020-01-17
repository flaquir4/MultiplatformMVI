package style

import kotlinx.css.*
import styled.StyledComponents.css
import styled.StyledDOMBuilder
import styled.css
import kotlin.browser.document

val styles = CSSBuilder().apply {
    root {
        margin(0.px)
        padding(0.px)
    }
    body {
        margin(0.px)
        padding(0.px)
    }
}

fun CSSBuilder.sm(block: RuleSet) =
    media("(min-width: 640px)") {
        block()
    }

fun CSSBuilder.md(block: RuleSet) =
    media("(min-width: 768px)") {
        block()
    }
fun CSSBuilder.lg(block: RuleSet) =
    media("(min-width: 1024px)") {
        block()
    }
fun CSSBuilder.xl(block: RuleSet) =
    media("(min-width: 1280px)") {
        block()
    }

