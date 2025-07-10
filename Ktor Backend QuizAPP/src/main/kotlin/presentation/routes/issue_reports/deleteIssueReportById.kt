package com.example.presentation.routes.issue_reports

import com.example.domain.repository.IssueReportRepository
import com.example.domain.util.onFailure
import com.example.domain.util.onSuccess
import com.example.presentation.util.respondWithError
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.delete
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

fun Route.deleteIssueReportById(
    repository: IssueReportRepository
){
    delete<IssueReportsRoutesPath.ById> { path ->
        repository.deleteIssueReportById(path.issueId)
            .onSuccess {
                call.respond(HttpStatusCode.NoContent)
            }.onFailure {error ->
                respondWithError(error)
            }
    }
}