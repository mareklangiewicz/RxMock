package pl.mareklangiewicz.rxmock

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.SingleSubject

class RxMockSingle1<A, T>(var invocationCheck: (A) -> Boolean = { true })
    : SingleObserver<T>, Consumer<T>, (A) -> Single<T> {

    constructor(vararg allowedArgs: A) : this({ it in allowedArgs })

    val invocations = mutableListOf<A>()

    var subject: SingleSubject<T>? = null

    override fun invoke(arg: A): Single<T> {
        if (!invocationCheck(arg)) throw RxMockException("RxMockSingle1 fail for arg: $arg")
        invocations += arg
        return SingleSubject.create<T>().also { subject = it }
    }

    override fun accept(t: T) = onSuccess(t)
    override fun onSuccess(t: T) = subject?.onSuccess(t) ?: throw RxMockException()
    override fun onSubscribe(d: Disposable) = subject?.onSubscribe(d) ?: throw RxMockException()
    override fun onError(e: Throwable) = subject?.onError(e) ?: throw RxMockException()
}
