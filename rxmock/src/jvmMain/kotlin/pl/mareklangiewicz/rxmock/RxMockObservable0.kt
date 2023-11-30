package pl.mareklangiewicz.rxmock

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

class RxMockObservable0<T: Any>(var invocationCheck: () -> Boolean = { true })
    : Observer<T>, Consumer<T>, () -> Observable<T> {

    var invocations = 0

    var subject: Subject<T>? = null

    override fun invoke(): Observable<T> {
        if (!invocationCheck()) throw RxMockException("RxMockObservable0 fail")
        invocations ++
        return PublishSubject.create<T>().also { subject = it }
    }

    override fun accept(t: T) = onNext(t)
    override fun onNext(t: T) = subject?.onNext(t) ?: throw RxMockException()
    override fun onComplete() = subject?.onComplete() ?: throw RxMockException()
    override fun onSubscribe(d: Disposable) = subject?.onSubscribe(d) ?: throw RxMockException()
    override fun onError(e: Throwable) = subject?.onError(e) ?: throw RxMockException()
}
