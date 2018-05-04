package edittext;

import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * <p color=blue size=5>Title:JabOut</p>
 * <p color=blue size=5>Description:关于对话框</p>
 * <p color=blue size=5>Company:</p>
 * <p color=blue size=5>@author Bob Simon</p>
 * <p color=blue size=5>2017-11-24 下午09:32:15</p>
 */
public class JabOut extends JDialog{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public JabOut(){

		this.setTitle("关于");

		this.add(new JLabel("作者 余楚波"));

		this.setSize(250,100);

		this.setVisible(true);
	}

}
