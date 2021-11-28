package com.kh.billida.rentalHistory.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.billida.rentalHistory.model.dto.LockerForLental;
import com.kh.billida.rentalHistory.model.dto.Rental;
import com.kh.billida.rentalHistory.model.dto.ReviewForRentHistory;

@Mapper
public interface RentalRepository {

	@Select("select * from locker where LOCKER_ID=#{lockerId}")
	LockerForLental selectLocker(Long param);
	
	@Insert("insert into rent_history(HISTORY_INDEX, LOCKER_ID, USER_CODE, RENT_START, RENT_END, RENT_COST) "
			+ "values(RENT_HISTORY_INDEX.nextval, #{lockerId}, #{userCode}, #{rentStart}, #{rentEnd}, #{rentCost})")
	void insertRental(Rental rental);
	
	@Select("select * from (select * from review inner join member using(user_code) "
			+ "where locker_id = #{lockerId} and deleteyn = 'N' ORDER BY history_index desc) where ROWNUM <= 5 ")
	List<ReviewForRentHistory> selectReview(Long lockerId);
	
	@Update("update locker set rent_status = 1 where locker_id = #{lockerId}")
	void updateRental(Long lockerId);
	
	@Update("update member set grade = '00' where user_code = #{userCode}")
	void downGradeMember(String userCode);
	
	
}
