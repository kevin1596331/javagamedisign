package 期末作業;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class 遊戲設計期末 extends JFrame implements KeyListener{
	int WIDTH=9,HEIGHT=8;
	int stage=1;
	int SIZE_OF_LINEBORDER=3;
	String ARROW_UP="↑",ARROW_LEFT="←",ARROW_DOWN="↓",ARROW_RIGHT="→",ENTER="Enter";
	Border redBorder=new LineBorder(Color.RED,SIZE_OF_LINEBORDER);
	Border blueBorder=new LineBorder(Color.BLUE,SIZE_OF_LINEBORDER);
	
	int[][] squaresset=squares(stage);
	int[] squareamount=squaresint(stage);
	int squaretotal=squarestotalint(stage);
	boolean[] squaresused=squaresuse(stage);
	int currentsquare=0;
	boolean nextstage=false;
	
	int current_position=35;
	JButton defaultButton=new JButton();
	JButton[] mainButtons;
		
	public 遊戲設計期末() {
		this.initFrame();
		System.out.println("請切換成英文輸入法來遊玩");
		System.out.println("遊戲操作：");
		System.out.println("移動：方向鍵");
		System.out.println("確定方塊位置：空白鍵");
		System.out.println("切換方塊：T");
		System.out.println("重新本關卡：R");
		System.out.println("下一關：N(僅限過關時)");
		JPanel center_jp=this.createCenterPanel();

		String filepath = "music/music.wav";
		//音樂來源:自行編曲
		musicStuff musicObject = new musicStuff();
		musicObject.playMusic(filepath);
		
		this.createBgcolorMap();
		this.add(center_jp,BorderLayout.CENTER);
		this.setFocusable(true);
		this.addKeyListener(this);
	}
	//背景音樂播放
	public class musicStuff {
		void playMusic(String musicLocation)
		{
			try
			{
				File musicPath = new File(musicLocation);
				
				if(musicPath.exists())
				{
					AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
					Clip clip = AudioSystem.getClip();
					clip.open(audioInput);
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
				else
				{
					
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}

	}
	
	//設定背景
	public void createBgcolorMap() {
		// TODO Auto-generated method stub
		String bgcolor=stages(stage);
		for(int i=0;i<bgcolor.length();i++) {
			switch(bgcolor.charAt(i)) {
			case ' ':
				mainButtons[i].setBackground(new Color(255,239,213));
				break;
			case 'B':
				mainButtons[i].setBackground(Color.BLACK);
				break;
			case 'b':
				mainButtons[i].setBackground(Color.DARK_GRAY);
				break;
			case 'w':
				mainButtons[i].setBackground(Color.WHITE);
				break;
			default:
				System.out.println("背景設定錯誤");
				break;
			}
			
		}
	}

	//設定視窗
	public void initFrame() {
		this.setTitle("第"+stage+"關");
		this.setSize(400,450);
		this.setLocation(100,100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	}
	
	//製作按鈕
	private JPanel createCenterPanel() {
		JPanel jp=new JPanel();
		GridLayout gl=new GridLayout(WIDTH,HEIGHT);
		jp.setLayout(gl);
		mainButtons=new JButton[WIDTH*HEIGHT];
		
		
		
		for(int i=0;i<mainButtons.length;i++) {
			mainButtons[i]=new JButton();
			mainButtons[i].setEnabled(false);
			jp.add(mainButtons[i]);
		}
		for(int i=0;i<squareamount[currentsquare];i++) {
			mainButtons[current_position+squaresset[currentsquare][i]].setBorder(redBorder);
		}
		mainButtons[currentsquare].setBorder(blueBorder);
		for(int i=0;i<squaretotal;i++) {
			mainButtons[i].setText(""+(i+1));
		}
		return jp;
	}

	//main
	public static void main(String[] args) {
		遊戲設計期末 program=new 遊戲設計期末();
		program.setVisible(true);
	}
	
	//重制遊戲
	public int restartgame(){
		int new_position;
		currentsquare=0;
		JPanel center_jp=this.createCenterPanel();
		this.setTitle("第"+stage+"關");
		this.createBgcolorMap();
		this.add(center_jp,BorderLayout.CENTER);
		squaresused=squaresuse(stage);
		new_position=35;
		return new_position;
	}
	
	//方塊設計
	public static int[][] squares(int stage){
		int[][] data1= {{-1,0,1,9},{-8,0,7,8},{-9,-1,0,1},{-8,-7,0,8}};
		int[][] data2= {{-2,-1,0,1,9},{-2,-1,0,1},{-1,0,1},{-8,0,1},{-1,0,1,9}};
		int[][] data3= {{-1,0,1,8},{0,8},{-8,0,1},{0,1},{-8,-7,0,1,8}};
		int[][] data4= {{-1,0,1},{-8,-1,0,1},{0},{-1,0,1,9},{-8,-7,0,8,9},{-1,0,1,7},{0,8},{-8,-1,0,7,8}};
		int[][] data5= {{-8,-1,0},{0,1},{0,1,8,9},{-1,0,8},{-1,0,8,9},{0,1,8},{0}};
		int[][] data6= {{-8,-1,0,8},{-1,0,1},{0,1},{-2,-1,0,1},{-8,-1,0,1}};
		int[][] data7= {{0,1,7,8},{-8,0,1},{0,1,7,8},{0,1,8},{-1,0,8,9},{0,1},{-8,-1,0,8}};
		int[][] data8= {{-1,0,1,9},{-8,-1,0,7},{0,1,8,9},{-7,-1,0,1},{-16,-8,0,8},{-8,-7,-1,0},{-8,-1,0,1}};
		int[][] data9= {{0,8},{0,1,8},{-8,0,1},{-8,0,7,8},{0},{-1,0,1}};
		int[][] data10= {{0,1,8},{-9,-8,0,7,8},{-8,0,8},{-1,0,1,9},{0},{-1,0,8},{0,1}};

		
		if(stage==1) return data1;
		if(stage==2) return data2;
		if(stage==3) return data3;
		if(stage==4) return data4;
		if(stage==5) return data5;
		if(stage==6) return data6;
		if(stage==7) return data7;
		if(stage==8) return data8;
		if(stage==9) return data9;
		if(stage==10) return data10;
		return null;
	}
	
	//方塊大小數量
	public static int[] squaresint(int stage){
		int[] data1= {4,4,4,4};
		int[] data2= {5,4,3,3,4};
		int[] data3= {4,2,3,2,5};
		int[] data4= {3,4,1,4,5,4,2,5};
		int[] data5= {3,2,4,3,4,3,1};
		int[] data6= {4,3,2,4,4};
		int[] data7= {4,3,4,3,4,2,4};
		int[] data8= {4,4,4,4,4,4,4};
		int[] data9= {2,3,3,4,1,3};
		int[] data10= {3,5,3,4,1,3,2};		
		
		if(stage==1) return data1;
		if(stage==2) return data2;
		if(stage==3) return data3;
		if(stage==4) return data4;
		if(stage==5) return data5;
		if(stage==6) return data6;
		if(stage==7) return data7;
		if(stage==8) return data8;
		if(stage==9) return data9;
		if(stage==10) return data10;
		return null;
	}
	
	//方塊總數量
	public static int squarestotalint(int stage){
		int data1= 4;
		int data2= 5;
		int data3= 5;
		int data4= 8;
		int data5= 7;
		int data6= 5;
		int data7= 7;
		int data8= 7;
		int data9= 6;
		int data10= 7;
		
		if(stage==1) return data1;
		if(stage==2) return data2;
		if(stage==3) return data3;
		if(stage==4) return data4;
		if(stage==5) return data5;
		if(stage==6) return data6;
		if(stage==7) return data7;
		if(stage==8) return data8;
		if(stage==9) return data9;
		if(stage==10) return data10;
		return 0;
	}
	
	//方塊使用
	public static boolean[] squaresuse(int stage){
		boolean[] data1= {false,false,false,false};
		boolean[] data2= {false,false,false,false,false};
		boolean[] data3= {false,false,false,false,false};
		boolean[] data4= {false,false,false,false,false,false,false,false};
		boolean[] data5= {false,false,false,false,false,false,false};
		boolean[] data6= {false,false,false,false,false};
		boolean[] data7= {false,false,false,false,false,false,false};
		boolean[] data8= {false,false,false,false,false,false,false};
		boolean[] data9= {false,false,false,false,false,false};
		boolean[] data10= {false,false,false,false,false,false,false};

		
		
		if(stage==1) return data1;
		if(stage==2) return data2;
		if(stage==3) return data3;
		if(stage==4) return data4;
		if(stage==5) return data5;
		if(stage==6) return data6;
		if(stage==7) return data7;
		if(stage==8) return data8;
		if(stage==9) return data9;
		if(stage==10) return data10;
		return null;
	}
	
	//關卡設計
	public static String stages(int stage){
		String bgcolor1="        "+
            	        "BBBBBBBB"+
            	        "BbbbbbbB"+
             	        "BbwwwwbB"+
             	        "BbwwwwbB"+
             	        "BbwwwwbB"+
             	        "BbwwwwbB"+
            	        "BbbbbbbB"+
            	        "BBBBBBBB";
		
		String bgcolor2="        "+
						"BBBBBBBB"+
						"BbbwwwwB"+
						"BwwwwbbB"+
						"BwwwwbbB"+
						"BbwwbbbB"+
						"BbwwbbbB"+
						"BwwwbbbB"+
						"BBBBBBBB";
		
		String bgcolor3="        "+
						"BBBBBBBB"+
						"BbbbbwwB"+
						"BbbbwwwB"+
						"BbbwwwbB"+
						"BbwwwbbB"+
						"BwwwbbbB"+
						"BwwbbbbB"+
						"BBBBBBBB";
		
		String bgcolor4="        "+
						"BBBBBBBB"+
						"BwwwwwwB"+
						"BwbbwbwB"+
						"BwwwbwwB"+
						"BwwwbwwB"+
						"BwbbwbwB"+
						"BwwwwwwB"+
						"BBBBBBBB";
		
		String bgcolor5="        "+
						"BBBBBBBB"+
						"BwwbbwwB"+
						"BbwwwwbB"+
						"BbbwwbbB"+
						"BbbwwbbB"+
						"BbwwwwbB"+
						"BwwbbwwB"+
						"BBBBBBBB";
		
		String bgcolor6="        "+
						"BBBBBBBB"+
						"BbbbbbbB"+
						"BbwwwwwB"+
						"BbbbbwwB"+
						"BbbwwwwB"+
						"BbbbbwbB"+
						"BbwwwwwB"+
						"BBBBBBBB";
		
		String bgcolor7="        "+
						"BBBBBBBB"+
						"BbwwwwbB"+
						"BwwwwbbB"+
						"BbwwwwbB"+
						"BbbwwwwB"+
						"BbwwwwbB"+
						"BwwwwbbB"+
						"BBBBBBBB";
		
		String bgcolor8="        "+
						"BBBBBBBB"+
						"BbbwwwwB"+
						"BbwwwbwB"+
						"BbwwwwwB"+
						"BbwwwwwB"+
						"BwwwwwwB"+
						"BwwwbbwB"+
						"BBBBBBBB";
		
		String bgcolor9="        "+
						"BBBBBBBB"+
						"BbbwwbbB"+
						"BbbwwbbB"+
						"BbbwwbbB"+
						"BbwwwwbB"+
						"BwwbbwwB"+
						"BwbbbbwB"+
						"BBBBBBBB";
		
		String bgcolor10="        "+
						 "BBBBBBBB"+
						 "BwwwwwwB"+
						 "BbwwwwwB"+
						 "BbbwwwwB"+
						 "BbbbwwwB"+
					 	 "BbbbbwwB"+
						 "BbbbbbwB"+
						 "BBBBBBBB";
		if(stage==1) return bgcolor1;
		if(stage==2) return bgcolor2;
		if(stage==3) return bgcolor3;
		if(stage==4) return bgcolor4;
		if(stage==5) return bgcolor5;
		if(stage==6) return bgcolor6;
		if(stage==7) return bgcolor7;
		if(stage==8) return bgcolor8;
		if(stage==9) return bgcolor9;
		if(stage==10) return bgcolor10;
		else return null;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		boolean isMove=true;
		boolean canplace=true;
		int count=0;
		int new_position=current_position;
		Border defaultBorder=defaultButton.getBorder();
		switch(evt.getKeyCode()) {
			case KeyEvent.VK_N:
				if(nextstage==true) {
					stage+=1;
					if(stage==11) stage=1;
					squaresset=squares(stage);
					squareamount=squaresint(stage);
					squaretotal=squarestotalint(stage);
					currentsquare=0;
					current_position=35;
					nextstage=false;
					new_position=restartgame();
				}
				break;
			case KeyEvent.VK_T:
				if(nextstage==true) {
					isMove=false; 
					break;
				}
				for(int i=0;i<squareamount[currentsquare];i++) {
					mainButtons[current_position+squaresset[currentsquare][i]].setBorder(defaultBorder);
				}
				mainButtons[currentsquare].setBorder(defaultBorder);
				currentsquare++;
				if(currentsquare==squaretotal) currentsquare=0;
				while(squaresused[currentsquare]) {
					currentsquare++;
					if(currentsquare==squaretotal) currentsquare=0;
				}
				mainButtons[currentsquare].setBorder(blueBorder);
				break;
			case KeyEvent.VK_R:
				if(nextstage==true) {
					isMove=false; 
					break;
				}
				new_position=restartgame();
				break;
			case KeyEvent.VK_UP:
				if(nextstage==true) {
					isMove=false; 
					break;
				}
				for(int i=0;i<squareamount[currentsquare];i++) {
					if(mainButtons[current_position+squaresset[currentsquare][i]-8].getBackground()==Color.BLACK) {
						isMove=false; 
						break;
					}
				}
				new_position-=8;
				break;
			case KeyEvent.VK_LEFT:
				if(nextstage==true) {
					isMove=false; 
					break;
				}
				for(int i=0;i<squareamount[currentsquare];i++) {
					if(mainButtons[current_position+squaresset[currentsquare][i]-1].getBackground()==Color.BLACK) {
						isMove=false; 
						break;
					}
				}
				new_position-=1;
				break;
			case KeyEvent.VK_DOWN:
				if(nextstage==true) {
					isMove=false; 
					break;
				}
				for(int i=0;i<squareamount[currentsquare];i++) {
					if(mainButtons[current_position+squaresset[currentsquare][i]+8].getBackground()==Color.BLACK) {
						isMove=false; 
						break;
					}
				}
				new_position+=8;
				break;
			case KeyEvent.VK_RIGHT:
				if(nextstage==true) {
					isMove=false; 
					break;
				}
				for(int i=0;i<squareamount[currentsquare];i++) {
					if(mainButtons[current_position+squaresset[currentsquare][i]+1].getBackground()==Color.BLACK) {
						isMove=false; 
						break;
					}
				}
				new_position+=1;
				break;
			case KeyEvent.VK_SPACE:
				if(nextstage==true) {
					isMove=false; 
					break;
				}
				for(int i=0;i<squareamount[currentsquare];i++) {
					if(mainButtons[current_position+squaresset[currentsquare][i]].getBackground()!=Color.WHITE) {
						canplace=false;
						break;
					}					
				}
				if(canplace==true) {
					for(int i=0;i<squareamount[currentsquare];i++) {
						if(currentsquare==0) mainButtons[current_position+squaresset[currentsquare][i]].setBackground(new Color(227,23,13));
						else if(currentsquare==1) mainButtons[current_position+squaresset[currentsquare][i]].setBackground(new Color(255,153,18));
						else if(currentsquare==2) mainButtons[current_position+squaresset[currentsquare][i]].setBackground(new Color(255,215,0));
						else if(currentsquare==3) mainButtons[current_position+squaresset[currentsquare][i]].setBackground(new Color(127,255,0));
						else if(currentsquare==4) mainButtons[current_position+squaresset[currentsquare][i]].setBackground(new Color(30,144,255));
						else if(currentsquare==5) mainButtons[current_position+squaresset[currentsquare][i]].setBackground(new Color(25,25,112));
						else if(currentsquare==6) mainButtons[current_position+squaresset[currentsquare][i]].setBackground(new Color(135,38,87));
						else if(currentsquare==7) mainButtons[current_position+squaresset[currentsquare][i]].setBackground(new Color(128,112,214));
						
					}
					
					squaresused[currentsquare]=true;
					mainButtons[currentsquare].setBackground(Color.GRAY);
					
					for(int i=0;i<squareamount[currentsquare];i++) {
						mainButtons[current_position+squaresset[currentsquare][i]].setBorder(defaultBorder);
					}
					mainButtons[currentsquare].setBorder(defaultBorder);
					currentsquare++;
					if(currentsquare==squaretotal) currentsquare=0;
					while(squaresused[currentsquare]) {
						count++;
						if(count>squaretotal) break;
						currentsquare++;
						if(currentsquare==squaretotal) currentsquare=0;
					}
					for(int i=0;i<squareamount[currentsquare];i++) {
						if(count>squaretotal) break;
						mainButtons[current_position+squaresset[currentsquare][i]].setBorder(redBorder);
					}
					if(count<=squaretotal) mainButtons[currentsquare].setBorder(blueBorder);
				}
				isMove=false;
				break;
			
			default:
				isMove=false;
				break;
		}
		if(isMove) {
			
			for(int i=0;i<squareamount[currentsquare];i++) {
				mainButtons[current_position+squaresset[currentsquare][i]].setBorder(defaultBorder);
			}
			for(int i=0;i<squareamount[currentsquare];i++) {
				mainButtons[new_position+squaresset[currentsquare][i]].setBorder(redBorder);
			}
			
			current_position=new_position;
		}
		if(count>squaretotal) {
			nextstage=true;
			if(stage==10) {
				mainButtons[34].setText("恭");
				mainButtons[35].setText("喜");
				mainButtons[36].setText("過");
				mainButtons[37].setText("關");
				mainButtons[42].setText("按");
				mainButtons[43].setText("[");
				mainButtons[44].setText("N");
				mainButtons[45].setText("]");
				mainButtons[50].setText("重");
				mainButtons[51].setText("頭");
				mainButtons[52].setText("開");
				mainButtons[53].setText("始");
			}
			else {
				mainButtons[34].setText("按");
				mainButtons[35].setText("[");
				mainButtons[36].setText("N");
				mainButtons[37].setText("]");
				mainButtons[43].setText("下");
				mainButtons[44].setText("一");
				mainButtons[45].setText("關");
			}
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
