package com.example.votingapplication.services.api

import com.example.votingapplication.entity.CustomResponse
import com.example.votingapplication.entity.Party
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface partyAPI {
    @GET("/parties")
    suspend fun getParties() : Response<List<Party>>

    @POST("/party")
    suspend fun addParty(@Body party : Party) : Response<CustomResponse>

    @GET("/user/vote/{userId}/{partyId}")
    suspend fun vote(@Path("userId") userId : Long, @Path("partyId") partyId : Long) : Response<CustomResponse>

    @DELETE("/party/{id}")
    suspend fun deleteParty(@Path("id") partyId : Long) : Response<CustomResponse>

    @GET("/voting/status")
    suspend fun getVotingStatus() : Response<CustomResponse>

    @POST("/voting/status/{status}")
    suspend fun endVoting(@Path("status") status : String) : Response<CustomResponse>
}