package com.liyang.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.liyang.domain.myinvite.MyInvite;
import com.liyang.domain.myinvite.MyInviteRepository;
import com.liyang.helper.DisplayException;

/**
 * @author Administrator
 *
 */
@Service
public class InviteStringService {
	
	static final String ALPHA[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m"
			,"n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F"
			,"G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y"
			,"Z","1","2","3","4","5","6","7","8","9"};
	
//	@Autowired
//	private MyInviteRepository myInviteRepository;
//	
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
	
//	@PostConstruct
//	public void myInviteInit(){
//	
//		if(myInviteRepository.size() .equals(0) ){
//			System.out.println("正在插入数据，请稍后.....");
//			try{
//				java.util.Set<String> set = new HashSet<String>();
//				for(int i = 0 ; i < 1000 ; i++ ){
//					set.add(randomString());
//				}
//				String sql = "insert into my_invite (id,random_string , tag) values (?,?,?)" ;
//				List<Object[]> batch = new ArrayList<Object[]>();
//				int j = 0;
//				for(String s: set){
//					j++;
//					batch.add(new Object[]{j,s , 0});
//				}
//				jdbcTemplate.batchUpdate(sql , batch);
//				
//			}catch(Exception e){
//				throw new DisplayException("初始化数据库失败");
//			}
//		}		
//	}
	
//	//直接插入
//	public void batchSaveInvite(HashSet<String> set){
//		for(String s : set){
//			MyInvite invite = new MyInvite();
//			invite.setRandomString(s);
//			invite.setTag(0);
//			
////			myInviteRepository.save(invite);
//		}
//	}
	
	
	public StringBuffer randomString(){
		StringBuffer result = new StringBuffer() ;
		for(int i = 0 ; i<6 ; i++){
			result.append(randomChar());
		}
		return result;
	}
	
	public String randomChar(){
		Random random = new Random();
		int i = random.nextInt(61);
		return ALPHA[i];
	}
}
