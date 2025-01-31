package com.se2.student_dashboard.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.se2.base.Common.Result
import com.se2.student_dashboard.data.dto.InternshipFormDto
import com.se2.student_dashboard.data.dto.PostInternshipResponseDto
import com.se2.student_dashboard.domain.repository.InternshipRepository

class PostInternshipUseCase (
    private val repository: InternshipRepository
){
    operator fun invoke(internshipFormDto: InternshipFormDto
    ): Flow<Result<Boolean>> = flow {
        try {
            emit(Result.Loading<Boolean>())
            val result = repository.postInternship(internshipFormDto)
            emit(Result.Success<Boolean>(result))
        } catch(e: HttpException) {
            emit(Result.Error<Boolean>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Result.Error<Boolean>(e.toString()))
        }
    }
}