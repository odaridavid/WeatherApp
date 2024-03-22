package com.github.odaridavid.weatherapp.data.ai

import com.github.odaridavid.weatherapp.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class OutfitRecommendationRecommenderGemini @Inject constructor() {

    private val outfitsModel by lazy {
        GenerativeModel(
            modelName = "gemini-1.0-pro",
            apiKey = BuildConfig.VERTEX_AI_API_KEY
        )
    }

    suspend fun generateOutfitRecommendation(temperature: String): Flow<String?> {
        // TODO implement for language changes
        val prompt = "What would be a nice outfit for $temperature weather?"
        return flowOf(outfitsModel.generateContent(prompt).text)
    }


}

