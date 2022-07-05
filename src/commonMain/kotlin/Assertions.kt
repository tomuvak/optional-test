package com.tomuvak.optional.test

import com.tomuvak.optional.Optional
import com.tomuvak.optional.Optional.None
import com.tomuvak.optional.Optional.Value
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * Asserts that the given [optional] is [None].
 */
fun <T> assertNone(optional: Optional<T>) = assertEquals(None, optional)

/**
 * Asserts that the given [optional] is a [Value] and returns its underlying value.
 */
fun <T> assertValue(optional: Optional<T>): T = assertIs<Value<T>>(optional).value

/**
 * Asserts that the given [optional] is a [Value] and that its underlying value satisfies the given [predicate].
 */
fun <T> assertValue(optional: Optional<T>, predicate: (T) -> Boolean) = when(optional) {
    None -> fail("Expected Value satisfying predicate; got None")
    is Value -> assertTrue(
        predicate(optional.value),
        "Expected Value satisfying predicate; got Value not satisfying predicate: ${optional.value}"
    )
}

/**
 * Asserts that the given [optional] is a [Value] and that its underlying value is equal to the [expected] value.
 */
fun <T> assertValue(expected: T, optional: Optional<T>) = when (optional) {
    None -> fail("Expected Value of $expected; got None")
    is Value -> assertEquals(expected, optional.value)
}
