package com.example.plantsbook.utils

inline fun <reified T : Enum<T>> random(): T = enumValues<T>().random()