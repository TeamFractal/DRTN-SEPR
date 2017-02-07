package com.mygdx.game;

public class Trade {
	public final int oreAmount;
	public final int energyAmount;
	public final int foodAmount;
	private int price;
	private Player sender; 
	private Player targetPlayer;

	public Trade(int oreAmount, int energyAmount, int foodAmount, int price,
			Player sender, Player targetPlayer){
		this.oreAmount = oreAmount;
		this.energyAmount = energyAmount;
		this.foodAmount = foodAmount;
		this.price = price;
		this.targetPlayer = targetPlayer;
		this.sender = sender;
	}
	
	public int getPrice(){
		return this.price;
	}
	
	public void setPrice(int newPrice){
		this.price = newPrice;
	}
	
	public Player getTargetPlayer(){
		return targetPlayer;
	}
	
	public Player getSender(){
		return this.sender;
	}
	public void execute(){
		if (targetPlayer.getMoney() > getPrice()){
			targetPlayer.varyResource("Ore", this.oreAmount);
			targetPlayer.varyResource("Energy", this.energyAmount);
			targetPlayer.varyResource("Food", this.foodAmount);
			targetPlayer.varyResource("Money", -this.price);
			sender.varyResource("Ore", -this.oreAmount);
			sender.varyResource("Energy", -this.energyAmount);
			sender.varyResource("Food", -this.foodAmount);
			sender.varyResource("Money", this.price);
		}
	}
	
}
