package com.tomuvak.optional.test

import com.tomuvak.optional.Optional.None
import com.tomuvak.optional.Optional.Value
import com.tomuvak.testing.assertFailsWithTypeAndMessageContaining
import kotlin.test.*

class AssertionsTest {
    @Test fun assertingNoneSucceedsOnNone() = assertNone(None)
    @Test fun assertingNoneFailsOnValue() = thenFails { assertNone(Value(Unit)) }
    @Test fun assertingNoneFailsOnValueOfNone() = thenFails { assertNone(Value(None)) }
    @Test fun assertingNoneFailsOnValueOfNull() = thenFails { assertNone(Value(null)) }

    @Test fun assertingValueFailsOnNone() = thenFails { assertValue(None) }
    @Test fun assertingValueSucceedsOnValue() = testValue(3)
    @Test fun assertingValueSucceedsOnValueOfNone() = testValue(None)
    @Test fun assertingValueSucceedsOnValueOfNull() = testValue(null)
    private fun <T> testValue(value: T) = assertEquals(value, assertValue(Value(value)))

    @Test fun assertingPredicateOnValueFailsOnNoneWithoutInvokingPredicate() {
        var numInvocations = 0
        thenFailsWith("got None") { assertValue(None) {
            numInvocations++
            error("Should not be invoked")
        } }
        assertEquals(0, numInvocations)
    }
    @Test fun assertingPredicateOnValueFailsWhenValueDoesNotMeetPredicate() =
        thenFailsWith("345") { assertValue(Value(345)) { false } }
    @Test fun assertingPredicateOnValueSucceedsWhenValueMeetsPredicate() = assertValue(Value(3)) {
        assertEquals(3, it)
        true
    }

    @Test fun assertingExactValueFailsOnNone() = thenFailsWith(3, "got None") { assertValue(3, None) }
    @Test fun assertingExactValueFailsOnWrongValue() {
        thenFailsWith(3, 4) { assertValue(3, Value(4)) }
        thenFailsWith(null, None) { assertValue(null, Value(None)) }
        thenFailsWith(None, null) { assertValue(None, Value(null)) }
    }
    @Test fun assertingExactValueSucceedsOnRightValue() {
        assertValue(3, Value(3))
        assertValue(None, Value(None))
        assertValue(null, Value(null))
    }

    private fun thenFails(block: () -> Unit) { assertFailsWith<AssertionError>(block=block) }
    private fun thenFailsWith(vararg messageParts: Any?, block: () -> Unit) =
        assertFailsWithTypeAndMessageContaining<AssertionError>(*messageParts, block=block)
}
