import java.util.Scanner;
import java.util.Random;

public class BlackjackCurrent {
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Let's play some blackjack!");
		System.out.println("The rules are simple:");
		System.out.println("Start out with 2 cards, and you want to get the sum of the cards as close as possible to 21 without going over.");
		System.out.println("Aces count as 1 or 11 (if Ace being 11 causes you to go over 21, then Ace would count as 1), face cards count as 10, and the rest of the numbers count as face value.");
		System.out.println("The goal is to get closer to 21 (without going over) than the dealer.");
		System.out.println("At the beginning of each turn, you wager a certain amount of money. If you lose, it goes to the dealer. If you win, the dealer gives it to you.");
		System.out.println("With each turn, you can choose to \"stand\", which means not get a new card, or you can \"hit\", which means you get a new card.");
		System.out.println("If you choose to \"stand\", the dealer will then take his turn and if your hand is closer to 21 than the dealer's hand or the dealer's hand is more than 21, you win.");
		System.out.println("If you choose to \"hit\" and your hand goes over 21, you go \"bust\", and the dealer wins.");
		System.out.println("If you tie with the dealer, the dealer wins.");
		System.out.println("Got it? Let's go!");
		String[] playerHand = playerStarterHand(2);
		while (playerHand[0] == playerHand[1]) {
			playerHand = playerStarterHand(2);
		}
		String[] dealerHand = playerStarterHand(2);
		while (dealerHand[0] == dealerHand[1]) {
			dealerHand = playerStarterHand(2);
		}
		while (playerHand[0] == dealerHand[0] || playerHand[0] == dealerHand[1]) {
			dealerHand = playerStarterHand(2);
		}
		while (playerHand[1] == dealerHand[0] || playerHand[1] == dealerHand[1]) {
			dealerHand = playerStarterHand(2);
		}
		playGame(playerHand, dealerHand);
	}
	public static void playGame(String[] playerHand, String[] dealerHand) {
		Scanner scnr = new Scanner(System.in);
		int playerChoice;
		int playerSum = playerHandValue(playerHand);
		int dealerSum = playerHandValue(dealerHand);
		System.out.print("How much money do you have? ");
		double playerMoney = scnr.nextDouble();
		while (playerMoney <= 0) {
			System.out.print("Sorry, that's not a valid amount! Enter another amount: ");
			playerMoney = scnr.nextDouble();
		}
		System.out.print("How much money do you want to bet? ");
		double playerBet = scnr.nextDouble();
		while (playerBet > playerMoney) {
			System.out.print("Sorry, you don't have that much! Enter another amount: ");
			playerBet = scnr.nextDouble();
		}
		while (playerBet <= 0) {
			System.out.print("Sorry, that's not a valid amount! Enter another amount: ");
			playerBet = scnr.nextDouble();
		}
		System.out.print("This is your hand: ");
		printArray(playerHand);
		System.out.println();
		System.out.print("This is the dealer's hand: ");
		printDealerHand(dealerHand);
		System.out.println();
		if (playerSum == 21) {
			playerChoice = 1;
		}
		else {
			System.out.print("Would you like to stand or hit? 1 for stand, 2 for hit: ");
			playerChoice = scnr.nextInt();
			while (playerChoice != 1 && playerSum < 21) {
				if (playerChoice == 2) {
					addCardToHand(playerHand);
					for (int i = 0; i < indexOfKey(playerHand, "00") - 1; i++) {
						while (playerHand[i] == playerHand[indexOfKey(playerHand, "00") - 1]) {
							playerHand[indexOfKey(playerHand, "00") - 1] = "00";
							addCardToHand(playerHand);
						}
					}
					System.out.print("This is your new hand: ");
					printArray(playerHand);
					System.out.println();
					playerSum = playerHandValue(playerHand);
				}
				else {
					System.out.print("That wasn't one of the choices! 1 for stand, 2 for hit: ");
					playerChoice = scnr.nextInt();
				}
				if (playerSum < 21) {
					System.out.print("Would you like to stand or hit? 1 for stand, 2 for hit: ");
					playerChoice = scnr.nextInt();
				}
			}
		}
		while (playerSum <= 21 && dealerSum < playerSum) {
			System.out.print("This is the dealer's hand: ");
			printArray(dealerHand);
			System.out.println();
			addCardToHand(dealerHand);
			for (int i = 0; i < indexOfKey(dealerHand, "00") - 1; i++) {
						while (dealerHand[i] == dealerHand[indexOfKey(dealerHand, "00") - 1]) {
							dealerHand[indexOfKey(dealerHand, "00") - 1] = "00";
							addCardToHand(dealerHand);
						}
					}

			dealerSum = playerHandValue(dealerHand);
		}
		System.out.print("This is the dealer's hand: ");
		printArray(dealerHand);
		System.out.println();
		if (playerWin(playerSum, dealerSum)) {
			System.out.println("Congratulations, you beat the dealer!");
			playerMoney += playerBet;
			System.out.println("You now have $" + String.format("%.2f", playerMoney));
		}
		else {
			System.out.println("Sorry, the dealer got you!");
			playerMoney -= playerBet;
			System.out.println("You now have $" + String.format("%.2f", playerMoney));
		}
	}
	public static boolean playerWin(int playerSum, int dealerSum) {
		if (playerSum < 21) {
			if (dealerSum <= 21 && dealerSum >= playerSum) {
				return false;
			}
			else if (dealerSum < playerSum) {
				return true;
			}
			else if (dealerSum > 21) {
				return true;
			}
		}
		if (playerSum == 21) {
			if (dealerSum == 21) {
				return false;
			}
			if (playerSum > dealerSum) {
				return true;
			}
			if (dealerSum > 21) {
				return true;
			}
		}
		if (playerSum > 21) {
			return false;
		}
		return false;
	}
	public static void addCardToHand(String[] hand) {
		Random rng = new Random();
		int index = indexOfKey(hand, "00");
		if (index > -1) {
			int x;
			int y;
			String str1 = "";
			String str2 = "";
			x = rng.nextInt(13);
			y = rng.nextInt(4);
			if (x == 0) {
					str1 = "Ace";
				}
				if (x == 1) {
					str1 = "2";
				}
				if (x == 2) {
					str1 = "3";
				}
				if (x == 3) {
					str1 = "4";
				}
				if (x == 4) {
					str1 = "5";
				}
				if (x == 5) {
					str1 = "6";
				}
				if (x == 6) {
					str1 = "7";
				}
				if (x == 7) {
					str1 = "8";
				}
				if (x == 8) {
					str1 = "9";
				}
				if (x == 9) {
					str1 = "10";
				}
				if (x == 10) {
					str1 = "Jack";
				}
				if (x == 11) {
					str1 = "Queen";
				}
				if (x == 12) {
					str1 = "King";
				}
				if (y == 0) {
					str2 = " of hearts";
				}
				if (y == 1) {
					str2 = " of diamonds";
				}
				if (y == 2) {
					str2 = " of clubs";
				}
				if (y == 3) {
					str2 = " of spades";
				}
				hand[index] = str1.concat(str2);
		}
	}
	public static int indexOfKey(String[] x, String key) {
		int index = -1;
		for (int i = 0; i < x.length; i++) {
			if (x[i].indexOf(key) > -1) {
				index = i;
				return index;
			}
		}
		return index;
	}
	public static void printDealerHand(String[] x) {
		System.out.println(x[0]);
	}
	public static void printArray(String[] x) {
		for (int i = 0; i < x.length; i++) {
			if (!x[i].equals("00")) {
				System.out.print(x[i] + " ");
			}
		}
	}
	public static void printArray(int[] x) {
		for (int i = 0; i < x.length; i++) {
			if (x[i] != 0) {
				System.out.print(x[i] + " ");
			}
		}
	}
	public static int playerHandValue(String[] x) {
		Scanner scnr = new Scanner(System.in);
		int[] value = new int[x.length];
		int sum = 0;
		for (int i = 0; i < x.length; i++) {
			if (x[i].indexOf("0") > -1) {
				value[i] = 0;
			}
			if (x[i].indexOf("Ace") > -1) {
				value[i] = 1;
			}
			if (x[i].indexOf("2") > -1) {
				value[i] = 2;
			}
			if (x[i].indexOf("3") > -1) {
				value[i] = 3;
			}
			if (x[i].indexOf("4") > -1) {
				value[i] = 4;
			}
			if (x[i].indexOf("5") > -1) {
				value[i] = 5;
			}
			if (x[i].indexOf("6") > -1) {
				value[i] = 6;
			}
			if (x[i].indexOf("7") > -1) {
				value[i] = 7;
			}
			if (x[i].indexOf("8") > -1) {
				value[i] = 8;
			}
			if (x[i].indexOf("9") > -1) {
				value[i] = 9;
			}
			if (x[i].indexOf("10") > -1 || x[i].indexOf("Jack") > -1 || x[i].indexOf("Queen") > -1 || x[i].indexOf("King") > -1) {
				value[i] = 10;
			}
			sum += value[i];
		}
		for (int i = 0; i < x.length; i++) {
			if (value[i] == 1) {
				if (sum + 10 <= 21) {
					value[i] = 11;
					sum += 10;
				}
			}
		}
		return sum;
	}
	public static String[] playerStarterHand(int n) {
		Random rng = new Random();
		String[] a = new String[21];
		int x;
		int y;
		String str1 = "";
		String str2 = "";
		for (int i = 0; i < 21; i++) {
			x = rng.nextInt(13);
			y = rng.nextInt(4);
			if (i >= n) {
				str1 = "0";
				str2 = "0";
			}
			else {
				if (x == 0) {
					str1 = "Ace";
				}
				if (x == 1) {
					str1 = "2";
				}
				if (x == 2) {
					str1 = "3";
				}
				if (x == 3) {
					str1 = "4";
				}
				if (x == 4) {
					str1 = "5";
				}
				if (x == 5) {
					str1 = "6";
				}
				if (x == 6) {
					str1 = "7";
				}
				if (x == 7) {
					str1 = "8";
				}
				if (x == 8) {
					str1 = "9";
				}
				if (x == 9) {
					str1 = "10";
				}
				if (x == 10) {
					str1 = "Jack";
				}
				if (x == 11) {
					str1 = "Queen";
				}
				if (x == 12) {
					str1 = "King";
				}
				if (y == 0) {
					str2 = " of hearts";
				}
				if (y == 1) {
					str2 = " of diamonds";
				}
				if (y == 2) {
					str2 = " of clubs";
				}
				if (y == 3) {
					str2 = " of spades";
				}
			}
			a[i] = str1.concat(str2);
		}
		return a;
	}
}