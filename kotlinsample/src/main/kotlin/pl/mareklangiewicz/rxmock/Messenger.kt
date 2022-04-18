package pl.mareklangiewicz.rxmock

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.schedulers.*
import java.util.concurrent.*

typealias Message = String
typealias Seconds = Long

fun messenger(
    sendMessageEventS: Observable<Message>,
    apiSendMessage: (Message) -> Completable,
    apiReceiveNewMessages: () -> Maybe<List<Message>>,
    refreshScheduler: Scheduler = Schedulers.computation(),
    refreshEvery: Seconds = 5
): Observable<Message> {

    val sendMessageResultS = sendMessageEventS
        .switchMapSingle { messageToSend ->
            apiSendMessage(messageToSend)
                .toSingleDefault("Sent: $messageToSend")
                .onErrorReturnItem("Error sending: $messageToSend")
        }

    val receivedMessageResultS = Observable.interval(refreshEvery, TimeUnit.SECONDS, refreshScheduler)
        .switchMap { _ ->
            apiReceiveNewMessages()
                .defaultIfEmpty(emptyList())
                .flatMapObservable { messages ->
                    Observable.fromIterable(messages.map { message -> "Received: $message" })
                }
                .onErrorReturnItem("Error receiving new messages")
        }

    return Observable.merge(sendMessageResultS, receivedMessageResultS)
}

