package com.technicalassigments.movieapp.domain.mapper

interface Mapper<R, M> {
    fun mapFrom(response: R): M
}