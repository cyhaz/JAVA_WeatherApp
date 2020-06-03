package lib;

public class FileManager {
	// ������ Ȯ���� ���ϱ�
	// ȣ�� �� ��� �ѱ��!
	public static String getEXT(String path) {
		
		// 1) ���ϸ�.Ȯ���� ����	(������ ������ ���� ~ ��)
		int lastIndex=path.lastIndexOf("\\");		// escape ��Ű�� Ư�����ڰ� ����� Ÿ���Ͽ� �׳� �Ϲݹ��� ó����
		String fileName=path.substring(lastIndex+1, path.length());
		
		// 2) ���ϸ�.Ȯ���� �κ��� Ȯ���ڸ� ����		(���� ������ . ~ ��)
		lastIndex=fileName.lastIndexOf(".");
		String exit=fileName.substring((lastIndex)+1, fileName.length());
		
		return exit;
	}
	
	// ���ϸ� �����ϱ� (���� �ϵ� ��)
	public static String getFilenameFromLocal(String path) {
		int lastIndex=path.lastIndexOf("\\");		// escape ��Ű�� Ư�����ڰ� ����� Ÿ���Ͽ� �׳� �Ϲݹ��� ó����
		String fileName=path.substring(lastIndex+1, path.length());

		return fileName;
	}
	
	// ���ϸ� �����ϱ� (http ��)
	public static String getFilenameFromHttp(String path) {
		int lastIndex=path.lastIndexOf("/");		// escape ��Ű�� Ư�����ڰ� ����� Ÿ���Ͽ� �׳� �Ϲݹ��� ó����
		String fileName=path.substring(lastIndex+1, path.length());

		return fileName;
	}
}
