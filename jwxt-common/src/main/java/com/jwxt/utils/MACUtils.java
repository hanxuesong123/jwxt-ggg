package com.jwxt.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MACUtils {

    public static String getMACAddressByWindows() throws Exception {
        String result = "";
        Process process = Runtime.getRuntime().exec("ipconfig /all");
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));

        String line;
        int index = -1;
        while ((line = br.readLine()) != null) {
            index = line.toLowerCase().indexOf("物理地址");
            if (index >= 0) {// 找到了
                index = line.indexOf(":");
                if (index >= 0) {
                    result = line.substring(index + 1).trim();
                }
                break;
            }
        }
        br.close();
        return result;
    }

    public static String getMACAddressByLinux() throws Exception {
        String[] cmd = {"ifconfig"};

        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();

        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String str1 = sb.toString();
        String str2 = str1.split("ether")[1].trim();
        String result = str2.split("txqueuelen")[0].trim();
        br.close();

        return result;
    }














    public static String getClientIP(HttpServletRequest request)
    {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getRemoteAddr();
        return ip;
    }

    public static String getLocalMac(String ipAddress) throws Exception,
            UnknownHostException, UnknownHostException {

        String str = "";
        String macAddress = "";
        final String LOOPBACK_ADDRESS = "127.0.0.1";
        // 如果为127.0.0.1,则获取本地MAC地址。
        if (LOOPBACK_ADDRESS.equals(ipAddress)) {
            InetAddress inetAddress = InetAddress.getLocalHost();
            // 貌似此方法需要JDK1.6。
            byte[] mac = NetworkInterface.getByInetAddress(inetAddress)
                    .getHardwareAddress();
            // 下面代码是把mac地址拼装成String
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                // mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
            // 把字符串所有小写字母改为大写成为正规的mac地址并返回
            macAddress = sb.toString().trim().toUpperCase();
            return macAddress;
        } else {
            // 获取非本地IP的MAC地址
            try {
                System.out.println(ipAddress);
                Process p = Runtime.getRuntime().exec("nbtstat -A " + ipAddress);
                System.out.println("===process==" + p);
                InputStreamReader ir = new InputStreamReader(p.getInputStream());

                BufferedReader br = new BufferedReader(ir);

                while ((str = br.readLine()) != null) {
                    if (str.indexOf("MAC") > 1) {
                        macAddress = str.substring(str.indexOf("MAC") + 9, str.length());
                        macAddress = macAddress.trim();
                        System.out.println("macAddress:" + macAddress);
                        break;
                    }
                }
                p.destroy();
                br.close();
                ir.close();
            } catch (IOException ex) {
            }
            return macAddress;
        }
    }


    public static void main(String[] args) {
        String localMac = getLocalMac();
        System.out.println(localMac);
    }


    private static String getLocalMac()
    {
        String result = "";
        try {
            InetAddress adress = InetAddress.getLocalHost();
            NetworkInterface net = NetworkInterface.getByInetAddress(adress);
            byte[] macBytes = net.getHardwareAddress();
            result = transBytesToStr(macBytes);
        } catch (UnknownHostException e) {
            result = "";
            e.printStackTrace();
        } catch (SocketException e) {
            result = "";
            e.printStackTrace();
        }
        finally
        {
            return result;
        }
    }

    private static String transBytesToStr(byte[] bytes)
    {
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < bytes.length; i++){
            if(i != 0)
                buffer.append("-");
            //bytes[i]&0xff将有符号byte数值转换为32位有符号整数，其中高24位为0，低8位为byte[i]
            int intMac = bytes[i]&0xff;
            //toHexString函数将整数类型转换为无符号16进制数字
            String str = Integer.toHexString(intMac);
            if(str.length() == 0){
                buffer.append("00");
            }
            else if (str.length() < 2)
            {
                buffer.append("0");
            }
            buffer.append(str);
        }
        return buffer.toString().toUpperCase();
    }
}
