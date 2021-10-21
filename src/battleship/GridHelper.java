package battleship;

//@{}
public class GridHelper {
	public static int convertLetterToInt(String val) {
		int toReturn = -1;
		switch (val) {
		case "A":
			toReturn = 0;
			break;
		case "B":
			toReturn = 1;
			break;
		case "C":
			toReturn = 2;
			break;
		case "D":
			toReturn = 3;
			break;
		case "E":
			toReturn = 4;
			break;
		case "F":
			toReturn = 5;
			break;
		case "G":
			toReturn = 6;
			break;
		case "H":
			toReturn = 7;
			break;
		case "I":
			toReturn = 8;
			break;
		case "J":
			toReturn = 9;
			break;
		default:
			toReturn = -1;
			break;
		}

		return toReturn;
	}

	public static String convertIntToLetter(int val) {
		String toReturn = "Z";
		switch (val) {
		case 0:
			toReturn = "A";
			break;
		case 1:
			toReturn = "B";
			break;
		case 2:
			toReturn = "C";
			break;
		case 3:
			toReturn = "D";
			break;
		case 4:
			toReturn = "E";
			break;
		case 5:
			toReturn = "F";
			break;
		case 6:
			toReturn = "G";
			break;
		case 7:
			toReturn = "H";
			break;
		case 8:
			toReturn = "I";
			break;
		case 9:
			toReturn = "J";
			break;
		default:
			toReturn = "Z";
			break;
		}

		return toReturn;
	}

	public static int convertUserColToProCol(int val) {
		int toReturn = -1;
		switch (val) {
		case 1:
			toReturn = 0;
			break;
		case 2:
			toReturn = 1;
			break;
		case 3:
			toReturn = 2;
			break;
		case 4:
			toReturn = 3;
			break;
		case 5:
			toReturn = 4;
			break;
		case 6:
			toReturn = 5;
			break;
		case 7:
			toReturn = 6;
			break;
		case 8:
			toReturn = 7;
			break;
		case 9:
			toReturn = 8;
			break;
		case 10:
			toReturn = 9;
			break;
		default:
			toReturn = -1;
			break;
		}

		return toReturn;
	}
	
	public static int convertCompColToRegular(int val) {
		int toReturn = -1;
		switch (val) {
		case 0:
			toReturn = 1;
			break;
		case 1:
			toReturn = 2;
			break;
		case 2:
			toReturn = 3;
			break;
		case 3:
			toReturn = 4;
			break;
		case 4:
			toReturn = 5;
			break;
		case 5:
			toReturn = 6;
			break;
		case 6:
			toReturn = 7;
			break;
		case 7:
			toReturn = 8;
			break;
		case 8:
			toReturn = 9;
			break;
		case 9:
			toReturn = 10;
			break;
		default:
			toReturn = -1;
			break;
		}

		return toReturn;
	}
}
