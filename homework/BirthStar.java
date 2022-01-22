package com.ssafy.ws05;

public class BirthStar {
	public static String[] Star = { "¿°¼Ò", "¹°º´", "¹°°í±â", "¾ç", "È²¼Ò", "½ÖµÕÀÌ", "°Ô", "»çÀÚ", "Ã³³à", "ÃµÄª", "Àü°¥", "»ç¼ö" };

	public static String getStar(int month, int day) {
		switch (month) {
		case 1:
			return Star[1];
		case 2:
			if (day < 20)
				return Star[1];
			if (day >= 20)
				return Star[2];

		case 3:
			if (day < 21)
				return Star[2];
			if (day >= 21)
				return Star[3];
		case 4:
			if (day < 21)
				return Star[3];
			if (day >= 21)
				return Star[4];
		case 5:
			if (day < 22)
				return Star[4];
			if (day >= 22)
				return Star[5];
		case 6:
			if (day < 22)
				return Star[5];
			if (day >= 22)
				return Star[6];
		case 7:
			if (day < 24)
				return Star[6];
			if (day >= 24)
				return Star[7];
		case 8:
			if (day < 24)
				return Star[7];
			if (day >= 24)
				return Star[8];
		case 9:
			if (day < 24)
				return Star[8];
			if (day >= 24)
				return Star[9];
		case 10:
			if (day < 24)
				return Star[9];
			if (day >= 24)
				return Star[10];
		case 11:
			if (day < 23)
				return Star[10];
			if (day >= 23)
				return Star[11];
		case 12:
			if (day < 23)
				return Star[11];
			if (day >= 23)
				return Star[0];

		}
		return null;
	}

}
