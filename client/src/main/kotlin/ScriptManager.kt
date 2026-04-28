import java.io.File

class ScriptManager {
    val buffer: ArrayDeque<MutableList<String>> = ArrayDeque()
    var isRunning = false
    var set: MutableSet<String> = emptySet<String>().toMutableSet()

    fun add(path: String) {
        val file = File(path)
        if (set.contains(path)) {
            panic()
            throw ScriptError("рекурсия")
        }
        set.add(path)
        buffer.addLast(
            file
                .readLines()
                .reversed()
                .toMutableList()
        )
    }

    fun getLine(): String {
        val lastScript = buffer.last()
        if (lastScript.size == 1) {
            buffer.removeLast()
        }

        return lastScript.removeLast()
    }

    fun panic() {
        buffer.clear()
        set.clear()
        isRunning = false
    }
}