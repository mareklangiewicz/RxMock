@file:Suppress("unused")

package pl.mareklangiewicz.rxmock

import io.reactivex.rxjava3.exceptions.UndeliverableException
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.junit.jupiter.api.Assertions.*

infix fun <T> TestObserver<T>.isNow(value: T) {
    assertEquals(value, values().last())
}

infix fun <T> TestObserver<T>.hasNow(predicate: T.() -> Boolean) {
    val values = values()
    assertTrue(values.last().predicate(), "TestObserver values: $values")
}

infix fun <T> TestObserver<T>.hadAny(predicate: T.() -> Boolean) {
    val values = values()
    assertTrue(values.any { it.predicate() }, "TestObserver values: $values")
}

infix fun <T> TestObserver<T>.wasAny(value: T) {
    val values = values()
    assertTrue(values.any { it == value }, "TestObserver values: $values")
}

@Suppress("UNCHECKED_CAST")
infix fun <T> TestObserver<*>.hasNowType(predicate: T.() -> Boolean) {
    val values = values()
    assertTrue((values.last() as T).predicate(), "TestObserver values: $values")
}


infix fun <T> Consumer<T>.put(value: T) = accept(value)

infix fun <T> Collection<T>.hasNo(t: T) = assertEquals(0, count { it == t })

infix fun <T> Collection<T>.hasOne(t: T) = assertEquals(1, count { it == t })

infix fun <T> Collection<T>.hasTwo(t: T) = assertEquals(2, count { it == t })

infix fun <T> Collection<T>.hasThree(t: T) = assertEquals(3, count { it == t })

infix fun <T> Collection<T>.hasAny(t: T) = assertTrue(contains(t), "Collection contains: $this")

fun setupRxJavaErrorHandler() {
    RxJavaPlugins.setErrorHandler { if (it !is UndeliverableException) throw it }
}

