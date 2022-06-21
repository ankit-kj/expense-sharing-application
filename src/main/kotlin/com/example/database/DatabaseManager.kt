
import com.example.database.GroupTable
import com.example.database.GroupUserPairTable
import com.example.database.UserTable
import org.ktorm.database.Database
import org.ktorm.dsl.*
import javax.swing.GroupLayout.Group

class DatabaseManager{
    private val hostname = "127.0.0.1"
    private val databaseName = "Expense_Sharing_App"
    private val username = "root"
    private val password = "ankit@porter"

    private val ktormDatabase : Database

    init{
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false"
        ktormDatabase = Database.connect (jdbcUrl)
    }

    fun addUser(draft : UserDraft): Int{

        val groupNameQuery = ktormDatabase.from(GroupTable).select().where {GroupTable.groupName like draft.groupName}
        if(groupNameQuery.totalRecords == 0) return -1

        val insertedId = ktormDatabase.insertAndGenerateKey(UserTable){
            set(UserTable.name , draft.name)
            set(UserTable.email , draft.email)
            set(UserTable.mobile , draft.mobile)
        } as Int

        var grpId : Int? = -1    // groupId will always be modified in the for loop below
        for(row in groupNameQuery) grpId = row[GroupTable.groupId]

        ktormDatabase.insertAndGenerateKey(GroupUserPairTable){
            set(GroupUserPairTable.userId , insertedId)
            set(GroupUserPairTable.groupId , grpId)
        }

        return insertedId
    }

    fun addGroup(groupName : String) : Int {
        val groupNameQuery = ktormDatabase.from(GroupTable).select().where {GroupTable.groupName like groupName}
        if(groupNameQuery.totalRecords != 0) return -1

        val insertedKey = ktormDatabase.insertAndGenerateKey(GroupTable){
            set(GroupTable.groupName , groupName)
        } as Int

        return insertedKey
    }


}

