package app.web.drjackycv.presentation.products.productdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import app.web.drjackycv.presentation.R
import app.web.drjackycv.presentation.extension.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_detail.*

@AndroidEntryPoint
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        val safeArgs: ProductDetailFragmentArgs by navArgs()
        val product = safeArgs.productDetailBeerUI

        with(product) {
            productDetailIdTxv.text = id.toString()
            productDetailImv.load(imageUrl)
            productDetailNameTxv.text = name
            productDetailDescriptionTxv.text = getString(R.string.description, description)
            productDetailAbvTxv.text = getString(R.string.abv, abv.toString())
        }
    }

}