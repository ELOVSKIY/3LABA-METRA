package application

fun main() {
    val str =
            "while(\$var != 0){\n" +
            "    \$sum += \$i;\n" +
            "    \$i *= 2;\n" +
            "}  "+
    "while(\$var != 0){\n" +
            "    \$sum += \$i;\n" +
            "    \$i *= 2;\n" +
            "    \$var--;\n" +
            "}  "
    val sp = ChapinAnalayzer(str)

    val e = 2
}