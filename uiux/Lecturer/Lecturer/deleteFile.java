package Lecturer;
import java.io.File;

public class deleteFile {
	public void delete(File file) {
		if( file.exists() ){ //�������翩��Ȯ�� 
			if(file.isDirectory()){ //������ ���丮���� Ȯ�� 
				File[] files = file.listFiles(); 
				for( int i=0; i<files.length; i++){ 
					if( files[i].delete() ){ 
						System.out.println(files[i].getName()+" ��������");
					}else{ 
						System.out.println(files[i].getName()+" ��������"); 
					} 
				} 
			} 
			if(file.delete()){ 
				System.out.println("���ϻ��� ����"); 
				}
			else{ System.out.println("���ϻ��� ����"); } 
			
		}else{ 
			System.out.println("������ �������� �ʽ��ϴ�."); 
		}
	}
}
