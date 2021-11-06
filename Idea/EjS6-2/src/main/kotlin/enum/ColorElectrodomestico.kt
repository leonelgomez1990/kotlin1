package enum

/*
Listado de colores de electrodomésticos
 */
enum class ColorElectrodomestico {
    BLANCO {
        override fun toString(): String {
            return "Blanco"
        }
           },
    NEGRO {
        override fun toString(): String {
            return "Negro"
        }
          },
    GRIS {
        override fun toString(): String {
            return "Gris"
        }
    }
}