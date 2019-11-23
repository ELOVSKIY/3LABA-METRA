package application

import application.Models.Spen

class SpenAnalyzer(private val inputText: String){
    val varList = mutableListOf<String>()
    val spenList = mutableListOf<Spen>()

    private fun generateVarList() {
        val specialChar = '\\'
        val varSymbols = arrayOf('$', '@', '%')
        val availableSymbols = arrayOf('_', '\'')

        var i = 0
        while (i < inputText.length) {
            var char = inputText[i]
            if (char == specialChar) {
                i += 2
                continue
            }
            if (char in varSymbols) {
                var identifier = "" + char
                char = inputText[++i]
                while (char == ' ')
                    char = inputText[++i]
                if (char.isLetter() || (char in availableSymbols)) {
                    identifier += char
                    char = inputText[++i]
                }
                while (char.isLetterOrDigit() || (char in availableSymbols)) { // проверку на конец файла?
                    identifier += char
                    char = inputText[++i]
                }
                if (identifier.length != 1)
                    varList.add(identifier)
            }
//            } else if (char.isDigit()) {
//                var identifier = ""
//                while (char.isLetterOrDigit() || (char in availableSymbols)) { // проверку на конец файла?
//                    identifier += char
//                    char = inputText[++i]
//                }
//                varList.add(identifier)
//
//            }
            i++
        }
    }

    private fun generateSpenList(){
        val varMap = mutableMapOf<String, Int>()
        for (i in varList) {
            if (i in varMap.keys) {
                varMap[i] = varMap[i]!!.toInt() + 1
            } else
                varMap[i] = 1
        }
    }
}