package com.liyang.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.liyang.domain.banner.Banner;
import com.liyang.domain.banner.BannerAct;
import com.liyang.domain.banner.BannerActRepository;
import com.liyang.domain.banner.BannerForSearch;
import com.liyang.domain.banner.BannerForUpdate;
import com.liyang.domain.banner.BannerLog;
import com.liyang.domain.banner.BannerLogRepository;
import com.liyang.domain.banner.BannerRepository;
import com.liyang.domain.banner.BannerState;
import com.liyang.domain.banner.BannerStateRepository;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@Service
public class BannerService extends AbstractAuditorService<Banner, BannerState, BannerAct, BannerLog> {
	@Autowired
	private BannerRepository bannerRepository;
	@Autowired
	private BannerActRepository actRepository;
	@Autowired
	private BannerLogRepository logRepository;
	@Autowired
	private BannerStateRepository stateRepository;

	@Override
	@PostConstruct
	public void sqlInit() {
		List<BannerAct> list = actRepository.findAll();
		// 初始化
		if (list == null || list.size() == 0) {
			BannerAct save1 = actRepository.save(new BannerAct("创建", "create", 10, ActGroup.UPDATE));
			BannerAct save2 = actRepository.save(new BannerAct("读取", "read", 20, ActGroup.READ));
			BannerAct save3 = actRepository.save(new BannerAct("更新", "update", 30, ActGroup.UPDATE));
			BannerAct save4 = actRepository.save(new BannerAct("删除", "delete", 40, ActGroup.UPDATE));

			BannerState stateOnshelf = new BannerState("上架", 0, "ONSHELF");
			stateOnshelf.setActs(Arrays.asList(save1, save2, save3, save4).stream().collect(Collectors.toSet()));
			stateRepository.save(stateOnshelf);
			BannerState stateOffshelf = new BannerState("下架", 100, "OFFSHELF");
			stateOffshelf.setActs(Arrays.asList(save1, save2, save3, save4).stream().collect(Collectors.toSet()));
			stateRepository.save(stateOffshelf);
		}
		// 新增状态 删除
		if (stateRepository.findByStateCode("DELETED") == null) {
			stateRepository.save(new BannerState("已删除", 200, "DELETED"));
		}

	}

	@Override
	public LogRepository<BannerLog> getLogRepository() {
		return logRepository;
	}

	@Override
	public BannerLog getLogInstance() {
		return new BannerLog();
	}

	@Override
	public AuditorEntityRepository<Banner> getAuditorEntityRepository() {
		return bannerRepository;
	}

	@Override
	public ActRepository<BannerAct> getActRepository() {
		return actRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new Banner().setService(this);
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Banner().setLogRepository(logRepository);
	}

	@Transactional(rollbackFor = Exception.class)
	public void save(Banner banner) {
		if (banner == null || StringUtils.isEmpty(banner.getName()) || StringUtils.isEmpty(banner.getImgURL())) {
			throw new FailReturnObject(ExceptionResultEnum.BANNER_DATA_ERROR);
		}
		BannerState state = stateRepository.findByStateCode("OFFSHELF");
		banner.setState(state);
		bannerRepository.save(banner);
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateOnshelf(Banner banner) {
		// 上架量限制
		if (bannerRepository.findAllEnabled().size() >= Banner.ONSHELF_MAX) {
			throw new FailReturnObject(ExceptionResultEnum.BANNER_ONSHELF_NUMBER_ERROR);
		}
		BannerState stateOn = stateRepository.findByStateCode("ONSHELF");
		banner.setState(stateOn);
		// 上架图片权重设置最低
		banner.setWeight(Banner.WEIGHT_MAX);
		bannerRepository.save(banner);
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateOffshelf(Banner banner) {
		BannerState stateOff = stateRepository.findByStateCode("OFFSHELF");
		banner.setState(stateOff);
		// 下架图片权重设置为空
		banner.setWeight(null);
		banner = bannerRepository.save(banner);
	}

	public List<Banner> findAllEnabled() {
		List<Banner> banners = bannerRepository.findAllEnabled();
		return banners;
	}

	public Page<Banner> search(final BannerForSearch search, Pageable pageable) {
		Page<Banner> result = bannerRepository.findAll(new Specification<Banner>() {

			@Override
			public Predicate toPredicate(Root<Banner> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (search != null) {
					if (!StringUtils.isEmpty(search.getName())) {
						Predicate nameLike = cb.like(root.<String>get("name"), "%" + search.getName() + "%");
						predicates.add(nameLike);
					}
					if (!StringUtils.isEmpty(search.getStateCode())) {
						Predicate stateCodeEQ = cb.equal(root.get("state").get("stateCode"), search.getStateCode());
						predicates.add(stateCodeEQ);
					} else {
						Predicate stateCodeEQ1 = cb.equal(root.get("state").get("stateCode"), "ONSHELF");
						Predicate stateCodeEQ2 = cb.equal(root.get("state").get("stateCode"), "OFFSHELF");
						Predicate stateCodeOr = cb.or(stateCodeEQ1, stateCodeEQ2);
						predicates.add(stateCodeOr);
					}
				}
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateName(Banner banner ,BannerForUpdate forUpdate) {
		if (!StringUtils.isEmpty(forUpdate.getName())) {
			banner.setName(forUpdate.getName());
		}
		bannerRepository.save(banner);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Banner banner) {
		BannerState stateDelete = stateRepository.findByStateCode("DELETED");
		banner.setState(stateDelete);
		bannerRepository.save(banner);
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateWeight(Banner banner, Integer weight) {
		banner.setWeight(weight);
		bannerRepository.save(banner);
	}

	public Banner findById(Integer id) {
		return bannerRepository.findById(id);
	}

}
