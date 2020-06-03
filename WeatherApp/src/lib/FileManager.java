package lib;

public class FileManager {
	// 파일의 확장자 구하기
	// 호출 시 경로 넘기기!
	public static String getEXT(String path) {
		
		// 1) 파일명.확장자 추출	(마지막 슬래시 다음 ~ 끝)
		int lastIndex=path.lastIndexOf("\\");		// escape 시키면 특수문자가 기능을 타룰하여 그냥 일반문자 처리됨
		String fileName=path.substring(lastIndex+1, path.length());
		
		// 2) 파일명.확장자 로부터 확장자만 추출		(가장 마지막 . ~ 끝)
		lastIndex=fileName.lastIndexOf(".");
		String exit=fileName.substring((lastIndex)+1, fileName.length());
		
		return exit;
	}
	
	// 파일명 추출하기 (로컬 하드 용)
	public static String getFilenameFromLocal(String path) {
		int lastIndex=path.lastIndexOf("\\");		// escape 시키면 특수문자가 기능을 타룰하여 그냥 일반문자 처리됨
		String fileName=path.substring(lastIndex+1, path.length());

		return fileName;
	}
	
	// 파일명 추출하기 (http 용)
	public static String getFilenameFromHttp(String path) {
		int lastIndex=path.lastIndexOf("/");		// escape 시키면 특수문자가 기능을 타룰하여 그냥 일반문자 처리됨
		String fileName=path.substring(lastIndex+1, path.length());

		return fileName;
	}
}
