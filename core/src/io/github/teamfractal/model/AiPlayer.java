package io.github.teamfractal.model;

import io.github.teamfractal.GameEngine;
import io.github.teamfractal.actor.Market;
import io.github.teamfractal.actor.Tile;
import io.github.teamfractal.screens.GameScreen;

import java.util.Random;

public class AiPlayer extends Player {
    static private Random rnd = new Random();

    public AiPlayer(int i) {
        super(i);
    }

    @Override
    public boolean isAi() {
        return true;
    }

    public void performPhase(GameEngine engine, GameScreen screen) {
        Market market = engine.market();

        Trade trade = engine.getCurrentPendingTrade();
        if (trade != null) {
            // trade.oreAmount * trade.energyAmount * trade.foodAmount;
            // Likelihood:
            int total = market.getOreBuyPrice() * trade.oreAmount
                    + market.getEnergyBuyPrice() * trade.energyAmount
                    + market.getFoodBuyPrice() * trade.foodAmount;

            double likelihood = (double)trade.getPrice() / total;

            if (rnd.nextDouble() < likelihood) {
                trade.execute();
                System.out.println("Accept offer.");
            } else {
                System.out.println("Reject offer.");
            }

            engine.closeTrade();
        }


        switch(engine.getPhase()) {
            // Claim land
            case 1:
                for (Tile tile : engine.tiles()) {
                    if (!tile.isOwned()) {
                        engine.selectTile(tile);
                        engine.claimTile();
                        break;
                    }
                }
                break;

            // Buy roboticon
            case 2:
                while(market.getRoboticonStock() > 1 && market.getRoboticonBuyPrice() < getMoney()) {
                    try {
                        System.out.println("AI: Bought a roboticon at price $" + market.getRoboticonBuyPrice());
                        market.buy("roboticon", 1, this);
                    } catch (Exception e) {
                        System.out.println("Can't buy stuff.");
                        break;
                    }
                }
                engine.nextPhase();
                break;

            // Place roboticon
            case 3:
                for (Tile tile : getTileList()) {
                    if (getRoboticonInventory() == 0) {
                        break;
                    }

                    if (!tile.hasRoboticon()) {
                        engine.selectTile(tile);
                        engine.deployRoboticon();
                    }
                }
                engine.nextPhase();
                break;

            // produce resources.
            case 4:
                engine.nextPhase();
                break;

            // Market
            case 5:
                while(getMoney() < market.getRoboticonBuyPrice() + 20) {
                    try {
                        if (getOreCount() > 0) {
                            market.sell("ore", 1, this);
                        }
                        if (getEnergyCount() > 0) {
                            market.sell("energy", 1, this);
                        }
                        if (getFoodCount() > 0) {
                            market.sell("food", 1, this);
                        }

                        if (getEnergyCount() + getOreCount() + getFoodCount() == 0)
                            break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                engine.nextPhase();
                break;
        }
    }
}
