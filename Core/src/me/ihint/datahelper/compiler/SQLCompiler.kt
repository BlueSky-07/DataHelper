package me.ihint.datahelper.compiler

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.Group

/**
 * SQLCompiler<Group<Data>> : Compiler
 *
 * a tool to translate Group<Data> into SQL script
 */

interface SQLCompiler<G : Group<Data>> : Compiler {
    /**
     * INSERT INTO `$tableName` ($keys) VALUES ($value);
     */
    fun insert(record: G): String

    /**
     * INSERT INTO `$tableName` ($keys) VALUES ($value); SELECT LAST_INSERT_ID() as `id`;
     */
    fun insert(record: G, returnId: Boolean): String

    /**
     * UPDATE `${condition.tableName}` SET ${target.equations} WHERE TRUE ${condition.equations};
     */
    fun update(target: G, condition: G): String

    /**
     * SELECT * FROM `$tableName` WHERE TRUE $equations ORDER BY ${condition.orderList};
     */
    fun select(condition: G): String

    /**
     * SELECT * FROM `$tableName` WHERE TRUE $equations ORDER BY ${condition.orderList} LIMIT $offset, $size;
     */
    fun select(condition: G, offset: Long, size: Long): String

    /**
     * DELETE FROM `$tableName`
     */
    fun delete(condition: G): String
}
