package me.ihint.datahelper.impl.core.datatype.mysql

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.DataType
import me.ihint.datahelper.core.Entry
import me.ihint.datahelper.exception.ValueIsNullException
import me.ihint.datahelper.exception.VerifyNotPassException
import java.util.regex.Pattern

/**
 * VARCHAR : DataType
 *
 * config: Config
 *      (FOR READING)
 *           ["length"] : Int             // the max length of value
 *          ["pattern"] : Pattern?        // regex check when it set
 *
 *      (FOR GENERATING)
 */

object VARCHAR : DataType {
	override fun verify(data: Data, allowNull: Boolean): Boolean =
			when (data.value) {
				null -> allowNull
				else -> {
					val config = data.config
					val length: Int = config["length"] as Int
					data.value!!.length <= length && when (config["pattern"] as Pattern?) {
						null -> true
						else -> (config["pattern"] as Pattern).matcher(data.value!!).matches()
					}
				}
			}
	
	override fun toEntry(data: Data): Entry =
			if (verify(data, false)) Entry(
					"`${data.fieldName}`",
					"`${data.value!!.replace("\\", "\\\\")}`"
			)
			else when (data.value) {
				null -> throw ValueIsNullException()
				else -> throw VerifyNotPassException()
			}
}