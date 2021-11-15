package `in`.global.agro.ui.fragments.receipt

import `in`.global.agro.R
import `in`.global.agro.data.model.CustomerBalanceModel
import `in`.global.agro.data.model.ReceiptModel
import `in`.global.agro.databinding.FragmentAddReceiptBinding
import `in`.global.agro.extensions.showToast
import `in`.global.agro.viewmodels.ReceiptViewModel
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddReceipt : Fragment(R.layout.fragment_add_receipt) {

    private lateinit var binding:FragmentAddReceiptBinding
    private val viewModel by activityViewModels<ReceiptViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentAddReceiptBinding.bind(view)
        setDate()

        validate()


    }




    @SuppressLint("SimpleDateFormat")
    private fun setDate() {
        val currentCalender = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = simpleDateFormat.format(currentCalender.time)
        binding.date.text = currentDate


        binding.dateLayout.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(), { _, i, i1, i2 ->
                    if (month + 1 < 10) {
                        binding.date.text =
                            StringBuilder().append(i2).append("-").append("0").append(i1 + 1)
                                .append("-").append(i)
                    } else {
                        binding.date.text =
                            StringBuilder().append(i2).append("-").append(i1 + 1).append("-")
                                .append(i)
                    }
                }, year, month, day
            )
            datePickerDialog.show()
        }
    }


    private fun validate(){
        binding.apply {
            save.setOnClickListener {
                when{
                    customerName.text.toString().isEmpty()  ->{
                        requireContext().showToast("Enter customer name")
                    }

                    amount.text.toString().isEmpty()  ->{
                        requireContext().showToast("Enter amount")
                    } else ->{

                        saveReceipt()
                    }

                }
            }
        }
    }


    private fun saveReceipt(){
        lifecycleScope.launchWhenStarted {
            viewModel.insert(
                ReceiptModel(
                    binding.date.text.toString(),
                    binding.customerName.text.toString(),
                    binding.payType.text.toString(),
                    binding.payAccount.text.toString(),
                    binding.amount.text.toString(),
                    binding.balance.text.toString()
                )
            )

            viewModel.addCustomerBalance(CustomerBalanceModel(binding.customerName.text.toString(),binding.amount.text.toString()))


            findNavController().popBackStack()
        }


    }
}