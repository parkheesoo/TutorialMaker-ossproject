package Lecturer;
import java.io.File;

public class deleteFile {
	public void delete(File file) {
		if( file.exists() ){ //파일존재여부확인 
			if(file.isDirectory()){ //파일이 디렉토리인지 확인 
				File[] files = file.listFiles(); 
				for( int i=0; i<files.length; i++){ 
					if( files[i].delete() ){ 
						System.out.println(files[i].getName()+" 삭제성공");
					}else{ 
						System.out.println(files[i].getName()+" 삭제실패"); 
					} 
				} 
			} 
			if(file.delete()){ 
				System.out.println("파일삭제 성공"); 
				}
			else{ System.out.println("파일삭제 실패"); } 
			
		}else{ 
			System.out.println("파일이 존재하지 않습니다."); 
		}
	}
}
