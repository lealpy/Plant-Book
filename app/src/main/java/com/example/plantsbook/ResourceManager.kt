package com.example.plantsbook

import android.content.res.Resources
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourceManager @Inject constructor(
    private val resources: Resources
) {

    fun getString(@StringRes id: Int): String {
        return resources.getString(id)
    }

}
