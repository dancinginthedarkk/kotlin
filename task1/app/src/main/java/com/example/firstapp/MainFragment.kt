package com.example.firstapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardsAdapter: CardsAdapter
    private lateinit var button: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_main, container, false)
        button = view.findViewById<FloatingActionButton>(R.id.buttonAdd)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        cardsAdapter = CardsAdapter()

        recyclerView.adapter = cardsAdapter

        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.layoutManager = GridLayoutManager(view.context, 3)
        else
            recyclerView.layoutManager = GridLayoutManager(view.context, 4)

        mainViewModel.getCount().observe(viewLifecycleOwner, object : Observer<ArrayList<Int>>{
            override fun onChanged(t: ArrayList<Int>?) {
                if (t != null) {
                    cardsAdapter.setNumbers(t)
                }
            }
        })

        button.setOnClickListener{
            mainViewModel.increment()
        }

        return view
    }

}