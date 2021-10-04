package com.lfg.miacell.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.lfg.miacell.database.AppDatabase
import com.lfg.miacell.database.ProductDao
import com.lfg.miacell.databinding.FragmentDetailBinding
import com.lfg.miacell.repositories.ProductRepository
import com.lfg.miacell.viewmodels.DetailViewModel

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding : FragmentDetailBinding
    private var productRepository = ProductRepository()
    private val PREF_NAME = "mySelection"
    private var db: AppDatabase? = null
    private var productDao: ProductDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //val position  = DetailFragmentArgs.fromBundle(requireArguments()).position
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val position = sharedPref.getInt("position",0)!!
        val id : Long = sharedPref.getLong("id",0)!!
        var strDescription = "nuevo"
        var strId : Long = 0
        var strBrand = "nuevo"
        var strPresentation = "nuevo"
        var strPrice = 0.0
        var strUrlImage = "nuevo"

        if(id >= 0)
        {
            db = AppDatabase.getAppDataBase(binding.layoutDetail.context)
            productDao = db?.ProductDao()
            var product = productDao?.loadProductById(id)!!

            //val strDescription = productRepository.getList()[position].description
            //val strId = productRepository.getList()[position].id
            //val strBrand = productRepository.getList()[position].brand
            //val strPresentation = productRepository.getList()[position].presentation
            //val strPrice = productRepository.getList()[position].price
            //val strUrlImage = productRepository.getList()[position].urlImage
            strDescription = product.description
            strId = product.id
            strBrand = product.brand
            strPresentation = product.presentation
            strPrice = product.price
            strUrlImage = product.urlImage

            Snackbar.make(binding.layoutDetail, strDescription, Snackbar.LENGTH_SHORT).show()
        }

        //binding.txtDetailDescription.text = strDescription
        binding.txtDetailId.text = strId.toString()
        binding.txtDetailBrand.text = strBrand
        binding.txtDetailPresentation.text = strPresentation
        binding.txtDetailPrice.text = "$ " + strPrice.toString()

        var strImage = strUrlImage
        if (!URLUtil.isValidUrl(strUrlImage))
            strImage = "https://www.preciosclaros.gob.ar/img/no-image.png"

        Glide
            .with(requireContext())
            .load(strImage)
            .centerInside()
            .into(binding.imgDetail)

    }

}