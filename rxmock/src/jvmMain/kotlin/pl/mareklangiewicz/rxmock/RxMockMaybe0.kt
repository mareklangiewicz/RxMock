package pl.mareklangiewicz.rxmock

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.MaybeSubject

class RxMockMaybe0<T>(var invocationCheck: () -> Boolean = { true })
    : MaybeObserver<T>, Consumer<T>, () -> Maybe<T> {

    var invocations = 0

    var subject: MaybeSubject<T>? = null

    override fun invoke(): Maybe<T> {
        if (!invocationCheck()) throw RxMockException("RxMockMaybe0 fail")
        invocations ++
        return MaybeSubject.create<T>().also { subject = it }
    }

    override fun accept(t: T) = onSuccess(t)
    override fun onSuccess(t: T) = subject?.onSuccess(t) ?: throw RxMockException()
    override fun onComplete() = subject?.onComplete() ?: throw RxMockException()
    override fun onSubscribe(d: Disposable) = subject?.onSubscribe(d) ?: throw RxMockException()
    override fun onError(e: Throwable) = subject?.onError(e) ?: throw RxMockException()
}
