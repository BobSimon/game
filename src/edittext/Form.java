package edittext;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * <p color=blue size=5>Title:Form</p>
 * <p color=blue size=5>Description: 主面板:启动程序入口</p>
 * <p color=blue size=5>Company:</p>
 * <p color=blue size=5>@author Bob Simon</p>
 * <p color=blue size=5>2017-11-24 下午09:31:55</p>
 */
public class Form extends JFrame{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private JTextArea ta;

	private JMenuBar Jb;
	
	public Form(){

		Jb = new JMenuBar();

		this.setJMenuBar(Jb);
		JMenu jFile =new JMenu("文件 ");

		JMenu jEdit =new JMenu("编辑");

		JMenu jAbout =new JMenu("关于");

		Jb.add(jFile);

		Jb.add(jEdit);

		Jb.add(jAbout);

		JMenuItem jabOut =new JMenuItem("关于");

		jAbout.add(jabOut);

		jabOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				/**
				 * 监听事件，打开关于作者的对话框
				 */
			    new JabOut();
			}
		});

		JMenuItem jOpen =new JMenuItem("打开");

		jFile.add(jOpen);
		
		//绑定监听事件，打开电脑中的资源
		jOpen.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
				JFileChooser jfc=new JFileChooser();
				FileNameExtensionFilter fe =new FileNameExtensionFilter("文本文件(txt)","txt");
				jfc.setFileFilter(fe);
				jfc.showDialog(new JLabel(),"选择文件");
				System.out.println(jfc.getSelectedFile().toString());
				OpenFile o = new OpenFile(jfc.getSelectedFile().toString(),ta);
			
			}
		});

		ta= new JTextArea();

		this.add(ta);
		this.setSize(600,600);
		this.setVisible(true);

		this.setTitle("JAVA记事本");

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args){

		Form form =new Form();
	}
	
}
