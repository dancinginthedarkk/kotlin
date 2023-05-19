package com.example.secondapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.secondapp.presentationlayer.ItemAdapter
import com.example.secondapp.viewModel.MainViewModel

class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonReconnect: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: ItemAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        initViews(view)
        setListeners()
        observeViewModel()
        return view
    }

    private fun observeViewModel() {
        viewModel.cats.observe(viewLifecycleOwner) {
            adapter.setCats(it)
        }
        viewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                buttonReconnect.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                buttonReconnect.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            buttonReconnect.isEnabled = !isLoading
        }
    }

    private fun setListeners() {
        adapter = ItemAdapter(
            onReachEndListener = {
                viewModel.loadCats()
            },
            onItemClickListener = { cat ->
            }
        )
        recyclerView.adapter = adapter
        buttonReconnect.setOnClickListener {
            viewModel.loadCats()
        }
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerViewCats)
        buttonReconnect = view.findViewById(R.id.button_reconnect)
        progressBar = view.findViewById(R.id.progress_bar)
    }
}
