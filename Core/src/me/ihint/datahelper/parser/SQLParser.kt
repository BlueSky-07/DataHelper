package me.ihint.datahelper.parser

import me.ihint.datahelper.core.Bundle
import me.ihint.datahelper.core.Field
import me.ihint.datahelper.core.Group
import java.time.format.DateTimeFormatter

/**
 * SQLParser : Parser
 *
 * a tool to read struct text and create DataHelper
 */

interface SQLParser<G : Group<Field>> : Parser {
    fun parseFromFile(
            filePath: String, charset: String,
            readDateTimeFormatter: DateTimeFormatter,
            writeDateTimeFormatter: DateTimeFormatter
    ): Bundle<G>
}
