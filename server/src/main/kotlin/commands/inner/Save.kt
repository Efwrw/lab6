package commands.inner

import ServerContainer

class Save(val serverContainer: ServerContainer): InnerCommand {
    override val name = "save"

    override fun execute() {
        val collectionManager = serverContainer.collectionManager
        val storageManager = serverContainer.storageManager

        val collection = collectionManager.getCollection()

        storageManager.uploadCollection(ArrayDeque(collection), "")

    }
}