package biz;

import java.sql.SQLException;

import dao.CaiDao;
import dao.ZanDao;
import biz.BizException;
import util.Utils;

public class CaiBiz {

	private CaiDao zd=new CaiDao();
	public void updatedz(String uname,String singername) throws BizException {
	
		  Utils.checkNull(uname, "用户名不能为空,请先登录"); 
		  Utils.checkNull(singername, "歌手不能空");
		
			/*
			 * if(uname==null || uname.trim().isEmpty()) { 
			 * throw new BizException("用户名不能为空"); 
			 * }
			 */
		try {
			if(zd.getcnt(singername, uname)==1) {
				zd.qxzan(uname,singername);
			}else {
				zd.zanadd(uname, singername);
			}
				
		} catch (SQLException e) {
			throw new BizException("系统繁忙 稍后再试");
		}	
	}
}

