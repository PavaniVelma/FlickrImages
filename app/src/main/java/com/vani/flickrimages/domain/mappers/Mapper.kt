package com.vani.flickrimages.domain.mappers

interface Mapper<T: Any, Model : Any> {

    fun toModel(dto: T): Model
    fun fromModel(model: Model): T
}