package pl.mareklangiewicz.rxmock

import io.reactivex.rxjava3.core.Observable.*
import pl.mareklangiewicz.abcdk.*
import io.reactivex.rxjava3.core.Observable as O

fun <A : Any, B : Any> mergeAB(aS: O<A>, bS: O<B>): O<AB<A, B>> =
    merge(aS.map { AB.A(it) }, bS.map { AB.B(it) })

fun <A : Any, B : Any, C : Any> mergeABC(aS: O<A>, bS: O<B>, cS: O<C>): O<ABC<A, B, C>> =
    merge(aS.map { ABC.A(it) }, bS.map { ABC.B(it) }, cS.map { ABC.C(it) })

fun <A : Any, B : Any, C : Any, D : Any> mergeABCD(aS: O<A>, bS: O<B>, cS: O<C>, dS: O<D>): O<ABCD<A, B, C, D>> =
    merge(aS.map { ABCD.A(it) }, bS.map { ABCD.B(it) }, cS.map { ABCD.C(it) }, dS.map { ABCD.D(it) })

