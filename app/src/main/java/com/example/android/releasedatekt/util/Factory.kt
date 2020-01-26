package com.example.android.releasedatekt.util

interface Factory<T> {
    fun get(): T
}

class SimpleFactory<T> (
    private val constructionBlock: () -> T
) : Factory<T> {

    override fun get(): T = constructionBlock()

}

class SingletonFactory<T> (
    private val constructionBlock: () -> T
) : Factory<T> {

    private val value by lazy { constructionBlock() }

    override fun get(): T = value

}

fun <T> factory(constructionBlock: () -> T): Factory<T> = SimpleFactory(constructionBlock)
fun <T> singletonFactory(constructionBlock: () -> T): Factory<T> = SingletonFactory(constructionBlock)