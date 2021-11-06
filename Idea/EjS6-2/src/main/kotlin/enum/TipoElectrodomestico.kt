package enum

/*
Listado de tipos de electrodomésticos
 */
enum class TipoElectrodomestico {
    LAVADORA {
        override fun toString(): String {
            return "Lavadora"
        }
             },
    MICROONDAS {
        override fun toString(): String {
            return "Microondas"
        }
               },
    REFRIGERADOR {
        override fun toString(): String {
            return "Refrigerador"
        }
    }
}