package com.lfg.miacell.repositories

import com.lfg.miacell.entities.Product

class ProductRepository {

    private var productList : MutableList<Product> = mutableListOf()

    init {
        productList.add(Product(7794626009242,"HUGGIES","Pañal XXG Huggies Mega Classic 17 Un", 418.9,"17.0 un","https://imagenes.preciosclaros.gob.ar/productos/7794626009242.jpg"))
        productList.add(Product(7794626009235,"HUGGIES","Pañal Talle XG Mega Classic Huggies 18 Un", 418.9,"18.0 un","https://imagenes.preciosclaros.gob.ar/productos/7794626009235.jpg"))
        productList.add(Product(7794626009211,"HUGGIES","Pañal M Huggies Classic Plus Mega 26 Un", 418.9,"32.0 un","https://imagenes.preciosclaros.gob.ar/productos/7794626009211.jpg"))
        productList.add(Product(7794626009228,"HUGGIES","Pañal G Mega Classic Huggies 22 Un", 418.9,"22.0 un","https://imagenes.preciosclaros.gob.ar/productos/7794626009228.jpg"))
        productList.add(Product(7500435134798,"PAMPERS","Pañal M Supersec Pampers 26 Un", 395.2,"26.0 un","https://imagenes.preciosclaros.gob.ar/productos/7500435134798.jpg"))
        productList.add(Product(7798176009660,"SANCOR ","Leche Infantil Nutricion Completa Etapa 1 Sancor 200 Ml", 83.9,"200.0 ml","https://imagenes.preciosclaros.gob.ar/productos/7798176009660.jpg"))
        productList.add(Product(7500435133388,"PAMPERS","Pañal XG Supersec Pampers 18 Un", 395.2,"18.0 un","https://imagenes.preciosclaros.gob.ar/productos/7500435133388.jpg"))
        productList.add(Product(7500435133371,"PAMPERS","Pañal G Supersec Pampers 22 Un", 395.2,"22.0 un","https://imagenes.preciosclaros.gob.ar/productos/7500435133371.jpg"))
        productList.add(Product(7798176009622,"SANCOR ","Leche Infantil Nutricion Completa Etapa 3 Sancor 200 Ml", 79.0,"200.0 ml","https://imagenes.preciosclaros.gob.ar/productos/7798176009622.jpg"))
        productList.add(Product(7798176009684,"SANCOR ","Leche Infantil Nutricion Completa Etapa 2 Sancor 200 Ml", 80.0,"200.0 ml","https://imagenes.preciosclaros.gob.ar/productos/7798176009684.jpg"))
        productList.add(Product(7794626999888,"HUGGIES","Pañales Triple Proteccion Recien Nacido Huggies 34 Un", 419.1,"34.0 un","https://imagenes.preciosclaros.gob.ar/productos/7794626999888.jpg"))
        productList.add(Product(7891010247324,"JOHNSON'S","Toallas Humedas Limpieza y Suavidad Johnson's 44 Un", 185.5,"44.0 un","https://imagenes.preciosclaros.gob.ar/productos/7891010247324.jpg"))
        productList.add(Product(7500435130905,"PAMPERS","Pañal XXG Supersec Pampers 16 Un", 418.9,"16.0 un","https://imagenes.preciosclaros.gob.ar/productos/7500435130905.jpg"))
        productList.add(Product(7613035494787,"NIDO","Leche en Polvo Infantil Nestle Nido Fortigrow 400 Gr", 271.0,"400.0 gr","https://imagenes.preciosclaros.gob.ar/productos/7613035494787.jpg"))
        productList.add(Product(4015400862116,"PAMPERS","Toallitas Humedas Fresh Clean Pampers 48 Un", 280.0,"48.0 un","https://imagenes.preciosclaros.gob.ar/productos/4015400862116.jpg"))
        productList.add(Product(7795323002130,"LA SERENÍSIMA","Leche Infantil La Serenisima Crecer Plus 3 200 Ml", 49.0,"200.0 ml","https://imagenes.preciosclaros.gob.ar/productos/7795323002130.jpg"))
        productList.add(Product(7795323002482,"NUTRILÓN","Leche Infantil Nutrilon Profutura Premium 3 200 Ml", 86.0,"200.0 ml","https://imagenes.preciosclaros.gob.ar/productos/7795323002482.jpg"))
        productList.add(Product(7795323002475,"NUTRILÓN","Leche Infantil Nutrilon Profutura Premium 2 200 Ml", 86.0,"200.0 ml","https://imagenes.preciosclaros.gob.ar/productos/7795323002475.jpg"))
        productList.add(Product(7891010251024,"JOHNSON'S","Toallitas Humedas Johnsons 44 Un", 185.5,"44.0 un","https://imagenes.preciosclaros.gob.ar/productos/7891010251024.jpg"))
        productList.add(Product(7795323002468,"NUTRILÓN","Leche Infantil Nutrilon Profutura Premium 1 200 Ml", 94.0,"200.0 ml","https://imagenes.preciosclaros.gob.ar/productos/7795323002468.jpg"))
        productList.add(Product(7795323002123,"LA SERENÍSIMA","Leche 6 a 12 meses Baby 2 La Serenisima 250 Ml", 51.0,"200.0 ml","https://imagenes.preciosclaros.gob.ar/productos/7795323002123.jpg"))
        productList.add(Product(7790010540247,"JOHNSON BABY","Acondicionador Manzanilla Johnson Baby 200 Ml", 266.0,"200.0 ml","https://imagenes.preciosclaros.gob.ar/productos/7790010540247.jpg"))
        productList.add(Product(7798176009677,"SANCOR ","Leche Infantil Nutricion Completa Etapa 1 Sancor 500 Ml", 184.0,"500.0 ml","https://imagenes.preciosclaros.gob.ar/productos/7798176009677.jpg"))
        productList.add(Product(7798176009639,"SANCOR ","Leche Infantil Nutricion Completa Sancor 1 Lt", 319.7,"1.0 lt","https://imagenes.preciosclaros.gob.ar/productos/7798176009639.jpg"))
        productList.add(Product(7613287530516,"NIDO","Leche Infantil Entera Etapa 3 Nido 200 Ml", 58.5,"200.0 ml","https://imagenes.preciosclaros.gob.ar/productos/7613287530516.jpg"))
        productList.add(Product(7500435133357,"PAMPERS","Pañal Supersec Pampers 30 Un", 459.4,"30.0 un","https://imagenes.preciosclaros.gob.ar/productos/7500435133357.jpg"))
        productList.add(Product(7790064002104,"ESTRELLA","Paños de Algodon para Bebe Estrella 40 Un", 214.0,"40.0 un","https://imagenes.preciosclaros.gob.ar/productos/7790064002104.jpg"))
        productList.add(Product(7798176009691,"SANCOR ","Leche Fluida Infantil Etapa 2 Bebe Sancor 500 Ml", 174.1,"500.0 ml","https://imagenes.preciosclaros.gob.ar/productos/7798176009691.jpg"))
        productList.add(Product(7501058650917,"GERBER","Papilla Infantil Manzana Pouch Gerber 95 Gr", 131.0,"95.0 gr","https://imagenes.preciosclaros.gob.ar/productos/7501058650917.jpg"))
        productList.add(Product(7795323002116,"LA SERENÍSIMA","Leche Infantil La Serenisima Baby 1 200 Cc", 53.0,"200.0 cc","https://imagenes.preciosclaros.gob.ar/productos/7795323002116.jpg"))
        productList.add(Product(7790010684224,"JOHNSON'S","Colonia Fresca Caricia Johnsons Baby 100 Ml", 193.9,"100.0 ml","https://imagenes.preciosclaros.gob.ar/productos/7790010684224.jpg"))
        productList.add(Product(7501058650924,"GERBER","Papilla Infantil Pera Pouch Gerber 95 Gr", 131.0,"95.0 gr","https://imagenes.preciosclaros.gob.ar/productos/7501058650924.jpg"))
        productList.add(Product(7794626010217,"HUGGIES","Toallitas Humedas Triple Proteccion Oleo Huggies 48 Un", 297.0,"48.0 un","https://imagenes.preciosclaros.gob.ar/productos/7794626010217.jpg"))
        productList.add(Product(7500435148450,"PAMPERS","Toallitas Humedas Pampers 1 Un", 214.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/7500435148450.jpg"))
        productList.add(Product(7790010960021,"JOHNSON'S","Aceite Bebe con Aloe y Vitamina E Johnsons Baby 200 Cc", 325.9,"200.0 cc","https://imagenes.preciosclaros.gob.ar/productos/7790010960021.jpg"))
        productList.add(Product(8480017135834,"BABYSEC","Pañal Tallle XG Babysec 18 Un", 417.0,"18.0 un","https://imagenes.preciosclaros.gob.ar/productos/8480017135834.jpg"))
        productList.add(Product(8480017135810,"BABYSEC","Pañal Talle G Babysec 20 Un", 417.0,"20.0 un","https://imagenes.preciosclaros.gob.ar/productos/8480017135810.jpg"))
        productList.add(Product(8480017180056,"BABYS","Toallitas para Bebes con Oleo en Paquete Babys 50 Un", 149.0,"50.0 un","https://imagenes.preciosclaros.gob.ar/productos/8480017180056.jpg"))
        productList.add(Product(8480017229496,"BABYSEC","Pañal Talle XXG Babysec 16 Un", 417.0,"16.0 un","https://imagenes.preciosclaros.gob.ar/productos/8480017229496.jpg"))
        productList.add(Product(8480017134431,"SIN MARCA","Toallitas Humedas para Bebe en Doypack 70 Un", 199.0,"70.0 un","https://imagenes.preciosclaros.gob.ar/productos/8480017134431.jpg"))
        productList.add(Product(8480017134448,"SIN MARCA","Toallitas Humedas para Bebe 50 Un", 149.0,"50.0 un","https://imagenes.preciosclaros.gob.ar/productos/8480017134448.jpg"))
        productList.add(Product(8480017125965,"BABYS","Oleo Calcareo Babys 500 Ml", 225.0,"500.0 ml","https://imagenes.preciosclaros.gob.ar/productos/8480017125965.jpg"))
        productList.add(Product(8480017135803,"BABYSEC","Pañal Talle M Babysec 24 Un", 417.0,"24.0 un","https://imagenes.preciosclaros.gob.ar/productos/8480017135803.jpg"))
        productList.add(Product(7794626009648,"HUGGIES","Pañal Talle Pequeño Active Sec Huggies 40 Un", 757.0,"40.0 un","https://imagenes.preciosclaros.gob.ar/productos/7794626009648.jpg"))
        productList.add(Product(7500435169103,"PAMPERS","Pañal Talle Recien Nacido Confort Sec Ultra Pampers 36 Un", 816.5,"36.0 un","https://imagenes.preciosclaros.gob.ar/productos/7500435169103.jpg"))
        productList.add(Product(7891024078099,"COLGATE","Cepillo Dental para Bebes 0-12 Meses Colgate 1 Un", 1061.1,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/7891024078099.jpg"))
        productList.add(Product(7791274192326,"ALGABO","Shampoo Cabello Normal Extra Suave Baby Algabo 444 Ml", 147.2,"444.0 ml","https://imagenes.preciosclaros.gob.ar/productos/7791274192326.jpg"))
        productList.add(Product(7891010936624,"JOHNSON'S","Toallitas Humedas Hora de Jugar Johnsons Baby 25 Un", 129.0,"25.0 un","https://imagenes.preciosclaros.gob.ar/productos/7891010936624.jpg"))
        productList.add(Product(7798176009714,"SANCOR","Leche Infantil Bebe 4 (Nutricion Completa) Sancor 1 Lt", 287.5,"1.0 lt","https://imagenes.preciosclaros.gob.ar/productos/7798176009714.jpg"))
        productList.add(Product(7798176009653,"SANCOR","Leche Infantil Bebe 3 -Nutricion Completa- Vainilla Brick 1 Lt", 326.7,"1.0 lt","https://imagenes.preciosclaros.gob.ar/productos/7798176009653.jpg"))

    }
    fun getList() : MutableList<Product> {
        return productList
    }
}