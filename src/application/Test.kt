package application

fun main(){
    val str =
            "        elsif (\$element eq \"\\\\\") {      #   literal character follows\n" +
            "            \$formatted .= substr (\$picture, 1, 1);\n" +
            "            \$picture    = substr (\$picture, 2);\n" +
            "        }\n" +
            "        else {\n" +
            "            \$formatted .= \$element;\n" +
            "        }\n" +
            "        \$lastch = substr (\$formatted, -1, 1);\n" +
            "    }\n" +
            "    return (\$formatted);\n" +
            "}\n" +
            "1;"
    val sp = SpenAnalyzer(str)
    val r = sp.spenList
    val e = 2
}