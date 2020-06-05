package lib;

import java.io.File;

public class FilePath {
	public static String resDir=new File("src/res").getAbsolutePath();
	public static String bgDir=resDir+"./bg/";
	public static String buttonDir=resDir+"./button/";
	public static String topIconDir=resDir+"./topIcon/";
	public static String weatherIconDir=resDir+"./weatherIcon/";
	public static String oriIconDir=resDir+"./oriIcon/";
	public static String selectIconDir=resDir+"./selectIcon/";
	public static String copyObjectDir=resDir+"./copyObject/";
	public static String dustIconDir=resDir+"./dustIcon/";
	
	public static String getEXT(String path) {
		
		// 1) ���ϸ�.Ȯ���� ����	(������ ������ ���� ~ ��)
		int lastIndex=path.lastIndexOf("\\");		// escape ��Ű�� Ư�����ڰ� ����� Ÿ���Ͽ� �׳� �Ϲݹ��� ó����
		String fileName=path.substring(lastIndex+1, path.length());
		
		// 2) ���ϸ�.Ȯ���� �κ��� Ȯ���ڸ� ����		(���� ������ . ~ ��)
		lastIndex=fileName.lastIndexOf(".");
		String exit=fileName.substring((lastIndex)+1, fileName.length());
		return exit;
	}
}