package com.wwqk.utils;

public class PageTools {

		/**
	    * 返回每页的起始行和结束行
	    */
		public static int[] getRows(int pageNo,int pageSize, int totalCount) {
			if (pageNo <= 1) {
				pageNo = 1;
			}

			int a[] = new int[2];
			try {
				a[0] = (pageNo - 1) * pageSize;
				if (pageNo * pageSize >= totalCount) {
					a[1] = totalCount;
				} else {
					a[1] = pageNo * pageSize;
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
				a[0] = 0;
				a[1] = pageSize - 1;
			}
			return a;
		}
		
		/**
		 * 得到总页数
		 * @param count
		 * @param pageSize
		 * @return
		 */
		public static int getPageCount(int count, int pageSize){
			return count%pageSize==0?count/pageSize:(count/pageSize+1);
		}

	
}
