package edittext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JTextArea;

/**
 * <p color=blue size=5>Title:OpenFile</p>
 * <p color=blue size=5>Description:打开本地文件</p>
 * <p color=blue size=5>Company:</p>
 * <p color=blue size=5>@author Bob Simon</p>
 * <p color=blue size=5>2017-11-24 下午09:33:33</p>
 */
public class OpenFile {

	/**
	 * 调取本地电脑文件
	 * @param path
	 * @param ta
	 */
	public OpenFile(String path, JTextArea ta){

		File file = new File(path);

		try {
			FileReader fr = new FileReader(file);

			BufferedReader br = new BufferedReader(fr);

			String tem;

			while ((tem = br.readLine()) != null){

				ta.append(tem + "\n");
			}

			br.close();

			fr.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}

	}


}
