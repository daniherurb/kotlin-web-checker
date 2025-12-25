import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.nio.file.Files
import java.nio.file.Path

class ConfigurationParameters(val urls: List<String>, val sleep : Double, val sender : String,
                              val key : String, val receiver : String) {

    companion object {
        private val config by lazy { loadConfiguration() }

        fun getConfiguration(): ConfigurationParameters = config

        private fun loadConfiguration(): ConfigurationParameters {
            val gson : Gson = GsonBuilder().create()
            val path = Path.of("config.json")
            val jsonContent: String = Files.readString(path)
            try {
                val configurationParameters : ConfigurationParameters = gson.fromJson(jsonContent, ConfigurationParameters::class.java)
                return configurationParameters
            }
            catch (e: Exception) {
                throw Exception("Failed to load configuration: ${e.message}", e)
            }
        }
    }


}
