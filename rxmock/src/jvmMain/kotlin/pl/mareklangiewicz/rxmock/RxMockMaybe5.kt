package pl.mareklangiewicz.rxmock

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.MaybeSubject
import pl.mareklangiewicz.tuplek.Jackson

class RxMockMaybe5<A1, A2, A3, A4, A5, T: Any>(var invocationCheck: (A1, A2, A3, A4, A5) -> Boolean = { _, _, _, _, _ -> true }) : MaybeObserver<T>, Consumer<T>, (A1, A2, A3, A4, A5) -> Maybe<T> {

    constructor(vararg allowedArgs: Jackson<A1, A2, A3, A4, A5>) : this({ a1, a2, a3, a4, a5 -> Jackson(a1, a2, a3, a4, a5) in allowedArgs })

    val invocations = mutableListOf<Jackson<A1, A2, A3, A4, A5>>()

    var subject: MaybeSubject<T>? = null

    override fun invoke(arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5): Maybe<T> {
        if (!invocationCheck(arg1, arg2, arg3, arg4, arg5)) throw RxMockException("RxMockMaybe5 fail for args: $arg1, $arg2, $arg3, $arg4, $arg5")
        invocations += Jackson(arg1, arg2, arg3, arg4, arg5)
        return MaybeSubject.create<T>().also { subject = it }
    }

    override fun accept(t: T) = onSuccess(t)
    override fun onSuccess(t: T) = subject?.onSuccess(t) ?: throw RxMockException()
    override fun onComplete() = subject?.onComplete() ?: throw RxMockException()
    override fun onSubscribe(d: Disposable) = subject?.onSubscribe(d) ?: throw RxMockException()
    override fun onError(e: Throwable) = subject?.onError(e) ?: throw RxMockException()
}
