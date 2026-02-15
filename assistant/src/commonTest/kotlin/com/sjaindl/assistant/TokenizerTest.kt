package com.sjaindl.assistant

import com.sjaindl.assistant.util.Tokenizer
import kotlin.test.Test
import kotlin.test.assertEquals

class TokenizerTest {

    @Test
    fun testNormalization() {
        val input = "High-Quality"

        val normalized = input.lowercase()
        val tokens = normalized.split(Regex("(?<=-)|(?=-)"))

        val expected = listOf("high", "-", "quality")

        assertEquals(expected, tokens, "The text was not correctly split at the hyphen boundary.")
    }

    @Test
    fun testFrequentPair() {
        val word1 = listOf("h", "u", "g")
        val word2 = listOf("p", "u", "g")
        val corpus = listOf(word1, word2)

        val pairs = mutableMapOf<Pair<String, String>, Int>()
        for (word in corpus) {
            for (i in 0 until word.size - 1) {
                val pair = Pair(word[i], word[i + 1])
                pairs[pair] = pairs.getOrElse(pair, { 0 }) + 1
            }
        }

        val bestPair = pairs.maxByOrNull { it.value }?.key

        // Both "h-u" and "p-u" occur once, but "u-g" occurs twice.
        assertEquals(Pair("u", "g"), bestPair, "The algorithm failed to identify 'u-g' as the most frequent pair.")
    }

    @Test
    fun testMergeLogic() {
        val originalWord = mutableListOf("a", "b", "c", "a", "b")
        val pairToMerge = Pair("a", "b")
        val newToken = "ab"

        val result = mutableListOf<String>()
        var index = 0
        while (index < originalWord.size) {
            if (index < originalWord.size - 1 && originalWord[index] == pairToMerge.first && originalWord[index+1] == pairToMerge.second) {
                result.add(newToken)
                index += 2
            } else {
                result.add(originalWord[index])
                index++
            }
        }

        val expected = listOf("ab", "c", "ab")
        assertEquals(expected, result, "The merge logic failed to replace all occurrences of the pair.")
    }

    @Test
    fun testTokenization() {
        val corpus = listOf(
            "This is the Course about Full Stack Android AI.",
            "And in this lecture, we want to learn everything about tokenization.",
            "Android is great!",
        )

        val tokenizer = Tokenizer(vocabularySize = 50)
        tokenizer.train(corpus = corpus)

       val result = tokenizer.tokenize(text = "This is a great Android course")
        println("\nFinal Tokens: $result")
    }
}
