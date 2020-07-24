package com.agarifullin.practiceapp

import android.content.ContentValues
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_new_order.*
import java.text.SimpleDateFormat
import java.util.*


class NewOrderFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_order, container, false)

    }
    lateinit var db :SQLiteDatabase
    lateinit var query:Cursor

    override fun onResume() {
        super.onResume()

        db = requireActivity().baseContext.openOrCreateDatabase(
            "app.db",
            MODE_PRIVATE,
            null
        )
        db.execSQL("CREATE TABLE IF NOT EXISTS orders (who TEXT, what TEXT, startDate TEXT, deadline TEXT)")
        // создаем объект для данных

        query = db.rawQuery("SELECT * FROM orders;", null)
        if (query.moveToFirst()) {
            val who: String = query.getString(0)
            val what: String = query.getString(1)
            val startDate:String = query.getString(2)
            val deadLine:String = query.getString(3)
        }

        val sdf = SimpleDateFormat("dd.MM.yyyy")
        var dateText = "Без дедлайна"

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            dateText = "$dayOfMonth.${month + 1}.$year"
        }

        addToDb.setOnClickListener {
            val cv = ContentValues()
            cv.put("who", name.text.toString())
            cv.put("what", task.text.toString())
            cv.put("startDate", sdf.format(Calendar.getInstance().time))
            cv.put("deadline", dateText)
            db.insert("orders", null ,cv)
            Toast.makeText(context, "Задание добавлено", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    override fun onPause() {
        super.onPause()
        db.close()
        query.close()
    }

    companion object {

    }
}