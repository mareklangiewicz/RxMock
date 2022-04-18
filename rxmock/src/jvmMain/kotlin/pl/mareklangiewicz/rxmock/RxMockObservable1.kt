package pl.mareklangiewicz.rxmock

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

class RxMockObservable1<A, T>(var invocationCheck: (A) -> Boolean = { true })
    : Observer<T>, Consumer<T>, (A) -> Observable<T> {

    constructor(vararg allowedArgs: A) : this({ it in allowedArgs })

    val invocations = mutableListOf<A>()

    var subject: Subject<T>? = null

    override fun invoke(arg: A): Observable<T> {
        if (!invocationCheck(arg)) throw RxMockException("RxMockObservable1 fail for arg: $arg")
        invocations += arg
        return PublishSubject.create<T>().also { subject = it }
    }

    override fun accept(t: T) = onNext(t)
    override fun onNext(t: T) = subject?.onNext(t) ?: throw RxMockException()
    override fun onComplete() = subject?.onComplete() ?: throw RxMockException()
    override fun onSubscribe(d: Disposable) = subject?.onSubscribe(d) ?: throw RxMockException()
    override fun onError(e: Throwable) = subject?.onError(e) ?: throw RxMockException()
}
