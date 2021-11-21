package app.kugimiya.shintyan.syoumikigen2

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add.*
import java.time.LocalDateTime
import java.util.*

class addActivity : AppCompatActivity() {
    val realm: Realm = Realm.getDefaultInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        ketteiButton.setOnClickListener {
            val name: String = NameEditText.text.toString()
            val place: String = basyoEdittext.text.toString()
            val date: String = DateEditText.text.toString()
            if (NameEditText.text.toString() == "") {
                Toast.makeText(applicationContext, "物の名前を入力してください", Toast.LENGTH_SHORT).show()
            } else if (basyoEdittext.text.toString() == "") {
                Toast.makeText(applicationContext, "入れた場所を入力してください", Toast.LENGTH_SHORT).show()
            } else if (DateEditText.text.toString() == "") {
                Toast.makeText(applicationContext, "賞味期限を入力してください", Toast.LENGTH_SHORT).show()
            }
            val date2String = date.replace("/", "").replace("-", "").replace(".", "")
            Log.d("date2String", date2String)
            val date2: Int = date2String.toInt()
            val datelen = date2.toString().length
            val date2len = date.length
            if (datelen != 8) {
                Toast.makeText(applicationContext, "YYYY/MM/DDの形式で入力してください", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else if (date2len != 10) {
                Toast.makeText(applicationContext, "YYYY/MM/DDの形式で入力してください", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener

            }
            val mon = date2.toString().substring(4, 6).toInt()
            val day = date2.toString().substring(6, 8).toInt()
            val specialYear = date2.toString().substring(0, 4).toInt()
            if (mon > 12 || day > 31) {
                maketext(mon, day)
            } else if (day == 31) {
                if (mon == 4 || mon == 6 || mon == 9 || mon == 11 || mon == 2) {
                    maketext(mon, day)
                }else {
                    save(name,place,date2)
                    finish()
                }
            } else if (day == 29 && mon == 2) {
                urudoshi(specialYear, day, mon,name,place,date2)
            } else if (day == 30) {
                if (mon == 2) {
                    maketext(mon, day)
                } else {
                    save(name, place, date2)
                    finish()
                }
            } else {
                save(name, place, date2)
                finish()
            }


        }


    }

    fun save(/*imageId: Int,*/ content: String, basyo: String, hiduke: Int) {
        realm.executeTransaction {
            val task = it.createObject(AllViewData::class.java, UUID.randomUUID().toString())
            //task.imageId = imageId
            task.name = content
            task.place = basyo
            task.kigen = hiduke
        }
    }

    fun urudoshi(year: Int, day: Int, mon: Int,name: String, place: String,date2:Int) {
        if (year % 4 != 0) {
            Toast.makeText(
                applicationContext,
                year.toString() + "年" + mon.toString() + "月" + day.toString() + "日は存在しないです",
                Toast.LENGTH_SHORT
            ).show()
        } else if (year % 100 == 0 && year % 400 != 0) {
            Toast.makeText(
                applicationContext,
                year.toString() + "年" + mon.toString() + "月" + day.toString() + "日は存在しないです",
                Toast.LENGTH_SHORT
            ).show()

        }else{
            save(name, place, date2)
            finish()
        }
    }

    fun maketext(mon: Int, day: Int) {
        Toast.makeText(
            applicationContext,
            mon.toString() + "月" + day.toString() + "日は存在しないです",
            Toast.LENGTH_SHORT
        ).show()
    }
}