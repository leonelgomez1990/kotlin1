package com.lfg.homemarket.clases

class PriceBranch (
    var id: Long,
    var type : String,
    var description : String,
    var price : Double,
    var address : String,
    var distance : Double,
    var urlImage : String
) {
    constructor() : this(0, "", "", 0.0, "", 0.0, "")
}