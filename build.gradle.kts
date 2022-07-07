import java.util.Properties

plugins {
    kotlin("multiplatform") version "1.7.0"
    `maven-publish`
}

group = "com.tomuvak.optional-test"
version = "0.0.3-SNAPSHOT"

val localProperties = Properties()
project.rootProject.file("local.properties").takeIf { it.canRead() }?.inputStream()?.let(localProperties::load)
fun local(key: String): String? = localProperties.getProperty(key)

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/tomuvak/optional-type")
        credentials {
            username = local("githubUser")
            password = local("githubToken")
        }
    }
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(BOTH) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("com.tomuvak.optional-type:optional-type:0.0.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("com.tomuvak.testing-assertions:testing-assertions:0.0.2")
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }

    local("githubRepository")?.let { githubRepository ->
        publishing {
            repositories {
                maven {
                    url = uri("https://maven.pkg.github.com/$githubRepository")
                    credentials {
                        username = local("githubUser")
                        password = local("githubToken")
                    }
                }
            }
        }
    }
}
