package com.game.xo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

lateinit var databaseRef: DatabaseReference

lateinit var Button1 : Button
lateinit var Button2: Button
lateinit var Button3: Button
lateinit var Button4: Button
lateinit var Button5: Button
lateinit var Button6: Button
lateinit var Button7: Button
lateinit var Button8: Button
lateinit var Button9: Button
lateinit var Button10: Button
lateinit var Button11: Button
lateinit var Button12: Button
lateinit var Button13: Button
lateinit var Button14: Button
lateinit var Button15: Button
lateinit var Button16: Button

lateinit var buttonNewGame : Button
lateinit var buttonNewRoom : Button

lateinit var His_list : ListView

var count = 0
var Green = "#26A69A"
var Blue = "#42A5F5"
var default = "#EEEEEE"
var PlayerA = "Player B"
var PlayerB = "Player A"
var Room = ""
var key = ""
lateinit var textViewPlayer : TextView
lateinit var textViewRoom : TextView

var x1 : String = ""
var x2 : String = ""
var x3 : String = ""
var x4 : String = ""
val KeyList : MutableList<String> = mutableListOf<String>()

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val room = getIntent().getExtras()!!.getString("room").toString()
        Room = room
        findByID()
        textViewRoom.setText("Room :: $Room")

        Onclick()
        setdefaultpage()
        GetHistory()

    }

    private fun GetHistory() {
        GetKeyRoom()
    }

    private fun GetKeyRoom() {
        databaseRef = FirebaseDatabase.getInstance().getReference().child("Room").child(Room)
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                KeyList.clear()
                for (postSnapshot in snapshot.getChildren()) {
                    val Spin = postSnapshot.getValue(room_log::class.java)
                    KeyList.add(Spin!!.key)
                }

                val adp : ArrayAdapter<String> = ArrayAdapter(this@GameActivity,android.R.layout.simple_list_item_1, KeyList)
                His_list.adapter = adp
                His_list.onItemClickListener= AdapterView.OnItemClickListener { parent, view, position, id ->
                    val builder = AlertDialog.Builder(this@GameActivity)
                    builder.setTitle("View")
                    builder.setPositiveButton("YES"){dialog, which ->
                        SetViewHistory(KeyList[position])
                    }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            }
            override fun onCancelled(p0: DatabaseError) {}
        })

    }

    private fun SetViewHistory(Key : String){
        setdefaultHistorypage(Key)
    }

    private fun setdefaultHistorypage(Key: String) {
        Button1.setBackgroundColor(Color.parseColor("$default"));
        Button2.setBackgroundColor(Color.parseColor("$default"));
        Button3.setBackgroundColor(Color.parseColor("$default"));
        Button4.setBackgroundColor(Color.parseColor("$default"));
        Button5.setBackgroundColor(Color.parseColor("$default"));
        Button6.setBackgroundColor(Color.parseColor("$default"));
        Button7.setBackgroundColor(Color.parseColor("$default"));
        Button8.setBackgroundColor(Color.parseColor("$default"));
        Button9.setBackgroundColor(Color.parseColor("$default"));
        Button10.setBackgroundColor(Color.parseColor("$default"));
        Button11.setBackgroundColor(Color.parseColor("$default"));
        Button12.setBackgroundColor(Color.parseColor("$default"));
        Button13.setBackgroundColor(Color.parseColor("$default"));
        Button14.setBackgroundColor(Color.parseColor("$default"));
        Button15.setBackgroundColor(Color.parseColor("$default"));
        Button16.setBackgroundColor(Color.parseColor("$default"));
        Button1.isEnabled = false;
        Button2.isEnabled = false;
        Button3.isEnabled = false;
        Button4.isEnabled = false;
        Button5.isEnabled = false;
        Button6.isEnabled = false;
        Button7.isEnabled = false;
        Button8.isEnabled = false;
        Button9.isEnabled = false;
        Button10.isEnabled = false;
        Button11.isEnabled = false;
        Button12.isEnabled = false;
        Button13.isEnabled = false;
        Button14.isEnabled = false;
        Button15.isEnabled = false;
        Button16.isEnabled = false;
        //Log.w("##","key $Key")

        databaseRef = FirebaseDatabase.getInstance().getReference("Room").child(Room).child(Key)
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    val Spin = snapshot.getValue(room_log::class.java)
                    textViewPlayer.setText("Winner :: Player ${Spin!!.winner}")
                    Button1.setText("${Spin!!.x1}");
                    Button2.setText("${Spin!!.x2}");
                    Button3.setText("${Spin!!.x3}");
                    Button4.setText("${Spin!!.x4}");
                    Button5.setText("${Spin!!.x5}");
                    Button6.setText("${Spin!!.x6}");
                    Button7.setText("${Spin!!.x7}");
                    Button8.setText("${Spin!!.x8}");
                    Button9.setText("${Spin!!.x9}");
                    Button10.setText("${Spin!!.x10}");
                    Button11.setText("${Spin!!.x11}");
                    Button12.setText("${Spin!!.x12}");
                    Button13.setText("${Spin!!.x13}");
                    Button14.setText("${Spin!!.x14}");
                    Button15.setText("${Spin!!.x15}");
                    Button16.setText("${Spin!!.x16}");

                }
            }
            override fun onCancelled(p0: DatabaseError) {}
        })



    }

    private fun Onclick() {
        buttonNewGame.setOnClickListener { setdefaultpage() }
        buttonNewRoom.setOnClickListener {
            val intent = Intent(this@GameActivity, MainActivity::class.java)
            finish()
            //intent.putExtra("room", room)
            // intent.putExtra("key", key)
            startActivity(intent)
        }
        Button1.setOnClickListener { Button1.isEnabled = false;if(count % 2 == 0){ Button1.setText("X");CheckWin();Button1.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA"); } else{ Button1.setText("O");CheckWin();Button1.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }
        Button2.setOnClickListener { Button2.isEnabled = false;if(count % 2 == 0){ Button2.setText("X");CheckWin();Button2.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA") } else{ Button2.setText("O");CheckWin();Button2.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }
        Button3.setOnClickListener { Button3.isEnabled = false;if(count % 2 == 0){ Button3.setText("X");CheckWin();Button3.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA") } else{ Button3.setText("O");CheckWin();Button3.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }
        Button4.setOnClickListener { Button4.isEnabled = false;if(count % 2 == 0){ Button4.setText("X");CheckWin();Button4.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA") } else{ Button4.setText("O");CheckWin();Button4.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }
        Button5.setOnClickListener { Button5.isEnabled = false;if(count % 2 == 0){ Button5.setText("X");CheckWin();Button5.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA") } else{ Button5.setText("O");CheckWin();Button5.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }
        Button6.setOnClickListener { Button6.isEnabled = false;if(count % 2 == 0){ Button6.setText("X");CheckWin();Button6.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA") } else{ Button6.setText("O");CheckWin();Button6.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }
        Button7.setOnClickListener { Button7.isEnabled = false;if(count % 2 == 0){ Button7.setText("X");CheckWin();Button7.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA") } else{ Button7.setText("O");CheckWin();Button7.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }
        Button8.setOnClickListener { Button8.isEnabled = false;if(count % 2 == 0){ Button8.setText("X");CheckWin();Button8.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA") } else{ Button8.setText("O");CheckWin();Button8.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }
        Button9.setOnClickListener { Button9.isEnabled = false;if(count % 2 == 0){ Button9.setText("X");CheckWin();Button9.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA") } else{ Button9.setText("O");CheckWin();Button9.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }
        Button10.setOnClickListener { Button10.isEnabled = false;if(count % 2 == 0){ Button10.setText("X");CheckWin();Button10.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA") } else{ Button10.setText("O");CheckWin();Button10.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }
        Button11.setOnClickListener { Button11.isEnabled = false;if(count % 2 == 0){ Button11.setText("X");CheckWin();Button11.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA") } else{ Button11.setText("O");CheckWin();Button11.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }
        Button12.setOnClickListener { Button12.isEnabled = false;if(count % 2 == 0){ Button12.setText("X");CheckWin();Button12.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA") } else{ Button12.setText("O");CheckWin();Button12.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }
        Button13.setOnClickListener { Button13.isEnabled = false;if(count % 2 == 0){ Button13.setText("X");CheckWin();Button13.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA") } else{ Button13.setText("O");CheckWin();Button13.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }
        Button14.setOnClickListener { Button14.isEnabled = false;if(count % 2 == 0){ Button14.setText("X");CheckWin();Button14.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA") } else{ Button14.setText("O");CheckWin();Button14.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }
        Button15.setOnClickListener { Button15.isEnabled = false;if(count % 2 == 0){ Button15.setText("X");CheckWin();Button15.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA") } else{ Button15.setText("O");CheckWin();Button15.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }
        Button16.setOnClickListener { Button16.isEnabled = false;if(count % 2 == 0){ Button16.setText("X");CheckWin();Button16.setBackgroundColor(Color.parseColor("$Green"));textViewPlayer.setText("$PlayerA") } else{ Button16.setText("O");CheckWin();Button16.setBackgroundColor(Color.parseColor("$Blue"));textViewPlayer.setText("$PlayerB") };count +=1; }

    }

    private fun CheckWin() {
        if(Button1.getText().toString().equals("${Button2.getText().toString()}") and Button2.getText().toString().equals("${Button3.getText().toString()}") and Button3.getText().toString().equals("${Button4.getText().toString()}") and !Button1.getText().toString().equals("")){ AlertWinner(Button1.getText().toString()) }
        if(Button5.getText().toString().equals("${Button6.getText().toString()}") and Button6.getText().toString().equals("${Button7.getText().toString()}") and Button7.getText().toString().equals("${Button8.getText().toString()}") and !Button5.getText().toString().equals("")){ AlertWinner(Button5.getText().toString()) }
        if(Button9.getText().toString().equals("${Button10.getText().toString()}") and Button10.getText().toString().equals("${Button11.getText().toString()}") and Button11.getText().toString().equals("${Button12.getText().toString()}") and !Button9.getText().toString().equals("")){ AlertWinner(Button9.getText().toString()) }
        if(Button13.getText().toString().equals("${Button14.getText().toString()}") and Button14.getText().toString().equals("${Button15.getText().toString()}") and Button15.getText().toString().equals("${Button16.getText().toString()}") and !Button13.getText().toString().equals("")){ AlertWinner(Button13.getText().toString()) }
        if(Button1.getText().toString().equals("${Button5.getText().toString()}") and Button5.getText().toString().equals("${Button9.getText().toString()}") and Button9.getText().toString().equals("${Button13.getText().toString()}") and !Button1.getText().toString().equals("")){ AlertWinner(Button1.getText().toString()) }
        if(Button2.getText().toString().equals("${Button6.getText().toString()}") and Button6.getText().toString().equals("${Button10.getText().toString()}") and Button10.getText().toString().equals("${Button14.getText().toString()}") and !Button2.getText().toString().equals("")){ AlertWinner(Button2.getText().toString()) }
        if(Button3.getText().toString().equals("${Button7.getText().toString()}") and Button7.getText().toString().equals("${Button11.getText().toString()}") and Button11.getText().toString().equals("${Button15.getText().toString()}") and !Button3.getText().toString().equals("")){ AlertWinner(Button3.getText().toString()) }
        if(Button4.getText().toString().equals("${Button8.getText().toString()}") and Button8.getText().toString().equals("${Button12.getText().toString()}") and Button12.getText().toString().equals("${Button16.getText().toString()}") and !Button4.getText().toString().equals("")){ AlertWinner(Button4.getText().toString()) }
        if(Button1.getText().toString().equals("${Button6.getText().toString()}") and Button6.getText().toString().equals("${Button11.getText().toString()}") and Button11.getText().toString().equals("${Button16.getText().toString()}") and !Button1.getText().toString().equals("")){ AlertWinner(Button1.getText().toString()) }
        if(Button4.getText().toString().equals("${Button7.getText().toString()}") and Button7.getText().toString().equals("${Button10.getText().toString()}") and Button10.getText().toString().equals("${Button13.getText().toString()}") and !Button4.getText().toString().equals("")){ AlertWinner(Button4.getText().toString()) }

    }

    private fun AlertWinner(winner: String) {
        var ch = ""
        if(winner.equals("X")){ ch = "A"}else{ ch = "B"}
        val builder = AlertDialog.Builder(this@GameActivity)
        builder.setTitle("Winner")
        builder.setMessage("Player $ch")
        builder.setPositiveButton("YES"){dialog, which -> SetLog(ch) }
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun SetLog(winner: String) {
        SaveLog(winner)
        setdefaultpage()
    }

    private fun SaveLog(winner: String) {
        var timE = SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime())

        val ref = FirebaseDatabase.getInstance().getReference("Room")
        var key = Room + timE
        val items = room_log(
            "$key","$winner",
            "${Button1.getText()}", "${Button2.getText()}", "${Button3.getText()}", "${Button4.getText()}",
            "${Button5.getText()}", "${Button6.getText()}", "${Button7.getText()}", "${Button8.getText()}",
            "${Button9.getText()}", "${Button10.getText()}", "${Button11.getText()}", "${Button12.getText()}",
            "${Button13.getText()}", "${Button14.getText()}", "${Button15.getText()}", "${Button16.getText()}"
        )
        ref.child(Room).child(key).setValue(items).addOnCompleteListener {
        }
    }

    private fun setdefaultpage() {
        count = 0
        textViewPlayer.setText("Player A")
        Button1.setBackgroundColor(Color.parseColor("$default"));
        Button2.setBackgroundColor(Color.parseColor("$default"));
        Button3.setBackgroundColor(Color.parseColor("$default"));
        Button4.setBackgroundColor(Color.parseColor("$default"));
        Button5.setBackgroundColor(Color.parseColor("$default"));
        Button6.setBackgroundColor(Color.parseColor("$default"));
        Button7.setBackgroundColor(Color.parseColor("$default"));
        Button8.setBackgroundColor(Color.parseColor("$default"));
        Button9.setBackgroundColor(Color.parseColor("$default"));
        Button10.setBackgroundColor(Color.parseColor("$default"));
        Button11.setBackgroundColor(Color.parseColor("$default"));
        Button12.setBackgroundColor(Color.parseColor("$default"));
        Button13.setBackgroundColor(Color.parseColor("$default"));
        Button14.setBackgroundColor(Color.parseColor("$default"));
        Button15.setBackgroundColor(Color.parseColor("$default"));
        Button16.setBackgroundColor(Color.parseColor("$default"));

        Button1.setText("");
        Button2.setText("");
        Button3.setText("");
        Button4.setText("");
        Button5.setText("");
        Button6.setText("");
        Button7.setText("");
        Button8.setText("");
        Button9.setText("");
        Button10.setText("");
        Button11.setText("");
        Button12.setText("");
        Button13.setText("");
        Button14.setText("");
        Button15.setText("");
        Button16.setText("");

        Button1.isEnabled = true;
        Button2.isEnabled = true;
        Button3.isEnabled = true;
        Button4.isEnabled = true;
        Button5.isEnabled = true;
        Button6.isEnabled = true;
        Button7.isEnabled = true;
        Button8.isEnabled = true;
        Button9.isEnabled = true;
        Button10.isEnabled = true;
        Button11.isEnabled = true;
        Button12.isEnabled = true;
        Button13.isEnabled = true;
        Button14.isEnabled = true;
        Button15.isEnabled = true;
        Button16.isEnabled = true;


    }

    private fun findByID() {
        textViewPlayer = findViewById(R.id.textViewPlayer)
        textViewRoom = findViewById(R.id.textViewRoom)
        His_list = findViewById(R.id.His_list)

        Button1  = findViewById(R.id.button4)
        Button2 = findViewById(R.id.button5)
        Button3 = findViewById(R.id.button6)
        Button4 = findViewById(R.id.button7)
        Button5 = findViewById(R.id.button8)
        Button6 = findViewById(R.id.button9)
        Button7 = findViewById(R.id.button10)
        Button8 = findViewById(R.id.button11)
        Button9 = findViewById(R.id.button12)
        Button10 = findViewById(R.id.button13)
        Button11 = findViewById(R.id.button14)
        Button12 = findViewById(R.id.button15)
        Button13 = findViewById(R.id.button16)
        Button14 = findViewById(R.id.button17)
        Button15 = findViewById(R.id.button18)
        Button16 = findViewById(R.id.button19)
        buttonNewGame = findViewById(R.id.buttonNewGame)
        buttonNewRoom = findViewById(R.id.buttonNewRoom)
    }
}