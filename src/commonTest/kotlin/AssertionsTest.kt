package com.tomuvak.optional.test

import com.tomuvak.optional.Optional.None
import com.tomuvak.optional.Optional.Value
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AssertionsTest {
    @Test fun assertingNoneSucceedsOnNone() = assertNone(None)
    @Test fun assertingNoneFailsOnValue() = thenFails { assertNone(Value(Unit)) }
    @Test fun assertingNoneFailsOnValueOfNone() = thenFails { assertNone(Value(None)) }
    @Test fun assertingNoneFailsOnValueOfNull() = thenFails { assertNone(Value(null)) }

    @Test fun assertingValueFailsOnNone() = thenFails { assertValue(None) }
    @Test fun assertingValueSucceedsOnValue() = assertValue(Value(Unit))
    @Test fun assertingValueSucceedsOnValueOfNone() = assertValue(Value(None))
    @Test fun assertingValueSucceedsOnValueOfNull() = assertValue(Value(null))

    @Test fun assertingPredicateOnValueFailsOnNoneWithoutInvokingPredicate() {
        var numInvocations = 0
        thenFails { assertValue(None) {
            numInvocations++
            error("Should not be invoked")
        } }
        assertEquals(0, numInvocations)
    }
    @Test fun assertingPredicateOnValueFailsWhenValueDoesNotMeetPredicate() =
        thenFails { assertValue(Value(3)) { false } }
    @Test fun assertingPredicateOnValueSucceedsWhenValueMeetsPredicate() = assertValue(Value(3)) {
        assertEquals(3, it)
        true
    }

    @Test fun assertingExactValueFailsOnNone() = thenFails { assertValue(3, None) }
    @Test fun assertingExactValueFailsOnWrongValue() {
        thenFails { assertValue(3, Value(4)) }
        thenFails { assertValue(null, Value(None)) }
        thenFails { assertValue(None, Value(null)) }
    }
    @Test fun assertingExactValueSucceedsOnRightValue() {
        assertValue(3, Value(3))
        assertValue(None, Value(None))
        assertValue(null, Value(null))
    }

    private fun thenFails(block: () -> Unit) { assertFailsWith<AssertionError> { block() } }
}
