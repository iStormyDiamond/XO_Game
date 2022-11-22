package com.game.xo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

lateinit var ButtonTest : Button
lateinit var EditTextRoom : EditText
var room : String = ""
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FindByID()
        Onclick()

    }

    private fun Onclick() {
        ButtonTest.setOnClickListener {
            room = EditTextRoom.getText().toString()
            if(room.equals("")){
                Toast.makeText(this, "Please input room name.", Toast.LENGTH_SHORT).show();

            }else{
                val intent = Intent(this@MainActivity, GameActivity::class.java)
                finish()
                intent.putExtra("room", room)
                // intent.putExtra("key", key)
                startActivity(intent)
            }


            /*
            val ref = FirebaseDatabase.getInstance().getReference("User")
            var ItemID = ref.push().key
            val items = con_rule(
                "3",
                ItemID.toString())
            ref.child(ItemID.toString()).setValue(items).addOnCompleteListener {
                // Toast .....
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
            }

             */

        }
    }

    private fun FindByID() {
        ButtonTest = findViewById(R.id.button)
        EditTextRoom = findViewById(R.id.EditTextRoom)


    }
}