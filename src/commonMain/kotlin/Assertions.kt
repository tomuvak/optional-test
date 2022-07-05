package com.tomuvak.optional.test

import com.tomuvak.optional.Optional
import com.tomuvak.optional.Optional.None
import com.tomuvak.optional.Optional.Value
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

fun <T> assertNone(optional: Optional<T>) = assertEquals(None, optional)

fun <T> assertValue(optional: Optional<T>) = assertTrue(optional is Value)
fun <T> assertValue(optional: Optional<T>, predicate: (T) -> Boolean) = when(optional) {
    None -> fail("Expected Value satisfying predicate; got None")
    is Value -> assertTrue(predicate(optional.value))
}
fun <T> assertValue(expected: T, optional: Optional<T>) = when (optional) {
    None -> fail("Expected Value of $expected; got None")
    is Value -> assertEquals(expected, optional.value)
}
