package br.com.afCastrofo.testeframework.data.model.product

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Long,
    val name: String,
    val value: Double,
    @DrawableRes val image: Int
): Parcelable