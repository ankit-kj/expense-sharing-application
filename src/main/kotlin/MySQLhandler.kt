

class MySQLhandler {
    private val database = DatabaseManager()

    fun addUser(newUserDraft : UserDraft) : Int{
        return database.addUser(newUserDraft)
//        if(id == -1 or id) return null
//        return User(id , newUserDraft.name , newUserDraft.email , newUserDraft.mobile)
    }

    fun addGroup(groupName : String) : Int {
        return database.addGroup(groupName)
    }

    fun addTransaction(transaction: Transaction) : Int {
        return database.addTransaction(transaction)
    }
}