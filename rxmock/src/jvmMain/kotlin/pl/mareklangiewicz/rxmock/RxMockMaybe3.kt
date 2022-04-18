package pl.mareklangiewicz.rxmock

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.MaybeSubject

class RxMockMaybe3<A1, A2, A3, T>(var invocationCheck: (A1, A2, A3) -> Boolean = { _, _, _ -> true })
    : MaybeObserver<T>, Consumer<T>, (A1, A2, A3) -> Maybe<T> {

    constructor(vararg allowedArgs: Triple<A1, A2, A3>) : this({ a1, a2, a3 -> Triple(a1, a2, a3) in allowedArgs })

    val invocations = mutableListOf<Triple<A1, A2, A3>>()

    var subject: MaybeSubject<T>? = null

    override fun invoke(arg1: A1, arg2: A2, arg3: A3): Maybe<T> {
        if (!invocationCheck(arg1, arg2, arg3)) throw RxMockException("RxMockMaybe3 fail for args: $arg1, $arg2, $arg3")
        invocations += Triple(arg1, arg2, arg3)
        return MaybeSubject.create<T>().also { subject = it }
    }

    override fun accept(t: T) = onSuccess(t)
    override fun onSuccess(t: T) = subject?.onSuccess(t) ?: throw RxMockException()
    override fun onComplete() = subject?.onComplete() ?: throw RxMockException()
    override fun onSubscribe(d: Disposable) = subject?.onSubscribe(d) ?: throw RxMockException()
    override fun onError(e: Throwable) = subject?.onError(e) ?: throw RxMockException()
}
