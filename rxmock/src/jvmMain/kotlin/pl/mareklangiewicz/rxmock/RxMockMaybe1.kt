package pl.mareklangiewicz.rxmock

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.MaybeSubject

class RxMockMaybe1<A, T>(var invocationCheck: (A) -> Boolean = { true })
    : MaybeObserver<T>, Consumer<T>, (A) -> Maybe<T> {

    constructor(vararg allowedArgs: A) : this({ it in allowedArgs })

    val invocations = mutableListOf<A>()

    var subject: MaybeSubject<T>? = null

    override fun invoke(arg: A): Maybe<T> {
        if (!invocationCheck(arg)) throw RxMockException("RxMockMaybe1 fail for arg: $arg")
        invocations += arg
        return MaybeSubject.create<T>().also { subject = it }
    }

    override fun accept(t: T) = onSuccess(t)
    override fun onSuccess(t: T) = subject?.onSuccess(t) ?: throw RxMockException()
    override fun onComplete() = subject?.onComplete() ?: throw RxMockException()
    override fun onSubscribe(d: Disposable) = subject?.onSubscribe(d) ?: throw RxMockException()
    override fun onError(e: Throwable) = subject?.onError(e) ?: throw RxMockException()
}
