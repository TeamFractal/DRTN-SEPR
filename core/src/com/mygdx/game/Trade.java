package com.mygdx.game;

public class Trade {
	public final int oreAmount;
	public final int energyAmount;
	public final int foodAmount;
	private int price;
	private Player targetPlayer;

	public Trade(int oreAmount, int energyAmount, int foodAmount, int price, Player targetPlayer){
		this.oreAmount = oreAmount;
		this.energyAmount = energyAmount;
		this.foodAmount = foodAmount;
		this.price = price;
		this.targetPlayer = targetPlayer;
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
	
	public void execute(){
		targetPlayer.varyResource("Ore", this.oreAmount);
		targetPlayer.varyResource("Energy", this.energyAmount);
		targetPlayer.varyResource("Food", this.foodAmount);
		targetPlayer.varyResource("Money", -this.price);
	}
	
}
