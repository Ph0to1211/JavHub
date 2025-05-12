package com.jadesoft.javhub.util

import com.jadesoft.javhub.data.db.dto.ActressEntity
import com.jadesoft.javhub.data.db.dto.HistoryEntity
import com.jadesoft.javhub.data.db.dto.MovieEntity
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.data.model.Movie

fun MovieEntity.toModel(): Movie = Movie(
    code = this.code,
    censored = this.censored,
    title = this.title,
    cover = this.cover,
)

fun Movie.toEntity(tag: String, cover: String, createDate: String): MovieEntity = MovieEntity(
    id = 0,
    tag = tag,
    code = this.code,
    title = this.title,
    cover = cover,
    createDate = createDate,
    censored = this.censored
)

fun ActressEntity.toModel(): Actress = Actress(
    code = this.code,
    name = this.name,
    avatar = this.avatar,
    censored = this.censored,
)

fun Actress.toEntity(createDate: String): ActressEntity = ActressEntity(
    id = 0,
    code = this.code,
    name = this.name,
    avatar = this.avatar,
    censored = this.censored,
    createDate = createDate
)

fun HistoryEntity.toModel(): Movie = Movie(
    code = this.code,
    title = this.title,
    cover = this.cover,
    censored = this.censored
)

fun Movie.toHistoryEntity(cover: String, createDate: String): HistoryEntity = HistoryEntity(
    id = 0,
    code = this.code,
    title = this.title,
    cover = cover,
    createDate = createDate,
    censored = this.censored
)