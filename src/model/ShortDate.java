package model;

import java.util.Arrays;

public class ShortDate {

	private int day;
	private int month;

	public ShortDate() {
		this(1, 1);
	}

	public ShortDate(int day, int month) {
		this.day = day;
		this.month = month;
		findError(day, month);
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public void print() {
		System.out.println(getDay() + "." + getMonth());
	}

	public String toString() {
		return String.format("%d.%d.2014", day, month);
	}

	private boolean getError(int d, int m) {
		if (m < 0 || m > 12) {
			return false;
		} else if (d < 0 || d > getNumberOfDays(m)) {
			return false;
		}
		return true;
	}

	private void findError(int d, int m) {
		if (!getError(d, m)) {
			System.out.print("Error creating ");
			this.print();
		}
	}

	private int getNumberOfDays(int m) {
		return (int) (28 + (m + Math.floor(m / 8)) % 2 + 2 % m + 2 * Math.floor(1 / m));
	}

	public boolean set(int day, int month) {
		if (getError(day, month)) {
			this.day = day;
			this.month = month;
			return true;
		}
		return false;
	}

	public boolean increment() {
		if (day == 31 & month == 12) {
			day = 1;
			month = 1;
			return false;
		}
		if (day + 1 <= getNumberOfDays(month)) {
			day++;
			return true;
		} else if (day == getNumberOfDays(month)) {
			day = 1;
			month++;
			return true;
		}
		return false;
	}

	public String[] getYear() {
		set(1, 1);
		int[] days = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		String arr[] = new String[365];
		for (int m = 0, i = 0; m < 12; m++) {
			for (int d = 1; d <= days[m]; d++) {
				this.increment();
				arr[i++] = this.toString();
			}
		}
		return arr;
	}

	public static void main(String[] args) {
		ShortDate obj = new ShortDate();
		System.out.println(Arrays.toString(obj.getYear()));
	}

}
