package com.example.data.repository

import com.example.data.database.entity.QuizQuestionEntity
import com.example.data.mapper.toQuizQuestion
import com.example.data.mapper.toQuizQuestionEntity
import com.example.data.util.Constant.QUESTION_COLLECTION_NAME
import com.example.domain.model.QuizQuestion
import com.example.domain.repository.QuizQuestionRepository
import com.example.domain.util.DataError
import com.example.domain.util.Result
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class QuizQuestionRepositoryImpl(
    mongoDatabase: MongoDatabase
): QuizQuestionRepository{

    private val questionCollection = mongoDatabase.getCollection<QuizQuestionEntity>(QUESTION_COLLECTION_NAME)

    override suspend fun upsertQuestion(question: QuizQuestion) : Result<Unit, DataError> {
        return try {
            if(question.id == null){
                questionCollection.insertOne(question.toQuizQuestionEntity())
            }else{
                val filterQuery = Filters.eq(QuizQuestionEntity::_id.name, question.id)

                val updateQuery = Updates.combine(
                    Updates.set(QuizQuestionEntity::question.name, question.question),
                    Updates.set(QuizQuestionEntity::correctAnswer.name, question.correctAnswer),
                    Updates.set(QuizQuestionEntity::incorrectAnswers.name, question.incorrectAnswers),
                    Updates.set(QuizQuestionEntity::explanation.name, question.explanation),
                    Updates.set(QuizQuestionEntity::topicCode.name, question.topicCode)
                )
                val updateResult = questionCollection.updateOne(filter = filterQuery, update = updateQuery)
                if(updateResult.modifiedCount == 0L){
                    return Result.Failure(DataError.NotFound)
                }

            }
            Result.Success(Unit)
        }catch (e: Exception){
            e.printStackTrace()
            Result.Failure(DataError.NotFound)
        }
    }

    override suspend fun getAllQuestions(
        topicCode: Int?,
        limit: Int?
    ): Result<List<QuizQuestion>, DataError>{
        return try {
            val questionLimit = limit?.takeIf { it > 0} ?: 10
            val filterQuery = topicCode?.let {
                Filters.eq(QuizQuestionEntity::topicCode.name, it)
            }?: Filters.empty()
            val questions = questionCollection
                .find(filter = filterQuery)
                .limit(questionLimit)
                .map {
                    it.toQuizQuestion()
                }.toList()
            if(questions.isNotEmpty()){
                Result.Success(questions)
            }else{
                Result.Failure(DataError.NotFound)
            }

        }catch (e : Exception){
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    override suspend fun getQuestionById(id: String?): Result<QuizQuestion, DataError> {
        if (id.isNullOrBlank()){
            return Result.Failure(DataError.Validation)
        }
        return try {
            val filterQuery = Filters.eq(
                QuizQuestionEntity::_id.name, id
            )
            val questionEntity = questionCollection
                .find(filterQuery)
                .firstOrNull()
            if(questionEntity != null){
                Result.Success(questionEntity.toQuizQuestion())
            }else{
                Result.Failure(DataError.NotFound)
            }
        }catch (e: Exception){
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    override suspend fun deleteQuestionById(id: String?): Result<Unit, DataError> {
        if (id.isNullOrBlank()){
            return Result.Failure(DataError.Validation)
        }
        return try{
            val filterQuery = Filters.eq(QuizQuestionEntity::_id.name, id)
            val deleteResult = questionCollection.deleteOne(filterQuery)
            if(deleteResult.deletedCount > 0){
                Result.Success(Unit)
            }else{
                Result.Failure(DataError.NotFound)
            }

        }catch (e: Exception){
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }
}