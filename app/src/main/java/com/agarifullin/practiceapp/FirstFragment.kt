package com.agarifullin.practiceapp

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




//        view.findViewById<Button>(R.id.button_first).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    lateinit var db:SQLiteDatabase
    lateinit var query:Cursor
    override fun onResume() {
        super.onResume()

        view?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            findNavController().navigate(R.id.action_FirstFragment_to_NewOrder)
        }

        db = requireActivity().baseContext.openOrCreateDatabase(
            "app.db",
            Context.MODE_PRIVATE,
            null
        )
        db.execSQL("CREATE TABLE IF NOT EXISTS orders (who TEXT, what TEXT, startDate TEXT, deadline TEXT)")
        // создаем объект для данных

        val adapterData = mutableListOf<OrderItem>()
        query = db.rawQuery("SELECT * FROM orders;", null)
        if (query.moveToFirst()) {
            val who: String = query.getString(0)
            val what: String = query.getString(1)
            val startDate:String = query.getString(2)
            val deadLine:String = query.getString(3)
            adapterData.add(OrderItem(startDate, deadLine, who, what))
            query.moveToNext()
        }
        while (!query.isAfterLast) {
            val who: String = query.getString(0)
            val what: String = query.getString(1)
            val startDate:String = query.getString(2)
            val deadLine:String = query.getString(3)
            adapterData.add(OrderItem(startDate, deadLine, who, what))
            query.moveToNext()
        }

        var adapter = OrderAdapter(adapterData)
        val layoutManager = LinearLayoutManager(context)
        ordersList?.layoutManager = layoutManager
        ordersList?.itemAnimator = DefaultItemAnimator()

        ordersList?.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    override fun onPause() {
        super.onPause()
        query.close()
        db.close()
    }

    private fun generateData(): List<OrderItem> {
        var result = ArrayList<OrderItem>()

        for (i in 0..9) {
            var user: OrderItem = OrderItem("Bett", "Awesome work ;)", "WHO", "WHAT?&?&")
            result.add(user)
        }

        return result
    }
}