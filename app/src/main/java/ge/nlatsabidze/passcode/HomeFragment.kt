package ge.nlatsabidze.passcode

import android.view.animation.AlphaAnimation
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.GridLayoutManager
import ge.nlatsabidze.passcode.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var itemAdapter: RecyclerItemAdapter
    private lateinit var listOfNumbers: MutableList<Data>
    private lateinit var passcode: String

    override fun start() {

        initRecyclerView()
        setResultToRecyclerView()
        validatePasscode()
    }

    private fun initRecyclerView() {
        itemAdapter = RecyclerItemAdapter()
        binding.idRecycler.apply {
            adapter = itemAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    private fun setResultToRecyclerView() {
        listOfNumbers =  mutableListOf(
            Data(1, ""), Data(2, ""), Data(3, ""), Data(4, ""),
            Data(5, ""), Data(6, ""), Data(7, ""), Data(8, ""), Data(9, ""),
            Data(R.drawable.ic_touch__id_1, "Image"), Data(0, ""), Data(R.drawable.ic_vector, "Image")
        )

        itemAdapter.numberList = listOfNumbers
    }

    private fun validatePasscode() {
        passcode = ""
        val correctPasscode = "0934"
        var numberOfTries = 0
        itemAdapter.setOnItemClickListener(object : RecyclerItemAdapter.onItemClickListener {

            override fun onItemClick(position: Int) {
                val currentItem = listOfNumbers[position]
                passcode += currentItem.number.toString()
                numberOfTries++

                binding.passwordField.setText(passcode)

                if (numberOfTries == 4 && passcode == correctPasscode) {
                    Toast.makeText(requireContext(), "Success", LENGTH_SHORT).show()
                    binding.passwordField.text?.clear()
                    passcode = ""
                    numberOfTries = 0

                } else if (numberOfTries == 4 && passcode != correctPasscode) {
                    Toast.makeText(requireContext(), "Not success", LENGTH_SHORT).show()
                    binding.passwordField.text?.clear()
                    passcode = ""
                    numberOfTries = 0

                }
            }
        })
    }
}