package pl.mareklangiewicz.rxmock

import io.reactivex.Observable as O
import io.reactivex.Observable.merge
import pl.mareklangiewicz.abcdk.AB
import pl.mareklangiewicz.abcdk.ABC
import pl.mareklangiewicz.abcdk.ABCD


fun <A, B> mergeAB(aS: O<A>, bS: O<B>): O<AB<A, B>> =
    merge(aS.map { AB.A(it) }, bS.map { AB.B(it) })

fun <A, B, C> mergeABC(aS: O<A>, bS: O<B>, cS: O<C>): O<ABC<A, B, C>> =
    merge(aS.map { ABC.A(it) }, bS.map { ABC.B(it) }, cS.map { ABC.C(it) })

fun <A, B, C, D> mergeABCD(aS: O<A>, bS: O<B>, cS: O<C>, dS: O<D>): O<ABCD<A, B, C, D>> =
    merge(aS.map { ABCD.A(it) }, bS.map { ABCD.B(it) }, cS.map { ABCD.C(it) }, dS.map { ABCD.D(it) })

