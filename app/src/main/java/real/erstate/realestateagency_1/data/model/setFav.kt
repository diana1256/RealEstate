package real.erstate.realestateagency_1.data.model


data class Response(
    val count: Int,
    val next: String?, // Replace String with the appropriate type for the 'next' field (e.g., URL or custom data class)
    val previous: String?, // Replace String with the appropriate type for the 'previous' field (e.g., URL or custom data class)
    val results: List<ResultFav>
)

data class ResultFav(
    val id: Int,
    val apartment: Apartment,
    val user: User,
    val created_at: String // Replace String with the appropriate type for the 'created_at' field (e.g., Date or LocalDateTime)
)

data class ApartmentFav(
    val id: Int,
    val type: Type,
    val floor: Floor,
    val document: Document,
    val series: Series,
    val region: Region,
    val currency: Currency,
    val apartment_images: List<ApartmentImage>,
    val author: Author,
    val title: String,
    val square: String,
    val address: String,
    val communications: String,
    val description: String,
    val best: Boolean,
    val price: String,
    val room_count: String,
    val lat: String,
    val lng: String,
    val created_at: String // Replace String with the appropriate type for the 'created_at' field (e.g., Date or LocalDateTime)
)

data class Type(
    val id: Int,
    val title: String,
    val created_at: String // Replace String with the appropriate type for the 'created_at' field (e.g., Date or LocalDateTime)
)

data class FloorFav(
    val id: Int,
    val title: String,
    val created_at: String // Replace String with the appropriate type for the 'created_at' field (e.g., Date or LocalDateTime)
)

data class DocumentFav(
    val id: Int,
    val title: String,
    val created_at: String // Replace String with the appropriate type for the 'created_at' field (e.g., Date or LocalDateTime)
)

data class SeriesFav(
    val id: Int,
    val title: String,
    val created_at: String // Replace String with the appropriate type for the 'created_at' field (e.g., Date or LocalDateTime)
)

data class RegionFav(
    val id: Int,
    val name: String,
    val created_at: String // Replace String with the appropriate type for the 'created_at' field (e.g., Date or LocalDateTime)
)

data class CurrencyFav(
    val id: Int,
    val symbol: String,
    val name: String,
    val created_at: String // Replace String with the appropriate type for the 'created_at' field (e.g., Date or LocalDateTime)
)

data class ApartmentImageFav(
    val id: Int,
    val image: String,
    val created_at: String, // Replace String with the appropriate type for the 'created_at' field (e.g., Date or LocalDateTime)
    val apartment: Int
)

data class Author(
    val id: Int,
    val user_images: List<UserImage>,
    val last_login: String?, // Replace String with the appropriate type for the 'last_login' field (e.g., Date or LocalDateTime)
    val is_superuser: Boolean,
    val login: String,
    val first_name: String,
    val last_name: String,
    val middle_name: String?, // Replace String with the appropriate type for the 'middle_name' field (e.g., String or null)
    val created_at: String, // Replace String with the appropriate type for the 'created_at' field (e.g., Date or LocalDateTime)
    val is_staff: Boolean,
    val is_active: Boolean
)

data class UserImage(
    val id: Int,
    val image: String,
    val is_main: Boolean,
    val created_at: String, // Replace String with the appropriate type for the 'created_at' field (e.g., Date or LocalDateTime)
    val user: Int
)

data class UserFav(
    val id: Int,
    val user_images: List<UserImage>,
    val last_login: String?, // Replace String with the appropriate type for the 'last_login' field (e.g., Date or LocalDateTime)
    val is_superuser: Boolean,
    val login: String,
    val first_name: String,
    val last_name: String,
    val middle_name: String?, // Replace String with the appropriate type for the 'middle_name' field (e.g., String or null)
    val created_at: String, // Replace String with the appropriate type for the 'created_at' field (e.g., Date or LocalDateTime)
    val is_staff: Boolean,
    val is_active: Boolean
)

data class ApiResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ApartmentA>
)

data class ApartmentA(
    val id: String,
    val type: Type,
    val floor: Floor,
    val document: Document,
    val series: Series,
    val region: Region,
    val currency: Currency,
    val apartment_images: List<ApartmentImage>,
    val author: Author,
    val title: String,
    val square: String,
    val address: String,
    val communications: String,
    val description: String,
    val best: Boolean,
    val price: String,
    val room_count: String,
    val lat: String,
    val lng: String,
    val created_at: String
)

data class TypeA(
    val id: Int,
    val title: String,
    val created_at: String
)

data class FloorA(
    val id: Int,
    val title: String,
    val created_at: String
)

data class DocumentA(
    val id: Int,
    val title: String,
    val created_at: String
)

data class SeriesA(
    val id: Int,
    val title: String,
    val created_at: String
)

data class RegionA(
    val id: Int,
    val name: String,
    val created_at: String
)

data class CurrencyA(
    val id: Int,
    val symbol: String,
    val name: String,
    val created_at: String
)

data class ApartmentImageA(
    val id: Int,
    val image: String,
    val created_at: String,
    val apartment: Int
)

data class AuthorA(
    val id: Int,
    val user_images: List<UserImage>,
    val last_login: String,
    val is_superuser: Boolean,
    val login: String,
    val first_name: String,
    val last_name: String,
    val middle_name: String?,
    val created_at: String,
    val is_staff: Boolean,
    val is_active: Boolean
)

data class UserImageA(
    val id: Int,
    val image: String,
    val is_main: Boolean,
    val created_at: String,
    val user: Int
)

data class admin(
    val login: String,
    val first_name: String,
    val last_name: String,
    val middle_name: String,
    val is_active: Boolean,
    val is_superuser: Boolean
)