package com.liamxsage.boilerplates.extensions

import com.liamxsage.boilerplates.PaperBoilerplate
import org.slf4j.LoggerFactory

fun getLogger(): org.slf4j.Logger {
    return LoggerFactory.getLogger(PaperBoilerplate::class.java)
}

fun <T : Any> T.nullIf(condition: (T) -> Boolean): T? {
    return if (condition(this)) null else this
}