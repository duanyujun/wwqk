package com.wwqk.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
 
/**
 * IP工具类
 */
public class IPUtil {
 
    /**
     * @param 获取远程IP
     * @return IP Address
     */
    public static String getIpAddrByRequest(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
     
    /**
     * @return 获取本机IP
     * @throws SocketException
     */
    public static String getRealIp() throws SocketException {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
 
        Enumeration<NetworkInterface> netInterfaces = 
            NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress() 
                        && !ip.isLoopbackAddress() 
                        && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress() 
                        && !ip.isLoopbackAddress() 
                        && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }
     
        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }
    
    
    public static final int IP_LENGTH_ERROR = -3;
    public static final int IS_BETWEEN = 0;
    public static final int IS_NOT_BETWEEN = 1;

	// judge the specified ip is between two ip addresses.
	public static boolean isBetween(String ip, String begin, String end) throws UnknownHostException {

		if (!IPUtil.isValidIP(begin))
			throw new UnknownHostException("ip begin :" + begin);
		if (!IPUtil.isValidIP(end))
			throw new UnknownHostException("ip end :" + end);
		if (!IPUtil.isValidIP(ip))
			throw new UnknownHostException("ip :" + ip);

		if (ip.compareToIgnoreCase(begin) == 0 || ip.compareToIgnoreCase(end) == 0)
			return true;

		// change dot to :
		// String strIP = IPUtil.replaceDot(ip, ":");
		// String strIPBegin = IPUtil.replaceDot(begin, ":");
		// String strIPEnd = IPUtil.replaceDot(end, ":");

		// get the ip part array .byte type is enough.
		String ipArray[] = ip.split("//.");
		String startArray[] = begin.split("//.");
		String endArray[] = end.split("//.");

		long ipLong = ((long) ((((Integer.parseInt(ipArray[0]) << 8) + Integer.parseInt(ipArray[1])) << 8) +

				Integer.parseInt(ipArray[2])) << 8) + Integer.parseInt(ipArray[3]);

		long startLong = ((long) ((((Integer.parseInt(startArray[0]) << 8) + Integer.parseInt(startArray[1])) << 8) +

				Integer.parseInt(startArray[2])) << 8) + Integer.parseInt(startArray[3]);

		long endLong = ((long) ((((Integer.parseInt(endArray[0]) << 8) + Integer.parseInt(endArray[1])) << 8) +

				Integer.parseInt(endArray[2])) << 8) + Integer.parseInt(endArray[3]);

		if (startLong < endLong)
			return ipLong > startLong && ipLong < endLong;
		else
			return ipLong > endLong && ipLong < startLong;
	}

	public static boolean isInTheSameSubnet(String strNetmask, String ip1, String ip2) throws UnknownHostException {

		if (!IPUtil.isValidNetmask(strNetmask))
			throw new UnknownHostException(strNetmask);

		if (!IPUtil.isValidIP(ip1))
			throw new UnknownHostException(ip1);

		if (!IPUtil.isValidIP(ip2))
			throw new UnknownHostException(ip2);
		// return false;

		byte[] ba_netmask = InetAddress.getByName(strNetmask).getAddress();
		byte[] ba_ip1 = InetAddress.getByName(ip1).getAddress();
		byte[] ba_ip2 = InetAddress.getByName(ip2).getAddress();

		for (int i = 0; i < 4; ++i) {
			if ((ba_netmask[i] & ba_ip1[i]) != (ba_netmask[i] & ba_ip2[i]))
				return false;
		}
		return true;
	}

	public static boolean isValidNetmask(String inString) {
		if (inString == null)
			return false;

		int[] iValildPart = { 0, 128, 192, 224, 240, 248, 252, 254, 255 };

		// String ipString = replaceDot(inString, ":");

		try {
			String partArray[] = inString.split("//.");

			if (partArray.length != 4)
				return false;

			int[] ipPart = new int[4];

			for (int i = 0; i < 4; ++i)
				ipPart[i] = Integer.parseInt(partArray[i]);

			// every part should be greater than 255 and less than 255
			for (int i = 0; i < 4; ++i)
				if (ipPart[i] < 0 || ipPart[i] > 255)
					return false;

			// the priv part should be 255
			for (int i = 0; i < 3; ++i)
				if (ipPart[i + 1] != 0 && ipPart[i] != 255)
					return false;

			for (int i = 0; i < 4; ++i) {
				boolean bValidCheck = false;
				for (int j = 0; j < iValildPart.length; ++j) {
					if (ipPart[i] == iValildPart[j]) {
						bValidCheck = true;
						break;
					}
				}
				if (bValidCheck == false)
					return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isValidIP(String str) {
		if (str == null)
			return false;
		str = str.trim();
		int pos1 = -1;
		int pos2 = -1;
		String str_temp = "";
		boolean have_err = false;
		int i = 0;

		// 循环开始处
		while (pos1 < str.length()) {
			pos2 = pos1;
			pos1 = str.indexOf('.', pos2 + 1);
			if (pos1 == -1)
				pos1 = str.length();
			str_temp = str.substring(pos2 + 1, pos1);
			i++;
			if (i > 4) {
				have_err = true;
				break;
			}
			try {
				int a = Integer.parseInt(str_temp);
				if (a < 0 || a > 255) {
					have_err = true;
					break;
				}
			} catch (NumberFormatException nfe) {
				have_err = true;
				break;
			}
		}

		if (have_err || i != 4) {
			return false;
		}
		return true;
	}

	public static String replaceDot(String str, String rep) {
		if (str == null)
			return null;
		StringBuffer strRet = new StringBuffer();
		for (int i = 0; i < str.length(); ++i) {
			if (str.toCharArray()[i] == '.')
				strRet.append(rep);
			else
				strRet.append(str.toCharArray()[i]);
		}
		return strRet.toString();
	}

	/**
	 * change ip-string-formatted address into byte array
	 * 
	 * @param str
	 *            ip adress
	 * @return byte array ip adress
	 * @throws java.lang.IllegalArgumentException
	 */
	public static byte[] strAddressToByte(String str) throws IllegalArgumentException {
		if (str == null)
			return null;

		StringTokenizer token = new StringTokenizer(str, ".");
		if (token != null && token.countTokens() == 4) {
			byte[] newAdd = new byte[4];
			for (int i = 0; i < 4; i++) {
				String s = token.nextToken();
				try {
					newAdd[i] = (byte) Integer.parseInt(s);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("The input string can't be store into byte array.");
				}
			}
			return newAdd;
		}
		return null;
	}

	public static String getNextIp(String ipBegin, String ipEnd, String thisIp) {
		byte[] byteIp = null;
		if (thisIp == null || thisIp.equalsIgnoreCase(ipEnd))
			return ipBegin;

		try {
			byteIp = (InetAddress.getByName(thisIp)).getAddress();
		} catch (UnknownHostException ex) {
			return ipBegin;
		}
		for (int i = byteIp.length - 1; i >= 0; --i) {
			byteIp[i] += 1;

			if (byteIp[i] == 0)
				continue;
			else
				break;
		}

		try {
			String strIp = InetAddress.getByAddress(byteIp).getHostAddress();
			if (isBetween(strIp, ipBegin, ipEnd))
				return strIp;

		} catch (UnknownHostException ex1) {
			return null;
		}
		return null;
	}

	public static boolean isBroadcastAddress(String ip) {
		if (ip.endsWith(".255"))
			return true;
		return false;
	}

	public static void main(String args[]) {
		String strNetmask = new String("192.168.4.251");
		System.out.println(strNetmask + " is valid netmask :" + IPUtil.isValidNetmask(strNetmask));
		System.out.println(strNetmask + " is valid ip  :" + IPUtil.isValidIP(strNetmask));

		String begin = "192.168.4.252";
		String end = "192.168.4.240";
		try {
			System.out.println(strNetmask + " is between " + begin + " and " + end + " "
					+ IPUtil.isBetween(strNetmask, begin, end));

			System.out.println(begin + " and " + end + " is in the same subnet ?"
					+ IPUtil.isInTheSameSubnet("255.255.255.0", begin, end));
		} catch (UnknownHostException ex) {
			System.err.println(ex.getMessage());
		}

		String nextIp = getNextIp("192.168.4.0", "192.169.4.2", null);

		while (nextIp != null) {
			System.out.println("next ip :" + nextIp);
			nextIp = getNextIp("192.168.4.0", "192.168.4.2", nextIp);
		}
	}
     
}