import java.io.Serializable

data class InternshipResponse(
    val data: List<Internship>,
    val hasNextPage: Boolean
) : Serializable

data class Internship(
    val company: Company,
    val optionalSkills: String? = null,
    val requiredSkills: String?,
    val endDate: String?, // Can be converted to a Date type if needed
    val startDate: String?, // Can be converted to a Date type if needed
    val duration: String?,
    val salary: String?,
    val type: String?,
    val description: String?,
    val title: String?,
    val id: String?,
    val createdAt: String?, // Use LocalDateTime if needed
    val updatedAt: String?, // Use LocalDateTime if needed
    val matchPercentage: Int? = null
) : Serializable

data class Company(
    val image: String? = null, // Assuming null if no image URL
    val id: Int?,
    val fullName: String?,
    val role: Role?,
    val status: InternshipStatus?,
    val createdAt: String?, // Use LocalDateTime if needed
    val updatedAt: String?, // Use LocalDateTime if needed
    val deletedAt: String? = null, // Use LocalDateTime if needed
    val gender: String? = null, // Could also be an Enum if predefined values exist
    val phoneNumber: String? = null,
    val aboutYou: String? = null,
    val address: String,
    val university: String? = null, // Assuming it could be null
    val personalEmail: String? = null,
    val attributes: String? = null, // Assuming this could contain a JSON string or null
    val matchPercentage: Int? = null
) : Serializable

data class Role(
    val id: Int,
    val name: String,
    val entity: String // Represents "__entity"
) : Serializable

data class InternshipStatus(
    val id: Int,
    val name: String,
    val entity: String // Represents "__entity"
) : Serializable
