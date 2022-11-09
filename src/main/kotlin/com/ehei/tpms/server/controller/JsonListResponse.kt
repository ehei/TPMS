import com.ehei.tpms.server.model.Term

class JsonListResponse(
    val content: List<Term>,
    val totalElements: Int
)