package ru.netology.nmedia.repository

interface Modifier {
    fun countModifier(count: Int): String {

        return when (count) {
           in 0 until 1000 -> count.toString()
           in 1000 until 10_000 -> String.format("%.1f", (count.toDouble() / 1000)) + "K"
           in 10_000 until 1_000_000 -> (count / 1000).toString() + "K"
           in 1_000_000 until 10_000_000 -> String.format("%.1f", (count.toDouble() / 1_000_000)) + "M"
           else -> (count / 1_000_000).toString() + "M"
       }
    }
}