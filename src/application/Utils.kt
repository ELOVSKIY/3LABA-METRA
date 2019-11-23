package application

fun removeTrash(text: String): String{
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