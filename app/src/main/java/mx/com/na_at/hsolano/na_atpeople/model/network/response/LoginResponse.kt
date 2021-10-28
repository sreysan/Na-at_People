package mx.com.na_at.hsolano.na_atpeople.model.network.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val email: String,
    val name: String,
    val lastName: String,
    val id: String
)
