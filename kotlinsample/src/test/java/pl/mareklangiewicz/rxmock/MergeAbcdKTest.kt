package pl.mareklangiewicz.rxmock

import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith
import pl.mareklangiewicz.abcdk.AB
import pl.mareklangiewicz.uspek.USpekRunner
import pl.mareklangiewicz.uspek.eq
import pl.mareklangiewicz.uspek.o
import pl.mareklangiewicz.uspek.uspek

@RunWith(USpekRunner::class)
class MergeAbcdKTest {

    @Test
    fun mergeAbcdKTest() {

        uspek {

            val rxMockA = RxMockObservable0<String>()
            val rxMockB = RxMockObservable0<Int>()
            val rxMockC = RxMockObservable0<Float>()

            val aS = rxMockA()
            val bS = rxMockB()
            val cS = rxMockC()

            "On mergeAB" o {
                val resultS = mergeAB(aS, bS)
                val resultT = resultS.test()

                "no results yet" o { resultT.assertEmpty() }

                onDisposeTests(rxMockA, rxMockB, resultT)

                "On aS error" o {
                    val error = RuntimeException("aS error")
                    rxMockA.onError(error)

                    "got aS error result" o { resultT.assertError(error) }
                    "unsubscribe from bS" o { rxMockB.subject!!.hasObservers() eq false }
                }

                "On bS error" o {
                    val error = RuntimeException("bS error")
                    rxMockB.onError(error)

                    "got bS error result" o { resultT.assertError(error) }
                    "unsubscribe from aS" o { rxMockA.subject!!.hasObservers() eq false }
                }

                "On first xxx string" o {
                    rxMockA put "xxx"

                    "got AB.A(xxx) result" o { resultT isNow AB.A("xxx") }

                    "On number 7" o {
                        rxMockB put 7

                        "got AB.B(7) result" o { resultT isNow AB.B(7) }

                        onDisposeTests(rxMockA, rxMockB, resultT)
                    }

                }

                "On first 666 Int" o {
                    rxMockB put 666

                    "got AB.B(666) result" o { resultT isNow AB.B(666) }

                    "On bla string" o {
                        rxMockA put "bla"

                        "got AB.A(bla)" o { resultT isNow AB.A("bla") }

                        "On another string" o {
                            rxMockA put "another"

                            "got AB.A(another)" o { resultT isNow AB.A("another") }

                            onDisposeTests(rxMockA, rxMockB, resultT)
                        }
                    }
                }
            }
        }
    }
}

private fun onDisposeTests(rxMockA: RxMockObservable0<String>, rxMockB: RxMockObservable0<Int>, resultT: TestObserver<AB<String, Int>>) {
    "On before dispose" o {
        "do not unsubscribe from aS yet" o { rxMockA.subject!!.hasObservers() eq true }
        "do not unsubscribe from bS yet" o { rxMockB.subject!!.hasObservers() eq true }

        "On dispose" o {
            resultT.dispose()

            "unsubscribe from aS" o { rxMockA.subject!!.hasObservers() eq false }
            "unsubscribe from bS" o { rxMockB.subject!!.hasObservers() eq false }
        }
    }
}
