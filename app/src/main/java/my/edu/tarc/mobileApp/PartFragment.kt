package my.edu.tarc.mobileApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_report.view.*
import kotlinx.android.synthetic.main.fragment_part.view.*

class PartFragment : Fragment() {

    private lateinit var partViewModel: PartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_part,container, false)

        val adapter = PartAdapter()
        val rvPartRecord = view.rvPartRecord
        rvPartRecord.adapter = adapter
        //rvPartRecord.LayoutManager = LinearLayoutManager(requireContext())

        partViewModel = ViewModelProvider(this).get(PartViewModel::class.java)
        partViewModel.getAllPart.observe(viewLifecycleOwner, Observer {part ->
            adapter.setData(part)
        })

        return view
    }

}