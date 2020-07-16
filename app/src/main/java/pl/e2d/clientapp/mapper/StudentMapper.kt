package pl.e2d.clientapp.mapper

import pl.e2d.clientapp.dto.masterDataEntity.StudentDto
import pl.e2d.clientapp.dto.masterDataEntity.UserDto
import pl.e2d.clientapp.model.Student


 fun mapToDto (student:Student): StudentDto {

    return StudentDto (student.id,
               UserDto (student.user?.id,
                                student.user?.firstName,
                                student.user?.secondName,
                                student.user?.email,
                                student.user?.phoneNumber),
                        student.id,
                        student.startEducation.toString(),
                        student.endEducation.toString()
    )
}
