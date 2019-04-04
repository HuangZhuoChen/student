package com.j2ee.student.service.impl;

import java.util.List;

import javax.naming.InitialContext;

import com.j2ee.student.dao.IStudentDao;
import com.j2ee.student.dao.impl.StudentDaoImpl;
import com.j2ee.student.entity.Student;
import com.j2ee.student.service.IStudentService;
import com.j2ee.student.util.Constant;
import com.j2ee.student.vo.PageBean;
import com.j2ee.student.vo.StudentSearchCondition;

public class StudentServiceImpl implements IStudentService {
	private IStudentDao studentDao = new StudentDaoImpl();

	@Override
	public List<Student> findAll() {
		List<Student> list = studentDao.findAll();
		for (Student student : list) {
			
		}
		return studentDao.findAll();
	}

	@Override
	public int add(Student student) {
		//鍒ゆ柇姝ょ敤鎴峰悕鏄惁瀛樺湪锛屽鏋滃瓨鍦ㄥ氨鏄剧ず锛氭鐢ㄦ埛鍚嶅凡缁忓瓨鍦�
		if (studentDao.checkName(student.getName())) {
			return Constant.ADD_NAME_REPEAT;
		} else {//鐢ㄦ埛鍚嶄笉瀛樺湪锛屽彲浠ョ洿鎺ユ坊鍔犲埌鏁版嵁搴�
			// return studentDao.add(student) > 0 ? true : false;
			int count = studentDao.add(student);
			if (count > 0) {
				return Constant.ADD_SUCCESS;
			} 
			return Constant.ADD_FAIL;
		}
	}

	@Override
	public List<Student> showStudentAndBanjiInfo() {
		return studentDao.showStudentAndBanjiInfo();
	}

	@Override
	public List<Student> findByName(String name) {
		return studentDao.findByName(name);
	}

	@Override
	public boolean deleteById(int id) {
		if (studentDao.deleteById(id) > 0) {
			return true;
		} 
		
		return false;
		
		/*if (studentDao.deleteById(id) > 0) {
			return true;
		} else {
			return false;
		}
		*/
		
		/*int count = studentDao.deleteById(id);
		if (count > 0) {
			return true;
		} else {
			return false;
		}*/
	}

	@Override
	public Student findById(int id) {
		return studentDao.findById(id);
	}

	@Override
	public boolean update(Student student) {
		if (studentDao.update(student) > 0) {
			return true;
		} 
		
		return false;
	}

	@Override
public PageBean searchByCondition(StudentSearchCondition studentSearchCondition) {
	PageBean pageBean = new PageBean();
	int pageNo = studentSearchCondition.getPageNo();
	int pageSize = studentSearchCondition.getPageSize();

	pageBean.setPageNo(pageNo);
	
	pageBean.setPageSize(pageSize);

	int totalCount = studentDao.getTotalCount(studentSearchCondition);
	// 涓�鍏辨湁澶氬皯椤� private Integer totalPage;
	int totalPage = (int) Math.ceil((double)totalCount / pageSize);
	pageBean.setTotalPage(totalPage);
	// 褰撳墠椤电殑鏁版嵁 private List<Student> list;
	//SELECT * FROM student LIMIT 3,3;
	//SELECT * FROM student WHERE NAME LIKE '%寮�%' AND age=20 LIMIT 3,3;
	List<Student> list = studentDao.findPageBeanList(studentSearchCondition);
	pageBean.setList(list);
	return pageBean;
}

	@Override
	public PageBean getPageBean(int pageNo, int pageSize) {
		PageBean pageBean = new PageBean();
		// 褰撳墠鏄鍑犻〉 private Integer pageNo;
		pageBean.setPageNo(pageNo);
		// 涓�椤垫湁澶氬皯鏉℃暟鎹� private Integer pageSize;
		pageBean.setPageSize(pageSize);
		// 鎬昏褰曟暟 private Integer totalCount;
		int totalCount = studentDao.getTotalCount();
		// 涓�鍏辨湁澶氬皯椤� private Integer totalPage;
		/**
		 * 鎬绘潯鏁�	姣忛〉鐨勬潯鏁�  	 鎬婚〉鏁�
		 * 10			3		 4
		 * 11			3		 4
		 * 12			3		 4
		 * 13			3		 5
		 */
		int totalPage = (int) Math.ceil((double)totalCount / pageSize);
		pageBean.setTotalPage(totalPage);
		// 褰撳墠椤电殑鏁版嵁 private List<Student> list;
		int offset = (pageNo - 1) * pageSize;
		List<Student> list = studentDao.findPageBeanList(offset, pageSize);
		pageBean.setList(list);
		
		return pageBean;
	}

	@Override
	public boolean checkName(String name) {
		return studentDao.checkName(name);
	}

	@Override
	public boolean deleteAll(String[] ids) {
		//鍦⊿ervice灞傚彲浠or寰幆閬嶅巻璋冪敤sutdentDao.deleteById
		/*for (String id : ids) {
			studentDao.deleteById(Integer.parseInt(id));
		}*/
		
		return studentDao.deleteAll(ids);
	}

}
