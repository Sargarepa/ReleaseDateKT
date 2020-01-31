package com.example.android.releasedatekt.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory
@Inject constructor (private val providerMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val provider = providerMap[modelClass] ?:
                providerMap.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key)}?.value
                ?: throw IllegalArgumentException("unknown model class: $modelClass")

        return try {
            provider.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}