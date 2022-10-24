package com.codesoom.assignment.mapper;

import com.codesoom.assignment.application.dto.UserCommand;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * 유저등록 Dto 객체를 유저등록 커맨드 객체로 변환한다.
     * @param request 유저 Dto
     * @return 유저등록 커맨드
     */
    UserCommand.Register of(UserDto.RegisterParam request);

    /**
     * 유저 ID와 유저수정 Dto 객체를 유저수정 커맨드 객체로 변환한다.
     * @param id 유저 ID
     * @param request 유저수정 Dto
     * @return 유저수정 커맨드
     */
    UserCommand.Update of(Long id, UserDto.UpdateParam request);

    /**
     * 유저등록 커맨드를 유저 엔티티로 변환한다.
     * @param command 유저등록 커맨드
     * @return 유저 엔티티
     */
    @Mapping(target = "id", ignore = true)
    User toEntity(UserCommand.Register command);

    /**
     * 유저수정 커맨드를 유저 엔티티로 변환한다.
     * @param command 유저수정 커맨드
     * @return 유저 엔티티
     */
    @Mapping(target = "email", ignore = true)
    User toEntity(UserCommand.Update command);
}
