package me.ihint.datahelper.impl.core.datatype.mysql

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.Entry
import me.ihint.datahelper.exception.verify.ValueIsNullException
import me.ihint.datahelper.exception.VerifyNotPassException
import java.util.regex.Pattern

/**
 * TEXT : MysqlDataType(DataType)
 *
 * config: Config
 *      (FOR READING)
 *               "max"] : Int?            // the max length of value
 *              ["min"] : Int?            // the min length of value
 *          ["pattern"] : Pattern?        // regex check when it set
 *
 *      (FOR GENERATING)
 */

object TEXT : MysqlDataType() {
    override fun verify(data: Data, allowNull: Boolean): Boolean =
            when (val value: String? = data.value) {
                null -> allowNull
                else -> {
                    val config = data.config
                    val max: Int? = config["max"] as Int?
                    val min: Int? = config["min"] as Int?
                    val pattern: Pattern? = config["pattern"] as Pattern?
                    val length = value.length
                    when {
                        (max != null && length >= max)
                                || (min != null && length < min)
                                || (pattern != null && !pattern.matcher(data.value!!).matches())
                        -> false
                        else -> true
                    }
                }
            }

    override fun toEntry(data: Data): Entry =
            if (verify(data, false)) Entry(
                    "`${data.fieldName}`",
                    "'${data.value!!
                            .replace("\\", "\\\\")
                            .replace("'", "''")
                    }'"
            )
            else when (data.value) {
                null -> throw ValueIsNullException(this)
                else -> throw VerifyNotPassException(data.value!!, this)
            }
}
