package application

import RpcRequest
import ServerContainer
import commands.*
import commands.Add

class CommandInvoker(val container: ServerContainer) {
    private val commands = mutableMapOf<String, Command>()

    private val add = Add(container)
    private val show = Show(container)
    private val countByType = CountByType(container)
    private val help = Help(container)
    private val info = Info(container)
    private val sumOfEmployeesCount = SumOfEmployeesCount(container)
    private val countLessThanOfficialAddress = CountLessThanOfficialAddress(container)
    private val removeLower = RemoveLower(container)
    private val removeGreater = RemoveGreater(container)
    private val removeByID = RemoveByID(container)
    private val update = Update(container)
    private val exit = Exit(container)

    fun registerCommand(command: Command) {
        commands[command.name] = command
    }

    init {
        registerCommand(show)
        registerCommand(add)
        registerCommand(countByType)
        registerCommand(help)
        registerCommand(info)
        registerCommand(sumOfEmployeesCount)
        registerCommand(countLessThanOfficialAddress)
        registerCommand(removeLower)
        registerCommand(removeGreater)
        registerCommand(removeByID)
        registerCommand(update)
        registerCommand(exit)
    }

    fun handleInput(req: RpcRequest): String {
        val commandName = req.name.trim()
        val command = commands[commandName]

        return command?.execute(req.args, req.data)
            ?: "Команда '$commandName' не найдена. Введите 'help', чтобы ознакомиться со списком доступных команд."
    }

    fun getCommands() = commands.values

}