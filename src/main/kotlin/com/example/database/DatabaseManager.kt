
import com.example.database.AuditTable
import com.example.database.GroupTable
import com.example.database.GroupUserPairTable
import com.example.database.UserTable
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.support.mysql.insertOrUpdate



class DatabaseManager{
    private val hostname = "127.0.0.1"
    private val databaseName = "Expense_Sharing_App"
    private val username = ""
    private val password = ""

    private val ktormDatabase : Database

    init{
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false"
        ktormDatabase = Database.connect (jdbcUrl)
    }

    fun addUser(draft : UserDraft): Int{

//        val groupNameQuery = ktormDatabase.from(GroupTable).select().where {GroupTable.groupName like draft.groupName}
//        if(groupNameQuery.totalRecords == 0) return -1
//
//        val userNameQuery = ktormDatabase.from(UserTable).select().where {UserTable.name like draft.name}
//        if(userNameQuery.totalRecords !=0) return -2
//
//        val insertedId = ktormDatabase.insertAndGenerateKey(UserTable){
//            set(UserTable.name , draft.name)
//            set(UserTable.email , draft.email)
//            set(UserTable.mobile , draft.mobile)
//        } as Int
//
//        var grpId : Int? = -1    // groupId will always be modified in the for loop below
//        for(row in groupNameQuery) grpId = row[GroupTable.groupId]
//
//        ktormDatabase.insert(GroupUserPairTable){
//            set(GroupUserPairTable.userId , insertedId)
//            set(GroupUserPairTable.groupId , grpId)
//            set(GroupUserPairTable.userName , draft.name)
//        }
//
//        return insertedId

        // group in which user wants to be a part should be already present in the group table
        val validGroup = ktormDatabase.sequenceOf(GroupTable).firstOrNull { it.groupName eq draft.groupName }
        if(validGroup == null) return -1

        // all users should have unique name
        val userWithSameName = ktormDatabase.sequenceOf(UserTable).firstOrNull{it.name eq draft.name}
        if(userWithSameName != null) return -2

        // primary key generated after inserting the new user in the UserTable
        val insertedId = ktormDatabase.insertAndGenerateKey(UserTable){
            set(UserTable.name , draft.name)
            set(UserTable.email , draft.email)
            set(UserTable.mobile , draft.mobile)
        } as Int

        // we need to update the group - user mapping in the GroupUserPairTable table as well
        ktormDatabase.insert(GroupUserPairTable){
            set(GroupUserPairTable.groupId , validGroup.groupId)
            set(GroupUserPairTable.userId , insertedId)
            set(GroupUserPairTable.groupName , draft.groupName)
            set(GroupUserPairTable.userName , draft.name)
        }

        return insertedId

    }

    fun addGroup(groupName : String) : Int {
//        val groupNameQuery = ktormDatabase.from(GroupTable).select().where { GroupTable.groupName eq groupName }
//        val len: Int = groupNameQuery.totalRecords
//
//        val insertedKey = ktormDatabase.insertAndGenerateKey(GroupTable){
//            set(GroupTable.groupName , groupName)
//        } as Int
//
//        return insertedKey

        // group Names are unique
        println(GroupTable)
        val sameGroupName = ktormDatabase.sequenceOf(GroupTable).firstOrNull(){ it.groupName eq groupName}
        if(sameGroupName != null) return -1

        val insertedKey = ktormDatabase.insertAndGenerateKey(GroupTable){
            set(GroupTable.groupName , groupName)
        } as Int

        return insertedKey
    }

    fun addTransaction(transaction: Transaction) : Int{
        val groupName = transaction.groupName
        val groupNameQuery = ktormDatabase.from(GroupTable).select().where {
            GroupTable.groupName eq groupName
        }

        if(groupNameQuery.totalRecords == 0) return -1 // no such group exists
        if(transaction.amount == 0) return 0  // no need to process something

        val totalMembers = groupNameQuery.totalRecords
        val amountPerHead : Int = transaction.amount / totalMembers
        val payingMember = transaction.memberName

        val amountSplitMembers = ktormDatabase.from(GroupUserPairTable).select().where { GroupUserPairTable.groupName eq groupName }
        for(row in amountSplitMembers){
            // add transactions in audit table
            val userName:String ? = row[GroupUserPairTable.userName]
            if(userName  == payingMember) continue
            ktormDatabase.insertOrUpdate(AuditTable) {
                set(AuditTable.userName1, userName)
                set(AuditTable.userName2, payingMember)
                set(AuditTable.amount, amountPerHead)
                onDuplicateKey {
                    set(AuditTable.amount, AuditTable.amount + amountPerHead)
                }
            }
        }

        return 0
    }
}

