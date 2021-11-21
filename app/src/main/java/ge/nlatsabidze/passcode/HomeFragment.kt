package ge.nlatsabidze.passcode

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
        with(binding) {
            passcode = ""
            val correctPasscode = "0934"
            var numberOfTries = 0
            itemAdapter.setOnItemClickListener(object : RecyclerItemAdapter.onItemClickListener {

                override fun onItemClick(position: Int) {
                    val currentItem = listOfNumbers[position]
                    passcode += currentItem.number.toString()
                    numberOfTries++

                    passwordField.setText(passcode)

                    if (numberOfTries == 4 && passcode == correctPasscode) {
                        Toast.makeText(requireContext(), "Success", LENGTH_SHORT).show()
                        passwordField.text?.clear()
                        passcode = ""
                        numberOfTries = 0

                    } else if (numberOfTries == 4 && passcode != correctPasscode) {
                        Toast.makeText(requireContext(), "Not success", LENGTH_SHORT).show()
                        passwordField.text?.clear()
                        passcode = ""
                        numberOfTries = 0

                    }
                }

                override fun onLastCharacterDeleted(position: Int) {
                    if (passcode.isNotEmpty()) {
                        passcode = passcode.dropLast(1)
                        numberOfTries--
                        val changedPasscode = passwordField.text.toString()
                        passwordField.setText(changedPasscode.substring(0, changedPasscode.length - 1))
                        Toast.makeText(requireContext(), passcode, LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Enter Passcode", LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}