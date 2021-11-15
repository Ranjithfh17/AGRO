package `in`.global.agro.ui.fragments.purchase

import `in`.global.agro.R
import `in`.global.agro.callbacks.SharePopupCallback
import `in`.global.agro.databinding.FragmentPurchasePreviewBinding
import `in`.global.agro.ui.dialog.Share
import `in`.global.agro.utils.ImageHelper
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
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


class PurchasePreview : Fragment(R.layout.fragment_purchase_preview) {

    private lateinit var binding: FragmentPurchasePreviewBinding
    private val args by navArgs<PurchasePreviewArgs>()
    private lateinit var pdfDocument: PdfDocument
    private lateinit var document: Document
    private lateinit var headerList: MutableList<String>
    private lateinit var childList: MutableList<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPurchasePreviewBinding.bind(view)

        setData()

        binding.savePdf.setOnClickListener {
            saveAsPdf()
        }

        share()


        headerList = mutableListOf(
            "Price",
            "Quantity",
            "Tax",
            "Total Amount"
        )

        childList = mutableListOf(
            args.purchase.price,
            args.purchase.quantity,
            args.purchase.tax.toString(),
            args.purchase.totalAmount,

            )

    }


    private fun share() {
        binding.share.setOnClickListener {

            findNavController().navigate(R.id.share2)

            Share.setOnSharePopupCallback(object : SharePopupCallback {
                override fun onShareClick(value: String) {
                    when (value) {
                        "Share Image" -> {
                            shareAsImage()
                        }
                        "Share Pdf" -> {
                            shareAsPdf()
                        }
                    }
                }
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {

        binding.apply {
            companyName.text = " SGS"
            phoneNo.text = " 8562524526"
            customerName.text = args.purchase.name
            date.text = args.purchase.date
            productName.text = "Product : " + args.purchase.product
            price.text = args.purchase.price
            quantity.text = args.purchase.quantity
            tax.text = args.purchase.tax
            totalAmount.text = args.purchase.totalAmount
        }


    }

    private fun shareAsImage() {

        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = ImageHelper.viewToBitmap(binding.contentLayout)

            try {
                val file = File(requireContext().cacheDir, "images")
                file.mkdirs()
                val stream = FileOutputStream("$file/purchase.png")
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.close()
            } catch (exception: Exception) {
                exception.printStackTrace()

            }


            val path = File(requireContext().cacheDir, "images")
            val newFile = File(path, "purchase.png")

            val contentUri: Uri = FileProvider.getUriForFile(
                requireContext(),
                "in.global.agro.fileprovider",
                newFile
            )

            val shareIntent = Intent().also {
                it.action = Intent.ACTION_SEND
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                it.setDataAndType(contentUri, requireContext().contentResolver.getType(contentUri))
                it.putExtra(Intent.EXTRA_STREAM, contentUri)
                it.type = "image/png"
            }


            startActivity(Intent.createChooser(shareIntent, "choose to share"))


        }

    }

    private fun shareAsPdf() {
        CoroutineScope(Dispatchers.Main).launch {

            var bitmap = ImageHelper.viewToBitmap(binding.contentLayout)
            pdfDocument = PdfDocument()
            val pagInfo =
                PdfDocument.PageInfo.Builder(bitmap.width + 100, bitmap.height + 50, 2)
                    .create()
            val page = pdfDocument.startPage(pagInfo)


            val canvas = page.canvas
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width, bitmap.height, true)

            canvas.drawBitmap(bitmap, 50f, 50f, null)
            pdfDocument.finishPage(page)


            try {
                val file = File(requireContext().cacheDir, "pdfs")
                file.mkdirs()
                val stream = FileOutputStream("$file/purchase.pdf")
                pdfDocument.writeTo(stream)
                pdfDocument.close()

            } catch (exception: Exception) {
                exception.printStackTrace()

            }


            val path = File(requireContext().cacheDir, "pdfs")
            val newFile = File(path, "sale.pdf")

            val contentUri: Uri = FileProvider.getUriForFile(
                requireContext(),
                "in.global.agro.fileprovider",
                newFile
            )


            val shareIntent = Intent().also {
                it.action = Intent.ACTION_SEND
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                it.setDataAndType(contentUri, requireContext().contentResolver.getType(contentUri))
                it.putExtra(Intent.EXTRA_STREAM, contentUri)
                it.type = "application/pdf"
            }


            startActivity(Intent.createChooser(shareIntent, "choose to share"))


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

                val paragraphCustomerName = Paragraph("Bill To : ${args.purchase.name}")
                paragraphCustomerName.alignment = Element.ALIGN_LEFT

                val paragraphDate = Paragraph("Date: ${args.purchase.date}")
                paragraphDate.alignment = Element.ALIGN_RIGHT

                val paragraphProductName = Paragraph("Product Name : ${args.purchase.product}")
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