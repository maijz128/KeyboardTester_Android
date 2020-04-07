package com.maijz.keyboardtester;
import android.view.*;
import java.util.*;

public class KeyMap
{
	
	
	public static String getKeyText(int keyCode){
		String result = "";
		String[] KEY_NAME = new String[]{"0"};
		
		HashMap<Integer, String> IC = new HashMap<Integer, String>();
		
		for (int i = 'a'; i <= 'z'; i++)	//29 - 54
		{
			IC.put(29 + i - 'a', String.valueOf((char)i));
		}
		for (int i = 'A'; i <= 'Z'; i++)	//65 - 90
		{
			IC.put(65 + i - 'A', String.valueOf((char)i));
		}
		
		for (int i = 7; i <= 16; i++){		// 0 - 9 
			IC.put(i, String.valueOf((i - 7)));
		}
		
		for (int i = 131; i <= 142; i++){		// F1 - F12 
			IC.put(i, String.valueOf("F" + (i - 130)));
		}
		
		for (int i = 96; i <= 105; i++){		// pad numbers
			IC.put(i, String.valueOf(i - 96)); 
		}	
		// pad
		IC.put(106, "*");
		IC.put(107, "+");
		IC.put(108, "Enter");
		IC.put(109, "-");
		IC.put(110, ".");
		IC.put(111, "/");
		IC.put(KeyEvent.KEYCODE_NUM_LOCK, "NumLock");
		
		IC.put(KeyEvent.KEYCODE_ESCAPE, "Esc");
		IC.put(KeyEvent.KEYCODE_GRAVE, "`");
		IC.put(KeyEvent.KEYCODE_MINUS, "-");
		IC.put(KeyEvent.KEYCODE_EQUALS, "=");
		IC.put(KeyEvent.KEYCODE_DEL, "Backspace");
		IC.put(KeyEvent.KEYCODE_TAB, "Tab");
		IC.put(71, "[");
		IC.put(72, "]");
		IC.put(KeyEvent.KEYCODE_BACKSLASH, "\\");
		IC.put(KeyEvent.KEYCODE_CAPS_LOCK, "CapsLock");
		IC.put(KeyEvent.KEYCODE_SEMICOLON, ";");
		IC.put(KeyEvent.KEYCODE_APOSTROPHE, "'");
		IC.put(KeyEvent.KEYCODE_ENTER, "Enter");
		IC.put(KeyEvent.KEYCODE_SHIFT_LEFT, "Shift Left");
		IC.put(KeyEvent.KEYCODE_COMMA, ",");
		IC.put(KeyEvent.KEYCODE_PERIOD, ".");
		IC.put(KeyEvent.KEYCODE_SLASH, "/");
		IC.put(KeyEvent.KEYCODE_SHIFT_RIGHT, "Shift Right");
		IC.put(KeyEvent.KEYCODE_CTRL_LEFT, "Ctrl Left");
		IC.put(KeyEvent.KEYCODE_ALT_LEFT, "Alt Left");
		IC.put(KeyEvent.KEYCODE_ALT_RIGHT, "Alt Right");
		IC.put(KeyEvent.KEYCODE_WINDOW, "Window");
		IC.put(KeyEvent.KEYCODE_SPACE, "Space");
		IC.put(KeyEvent.KEYCODE_MENU, "Menu");
		IC.put(KeyEvent.KEYCODE_CTRL_RIGHT, "Ctrl Right");
		
		IC.put(KeyEvent.KEYCODE_DPAD_UP, "Up");
		IC.put(KeyEvent.KEYCODE_DPAD_DOWN, "Down");
		IC.put(KeyEvent.KEYCODE_DPAD_LEFT, "Left");
		IC.put(KeyEvent.KEYCODE_DPAD_RIGHT, "Right");
		
		IC.put(KeyEvent.KEYCODE_INSERT, "Insert");
		IC.put(112, "Delete");
		IC.put(KeyEvent.KEYCODE_HOME, "Home");
		IC.put(122, "Home");
		IC.put(KeyEvent.KEYCODE_ENDCALL, "End");
		IC.put(123, "End");
		IC.put(KeyEvent.KEYCODE_PAGE_UP, "PageUp");
		IC.put(KeyEvent.KEYCODE_PAGE_DOWN, "PageDown");
		IC.put(KeyEvent.KEYCODE_PAIRING, "PrtSc");
		IC.put(KeyEvent.KEYCODE_SCROLL_LOCK, "ScrollLock");
		IC.put(KeyEvent.KEYCODE_BREAK, "Break");
		
		IC.put(KeyEvent.KEYCODE_SEARCH, "Search");
		IC.put(KeyEvent.KEYCODE_HELP, "Help");
		
		if (IC.containsKey(keyCode)){
			String c = IC.get(keyCode);
			return String.valueOf(c);
		}
		
		
		
		
		return result;
	}
	
	public static int KeyCodeToKeyCode_JS(int keyCode){

		HashMap<Integer, Integer> IC = new HashMap<Integer, Integer>();

		
		for (int i = 'a'; i <= 'z'; i++)	//29 - 54
		{
			IC.put(i, 65 + i - 'a');
		}
		for (int i = KeyEvent.KEYCODE_A; i <= KeyEvent.KEYCODE_Z; i++)	//65 - 90
		{
			IC.put(i, 65 + i - KeyEvent.KEYCODE_A);
		}
		
		for (int i = 7; i <= 16; i++){		// 0 - 9 , 48 - 57
			IC.put(i, 48 + i - 7);
		}

		for (int i = 131; i <= 142; i++){		// F1 - F12 , 112 - 123
			IC.put(i, 112 + i - 131);
		}

		for (int i = 96; i <= 105; i++){		// pad numbers
			IC.put(i, i); 
		}	
		// pad
		IC.put(106, 106);//"*");
		IC.put(107, 107);//"+");
		IC.put(108, 108);//"Enter");
		IC.put(109, 109);//"-");
		IC.put(110, 110);//".");
		IC.put(111, 111);//"/");
		IC.put(KeyEvent.KEYCODE_NUM_LOCK, 144);//"NumLock");

		IC.put(KeyEvent.KEYCODE_ESCAPE, 27);//"Esc");
		IC.put(KeyEvent.KEYCODE_GRAVE, 192);//"`");
		IC.put(KeyEvent.KEYCODE_MINUS, 189);//"-");
		IC.put(KeyEvent.KEYCODE_EQUALS, 187);//"=");
		IC.put(KeyEvent.KEYCODE_DEL, 8);//"Backspace");
		IC.put(KeyEvent.KEYCODE_TAB, 9);//"Tab");
		IC.put(71, 219);//"[");
		IC.put(72, 221);//"]");
		IC.put(KeyEvent.KEYCODE_BACKSLASH, 220);//"\\");
		IC.put(KeyEvent.KEYCODE_CAPS_LOCK, 20);//"CapsLock");
		IC.put(KeyEvent.KEYCODE_SEMICOLON, 186);//";");
		IC.put(KeyEvent.KEYCODE_APOSTROPHE, 222);//"'");
		IC.put(KeyEvent.KEYCODE_ENTER, 13);//"Enter");
		IC.put(KeyEvent.KEYCODE_SHIFT_LEFT, 1016);//"Shift Left");
		IC.put(KeyEvent.KEYCODE_COMMA, 188);//",");
		IC.put(KeyEvent.KEYCODE_PERIOD, 190);//".");
		IC.put(KeyEvent.KEYCODE_SLASH, 191);//"/");
		IC.put(KeyEvent.KEYCODE_SHIFT_RIGHT, 2016);//"Shift Right");
		IC.put(KeyEvent.KEYCODE_CTRL_LEFT, 1017);//"Ctrl Left");
		IC.put(KeyEvent.KEYCODE_ALT_LEFT, 1018);//"Alt Left");
		IC.put(KeyEvent.KEYCODE_ALT_RIGHT, 2018);//"Alt Right");
		IC.put(KeyEvent.KEYCODE_WINDOW, 91);//"Window");
		IC.put(KeyEvent.KEYCODE_SPACE, 32);//"Space");
		IC.put(KeyEvent.KEYCODE_MENU, 93);//"Menu");
		IC.put(KeyEvent.KEYCODE_CTRL_RIGHT, 2017);//"Ctrl Right");

		IC.put(KeyEvent.KEYCODE_DPAD_UP, 38);//"Up");
		IC.put(KeyEvent.KEYCODE_DPAD_DOWN, 40);//"Down");
		IC.put(KeyEvent.KEYCODE_DPAD_LEFT, 37);//"Left");
		IC.put(KeyEvent.KEYCODE_DPAD_RIGHT, 39);//"Right");

		
		IC.put(KeyEvent.KEYCODE_INSERT, 45);//"Insert");
		IC.put(112, 46);//"Delete");
		IC.put(KeyEvent.KEYCODE_HOME, 1036);//"Home");
		IC.put(122, 1036);
		IC.put(KeyEvent.KEYCODE_ENDCALL, 1035);//"End");
		IC.put(123, 1035);
		IC.put(KeyEvent.KEYCODE_PAGE_UP, 33);//"PageUp");
		IC.put(KeyEvent.KEYCODE_PAGE_DOWN, 34);//"PageDown");
		IC.put(KeyEvent.KEYCODE_PAIRING, 44);//"PrtSc");
		IC.put(KeyEvent.KEYCODE_SCROLL_LOCK, 145);//"ScrollLock");
		IC.put(KeyEvent.KEYCODE_BREAK, 19);//"Break");
		IC.put(KeyEvent.KEYCODE_VOLUME_DOWN, 174);
		IC.put(KeyEvent.KEYCODE_VOLUME_UP, 175);
		/*
		IC.put(KeyEvent.KEYCODE_SEARCH, "Search");
		IC.put(KeyEvent.KEYCODE_HELP, "Help");
		*/
		
		if (IC.containsKey(keyCode)){
			return IC.get(keyCode);
		}

		return keyCode;
	}
}
