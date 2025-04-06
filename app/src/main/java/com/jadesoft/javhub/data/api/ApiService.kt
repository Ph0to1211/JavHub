package com.jadesoft.javhub.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET("{type}/page/{index}")
    suspend fun getAllMovies(
        @Path("type") type: String,
        @Path("index") index: Int,
        @Header("Cookie") cookie: String,
        @Header("Cache-Control") cacheControl: String? = null
    ): Response<String>

    @GET("{type}/actresses/{page}")
    suspend fun getAllActresses(
        @Path("type") type: String,
        @Path("page") page: Int,
        @Header("Cache-Control") cacheControl: String? = null
    ): Response<String>

    @GET("{type}/genre")
    suspend fun getAllGenres(
        @Path("type") type: String,
        @Header("Cache-Control") cacheControl: String? = null
    ): Response<String>

    @GET("{type}/genre/{code}/{page}")
    suspend fun getFilteredMovie(
        @Path("type") type: String,
        @Path("code") code: String,
        @Path("page") page: Int,
        @Header("Cookie") cookie: String,
        @Header("Cache-Control") cacheControl: String? = null
    ): Response<String>

    @GET("{genre}/search/{query}/{page}&type={type}")
    suspend fun search(
        @Path("genre") genre: String,
        @Path("query") query: String,
        @Path("page") page: Int,
        @Path("type") type: Int,
        @Header("Cookie") cookie: String,
        @Header("Cache-Control") cacheControl: String? = null
    ): Response<String>

    @GET("{code}")
    suspend fun getMovie(
        @Path("code") code: String,
        @Header("Cache-Control") cacheControl: String? = null
    ): Response<String>

    @GET("{type}/studio/{code}/{page}")
    suspend fun getPublisher(
        @Path("type") type: String,
        @Path("code") code: String,
        @Path("page") page: Int,
        @Header("Cookie") cookie: String = "existmag=all",
        @Header("Cache-Control") cacheControl: String? = null
    ): Response<String>

    @GET("{type}/label/{code}/{page}")
    suspend fun getProducer(
        @Path("type") type: String,
        @Path("code") code: String,
        @Path("page") page: Int,
        @Header("Cookie") cookie: String = "existmag=all",
        @Header("Cache-Control") cacheControl: String? = null
    ): Response<String>

    @GET("{type}/genre/{code}/{page}")
    suspend fun getGenre(
        @Path("type") type: String,
        @Path("code") code: String,
        @Path("page") page: Int,
        @Header("Cookie") cookie: String = "existmag=all",
        @Header("Cache-Control") cacheControl: String? = null
    ): Response<String>

    @GET("{type}/series/{code}/{page}")
    suspend fun getSeries(
        @Path("type") type: String,
        @Path("code") code: String,
        @Path("page") page: Int,
        @Header("Cookie") cookie: String = "existmag=all",
        @Header("Cache-Control") cacheControl: String? = null
    ): Response<String>

    @GET("{type}/star/{code}/{page}")
    suspend fun getActress(
        @Path("type") type: String,
        @Path("code") code: String,
        @Path("page") page: Int,
        @Header("Cookie") cookie: String = "existmag=all",
        @Header("Cache-Control") cacheControl: String? = null
    ): Response<String>

    @GET("{type}/{genre}/{code}/{page}")
    suspend fun getTypedMovies(
        @Path("type") type: String,
        @Path("genre") genre: String,
        @Path("code") code: String,
        @Path("page") page: Int,
        @Header("Cookie") cookie: String = "existmag=all",
        @Header("Cache-Control") cacheControl: String? = null
    ): Response<String>

}
