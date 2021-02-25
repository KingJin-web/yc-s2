package biz;

import java.sql.SQLException;

import  biz.BizException;
import  util.Utils;
import dao.ScDao;

public class ScBiz {

	private ScDao cd=new ScDao();
	public void addcs(String singername,String songname ,String uname,String bofang_url) throws BizException {
		Utils.checkNull(singername, "歌手名不能为空");
		Utils.checkNull(songname, "歌名不能为空");
		//Utils.checkNull(uname, "用户不能为空");
		if(uname==null || uname.trim().isEmpty()) {
			throw new BizException("别急着收藏 亲先登录一下哦!!!");
		}
		try {
			if(cd.selectcnt(singername, songname, uname)>=1) {
				throw new BizException("亲你已经收藏了哦 快去我的收藏看看吧~~");
			}else {
				cd.insertcs(singername, songname, uname,bofang_url);
			}
		} catch (SQLException e) {
			throw new BizException("系统繁忙 请稍后再试");
		}
		
	}
	
	public void deletesongs(String singername,String songname) throws BizException {
		Utils.checkNull(singername, "歌手名不能为空");
		Utils.checkNull(songname, "歌名不能为空");
		try {
			cd.detelesongs(singername, songname);
		} catch (SQLException e) {
			throw new BizException("系统繁忙 请稍后再试");
		}
		
	}
	
}
