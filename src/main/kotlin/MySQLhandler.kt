

class MySQLhandler {
    private val database = DatabaseManager()

    fun addUser(newUserDraft : UserDraft) : Int{
        return database.addUser(newUserDraft)
    }

    fun addGroup(groupName : String) : Int {
        return database.addGroup(groupName)
    }

    fun addTransaction(transaction: Transaction) : Int {
        return database.addTransaction(transaction)
    }
}