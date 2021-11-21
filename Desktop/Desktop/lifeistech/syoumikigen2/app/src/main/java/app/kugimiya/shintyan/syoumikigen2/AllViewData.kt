package app.kugimiya.shintyan.syoumikigen2

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class AllViewData(
    @PrimaryKey open var id: String = UUID.randomUUID().toString(),
    //open var imageId:Int = 0,
    open var name: String = "",
    open var kigen: Int = 0,
    open var place:String = ""
    //open var createdAt: Date = Date(System.currentTimeMillis())
) : RealmObject()