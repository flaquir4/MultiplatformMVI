import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    kotlin("js")
}
apply {
    plugin("kotlin-dce-js")
}


repositories {
    jcenter()
    maven(url = "https://dl.bintray.com/kotlin/kotlin-js-wrappers")
}

kotlin {
    repositories {
        jcenter()
        maven(url = "https://dl.bintray.com/kotlin/kotlin-js-wrappers")

    }
    target {
        browser {
            testTask {
                useKarma {
                    useIe()
                }
            }

            webpackTask {

                compilation.kotlinOptions {
                    moduleKind = "commonjs"
                    sourceMap = false
                    sourceMapEmbedSources = "never"
                    metaInfo = false
                    friendModulesDisabled = false
                }
                compilation.packageJson {
                }
                sourceMaps = false
                outputFileName = "web.js"
            }
            runTask {
                useCommonJs()
                outputFileName = "web.js"
            }
        }
    }
    sourceSets.main {
        repositories {
            jcenter()
            mavenCentral()
            maven(url = "https://dl.bintray.com/kotlin/kotlin-js-wrappers")

        }
        dependencies {
            implementation(project(":common"))
            implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.6.12")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.3.2")
            implementation("org.jetbrains:kotlin-react:16.9.0-pre.88-kotlin-1.3.60")
            implementation("org.jetbrains:kotlin-react-dom:16.9.0-pre.88-kotlin-1.3.60")
            implementation("org.jetbrains:kotlin-css:1.0.0-pre.88-kotlin-1.3.60")
            implementation("org.jetbrains:kotlin-styled:1.0.0-pre.88-kotlin-1.3.60")
            implementation("org.jetbrains:kotlin-extensions:1.0.0-pre.88-kotlin-1.3.60")
            implementation(kotlin("stdlib-js"))
            api(npm("text-encoding"))
            implementation(npm("core-js", "3.4.5"))
            implementation(npm("tailwindcss"))
            compileOnly(npm("mini-css-extract-plugin"))
            this.
            implementation(npm("postcss-loader"))
            implementation(npm("postcss-import"))
            implementation(npm("@fullhuman/postcss-purgecss"))
            implementation(npm("uglifyjs-webpack-plugin"))
            implementation(npm("mini-css-extract-plugin"))
            implementation(npm("inline-style-prefixer", "5.1.0"))
            implementation(npm("styled-components", "4.3.2"))
            implementation(npm("react", "16.9.0"))
            implementation(npm("react-share"))
            implementation(npm("react-player"))
            implementation(npm("react-dom", "16.9.0"))
        }
    }
}


