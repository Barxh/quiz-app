package com.example.presentation.routes.issue_reports

import com.example.domain.repository.IssueReportRepository
import com.example.domain.util.onFailure
import com.example.domain.util.onSuccess
import com.example.presentation.util.respondWithError
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

fun Route.getAllIssueReports(
    repository: IssueReportRepository){
    get<IssueReportsRoutesPath>{
        repository.getAllIssueReports()
            .onSuccess { reports ->
                call.respond(
                    message = reports,
                    status = HttpStatusCode.OK
                )

            }.onFailure { error ->
                respondWithError(error)
            }

    }

}