package commands.inner

import ServerContainer

class Save(): InnerCommand {
    override val name = "save"
    override val description = "Сохраняет текущую коллекцию"
    override fun execute(context: ServerContainer) {
        val collectionManager = context.collectionManager
        val storageManager = context.storageManager

        val collection = collectionManager.getCollection()

        storageManager.uploadCollection(ArrayDeque(collection), "")

    }
}