package pl.mareklangiewicz.rxmock

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.CompletableSubject

class RxMockCompletable0(var invocationCheck: () -> Boolean = { true })
    : CompletableObserver, () -> Completable {

    var invocations = 0

    var subject: CompletableSubject? = null

    override fun invoke(): Completable {
        if (!invocationCheck()) throw RxMockException("RxMockCompletable0 fail")
        invocations ++
        return CompletableSubject.create().also { subject = it }
    }

    override fun onComplete() = subject?.onComplete() ?: throw RxMockException()
    override fun onSubscribe(d: Disposable) = subject?.onSubscribe(d) ?: throw RxMockException()
    override fun onError(e: Throwable) = subject?.onError(e) ?: throw RxMockException()
}
