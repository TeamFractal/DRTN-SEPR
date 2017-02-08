package com.mygdx.game;

public class AiPlayer extends Player {
    public AiPlayer(int i) {
        super(i);
    }

    @Override
    public boolean isAi() {
        return true;
    }

    public void performPhase(GameEngine engine, GameScreen screen) {
        Market market = engine.market();
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

            // Produce resources.
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

                break;
        }
    }
}
