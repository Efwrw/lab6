package application.commands

import application.Handler
import application.ScriptExecutor
import application.exceptions.ExitSignal

class Exit (
    override val app: Handler
): Command{
    override val name = "exit"
    override val description = "Завершает программу (без записи файла в коллекцию)"

    override fun execute(argument: String) {
        val message = if (app is ScriptExecutor) {
            "Сигнал выхода вызван из скрипта ${app.pathName}"
        } else "Завершение программы, удаление коллекции... Вы же не забыли её сохранить?... O_o"
        throw ExitSignal(message)
    }
}