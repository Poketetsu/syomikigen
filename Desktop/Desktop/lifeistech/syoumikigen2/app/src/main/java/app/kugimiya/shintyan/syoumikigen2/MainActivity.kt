package app.kugimiya.shintyan.syoumikigen2

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.*
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_all_data_cell.*
import java.nio.file.Files.delete


class MainActivity : AppCompatActivity() {
    val realm: Realm = Realm.getDefaultInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val AllData: RealmResults<AllViewData>? = AllView()

        val adapter =
            AllViewAdapter(this, AllData, object : AllViewAdapter.OnItemClickListener {
                override fun onItemClick(item: AllViewData) {
                    // クリック時の処理
                    AlertDialog.Builder(this@MainActivity) // FragmentではActivityを取得して生成
                        .setTitle("確認")
                        .setMessage(item.name + "を使いましたか")
                        .setPositiveButton("はい"){ dialog, which ->
                            delete(item.id)
                        }
                        .setNegativeButton("いいえ"){ dialog, which ->
                        }
                        .show()

                }
                },true)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        add.setOnClickListener {
            val toAddActivityIntent = Intent(this,addActivity::class.java)
            startActivity(toAddActivityIntent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
    fun AllView(): RealmResults<AllViewData>?{
        return realm.where(AllViewData::class.java).sort("kigen").findAll()
    }

    fun delete(id: String) {
        realm.executeTransaction {
            val task = realm.where(AllViewData::class.java).equalTo("id", id).findFirst()
                ?: return@executeTransaction
            task.deleteFromRealm()
        }
    }




}