
import com.example.database.UserTable
import org.ktorm.database.Database
import org.ktorm.dsl.insertAndGenerateKey

class DatabaseManager{
    private val hostname = "127.0.0.1"
    private val databaseName = "Users"
    private val username = "root"
    private val password = ""

    private val ktormDatabase : Database

    init{
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false"
        ktormDatabase = Database.connect (jdbcUrl)
    }

    fun addUser(draft : UserDraft): Int{
        val insertedId = ktormDatabase.insertAndGenerateKey(UserTable){
            set(UserTable.name , draft.name)
            set(UserTable.email , draft.email)
            set(UserTable.mobile , draft.mobile)
        } as Int
        return insertedId
    }


}

