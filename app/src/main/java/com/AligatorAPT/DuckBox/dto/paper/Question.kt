package com.AligatorAPT.DuckBox.dto.paper

data class Question (
    var type: QuestionType?,
    var question: String,
    var candidates: List<String>?
)

enum class QuestionType {
    MULTI, // [객관식]
    LIKERT, // [선형배율]
}
