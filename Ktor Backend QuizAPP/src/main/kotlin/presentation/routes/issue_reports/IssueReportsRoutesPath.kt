package com.example.presentation.routes.issue_reports

import com.example.domain.model.IssueReport
import io.ktor.resources.Resource


@Resource(path = "/report/issues")
class IssueReportsRoutesPath {

    @Resource("{issueId}")
    data class ById(
        val parent: IssueReportsRoutesPath = IssueReportsRoutesPath(),
        val issueId: String
    )
}