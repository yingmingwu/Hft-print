package com.example.myserialtest;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myserialtest.sdk.Command;
import com.example.myserialtest.sdk.PrinterCommand;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;

import android_serialport_api.SerialPort;
import zj.com.customize.sdk.Other;

public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";
    private TextView tv_receive;
    private EditText et_send;
    private Button bt_send;


    private SerialPort mSerialPort;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private String sPort = "/dev/ttyS0";
    private int iBaudRate = 115200;
    //115200
    private String receiveString;
    android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "xxxxxxxxxxxxxxxx");
            super.handleMessage(msg);

			
			//

            switch (msg.what) {
                case 1000:
                    String str1 = "test111";
//            sendByte(Command.FS_and);   //设置汉字打印模式
//            sendString(str1);
//            sendByte(str1.getBytes());
//            sendByte(Command.LF);
                    handler.sendEmptyMessageDelayed(2000, 1000);
                    break;
                case 2000:
                    String city = "长沙";
                    byte[][] allbuf = new byte[0][];
                    try {
                        allbuf = new byte[][]{

                                Command.ESC_Init, Command.ESC_Three,
                                String.format("┏━━┳━━━┳━━┳━━━━┓\n").getBytes("GBK"),
                                String.format("┃发站┃%-4s┃到站┃%-6s┃\n", city, city).getBytes("GBK"),
                                String.format("┣━━╋━━━╋━━╋━━━━┫\n").getBytes("GBK"),
                                String.format("┃件数┃%2d/%-3d┃单号┃%-8d┃\n", 1, 222, 555).getBytes("GBK"),
                                String.format("┣━━┻┳━━┻━━┻━━━━┫\n").getBytes("GBK"),
                                String.format("┃收件人┃%-12s┃\n", "【送】测试/测试人").getBytes("GBK"),
                                String.format("┣━━━╋━━┳━━┳━━━━┫\n").getBytes("GBK"),
                                String.format("┃业务员┃%-2s┃名称┃%-6s┃\n", city, city).getBytes("GBK"),
                                String.format("┗━━━┻━━┻━━┻━━━━┛\n").getBytes("GBK"),
                                Command.ESC_Align, "\n".getBytes("GBK")
                        };
                        byte[] buf = Other.byteArraysToBytes(allbuf);
                        sendByte(buf);
                        sendByte(Command.GS_V_m_n);
                        Log.i(TAG, "发送打印指令了。。。。");
                    } catch (UnsupportedEncodingException e) {
                        Log.i(TAG, "UnsupportedEncodingException:" + e);
                        e.printStackTrace();
                    }
                    break;

                case 3000:
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    String str = formatter.format(curDate);
                    String date = str + "\n\n\n\n\n\n";
                    try {
                        byte[] qrcode = PrinterCommand.getBarCommand("资江电子热敏票据打印机!", 0, 3, 6);//
                        Command.ESC_Align[2] = 0x01;
                        sendByte(Command.ESC_Align);
                        sendByte(qrcode);

                        sendByte(Command.ESC_Align);
                        Command.GS_ExclamationMark[2] = 0x11;
                        sendByte(Command.GS_ExclamationMark);
                        sendByte("NIKE专卖店\n".getBytes("GBK"));
                        Command.ESC_Align[2] = 0x00;
                        sendByte(Command.ESC_Align);
                        Command.GS_ExclamationMark[2] = 0x00;
                        sendByte(Command.GS_ExclamationMark);
                        sendByte("门店号: 888888\n单据  S00003333\n收银员：1001\n单据日期：xxxx-xx-xx\n打印时间：xxxx-xx-xx  xx:xx:xx\n".getBytes("GBK"));
                        sendByte("品名       数量    单价    金额\nNIKE跑鞋   10.00   899     8990\nNIKE篮球鞋 10.00   1599    15990\n".getBytes("GBK"));
                        sendByte("数量：                20.00\n总计：                16889.00\n付款：                17000.00\n找零：                111.00\n".getBytes("GBK"));
                        sendByte("公司名称：NIKE\n公司网址：www.xxx.xxx\n地址：深圳市xx区xx号\n电话：0755-11111111\n服务专线：400-xxx-xxxx\n================================\n".getBytes("GBK"));
                        Command.ESC_Align[2] = 0x01;
                        sendByte(Command.ESC_Align);
                        Command.GS_ExclamationMark[2] = 0x11;
                        sendByte(Command.GS_ExclamationMark);
                        sendByte("谢谢惠顾,欢迎再次光临!\n".getBytes("GBK"));
                        Command.ESC_Align[2] = 0x00;
                        sendByte(Command.ESC_Align);
                        Command.GS_ExclamationMark[2] = 0x00;
                        sendByte(Command.GS_ExclamationMark);

                        sendByte("(以上信息为测试模板,如有苟同，纯属巧合!)\n".getBytes("GBK"));
                        Command.ESC_Align[2] = 0x02;
                        sendByte(Command.ESC_Align);
                        sendString(date);
                        sendByte(Command.GS_i);
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case 4000:
//                    sendByte(Command.FS_dot);// 字符模式
//                    sendByte(Command.FS_and);//汉字模式
//                    sendByte(Command.ESC_Init);
//                    sendByte(Command.ESC_p);
//                    sendByte(PrinterCommand.POS_Set_Cashbox(0,20,20));
                    Log.i(TAG, "标准弹钱箱执行了");
                    break;
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         /* 隐藏标题栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* 隐藏状态栏 */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* 设定屏幕显示为横屏 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_main);

        //初始化组件
        tv_receive = (TextView) findViewById(R.id.tv_receive);
        et_send = (EditText) findViewById(R.id.et_send);
        bt_send = (Button) findViewById(R.id.bt_send);


        //获取串口实例
        try {
            mSerialPort = new SerialPort(new File(sPort), iBaudRate, 0);
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();
            mReadThread = new ReadThread();
//            mReadThread.start();
        } catch (IOException e) {
            Log.i(TAG, "获取串口实列异常：" + e);
            e.printStackTrace();
        }

        //发送数据
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = et_send.getText().toString();
                if (TextUtils.isEmpty(string)) {
                    et_send.setError("发送的数据不能为空");
                } else {
//                    sendByte(Command.ESC_Init);
//                    sendString(string);
                    sendByte(PrinterCommand.POS_Set_Cashbox(0,200,200));
                }
            }
        });


        handler.sendEmptyMessageDelayed(4000, 1000);
    }

    /**
     * 读串口线程
     */
    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[64];
                    if (mInputStream == null)
                        return;


                    size = mInputStream.read(buffer);
                    if (size > 0) {
//                        onDataReceived(buffer, size);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    /**
     * 发串口数据
     */
    public void sendByte(final byte[] string) {
        try {
            //去掉空格
//            String s = string;
//            s = s.replace(" ", "");
//            byte[] bytes = SerialDataUtils.HexToByteArr(s);
            if (string.length > 0)
                mOutputStream.write(string);

            Log.i(TAG, "发串口数据" + string);
        } catch (IOException e) {
            Log.i(TAG, "发串口数据异常：" + e);
            e.printStackTrace();
        } finally {

            try {
                mOutputStream.flush();
//                mOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public synchronized void sendString(final String string) {
        byte[] send;
        try {
            send = string.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            send = string.getBytes();
            e.printStackTrace();
        }
        Log.i(TAG, "string=" + string);
        sendByte(send);

    }

    public void sendString222(final String string) {

        try {
            //去掉空格
            String s = string;
            s = s.replace(" ", "");
//            byte[] bytes = SerialDataUtils.HexToByteArr(s);
            mOutputStream.write(string.getBytes());
            mOutputStream.write('\n');
            Log.i(TAG, "发串口数据" + string);
        } catch (IOException e) {
            Log.i(TAG, "发串口数据异常：" + e);
            e.printStackTrace();
        }
    }
//    public void sendByte(byte[] bits, UsbDevice dev)
//    {
//        if (bits == null)
//            return;
//        if ((this.ep != null) && (this.usbIf != null) && (this.conn != null)) {
//            this.conn.bulkTransfer(this.ep, bits, bits.length, 0);
//        } else {
//            if (this.conn == null)
//                this.conn = this.mUsbManager.openDevice(dev);
//            if (dev.getInterfaceCount() == 0) {
//                return;
//            }
//            this.usbIf = dev.getInterface(0);
//            if (this.usbIf.getEndpointCount() == 0) {
//                return;
//            }
//
//            for (int i = 0; i < this.usbIf.getEndpointCount(); i++) {
//                if ((this.usbIf.getEndpoint(i).getType() != 2) ||
//                        (this.usbIf.getEndpoint(i).getDirection() == 128)) continue;
//                this.ep = this.usbIf.getEndpoint(i);
//            }
//
//            if (this.conn.claimInterface(this.usbIf, true))
//                this.conn.bulkTransfer(this.ep, bits, bits.length, 0);
//        }
//    }

    @Override
    protected void onDestroy() {
        //释放串口
        mSerialPort.close();
        super.onDestroy();
    }
}
