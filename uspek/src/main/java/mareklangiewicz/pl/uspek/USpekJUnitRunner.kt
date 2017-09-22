package mareklangiewicz.pl.uspek

import org.junit.runner.Description
import org.junit.runner.Runner
import org.junit.runner.notification.RunNotifier
import java.util.*

class USpekJUnitRunner(testClass: Class<Any>) : Runner() {

    private val rootDescription = Description.createSuiteDescription(testClass.simpleName, UUID.randomUUID().toString())

    private val treeCollectionLogger = TreeCollectionLogger()

    init {
        USpek.log = treeCollectionLogger
        val instance = testClass.newInstance()
        testClass.declaredMethods.forEach { it.invoke(instance) }
        rootDescription.addChild(createDescriptions(treeCollectionLogger.testTree!!, testClass.name))
    }

    override fun getDescription(): Description {
        return rootDescription
    }

    override fun run(notifier: RunNotifier) {
        println("USpek is running....")
    }

    private fun createDescriptions(testBranch: TestTree, testSuite: String): Description {
        val description = if (testBranch.subtests.isNotEmpty()) {
            Description.createSuiteDescription(testBranch.name, UUID.randomUUID().toString())
        } else {
            Description.createTestDescription(testSuite, testBranch.name)
        }
        testBranch.subtests.forEach {
            val child = createDescriptions(it, testSuite + "." + testBranch.name)
            description.addChild(child)
        }
        return description
    }

}