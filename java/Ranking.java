
public class Ranking {

	private int rank;			//Rank
	private String character;	//Character
	private int level;			//Level
	private int days;			//Days
	private String initials;	//Initials
	
	public Ranking() {
		rank = -1;
		character = "";
		level = -1;
		days = -1;
		initials = "";
	}
	
	public Ranking(int r, String c, int l, int d, String i) {
		rank = r;
		character = c;
		level = l;
		days = d;
		initials = i;
	}
	
	public Ranking(String c, int l, int d, String i) {
		character = c;
		level = l;
		days = d;
		initials = i;
	}

	public void setRank(int r) {
		rank = r;
	}
	
	public int getRank() {
		return rank;
	}
	
	public String getCharacter() {
		return character;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getDays() {
		return days;
	}
	
	public String getInitials() {
		return initials;
	}
	
	public String toString() {
		return "[" + rank + "]\t" + character + "\t(Lv." + level + ")\t" + days + "\t" + initials;
	}
	
}
