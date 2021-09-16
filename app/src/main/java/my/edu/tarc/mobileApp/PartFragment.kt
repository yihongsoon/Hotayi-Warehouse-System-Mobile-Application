package my.edu.tarc.mobileApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.mobileApp.databinding.FragmentPartBinding

class PartFragment : Fragment() {

    private var _binding: FragmentPartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val partViewModel: PartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val partAdapter: PartAdapter = PartAdapter()

        partViewModel.partList.observe(
            viewLifecycleOwner, Observer {
                if(it.isEmpty()){
                    Toast.makeText(context, "No record", Toast.LENGTH_SHORT).show()
                } else {
                    partAdapter.setPart(it)
                }
            }
        )

        binding.rvPartRecord.apply {
            adapter = partAdapter
        }
    }
}