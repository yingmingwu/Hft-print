package com.example.myserialtest.sdk;

public class Command {

	//打印机初始化
	public static byte[] ESC_Init = new byte[] {0x1b, 0x40 };
	
	/**
	 * 打印命令
	 */
	//打印并换行
	public static byte[] LF = new byte[] {0x0a};
	
	//打印并走纸
	public static byte[] ESC_J = new byte[] {0x1b, 0x4a, 0x00 };
	public static byte[] ESC_d = new byte[] {0x1b, 0x64, 0x00 };
	
	//打印自检页
	public static byte[] US_vt_eot = new byte[] {0x1f, 0x11, 0x04 };
	
	 //蜂鸣指令
    public static byte[] ESC_B_m_n = new byte[] {0x1b, 0x42, 0x00, 0x00 };
	
    //切刀指令
    public static byte[] GS_V_n = new byte[] {0x1d, 0x56, 0x00 };
    public static byte[] GS_V_m_n = new byte[] {0x1d, 0x56, 0x42, 0x00 };
    public static byte[] GS_i = new byte[] {0x1b, 0x69 };
    public static byte[] GS_m = new byte[] {0x1b, 0x6d };
	
	/**
	 * 字符设置命令
	 */
	//设置字符右间距
	public static byte[] ESC_SP = new byte[] {0x1b, 0x20, 0x00 };
	
	//设置字符打印字体格式
	public static byte[] ESC_ExclamationMark = new byte[] {0x1b, 0x21, 0x00 };
	
	//设置字体倍高倍宽
	public static byte[] GS_ExclamationMark = new byte[] {0x1d, 0x21, 0x00 };
	
	//设置反显打印
	public static byte[] GS_B = new byte[] {0x1d, 0x42, 0x00 };
	
	//取消/选择90度旋转打印
	public static byte[] ESC_V = new byte[] {0x1b, 0x56, 0x00 };
	
	//选择字体字型(主要是ASCII码)
	public static byte[] ESC_M = new byte[] {0x1b, 0x4d, 0x00 };
	
	//选择/取消加粗指令
	public static byte[] ESC_G = new byte[] {0x1b, 0x47, 0x00 };
	public static byte[] ESC_E = new byte[] {0x1b, 0x45, 0x00 };
	
	//选择/取消倒置打印模式
	public static byte[] ESC_LeftBrace = new byte[] {0x1b, 0x7b, 0x00 };
	
	//设置下划线点高度(字符)
	public static byte[] ESC_Minus = new byte[] {0x1b, 0x2d, 0x00 };
	
	//字符模式
	public static byte[] FS_dot = new byte[] {0x1c, 0x2e };
	
	//汉字模式
	public static byte[] FS_and = new byte[] {0x1c, 0x26 };
	
	//设置汉字打印模式
	public static byte[] FS_ExclamationMark = new byte[] {0x1c, 0x21, 0x00 };
	
	//设置下划线点高度(汉字)
	public static byte[] FS_Minus = new byte[] {0x1c, 0x2d, 0x00 };
	
	//设置汉字左右间距
	public static byte[] FS_S = new byte[] {0x1c, 0x53, 0x00, 0x00 };
	
	//选择字符代码页
	public static byte[] ESC_t = new byte[] {0x1b, 0x74, 0x00 };
	
	/**
	 * 格式设置指令
	 */
	//设置默认行间距
	public static byte[] ESC_Two = new byte[] {0x1b, 0x32}; 
	
	//设置行间距
	public static byte[] ESC_Three = new byte[] {0x1b, 0x33, 0x00 };
	
	//设置对齐模式
	public static byte[] ESC_Align = new byte[] {0x1b, 0x61, 0x00 };
	
	//设置左边距
	public static byte[] GS_LeftSp = new byte[] {0x1d, 0x4c, 0x00 , 0x00 };
	
	//设置绝对打印位置
	//将当前位置设置到距离行首（nL + nH x 256）处。
	//如果设置位置在指定打印区域外，该命令被忽略
	public static byte[] ESC_Relative = new byte[] {0x1b, 0x24, 0x00, 0x00 };
	
	//设置相对打印位置
	public static byte[] ESC_Absolute = new byte[] {0x1b, 0x5c, 0x00, 0x00 };
	
	//设置打印区域宽度
	public static byte[] GS_W = new byte[] {0x1d, 0x57, 0x00, 0x00 };

	/**
	 * 状态指令
	 */
	//实时状态传送指令
	public static byte[] DLE_eot = new byte[] {0x10, 0x04, 0x00 };
	
	//实时弹钱箱指令
	public static byte[] DLE_DC4 = new byte[] {0x10, 0x14, 0x00, 0x00, 0x00 };
	
	//标准弹钱箱指令
	public static byte[] ESC_p = new byte[] {0x1b, 0x70, 0x00, 0x00, 0x00 };
	
	/**
	 * 条码设置指令
	 */
	//选择HRI打印方式
	public static byte[] GS_H = new byte[] {0x1d, 0x48, 0x00 };
	
	//设置条码高度
	public static byte[] GS_h = new byte[] {0x1d, 0x68, (byte) 0xa2 };
	
	//设置条码宽度
	public static byte[] GS_w = new byte[] {0x1d, 0x77, 0x00 };
	
	//设置HRI字符字体字型
	public static byte[] GS_f = new byte[] {0x1d, 0x66, 0x00 };
	
	//条码左偏移指令
	public static byte[] GS_x = new byte[] {0x1d, 0x78, 0x00 };
	
	//打印条码指令
	public static byte[] GS_k = new byte[] {0x1d, 0x6b, 0x41, 0x0c };

	//二维码相关指令		
    public static byte[] GS_k_m_v_r_nL_nH = new byte[] { 0x1b, 0x5a, 0x03, 0x03, 0x08, 0x00, 0x00 };
	
}
