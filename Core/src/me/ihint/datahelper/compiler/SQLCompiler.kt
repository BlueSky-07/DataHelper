package me.ihint.datahelper.compiler

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.Group

/**
 * SQLCompiler<Group<Data>> : Compiler
 *
 * a tool to translate Group<Data> into SQL script
 */

interface SQLCompiler<G : Group<Data>> : Compiler {
    fun insert(record: G): String
    fun insert(record: G, returnId: Boolean): String
    fun update(target: G, condition: G): String
    fun select(condition: G): String
    fun select(condition: G, offset: Long, size: Long): String
    fun delete(condition: G): String
}
