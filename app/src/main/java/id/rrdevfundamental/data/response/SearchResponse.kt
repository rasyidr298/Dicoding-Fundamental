package id.rrdevfundamental.data.response

data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int
)