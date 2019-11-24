package application

val controlOperators = listOf(
    "unitl", "for", "foreach", "if", "elsif", "while", "unless"
)

val operations = listOf('+', '-', '*', '/')

fun removeTrash(text: String): String {
    var result = removeBlockComments(text)
    result = removeLineComments(result)
    return removeStrings(result)
}

fun removeBlockComments(text: String): String {
    val beginComment = "=begin"
    val endComment = "=cut"
    val builder = StringBuilder()
    var isComment = false

    var i = 0
    while (i < text.length) {
        val char = text[i]
        if (char == '=') {
            var j = 0
            var bufferString = ""
            if (isComment) {
                while (j < endComment.length && text[i] == endComment[j]) {
                    bufferString += text[i]
                    i++
                    j++
                }
                if (bufferString == endComment) {
                    isComment = false
                }
            } else {
                while (j < beginComment.length && text[i] == beginComment[j]) {
                    bufferString += text[i]
                    i++
                    j++
                }
                if (bufferString == beginComment) {
                    isComment = true
                } else {
                    builder.append(bufferString)
                }
            }
        } else if (!isComment) {
            builder.append(char)
        }
        i++
    }
    return builder.toString()
}

fun removeLineComments(text: String): String {
    var isString = false
    var isComment = false
    val builder = StringBuilder()

    val beginComment = '#'
    val endComment = '\n'
    val stringSeparator = '\"'

    for (char in text) {

        if (char == stringSeparator && !isComment)
            isString = !isString
        if (char == beginComment && !isString)
            isComment = true
        if (char == endComment && !isString && isComment)
            isComment = false
        if (!isComment)
            builder.append(char)
    }
    return builder.toString()
}

fun removeStrings(text: String): String {
    val builder = StringBuilder()
    val stringSymbol = '\"'
    val altertnativeStringSymbol = '\''
    val slashSymbol = '\\'
    var isString = false
    var isAlternativeString = false
    var i = 0
    while (i < text.length) {
        val char = text[i]
        if (char == slashSymbol) {
            i += 2
            continue
        }
        if (char == stringSymbol) {
            isString = !isString
            continue
        }
        if (char == altertnativeStringSymbol) {
            isAlternativeString = !isAlternativeString
            continue
        }
        if (!isString and !isAlternativeString)
            builder.append(char)
        i++
    }
    return builder.toString()
}

fun getVarListFromText(inputText: String): List<String> {
    val varList = mutableListOf<String>()
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
                if (i != inputText.length - 1)
                    char = inputText[++i]
                else
                    break
            }
            while (char.isLetterOrDigit() || (char in availableSymbols)) { // проверку на конец файла?
                identifier += char
                if (i != inputText.length - 1)
                    char = inputText[++i]
                else
                    break
            }
            if (identifier.length != 1)
                varList.add(identifier)
        }
        i++
    }
    return varList
}