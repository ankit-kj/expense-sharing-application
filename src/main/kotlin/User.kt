data class User(
    val userId : Int ,
    val name : String ,
    val email : String ,
    val mobile : String

    )

class UserDraft(
    val name : String ,
    val email : String ,
    val mobile : String,
    val groupName : String
    )

class Transaction(
    val amount : Int,
    val groupName : String,
    val memberName : String
)

class groupDraft(
    val groupName : String
    )