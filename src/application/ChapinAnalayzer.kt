package application

import application.Models.ChapinField

class ChapinAnalayzer(private val inputText: String) {
    val varList = mutableListOf<String>()
    val inputVarList = mutableListOf<String>()
    val outputVarList = mutableListOf<String>()
    val controlVarList = mutableListOf<String>()
    val rightVarList = mutableListOf<String>()
    var leftVarList = mutableListOf<String>()
    var changeVarList = mutableListOf<String>()

    val Pgroup = mutableListOf<String>()
    val Mgroup = mutableListOf<String>()
    val Cgroup = mutableListOf<String>()
    val Tgroup = mutableListOf<String>()
    val IOgroup = mutableListOf<String>()

    init {
        generateVarList()
        generateInputVarList()
        generateOutputVarList()
        generateControlVarList()
        generateRightVarList()
        generateLeftVarList()
        generateChangeVarList()

        generateCgroup()
        generateIOgroup()
        generatePgroup()
        generateTgroup()
        generateMgroup()

    }

    fun getP(): List<String> {
        return Pgroup
    }

    fun getM(): List<String> {
        return Mgroup
    }

    fun getC(): List<String> {
        return Cgroup
    }

    fun getT(): List<String> {
        return Tgroup
    }

    fun getPIO(): List<String> {
        return Pgroup.filter { it in IOgroup }
    }

    fun getMIO(): List<String> {
        return Mgroup.filter { it in IOgroup }

    }

    fun getCIO(): List<String> {
        return Cgroup.filter { it in IOgroup }
    }

    fun getTIO(): List<String> {
        return Tgroup.filter { it in IOgroup }
    }

    fun getChapinFields(): List<ChapinField>{
        return listOf(
            ChapinField("P", getP(), getP().size),
            ChapinField("M", getM(), getM().size),
            ChapinField("C", getC(), getC().size),
            ChapinField("T", getT(), getT().size)
        )
    }

    fun getChapinFieldsIO(): List<ChapinField>{
        return listOf(
            ChapinField("P", getPIO(), getPIO().size),
            ChapinField("M", getMIO(), getMIO().size),
            ChapinField("C", getCIO(), getCIO().size),
            ChapinField("T", getTIO(), getTIO().size)
        )
    }

    fun getChapin(): Double{
        return getP().size + getM() .size * 2 + getC().size * 3 + getT().size * 0.5
    }

    fun getChapinIO(): Double{
        return getPIO().size + getMIO() .size * 2 + getCIO().size * 3 + getTIO().size * 0.5
    }


    private fun generateTgroup() {
        for (i in varList) {
            if ((i !in controlVarList) && (i !in outputVarList) && (i !in rightVarList)) {
                Tgroup.add(i)
            }
        }
    }

    private fun generatePgroup() {
        val varList = mutableListOf<String>()
        for (i in inputVarList) {
            if ((i !in controlVarList) && (i !in changeVarList)) {
                varList.add(i)
            }
        }
        Pgroup.addAll(varList)
    }

    private fun generateMgroup() {
        for (i in varList) {
            if ((i !in controlVarList) && (i !in Tgroup) && (i !in Cgroup) && (i !in Pgroup)) {
                Mgroup.add(i)
            }
        }
    }

    private fun generateChangeVarList() {
        for (i in leftVarList) {
            if (leftVarList.count { it == i } == 2) {
                changeVarList.add(i)
            }
        }
        val uniq = changeVarList.toHashSet().toList()
        changeVarList.clear()
        changeVarList.addAll(uniq)
    }

    private fun generateLeftVarList() {
        val operators = mutableListOf<String>()
        var wordBuffer = ""
        var i = 0
        while (i < inputText.length) {
            var char = inputText[i]
            wordBuffer += char

            if (((inputText[i] == '=') && (inputText[i - 1] != '=') && (inputText[i + 1] != '=') && (inputText[i - 1] != '!'))
                || ((inputText[i] in operations) && (inputText[i + 1] != '=') && (inputText[i + 1] !in operations))
            ) {
                val d = inputText[i]
                val d2 = inputText[i + 1]
                val d3 = inputText[i - 1]
                var value = ""
                var j = i - 1
                char = inputText[j]
                while ((char != ';') && (char != '(') && (char != '{') && (j != -1))  {
                    value += char
                    j--
                    if (j == 0)
                        break
                    char = inputText[j]
                }
                val sb = StringBuffer(value)
                sb.reverse()
                operators.add(sb.toString())
            } else {
                wordBuffer = ""
            }
            i++
        }
        val varList = mutableListOf<String>()
        for (k in operators) {
            varList.addAll(getVarListFromText(k))
        }
        leftVarList.addAll(varList)
    }

    private fun generateCgroup() {
        Cgroup.addAll(controlVarList)
    }

    private fun generateIOgroup() {
        IOgroup.addAll(inputVarList)
        IOgroup.addAll(outputVarList)
        val uniq = IOgroup.toHashSet().toList()
        IOgroup.clear()
        IOgroup.addAll(uniq)
    }

    private fun generateVarList() {
        varList.addAll(getVarListFromText(inputText))
        val uniq = varList.toHashSet().toList()
        varList.clear()
        varList.addAll(uniq)
    }

    private fun generateRightVarList() {
        rightVarList.addAll(getVarAfterKeyWords(listOf("=")))
        val uniq = rightVarList.toHashSet().toList()
        rightVarList.clear()
        rightVarList.addAll(uniq)
    }

    private fun generateControlVarList() {
        controlVarList.addAll(getVarAfterKeyWords(controlOperators))
        val uniq = controlVarList.toHashSet().toList()
        controlVarList.clear()
        controlVarList.addAll(uniq)
    }

    private fun generateOutputVarList() {
        outputVarList.addAll(getVarAfterKeyWords(listOf("print")))
        val uniq = outputVarList.toHashSet().toList()
        outputVarList.clear()
        outputVarList.addAll(uniq)
    }

    private fun generateInputVarList() {
        inputVarList.addAll(getVarAfterKeyWords(listOf("chomp")))
        val uniq = inputVarList.toHashSet().toList()
        inputVarList.clear()
        inputVarList.addAll(uniq)
    }

    private fun getVarAfterKeyWords(keyWords: List<String>): List<String> {
        val operatorHeads = getOperatorsAfter(keyWords)
        val varList = mutableListOf<String>()
        for (i in operatorHeads) {
            varList.addAll(getVarListFromText(i))
        }
        return varList
    }

    private fun getOperatorsAfter(keyWords: List<String>): MutableList<String> {
        val operators = mutableListOf<String>()
        var wordBuffer = ""
        var i = 0
        while (i < inputText.length) {
            var char = inputText[i]
            wordBuffer += char
            when (checkKeyWords(keyWords, wordBuffer)) {
                0 -> {
                    wordBuffer = ""
                }
                1 -> {
                    var ioValue = ""
                    i++
                    char = inputText[i]
                    while ((char != ';') && (char != ')')) {
                        ioValue += char
                        i++
                        char = inputText[i]
                    }
                    operators.add(ioValue)
                }
            }
            i++
        }
        return operators
    }

    private fun checkKeyWords(keyWords: List<String>, word: String): Int {
        val correctSize = keyWords.filter { it.length >= word.length }
        val possibleFunc = correctSize.filter {
            var filterFlag = true
            for (i in word.indices) {
                if (word[i] != it[i]) {
                    filterFlag = false
                    break
                }
            }
            filterFlag
        }.toMutableList()
        return if (possibleFunc.size == 0) {
            0
        } else if ((possibleFunc.size == 1) && (word.length == possibleFunc[0].length)) {
            1
        } else {
            2
        }
    }
}