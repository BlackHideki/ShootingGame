package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TextFileReader {

	/**
	 * ファイルを格納
	 */
	private File file;

	/**
	 * 読み込んだテキストを格納
	 */
	private ArrayList<String> text;

	public TextFileReader(String filePath) {
		file = new File(filePath);

		text = new ArrayList<>();
	}

	/**
	 * テキストファイルを読み込む
	 */
	public void textRead(){
		text.clear();

		BufferedReader br = null;

		try{
			br = new BufferedReader(new FileReader(file));
			String str = br.readLine();

				while(str != null){
					text.add(str);
					str = br.readLine();
				}
			}catch(FileNotFoundException e){
			  e.printStackTrace();
			}catch(IOException e){
			  e.printStackTrace();
			}finally{
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}

	/**
	 * テキストファイルに書き込む
	 * @param list
	 */
	public void textWrite(ArrayList<String> list){
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			for(String s : list){
				pw.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			pw.close();
		}
	}

	public ArrayList<String> getText() {
		return text;
	}

	public void setText(ArrayList<String> text) {
		this.text = text;
	}
}
