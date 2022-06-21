

class MySQLhandler {
    private val database = DatabaseManager()

    fun addUser(newUserDraft : UserDraft) : User?{
        val id = database.addUser(newUserDraft)
        if(id == -1) return null
        return User(id , newUserDraft.name , newUserDraft.email , newUserDraft.mobile)
    }

    fun addGroup(groupName : String) : Int {
        val id = database.addGroup(groupName)
        return id
    }

    fun addTransaction() {

    }
}