package br.com.afCastrofo.ecommerce.ui.receipt

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import br.com.afCastrofo.ecommerce.R
import br.com.afCastrofo.ecommerce.base.BaseViewModel
import br.com.afCastrofo.ecommerce.data.repository.cart.CartRepository
import br.com.afCastrofo.ecommerce.utils.SingleLiveData
import com.gkemon.XMLtoPDF.PdfGenerator
import com.gkemon.XMLtoPDF.PdfGeneratorListener
import com.gkemon.XMLtoPDF.model.FailureResponse
import com.gkemon.XMLtoPDF.model.SuccessResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val cartRepository: CartRepository
): BaseViewModel() {
    
    val onReceiptGenerated: LiveData<Unit> = SingleLiveData()
    
    fun finishPurchase(context: Context, contentView: View) {
        launch(errorMessage = R.string.error_generating_pdf) {
            cartRepository.clearCart()
            generateFile(context, contentView)
        }
    }
    
    private suspend fun generateFile(context: Context, contentView: View){
        val filename = "${Date.from(Instant.now())}-receipt.pdf"

        PdfGenerator.getBuilder()
            .setContext(context)
            .fromViewSource()
            .fromView(contentView)
            .setPageSize(PdfGenerator.PageSize.A4)
            .setFileName(filename)
            .setFolderName("purchaseReceipts")
            .openPDFafterGeneration(true)
            .build(object: PdfGeneratorListener() {
                override fun onStartPDFGeneration() {}
                override fun onFinishPDFGeneration() {}

                override fun onFailure(failureResponse: FailureResponse?) {
                    super.onFailure(failureResponse)
                    throw Exception()
                }

                override fun onSuccess(response: SuccessResponse?) {
                    super.onSuccess(response)
                    onReceiptGenerated.call()
                }
            })
    }
    
}