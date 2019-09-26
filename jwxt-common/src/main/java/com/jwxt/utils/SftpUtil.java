package com.jwxt.utils;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

/** sftp工具 */
public class SftpUtil {

    private ChannelSftp channel;

    /**
     * 连接sftp服务器
     * @param host 主机
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     */
    public SftpUtil(String host, int port, String username, String password) {
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            this.channel = (ChannelSftp) channel;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param path 上传的目录
     * @param name 上传的文件名
     * @param in 输入流
     */
    public void upload(String path, String name, InputStream in) {
        try {
            mkdirs(path);
            channel.put(in, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     * @param path 下载目录
     * @param name 下载的文件
     * @param local 存在本地的路径
     */
    public void download(String path, String name, String local) {
        try {
            channel.cd(path);
            File file = new File(local);
            channel.get(name, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     * @param path 要删除文件所在目录
     * @param name 要删除的文件
     */
    public void delete(String path, String name) {
        try {
            channel.cd(path);
            channel.rm(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param path 要列出的目录
     * @return
     */
    @SuppressWarnings("unchecked")
    public Vector<LsEntry> listFiles(String path) {
        try {
            return channel.ls(path);
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void mkdirs(String path) {
        String[] folders = path.split("/");
        try {
            channel.cd("/");
            for (String folder: folders) {
                if (folder.length()>0) {
                    try {
                        channel.cd(folder);
                    } catch (Exception e) {
                        channel.mkdir(folder);
                        channel.cd(folder);
                    }
                }
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    public void close() {
        channel.disconnect();
    }

}
