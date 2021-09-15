package com.lfg.masterdetail.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.lfg.masterdetail.R
import com.lfg.masterdetail.repositories.ProductRepository
import com.lfg.masterdetail.viewmodels.DetailViewModel

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel
    lateinit var v : View
    private var productRepository = ProductRepository()
    private lateinit var txtDetailDescription : TextView
    private lateinit var txtDetailId : TextView
    private lateinit var txtDetailBrand : TextView
    private lateinit var txtDetailPresentation : TextView
    private lateinit var txtDetailPrice : TextView
    private lateinit var imgDetail : ImageView
    private val PREF_NAME = "mySelection"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.detail_fragment, container, false)
        //Binding
        txtDetailDescription = v.findViewById(R.id.txtDetail)
        txtDetailId = v.findViewById(R.id.txtDetailId)
        txtDetailBrand = v.findViewById(R.id.txtDetailBrand)
        txtDetailPresentation = v.findViewById(R.id.txtDetailPresentation)
        txtDetailPrice = v.findViewById(R.id.txtDetailPrice)
        imgDetail = v.findViewById(R.id.imgDetail)

        return v
    }

    override fun onStart() {
        super.onStart()
        //val position  = DetailFragmentArgs.fromBundle(requireArguments()).position
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val position = sharedPref.getInt("position",0)!!

        val strDescription = productRepository.getList()[position].description
        val strId = productRepository.getList()[position].id
        val strBrand = productRepository.getList()[position].brand
        val strPresentation = productRepository.getList()[position].presentation
        val strPrice = productRepository.getList()[position].price
        val strUrlImage = productRepository.getList()[position].urlImage
        Snackbar.make(v, strDescription, Snackbar.LENGTH_SHORT).show()

        txtDetailDescription.text = strDescription
        txtDetailId.text = strId
        txtDetailBrand.text = strBrand
        txtDetailPresentation.text = strPresentation
        txtDetailPrice.text = "$ " + strPrice.toString()

        var strImage = strUrlImage
        if (!URLUtil.isValidUrl(strUrlImage))
            strImage = "https://www.preciosclaros.gob.ar/img/no-image.png"

        Glide
            .with(requireContext())
            .load(strImage)
            .centerInside()
            .into(imgDetail)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}