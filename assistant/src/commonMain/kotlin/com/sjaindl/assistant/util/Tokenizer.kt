package com.sjaindl.assistant.util

class Tokenizer(
    val vocabularySize: Int,
) {
    var vocabulary = mutableListOf<String>()

    val merges = mutableMapOf<Pair<String, String>, String>()

    private val preTokenizerRegex = Regex("""\w+|[^\w\s]""")

    fun train(corpus: List<String>) {
        bytePairEncoding(corpus = corpus)
        println("--- Training Complete ---\n")
    }

    fun tokenize(text: String): List<String> {
        val words = normalize(listOf(text))
        val pretokenized = pretokenize(normalizedCorpus = words)

        var currentSplits = pretokenized.map { word ->
            word.map {
                it.toString()
            }.toMutableList()
        }

        println("Tokenizing input: '$text'")
        for (pair in merges.keys) {
            currentSplits = currentSplits.map { split ->
                findNewSplit(split = split, firstWord = pair.first, secondWord = pair.second)
            }
        }

        return currentSplits.flatten()
    }

    private fun bytePairEncoding(corpus: List<String>) {
        // 1. normalization
        val normalizedCorpus = normalize(corpus = corpus)
        println("Normalized Corpus: $normalizedCorpus\n")

        // 2. Pre-tokenization
        val pretokenized = pretokenize(normalizedCorpus = normalizedCorpus)
        println("pretokenized: $pretokenized\n")

        // 3. Initial Alphabet (= unique chars)
        val alphabet = pretokenized
            .flatMap {
                it.toList()
            }
            .map {
                it.toString()
            }
            .distinct()
            .sorted()

        vocabulary.addAll(elements = alphabet)
        println("Initial Vocabulary: $alphabet\n")

        var splits = pretokenized.associateWith { word ->
            word.map {
                it.toString()
            }.toList()
        }.toMutableMap()
        println("Initial splits: $splits\n")

        // 4. Training Loop
        println("--- Starting Training Merges ---")

        val frequencies = mutableMapOf<String, Int>()
        for (match in pretokenized) {
            frequencies[match] = (frequencies[match] ?: 0) + 1
        }
        println("Word Frequencies: $frequencies\n")

        while (vocabulary.size < vocabularySize) {
            val pairFrequencies = computePairFrequencies(splits = splits, wordFrequencies = frequencies)
            println("Pair Frequencies: $pairFrequencies\n")

            // Find best pair with max frequency
            val bestPairEntry = pairFrequencies.maxByOrNull {
                it.value
            } ?: break
            val bestPair = bestPairEntry.key
            val maxFrequency = bestPairEntry.value

            println("Best pair found: $bestPair with frequency $maxFrequency")

            splits = mergePair(firstWord = bestPair.first, secondWord = bestPair.second, splits = splits)

            val newToken = bestPair.first + bestPair.second
            merges[bestPair] = newToken
            vocabulary.add(newToken)

            println("Adding merge: $newToken")
            println("New vocabulary: $vocabulary")
            println("New merges: $merges")
        }
    }

    private fun normalize(corpus: List<String>): List<String> {
        return corpus.map {
            it.trim().lowercase()
        }
    }

    private fun pretokenize(
        normalizedCorpus: List<String>,
    ): List<String> {
        return normalizedCorpus.flatMap {
            preTokenizerRegex.findAll(input = it).map { matchResult ->
                matchResult.value
            }
        }
    }

    private fun computePairFrequencies(
        splits: Map<String, List<String>>,
        wordFrequencies: Map<String, Int>,
    ): Map<Pair<String, String>, Int> {
        val pairFrequencies = mutableMapOf<Pair<String, String>, Int>()

        for ((word, frequency) in wordFrequencies) {
            val split = splits[word] ?: continue
            if (split.size < 2) continue

            for (index in 0 until split.size - 1) {
                val pair = Pair(split[index], split[index + 1])
                pairFrequencies[pair] = (pairFrequencies[pair] ?: 0) + frequency
            }
        }

        return pairFrequencies
    }

    private fun mergePair(
        firstWord: String,
        secondWord: String,
        splits: MutableMap<String, List<String>>,
    ): MutableMap<String, List<String>> {
        for (word in splits.keys) {
            val split = splits[word] ?: emptyList()
            val newSplit = findNewSplit(split = split, firstWord = firstWord, secondWord = secondWord)
            splits[word] = newSplit
        }
        return splits
    }

    private fun findNewSplit(
        split: List<String>,
        firstWord: String,
        secondWord: String,
    ): MutableList<String> {
        val newSplit = mutableListOf<String>()
        var index = 0

        while (index < split.size) {
            if (index < split.size - 1 && split[index] == firstWord && split[index + 1] == secondWord) {
                newSplit.add(firstWord + secondWord)
                index += 2
            } else {
                newSplit.add(split[index])
                index += 1
            }
        }

        return newSplit
    }
}
