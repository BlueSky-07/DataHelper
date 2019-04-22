package me.ihint.datahelper.core

/**
 * Field : class
 *
 * a definition of one data, including name, data type, and config
 */

class Field(
        val name: String,
        val type: DataType,
        val config: Config
) {
    fun newData(): Data = Data(
            null, this
    )

    fun newData(value: String): Data = Data(
            value, this
    )
}
