package com.matej.roadsurfacetopography.domain.authentication

import com.matej.roadsurfacetopography.common.LogoutSuccess

interface LogoutUseCase {
    fun execute(onSuccess: LogoutSuccess)
}