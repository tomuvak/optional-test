# `com.tomuvak.optional-test` – testing utilities for [`com.tomuvak.optional`](https://github.com/tomuvak/optional)
This library is licensed under the [MIT License](https://en.wikipedia.org/wiki/MIT_License);
see [LICENSE.txt](LICENSE.txt).

## Table of contents
* [Rationale](#rationale)
* [Usage](#usage)
  * [Including the library in a Kotlin project](#including-the-library-in-a-kotlin-project)

## Rationale
This library provides some nice-to-have (and by no means required) testing utilities for working with the `Optional`
type provided by [`com.tomuvak.optional`](https://github.com/tomuvak/optional). Like `com.tomuvak.optional`, it is also
written in multi-platform Kotlin.

`com.tomuvak.optional` itself uses (some of) these in its test suite. These utilities are provided as a separate library
so that users of `com.tomuvak.optional` who wish to do so could also utilise them.

## Usage
Package `com.tomuvak.optional.test` contains several assertion methods specific for use with values of the `Optional`
type.

### Including the library in a Kotlin project
To add the library from
[GitHub Packages](https://docs.github.com/en/packages/learn-github-packages/introduction-to-github-packages), a
reference to this repository's GitHub Packages
[Maven repository](https://maven.apache.org/guides/introduction/introduction-to-repositories.html) needs to be added
inside the `repositories { ... }` block in the project's `build.gradle.kts` file:

```kotlin
    maven {
        url = uri("https://maven.pkg.github.com/tomuvak/optional-test")
        credentials { // See note below
            username = "<GitHub user name>"
            password = "<GitHub personal access token>"
        }
    }
```

and the dependency should be declared for the relevant source set(s) inside the relevant `dependencies { ... }` block(s)
inside the `sourceSet { ... }` block, e.g.

```kotlin
        val commonTest by getting {
            dependencies {
                implementation("com.tomuvak.optional-test:optional-test:0.0.1")
            }
        }
```

to add it for all platforms in a multi-platform project.

Note about credentials: it seems that even though this repository is public and everyone can download this library from
GitHub Packages, one still needs to supply credentials for some reason. Any GitHub user should work, when provided with
a [personal access
token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)
for the user with (at least) the `read:packages` scope.

**You might want to keep the credentials private**, for example in case the GitHub user has access to private packages
(as GitHub personal access tokens can be restricted in the type of operations they're used for, but not in the
repositories they can access), or all the more so in case the token has a wider scope (and note also that one can change
a token's scope after its creation, so it's possible that at some future point the user might inadvertently grant a
token which was meant to be restricted more rights).

See this library's own [build.gradle.kts](build.gradle.kts) (and specifically
[commit 0a7d166](https://github.com/tomuvak/optional-test/commit/0a7d166)) for an example of one way this could be done,
by means of storing private information in a local file which is not source-controlled; in this specific case the file
is named `local.properties` and the commit mentioned above was accompanied by its creation, with two lines of contents:

```properties
githubUser=<user name>
githubToken=<personal access token for the user above, with the `read:packages` scope>
```

(note that that commit is for including `com.tomuvak.optional`, which is the library this library both depends on – for
its _main_ source sets – and serves – for that library's _test_ source sets; it is **not** for including _this_ library,
which is `com.tomuvak.optional-test`, and so the repository, package and artefact names are slightly different, and the
example seen in the commit is for including the dependency for the `commonMain` source set, while this library is more
likely to be included as a dependency for the `commonTest` source set).
