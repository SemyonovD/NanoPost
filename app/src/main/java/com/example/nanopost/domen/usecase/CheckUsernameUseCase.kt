package com.example.nanopost.domen.usecase

import com.example.nanopost.data.repository.LoginRepository
import javax.inject.Inject

class CheckUsernameUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(username: String):String {
        return loginRepository.checkUsername(username)
    }
}