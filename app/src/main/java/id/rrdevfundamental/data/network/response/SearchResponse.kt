package id.rrdevfundamental.data.network.response

data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int
)