package com.matej.roadsurfacetopography.domain.authentication

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.matej.roadsurfacetopography.common.ErrorLambda
import com.matej.roadsurfacetopography.common.SuccessLambda
import com.matej.roadsurfacetopography.model.UserData

interface LoginUseCase {
    fun execute(body: UserData, onSuccess: SuccessLambda<Task<AuthResult>>, onFailure: ErrorLambda<Task<AuthResult>>)
}