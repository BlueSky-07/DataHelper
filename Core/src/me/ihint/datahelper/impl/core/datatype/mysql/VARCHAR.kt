package me.ihint.datahelper.impl.core.datatype.mysql

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.Entry
import me.ihint.datahelper.exception.verify.ValueIsNullException
import me.ihint.datahelper.exception.VerifyNotPassException
import java.util.regex.Pattern

/**
 * VARCHAR : MysqlDataType(DataType)
 *
 * config: Config
 *      (FOR READING)
 *           ["length"] : Int             // required, the max length of value
 *          ["pattern"] : Pattern?        // regex check when it set
 *
 *      (FOR GENERATING)
 */

object VARCHAR : MysqlDataType() {
    override fun verify(data: Data, allowNull: Boolean): Boolean =
            when (val value: String? = data.value) {
                null -> allowNull
                else -> {
                    val config = data.config
                    val length: Int = config["length"] as Int
                    value.length <= length && when (config["pattern"] as Pattern?) {
                        null -> true
                        else -> (config["pattern"] as Pattern).matcher(data.value!!).matches()
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
