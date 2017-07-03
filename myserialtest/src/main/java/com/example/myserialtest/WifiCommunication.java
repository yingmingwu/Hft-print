package com.example.myserialtest;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by hft on 2017/6/28.
 */
public class WifiCommunication {
    private static Socket client = null;
    private static OutputStream out = null;
    private static InputStream inStream = null;
    private final Handler mHandler;
    private String AddressIp = null;
    private int port = 0;
    private ConnectThread mConnection = null;
    public static final int WFPRINTER_CONNECTED = 0;
    public static final int WFPRINTER_DISCONNECTED = 1;
    public static final int WFPRINTER_CONNECTEDERR = 2;
    public static final int SEND_FAILED = 4;
    public static final int WFPRINTER_REVMSG = 5;

    public WifiCommunication(Handler handler)
    {
        this.mHandler = handler;
    }

    public void initSocket()
    {
//        this.AddressIp = AddressIp;
//        this.port = port;
//        if (this.mConnection != null) {
//            this.mConnection = null;
//        }
//        if (this.mConnection == null) {
//            this.mConnection = new ConnectThread();
//            this.mConnection.start();
//        }
    }

    public void sendMsg(String sndMsg, String charset)
    {
        if (sndMsg == null)
            return;
        try {
            byte[] send;
            try {
                send = sndMsg.getBytes(charset);
            }
            catch (UnsupportedEncodingException e)
            {

                send = sndMsg.getBytes();
            }
            if ((client.isConnected()) &&
                    (!client.isOutputShutdown())) {
                out.write(send);
                out.flush();
            }
        }
        catch (IOException e) {
            Message msg = this.mHandler.obtainMessage(4);
            this.mHandler.sendMessage(msg);
            Log.d("WIFI-printer", e.toString());
        }
    }

    public void sndByte(byte[] send)
    {
        if (send == null)
            return;
        try {
            if ((client.isConnected()) &&
                    (!client.isOutputShutdown())) {
                out.write(send);
                out.flush();
            }
        }
        catch (IOException e) {
            Log.d("WIFI-printer", e.toString());
            Message msg_ret = this.mHandler.obtainMessage(4);
            this.mHandler.sendMessage(msg_ret);
        }
    }

    public void close()
    {
        try
        {
            if (out != null)
                out.close();
            if (inStream != null)
                inStream.close();
            if (client != null) {
                client.close();
                out = null;
                inStream = null;
                client = null;
                Message msg_ret = this.mHandler.obtainMessage(1);
                this.mHandler.sendMessage(msg_ret);
            }
        }
        catch (IOException e)
        {
            Log.d("WIFI-printer", e.toString());
        }
    }

    public byte[] revMsg()
    {
        try
        {
            byte[] revData = new byte[1024];
            inStream.read(revData);
            return revData;
        } catch (Exception e) {
            Log.d("WIFI-printer", e.toString());
        }
        return null;
    }

    public int revByte()
    {
        try
        {
            return inStream.read();
        } catch (Exception e) {
            Log.d("WIFI-printer", e.toString());
        }
        return -1;
    }

    public String bytesToString(byte[] b)
    {
        String str = null;
        try {
            str = new String(b, "UTF-8").trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    private class ConnectThread extends Thread
    {
        private ConnectThread()
        {
        }

        public void run()
        {
            try
            {
                InetAddress serverAddr = InetAddress.getByName(WifiCommunication.this.AddressIp);

                WifiCommunication.client = new Socket(serverAddr, WifiCommunication.this.port);
                if (WifiCommunication.client != null) {
                    WifiCommunication.out = WifiCommunication.client.getOutputStream();
                    WifiCommunication.inStream = WifiCommunication.client.getInputStream();
                }
                if ((WifiCommunication.client != null) && (WifiCommunication.out != null) && (WifiCommunication.inStream != null)) {
                    Message msg_ret = WifiCommunication.this.mHandler.obtainMessage(0);
                    WifiCommunication.this.mHandler.sendMessage(msg_ret);
                }
            } catch (IOException e) {
                Message msg_ret = WifiCommunication.this.mHandler.obtainMessage(2);
                WifiCommunication.this.mHandler.sendMessage(msg_ret);
                return;
            }
        }
    }
}
