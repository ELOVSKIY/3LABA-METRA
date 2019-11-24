package application

import application.Models.SpenField

class SpenAnalyzer(private var inputText: String){
    val varList = mutableListOf<String>()
    val spenList = mutableListOf<SpenField>()


    init{
        inputText = removeTrash(inputText)
        generateVarList()
        generateSpenList()
    }

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
                while ((char.isLetterOrDigit() || (char in availableSymbols)) && (i != inputText.length - 1)) { // проверку на конец файла?
                    identifier += char
                    char = inputText[++i]
                }
                if (identifier.length != 1)
                    varList.add(identifier)
            }
            i++
        }
    }

    private fun generateSpenList(){
        val varMap = mutableMapOf<String, Int>()
        for (i in varList) {
            if (i in varMap.keys) {
                varMap[i] = varMap[i]!!.toInt() + 1
            } else
                varMap[i] = 0
        }
        for (i in varMap.keys){
            varMap[i]?.let {
                spenList.add(SpenField(i, it))
            }
        }
        var count = 0;
        for (i in spenList){
            count += i.spen
        }
        spenList.add(SpenField("Суммарный спен", count))
    }

}