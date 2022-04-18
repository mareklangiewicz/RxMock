package pl.mareklangiewicz.rxmock

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.CompletableSubject

class RxMockCompletable1<A>(var invocationCheck: (A) -> Boolean = { true })
    : CompletableObserver, (A) -> Completable {

    constructor(vararg allowedArgs: A) : this({ it in allowedArgs })

    val invocations = mutableListOf<A>()

    var subject: CompletableSubject? = null

    override fun invoke(arg: A): Completable {
        if (!invocationCheck(arg)) throw RxMockException("RxMockCompletable1 fail for arg: $arg")
        invocations += arg
        return CompletableSubject.create().also { subject = it }
    }

    override fun onComplete() = subject?.onComplete() ?: throw RxMockException()
    override fun onSubscribe(d: Disposable) = subject?.onSubscribe(d) ?: throw RxMockException()
    override fun onError(e: Throwable) = subject?.onError(e) ?: throw RxMockException()
}
