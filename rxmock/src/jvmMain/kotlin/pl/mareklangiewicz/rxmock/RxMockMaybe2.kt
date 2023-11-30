package pl.mareklangiewicz.rxmock

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.MaybeSubject

class RxMockMaybe2<A1, A2, T: Any>(var invocationCheck: (A1, A2) -> Boolean = { _, _ -> true })
    : MaybeObserver<T>, Consumer<T>, (A1, A2) -> Maybe<T> {

    constructor(vararg allowedArgs: Pair<A1, A2>) : this({ a1, a2 -> a1 to a2 in allowedArgs })

    val invocations = mutableListOf<Pair<A1, A2>>()

    var subject: MaybeSubject<T>? = null

    override fun invoke(arg1: A1, arg2: A2): Maybe<T> {
        if (!invocationCheck(arg1, arg2)) throw RxMockException("RxMockMaybe2 fail for args: $arg1, $arg2")
        invocations += arg1 to arg2
        return MaybeSubject.create<T>().also { subject = it }
    }

    override fun accept(t: T) = onSuccess(t)
    override fun onSuccess(t: T) = subject?.onSuccess(t) ?: throw RxMockException()
    override fun onComplete() = subject?.onComplete() ?: throw RxMockException()
    override fun onSubscribe(d: Disposable) = subject?.onSubscribe(d) ?: throw RxMockException()
    override fun onError(e: Throwable) = subject?.onError(e) ?: throw RxMockException()
}
