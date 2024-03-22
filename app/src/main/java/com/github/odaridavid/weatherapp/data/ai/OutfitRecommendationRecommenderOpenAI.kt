package com.github.odaridavid.weatherapp.data.ai


import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.github.odaridavid.weatherapp.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class OutfitRecommendationRecommenderOpenAI @Inject constructor() {

    private val openAi by lazy {
        OpenAI(token = BuildConfig.OPEN_AI_KEY)
    }

    suspend fun generateOutfitRecommendation(temperature: String): Flow<String?> {
        // TODO implement for language changes
        val prompt = "What would be a nice outfit for $temperature weather?"
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo"),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.Assistant,
                    content = prompt
                )
            )
        )
        val completion = openAi.chatCompletion(chatCompletionRequest)

        val response = completion.choices.first().message.content
        return flowOf(response)
    }

}
