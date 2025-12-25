import java.nio.file.Files
import java.nio.file.Path

class WebChecker() {

    fun runCheck() {
        val checkers : List<Checker> =  buildCheckers()
        val configurationParameters: ConfigurationParameters = ConfigurationParameters.getConfiguration()
        val sleepTimeMilliseconds = (configurationParameters.sleep * 1000 * 60).toLong()
        val iterations = 1
        printBanner()
        while(true) {
            println("--- Iteration nº$iterations ---")
            for (checker in checkers) {
                checker.checkStatus()
            }
            println("---------------------\n")
            Thread.sleep(sleepTimeMilliseconds)
        }
    }

    private fun buildCheckers() : List<Checker> {
        val configurationParameters: ConfigurationParameters = ConfigurationParameters.getConfiguration()
        return configurationParameters.urls.map { Checker(it) }
    }

    private fun printBanner() {
        Files.readAllLines(Path.of("banner.txt")).forEach { println(it) }
    }


}