package com.kh.billida.member.model.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.billida.member.model.dto.Member;
import com.kh.billida.member.validator.JoinForm;

import lombok.Delegate;

@Mapper
public interface MemberRepository {

	@Insert ("insert into member(user_code,id,password,name,nick,phone,post_code,address,address_detail,email,grade)"
			+ " values(USER_CODE.nextval,#{id},#{password},#{name},#{nick}"
			+ ",#{phone},#{postCode},#{address},#{addressDetail},#{email},'01')")
	void insertMember(JoinForm form);
	
	@Select("select * from member where id = #{id}")
	Member selectMemberById(String id);
	
	@Select("select * from member where nick = #{nick}")
	Member selectMemberByNick(String id);
	
	@Select("select * from member where id = #{id} and password = #{password}")
	Member authenicateUser(Member member);

	@Insert ("insert into member(user_code,id,nick,email,grade,kakaonum)"
			+ " values(USER_CODE.nextval,#{id},'?',#{email},'01',#{kakaonum})")
	void insertkakaoMember(JoinForm form);
	
	@Update("UPDATE member SET name=#{name},nick=#{nick},phone=#{phone},email=#{email},post_code=#{postCode},address=#{address},address_detail=#{addressDetail} where id = #{id}")
	void updateMember(JoinForm form);
	
	@Delete("delete from member where user_code=#{userCode}")
	void deleteMember(String userCode);
}
