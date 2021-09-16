package my.edu.tarc.mobileApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.mobileApp.databinding.ActivityPartRecordBinding

class PartRecord : Fragment() {

    private var _binding: ActivityPartRecordBinding? = null

    private val binding get() = _binding!!
    private val partViewModel: PartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ActivityPartRecordBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val partAdapter: PartAdapter = PartAdapter()

        partViewModel.partList.observe(
            viewLifecycleOwner, Observer {
                if (it.isEmpty()) {
                    Toast.makeText(context, "No record", Toast.LENGTH_SHORT).show()
                } else {
                    partAdapter.setContact(it)
                }
            }
        )

        binding.rvPartRecord.apply {
            adapter = partAdapter
        }

        /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_record)


    }*/
    }
}