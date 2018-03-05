package com.liyang.domain.claimPolicyRewards;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.jpa.query.expression.generic.SpecificationQueryRepository;
import com.liyang.domain.base.AuditorEntityRepository;

/**
 * @author Administrator
 *
 */
public interface ClaimPolicyRewardRepository extends AuditorEntityRepository<ClaimPolicyReward>, JpaSpecificationExecutor<ClaimPolicyReward>, SpecificationQueryRepository<ClaimPolicyReward>{
	
//	@RestResource(path="findByFlag")
//	public Page<ClaimPolicyReward> findByReturnVehicleTaxFeeCheckFlagAndAdditionalFeeCheckFlag(@Param("vehicleFlag")Boolean returnVehicleTaxFeeCheckFlag, @Param("additionalFlag")Boolean additionalFeeCheckFlag, Pageable p);
	
	@RestResource(path="findUnchecked")
	public Page<ClaimPolicyReward> findByReturnVehicleTaxFeeCheckFlagIsFalseOrAdditionalFeeCheckFlagIsFalse(Pageable p);
	
	@RestResource(path="findUnclaimed")
	public Page<ClaimPolicyReward> findByReturnVehicleTaxFeeCheckFlagIsTrueAndAdditionalFeeCheckFlagIsTrueAndClaimFlagIsFalseAndClaimCheckFlagIsFalseAndStateStateCode(@Param("stateCode")String stateCode, Pageable p);

	@RestResource(path="findClaimed")
	public Page<ClaimPolicyReward> findByClaimFlagIsTrueAndClaimCheckFlagIsFalse(Pageable p);
	
	@RestResource(path="findClaimChecked")
	public Page<ClaimPolicyReward> findByClaimCheckFlagIsTrue(Pageable p);
	
	@RestResource(path="findDepartmentClaimed")
	public Page<ClaimPolicyReward> findByDepartmentIdAndClaimCheckFlagIsTrue(@Param("departmentId")Integer departmentId, Pageable pageable);
	
	@RestResource(path="findVehicleFalse")
	public Page<ClaimPolicyReward> findByReturnVehicleTaxFeeCheckFlagIsFalseAndStateStateCode(@Param("stateCode") String stateCode, Pageable p);
	
	@RestResource(path="findVehicleChecked")
	@Query("select c from ClaimPolicyReward c where c.insuraceType != '商业险' and c.returnVehicleTaxFeeCheckFlag=true")
	public Page<ClaimPolicyReward> findByInsuranceTypeIs(Pageable p);
	
	@RestResource(path="findAdditionalFalse")
	public Page<ClaimPolicyReward> findByAdditionalFeeCheckFlagIsFalseAndStateStateCode(@Param("stateCode")String stateCode, Pageable p);
	
	public ClaimPolicyReward findByPolicyNumberAndReceivedBrokerFee(@Param("policyNumber")String policyNumber, @Param("receivedBrokerFee")Double receivedBrokerFee);
	
	@Query("select sum(c.receivedBrokerFee + c.returnVehicleTaxFee + c.additionalFee) from ClaimPolicyReward c where c.claimCheckFlag=true")
	public Double findHeadquartersAccumulatedIncome();
	
	@Query("select sum(c.receivedBrokerFee + c.returnVehicleTaxFee + c.additionalFee) from ClaimPolicyReward c where c.claimTime between ?1 and ?2 and c.claimCheckFlag=true")
	public Double findHeadquartersAccumulatedIncomeBetween(Date claimBegin, Date claimEnd);
	
	@Query("select sum(c.profit) from ClaimPolicyReward c where c.claimCheckFlag = true")
	public Double findHeadquartersAccumulatedProfit();
	
	@Query("select sum(c.profit) from ClaimPolicyReward c where c.claimTime between ?1 and ?2 and c.claimCheckFlag=true")
	public Double findHeadquartersAccumulatedProfitBetween(Date claimBegin, Date claimEnd);
	
	@Query("select sum(c.vehicleCommission + c.insuranceCommission) from ClaimPolicyReward c where c.department.id=?1 and c.claimCheckFlag=true")
	public Double findDepartmentAccumulatedCommission(Integer departmentId);
	
	@RestResource(path="departmentCommissionBetween")
	@Query("select sum(c.vehicleCommission + c.insuranceCommission) from ClaimPolicyReward c where c.department.id=?1 and c.claimTime between ?2 and ?3 and c.claimCheckFlag=true")
	public Double findDepartmentAccumulatedCommissionBetween(Integer departmentId, Date claimBegin, Date claimEnd);

	public List<ClaimPolicyReward> findByInsuraceCompanyAndCarLicenseAndInsuraceTypeContainsAndAdditionalFeeCheckFlagIsTrueAndClaimFlagIsFalse(String insuranceCompany, String carLicense, String string);

	public List<ClaimPolicyReward> findByInsuraceCompanyAndCarLicenseAndInsuraceTypeContainsAndReturnVehicleTaxFeeCheckFlagIsTrueAndClaimFlagIsFalse(String insuranceCompany, String carLicense, String string);
	@Query("update ClaimPolicyReward c set c.salesman.name=?2 where c.id=?1")
	public void updateSalesman(Integer id,String name);
	
	
//	@Query("select sum(c.vehicleCommission + c.insuranceCommission) from ClaimPolicyReward c where c.department.id=?1 and c.claimTime between ?2 and ?3")
//	public Double findDepartmentAccumulatedCommissionBetween(Integer departmentId, Date claimBegin, Date claimEnd);
	
	
//	TODO  rest-- OKException???
//	@Query("select c.department.label,sum(c.profit) from ClaimPolicyReward c where c.claimFlag=true group by c.department")
//	public List<?> findDepartmentProfit();
	
	
//	@Query("select sum(c.profit) from ClaimPolicyReward c where c.department.id=?1")
//	public Double findProfitByDepartmentId(@Param("departmentId")Integer departmentId);
}
