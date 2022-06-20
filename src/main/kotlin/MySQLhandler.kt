

class MySQLhandler {
    private val database = DatabaseManager()

    fun addUser(newUserDraft : UserDraft) : User{
        val id = database.addUser(newUserDraft)
        return User(id , newUserDraft.name , newUserDraft.email , newUserDraft.mobile)
    }

    fun addGroup() {

    }

    fun addTransaction() {

    }
}