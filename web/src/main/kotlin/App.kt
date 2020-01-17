import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import style.styles
import styled.StyledComponents
import styled.injectGlobal

class App : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        StyledComponents.injectGlobal(styles.toString())

        div {
            videoList {}
        }
    }
}