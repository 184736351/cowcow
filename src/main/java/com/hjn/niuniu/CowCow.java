package com.hjn.niuniu;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 牛牛算法
 * @author 洪捃能
 */
public class CowCow {
	// 52张牌，百位数表示花色：4黑桃>3红心>2梅花>1方块
	public static final int[] CARDS = new int[52];
	//初始化52张牌
	static{
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				CARDS[i*13+j]=(i+1)*100+(j+1);
			}
		}
		
//		for (int i = 0; i < CARDS.length; i++) {
//			System.out.print(CARDS[i]+" ");
//			if ((i+1)%13==0) {
//				System.out.println("");
//			}
//		}
	}
	/**
	 * 发牌
	 * 根据参与人数随机获取纸牌,每人持的牌不能重复
	 * @param peopleNum 参与人数
	 * @return 每人5张牌，二维数组[人索引][牌索引]
	 */
	public static int[][] deal(int peopleNum) {
		if (peopleNum < 2) {
			return null;
		}
		Random random = new Random();
		Set<Integer> qc = new HashSet<Integer>();
		int[][] peopleCards = new int[peopleNum][5];
		for (int i = 0; i < peopleNum; i++) {
			for (int j = 0; j < 5; j++) {
				int card = CARDS[random.nextInt(52)];
				while (qc.contains(card)) {
					card = CARDS[random.nextInt(52)];
				}
				peopleCards[i][j] = card;
				qc.add(card);
			}
		}
		qc=null;
		return peopleCards;
	}
	/**
	 * 计算有牛的三张牌
	 * 能被10,20,30整除
	 * @param cards 5张牌数组
	 * @return 
	 */
	public static int[] cardCounting(int[] cards){
		if(cards==null||cards.length!=5){
			return null;
		}
		Arrays.sort(cards);
		for (int k = 2; k < cards.length-1; k++) {
			for (int i = 0; i < cards.length; i++) {
				for (int j = 0; j < cards.length-i-k; j++) {
					int cow=formatRank(cards[i])+formatRank(cards[i+k-1])+formatRank(cards[i+j+k]);
					if(cow%10==0){
						return new int[]{cards[i],cards[i+k-1],cards[i+j+k]};
					}
				}
			}
		}
		int cow=formatRank(cards[0])+formatRank(cards[3])+formatRank(cards[4]);
		if(cow%10==0){
			return new int[]{cards[0],cards[3],cards[4]};
		}
		return null;
	}
	public static int formatRank(int card){
		return card%100>10?10:card%100;
	}
	
	/**
	 * 纸牌点数(花色+点数)
	 * @return 如黑桃3,梅花Q
	 */
	public static String cardLabel(int card) {
		if (card < 101 || card > 413) {
			return null;
		}
		int hs = card / 100;
		int ds = card % 100;
		String cardNo = "";
		switch (hs) {
		case 1:
			cardNo = "♦方块";
			break;
		case 2:
			cardNo = "♣梅花";
			break;
		case 3:
			cardNo = "♥红心";
			break;
		case 4:
			cardNo = "♠黑桃";
			break;
		}
		switch (ds) {
		case 1:
			cardNo += "A ";
			break;
		case 11:
			cardNo += "J ";
			break;
		case 12:
			cardNo += "Q ";
			break;
		case 13:
			cardNo += "K ";
			break;
		default:
			cardNo += (ds<10?ds+" ":ds);
			break;
		}
		return cardNo;
	}
	
	public static void main(String[] args) {
		int peopleNum=5;
		for (int k = 0; k < 2; k++) {
			System.out.println("《第1"+k+"局》参与人数："+peopleNum+"人");
			int[][] peopleCards = deal(peopleNum);
			for (int i = 0; i < peopleCards.length; i++) {
				System.out.print(i==0?"【庄  家】：":"【闲家"+i+"】：");
				for (int j = 0; j < peopleCards[i].length; j++) {
					System.out.print(cardLabel(peopleCards[i][j]) + "  ");
				}
				Cards card=new Cards(peopleCards[i]);
				System.out.print("==> "+card.cowLabel());
				System.out.println("");
			}
			System.out.println("");
		}
		
	}
}
