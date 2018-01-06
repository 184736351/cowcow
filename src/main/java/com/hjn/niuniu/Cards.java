package com.hjn.niuniu;

/**
 * 手牌
 * @author 洪捃能
 */
public class Cards {
	int[] cowCards;//有牛的三张牌
	int cowMaxCard=0;//有牛的三张牌中的最大牌，0表示无牛
	//有牛时：剩余2张牌之和；
	//无牛时：5张牌中的最大牌
	int cowNum=0;
	int[] cards;//所有牌
	
	public Cards(int[] cards) {
		this.cards = cards;
		this.cowCards = CowCow.cardCounting(cards);
		this.cowCounting();
	}
	public int[] getCowCards() {
		return cowCards;
	}
	public int getCowNum() {
		return cowNum;
	}
	public int[] getCards() {
		return cards;
	}
	//计算牛几
	private void cowCounting() {
		//有牛
		if(cowCards!=null&&cowCards.length==3){
			for (int i = 0; i < cowCards.length; i++) {
				if(cowCards[i]>this.cowMaxCard){
					this.cowMaxCard=cowCards[i];
				}
			}
			for (int i = 0; i < cards.length; i++) {
				boolean hasFlag=false;
				for (int j = 0; j < cowCards.length; j++) {
					if(cards[i]==cowCards[j]){
						hasFlag=true;
						break;
					}
				}
				if(!hasFlag){
					if(this.cowNum==0){
						this.cowNum=cards[i];
					}else{
						this.cowNum=CowCow.formatRank(this.cowNum)+CowCow.formatRank(cards[i]);
					}
				}
			}
		}else{//没牛
			for (int i = 0; i < cards.length; i++) {
				if(cards[i]>this.cowNum){
					this.cowNum=cards[i];
				}
			}
		}
	}
	public String cowLabel(){
		if(this.cowMaxCard==0){
			return "没牛";
		}else{
			return "牛"+(this.cowNum%10==0?"牛":this.cowNum%10);
			
		}
	}
}
