package util

import java.io.File

class EnvParser {
    companion object {
        fun getEnvFromFile(path: String): Map<String, String> {
            val envFile = File(path)
            println(envFile.absolutePath)

            if (!envFile.exists()) throw Error("env file should be specified")

            val map = envFile
                .readLines()
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .map { it.split("=") }
                .filter { it.size == 2 }
                .associate { (k, v) -> k to v }

            return map
        }
    }
}