package pl.e2d.clientapp.model

import kotlin.Long

data class UserEntity(var id: Long? = null,
                      var firstName: String? = null,
                      var secondName: String? = null,
                      var email: String? = null,
                      var phoneNumber: String? = null
)