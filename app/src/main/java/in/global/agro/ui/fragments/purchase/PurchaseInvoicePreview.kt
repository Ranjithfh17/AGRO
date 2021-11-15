package `in`.global.agro.ui.fragments.purchase

import `in`.global.agro.R
import `in`.global.agro.databinding.FragmentPurchaseInvoicePreviewBinding
import `in`.global.agro.utils.ImageHelper
import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.itextpdf.text.*
import com.itextpdf.text.html.WebColors
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.*


class PurchaseInvoicePreview : Fragment(R.layout.fragment_purchase_invoice_preview) {

    private lateinit var binding: FragmentPurchaseInvoicePreviewBinding
    private val args by navArgs<PurchaseInvoicePreviewArgs>()
    private lateinit var document: Document
    private lateinit var headerList: MutableList<String>
    private lateinit var childList: MutableList<String>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPurchaseInvoicePreviewBinding.bind(view)

        setDetails()

        binding.save.setOnClickListener {
            saveAsPdf()

        }

        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }

        headerList = mutableListOf(
            "Price",
            "Quantity",
            "Tax",
            "Total Amount"
        )

        childList = mutableListOf(
            args.purchaseInvoice.price,
            args.purchaseInvoice.quantity,
            args.purchaseInvoice.tax.toString(),
            args.purchaseInvoice.totalAmount,

            )



    }


    private fun setDetails() {

        binding.apply {
            companyName.text = " SGS"
            phoneNo.text = " 8562524526"
            customerName.text = args.purchaseInvoice.name
            date.text = args.purchaseInvoice.date
            productName.text = "Product : " + args.purchaseInvoice.product
            price.text = args.purchaseInvoice.price
            quantity.text = args.purchaseInvoice.quantity
            tax.text = args.purchaseInvoice.tax
            totalAmount.text = args.purchaseInvoice.totalAmount
        }
    }


    private fun saveAsPdf() {

        CoroutineScope(Dispatchers.Main).launch {

            try {
                document = Document()
                val path =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/AGRO SOFT"
                val dir = File(path)

                if (!dir.exists()) {
                    dir.mkdirs()
                }

                val file = File(dir, "Purchase" + UUID.randomUUID() + ".pdf")
                val outputStream = FileOutputStream(file)
                PdfWriter.getInstance(document, outputStream)


                document.open()
                document.pageSize = PageSize.A4

                var cell = PdfPCell()
                cell.border = Rectangle.NO_BORDER
                cell.isUseAscender = true
                val header1 = PdfPTable(1)

                val paragraphCompany = Paragraph("Company Name : SGS")
                paragraphCompany.alignment = Element.ALIGN_LEFT

                val paragraphPhoneNo = Paragraph("Phone No : 859455854555")
                paragraphPhoneNo.alignment = Element.ALIGN_LEFT
                paragraphPhoneNo.paddingTop = 50f

                val paragraphTitle = Paragraph("Sales Invoice")
                paragraphTitle.alignment = Element.ALIGN_CENTER

                val paragraphCustomerName = Paragraph("Bill To : ${args.purchaseInvoice.name}")
                paragraphCustomerName.alignment = Element.ALIGN_LEFT

                val paragraphDate = Paragraph("Date: ${args.purchaseInvoice.date}")
                paragraphDate.alignment = Element.ALIGN_RIGHT

                val paragraphProductName = Paragraph("Product Name : ${args.purchaseInvoice.product}")
                paragraphProductName.alignment = Element.ALIGN_LEFT

                cell.addElement(paragraphCompany)
                cell.addElement(paragraphPhoneNo)
                cell.addElement(paragraphTitle)
                cell.addElement(paragraphCustomerName)
                cell.addElement(paragraphDate)
                cell.addElement(paragraphProductName)
                header1.addCell(cell)
                document.add(header1)


                val table = PdfPTable(4)
                val columnWidth = floatArrayOf(20f, 20f, 20f, 40f)
                table.setWidths(columnWidth)


                cell = PdfPCell()
                for (i in headerList.indices) {
                    cell = PdfPCell(Phrase(headerList[i]))
                    cell.backgroundColor = WebColors.getRGBColor("#5471f1")
                    table.addCell(cell)
                }


                for (i in childList.indices) {
                    cell = PdfPCell(Phrase(childList[i]))
                    table.addCell(cell)
                }


                document.add(table)


            } catch (exception: Exception) {
                Log.i("TAG", "saveAsPdf:${exception.message} ")
            } finally {
                document.close()
            }
        }
    }

}