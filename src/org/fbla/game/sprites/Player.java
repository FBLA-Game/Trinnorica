package org.fbla.game.sprites;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.fbla.game.Bridge;
import org.fbla.game.boards.GameBoard;
import org.fbla.game.sprites.tools.Bow;
import org.fbla.game.sprites.tools.Key;
import org.fbla.game.sprites.tools.NinjaCloak;
import org.fbla.game.spriteutils.Entity;
import org.fbla.game.spriteutils.Interactable;
import org.fbla.game.spriteutils.Keyable;
import org.fbla.game.spriteutils.Money;
import org.fbla.game.spriteutils.Moveable;
import org.fbla.game.spriteutils.Sprite;
import org.fbla.game.spriteutils.SpriteSubType;
import org.fbla.game.spriteutils.SpriteType;
import org.fbla.game.spriteutils.Tool;
import org.fbla.game.spriteutils.ToolType;
import org.fbla.game.utils.BoardType;
import org.fbla.game.utils.DamageReason;
import org.fbla.game.utils.Direction;
import org.fbla.game.utils.Sound;
import org.fbla.game.utils.Utils;

import res.Audio;
import res.Texture;

public class Player extends Entity implements Moveable,Keyable {

	public int dx;
	public int dy;
	public boolean onground;
	public boolean jumping;
	public boolean falling;
	public boolean climbing;
	public boolean gravity;
	private boolean disabled;
	public boolean shifting;
	public boolean left = false;
	public boolean right = false;
	public boolean up = false;
	private int score = 0;
	private int sscore = 0;
	private Interactable interact = null;
	public int lives = GameBoard.maxlives;	
	private int i;
	public int speedboost = 1;
	public int level = 1;
	int f = 970;
	
	public int rw = 13;
	public int rh = 40;
	public int ww = 30;
	public int wh = 40;
	
	private String model = "yellow";
	public List<Tool> inventory = new ArrayList<>();
	private HashMap<Integer, Integer> jumpInfo = new HashMap<>();
	private HashMap<Integer, Image> images = new HashMap<>();
	private boolean ctrl = false;
	public boolean ready;
	private int jumpboost = 1;

	public Player(int x, int y) {
		super(x, y);
		onground = false;
		jumping = false;
		falling = true;
		gravity = true;
		ready = true;
		initPlayer();
		health= 100;
		maxhealth = 100;
		images.put(0, Texture.loadTexture("playermodels/yellow/stand.png"));
		images.put(1, Texture.loadTexture("playermodels/yellow/walk.gif"));
		images.put(2, Texture.loadTexture("playermodels/yellow/jump.png"));
		
	}

	@Override
	public SpriteType getType() {
		return SpriteType.PLAYER;
	}
	
	
	public void setPlayerModel(String model){
		this.model = model;
	}
	public String getPlayerModel(){
		return model;
	}
	
	

	

	private void initPlayer() {
		Utils.checkPlayerInfo(this);
		loadImage("playermodels/" + model + "/stand.png");
		setImageDimensions(13, 41, -2, -2);
		setDirection(Direction.RIGHT);
		
		Utils.initPlayer(this);
		
	}



	private void openInventory(GameBoard b) {
		Bridge.getGame().openInventory(inventory);
	}

	private void jump() {
		if (jumping || falling)
			return;
		Audio.playSound(Sound.JUMP);
		onground = false;
		jumping = true;
		falling = false;
		jumpInfo.put(1, 1);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(!Bridge.getGame().getBoard().getType().equals(BoardType.GAME_BOARD)) return;
		if(!((GameBoard)Bridge.getGame().getBoard()).paused){
			
			

			int key = e.getKeyCode();
			if(key == KeyEvent.VK_CONTROL){
				ctrl = true;
			}
			if (key == KeyEvent.VK_SPACE) {
				jump();
			}

			if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
				setDirection(Direction.LEFT);
				facing = Direction.LEFT;
				dx = -3;
				walking= true;
				

				
				

			}

			if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
				setDirection(Direction.RIGHT);
				facing = Direction.RIGHT;
				if(!walking && !left && right) x=x-getWalkingWidth();
				dx = 3;
				walking= true;
				
				
				

			}

			
//			if (key == KeyEvent.VK_0) {
//				Audio.playSound(Sound.TEST);
//			}
			
			
			if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
				if (climbing) {
					setDirection(Direction.UP);
					dy = -2;
				}
				if (!gravity)
					dy = -2;
			}

			if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
				if (climbing) {
					setDirection(Direction.DOWN);
					dy = 2;
				}
				if (!gravity)
					dy = 2;
			}
//			if (key == KeyEvent.VK_F) {
//				((GameBoard)Bridge.getGame().getBoard()).toggleGravity();
//			}
			if (key == KeyEvent.VK_H) {
				((GameBoard)Bridge.getGame().getBoard()).toggleHitboxes();
			}
//			if (key == KeyEvent.VK_EQUALS) {
//				speedboost = speedboost + 1;
//			}
//			if (key == KeyEvent.VK_MINUS) {
//				if ((speedboost - 1) >= 1)
//					speedboost = speedboost - 1;
//			}
			if (key == KeyEvent.VK_F3) {
				((GameBoard)Bridge.getGame().getBoard()).toggleDebugMode();
			}
			
			if (key == KeyEvent.VK_SHIFT) {
				if(hasTool() && !shifting && !(tool instanceof Bow)){
					tool.use();
				}
				shifting = true;
			}
			if (key == KeyEvent.VK_E) {
				openInventory(((GameBoard)Bridge.getGame().getBoard()));
			}
			
//			if (key == KeyEvent.VK_O && shifting) {
//
//				((GameBoard)Bridge.getGame().getBoard()).gameStatus = "won:" + level;
//				((GameBoard)Bridge.getGame().getBoard()).ingame = false;
//				x = 0;
//				y = 0;
//			
//			}
			
			if (key == KeyEvent.VK_K) {
				if(shifting){
//					lives = 1;
					ready = false;
					kill(DamageReason.RANDOM);
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(!Bridge.getGame().getBoard().getType().equals(BoardType.GAME_BOARD)) return;
		
		if(!((GameBoard)Bridge.getGame().getBoard()).paused){
			
			if (key == KeyEvent.VK_SHIFT) {
				
				
				if(interact != null){
					if(tool != null)interact.interact(tool);
					else interact.interact();
				}
				shifting = false;
			}
			
			if(key == KeyEvent.VK_CONTROL){
				ctrl = false;
			}

			

			if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
				dx = 0;
				walking= false;
				
				
				if(invisible)
					toggleInvisiblility();
				
			}

			if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
				dx = 0;
				walking= false;
				if(right) x=x+(getRestingWidth()+4);
				if(invisible)
					toggleInvisiblility();
			}

			if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
				dy = 0;
			}

			

			if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
				dy = 0;
			}
			
		}
		if (key == KeyEvent.VK_ESCAPE) {
			if (((GameBoard)Bridge.getGame().getBoard()).paused) {
				((GameBoard)Bridge.getGame().getBoard()).resume();
			} else
				((GameBoard)Bridge.getGame().getBoard()).pause();
		}
	}

	public void setOnGround() {
		onground = true;
		falling = false;
		jumping = false;
	}

	public boolean isOnGround() {
		return onground;
	}

	

	public void levelUp() {
		score = score + sscore;
		sscore = 0;
		Utils.setPlayerLevel(level + 1);
		level = level +1;
		((GameBoard)Bridge.getGame().getBoard()).sprites.clear();
		((GameBoard)Bridge.getGame().getBoard()).clickables.clear();
		((GameBoard)Bridge.getGame().getBoard()).moveables.clear();
		((GameBoard)Bridge.getGame().getBoard()).tools.clear();
		((GameBoard)Bridge.getGame().getBoard()).moveables_temp.clear();
		((GameBoard)Bridge.getGame().getBoard()).removedSprites.clear();
		inventory.clear();
		inventory = new ArrayList<>();
		
		Utils.savePlayerInfo(Bridge.player);
		
//		if(Bridge.DEBUG){
//			inventory.add(new Bow(0,0));
//			inventory.add(new NinjaCloak(0,0));
//			inventory.add(new Key(0, 0, -1));
//		}
		
		
		

		
		((GameBoard)Bridge.getGame().getBoard()).loadLevel();
		((GameBoard)Bridge.getGame().getBoard()).ingame = true;
		
	}

	public void setLevel(int level) {
		this.level = level;
		((GameBoard)Bridge.getGame().getBoard()).ingame = true;
		((GameBoard)Bridge.getGame().getBoard()).loadLevel();
	}

	public int getLevel() {
		return level;
	}

	@Override
	public void move() {
		
		
			if(walking){
				if(getImage() != images.get(1)){
					loadImage(Texture.loadTexture("playermodels/yellow/walk.gif"));
					setImageDimensions(rw, rh, -2, -2);
				}
				
			} else {
				if(getImage() != images.get(0)){
					loadImage(Texture.loadTexture("playermodels/yellow/stand.png"));
					setImageDimensions(rw, rh, -2, -2);
				}
				
			
		}
		
		if(!walking) setImageDimensions(rw, rh, -2, -2);
		else setImageDimensions(ww, wh, -2, -2);
		
		if(ctrl) speedboost = 2;
		else speedboost = 1;
		if (disabled)
			return;
		Utils.checkPlayerInfo(this);
		if (!((GameBoard)Bridge.getGame().getBoard()).ingame) {
			return;
		}
		if (y >= 650*((GameBoard) Bridge.getGame().getBoard()).extra) {
			kill(DamageReason.VOID);
		}
		
		
		
		
		
		if (gravity) { 

			onground = false;
			up = false;
			left = false;
			right = false;
			climbing = false;
			
			Rectangle polygon = getPolygon().getBounds();
			polygon.setBounds(x, y, width+2, height+1);
			
			

			try {
				for (Sprite sprite : ((GameBoard)Bridge.getGame().getBoard()).sprites) {
					if(sprite instanceof Player) continue;
					if (!polygon.intersects(sprite.getPolygon().getBounds()))continue;
					if(!Utils.intersects(polygon, sprite.getPolygon())) continue;
					if(sprite.getType().getSubType().equals(SpriteSubType.PROJECTILE)) continue;
					
					if (sprite instanceof Money) {
						climbing = false;
						Money money = (Money) sprite;
						addScore(money.getValue());
						
						sprite.remove();
						Utils.player_score = score;
					}
					if(sprite instanceof Tool){
						setTool(((Tool) sprite));
						Audio.playSound(Sound.TOOL);
						inventory.add(((Tool) sprite));
					}
					if(sprite.getSubType().equals(SpriteSubType.BAD_THINGS)){
						damage(5, DamageReason.BAD_THINGS);
						dy=0;
						jump();
					}
					if(sprite.getSubType().equals(SpriteSubType.INTERACTABLE)){
						interact = (Interactable)sprite;
						Utils.displayMessage(new Random().nextInt(), "Press \"SHIFT\" to interact", (Bridge.getGameBoardSize(0)/2) - (Bridge.getGame().getGraphics().getFontMetrics().stringWidth("Press \"SHIFT\" to interact")/2), (Bridge.getGameBoardSize(1))-Bridge.getGameBoardSize(1)/7, 3, "#FFFFFF", 20,Bridge.getGame().getFont());
//						Utils.addPlayerMessage(new Random().nextInt(), "Press \"SHIFT\" to interact", 30, 20, 210, "#000000", 15);
					} else interact = null;
					if (sprite.getSubType().equals(SpriteSubType.PARTIAL_COLLIDEABLE) && !jumping) {
						switch(getIntercectingDirection(sprite.getPolygon().getBounds())){
						case DOWN:
							if(sprite.getType().equals(SpriteType.FALLING_FLOOR))
								if(!((FallingFloor) sprite).t) ((FallingFloor) sprite).startFalling();
							falling = false;
							
							if(!climbing)y=sprite.getY()-getHeight();
							onground = true;
							break;
						default:
							break;
						}
					}

					
					if (sprite.getType().getSubType().equals(SpriteSubType.CLIMABLE) && !jumping) {
						climbing = true;
						if (falling)
							dy = 0;
						falling = false;
					} else {
						climbing = false;
					}
					if(sprite.getType().equals(SpriteType.GATE)){
						((GameBoard)Bridge.getGame().getBoard()).gameStatus = "won:" + level;
						((GameBoard)Bridge.getGame().getBoard()).ingame = false;
//						Audio.playSound(Sound.LEVELUP);
						x = 0;
						y = 0;
					}
					
					if(sprite.getSubType().equals(SpriteSubType.COLLIDEABLE) || sprite.getSubType().equals(SpriteSubType.INTERACTABLE) || sprite.getSubType().equals(SpriteSubType.ENEMY)){
						if(sprite.getSubType().equals(SpriteSubType.ENEMY)){
							kill(DamageReason.ENEMY);
						}
						
						if(sprite instanceof Box){
							if(!walking && (((Box)sprite).falling || ((Box)sprite).onground)) ((Box) sprite).stop(); else
							((Box) sprite).bounce(this);
							continue;
						}
						
						switch(getIntercectingDirection(sprite.getPolygon().getBounds())){
						case LEFT:
							if(!onground) onground = false;
							left = true;
							break;
						case RIGHT:
							if(!onground) onground = false;
							right = true;
							break;
						case DOWN:
							onground = true;
							y=sprite.getY()-(height-1);
							falling = false;
							
							break;
						case UP:
							falling = true;
							jumping = false;
							break;
							default:
								left = false;
								right = false;
								
								if(!jumping){
									falling = true;
								}
								break;
						}
					}
				}

			} catch (Exception e1) {
				if(!onground && !jumping && !climbing) falling = true;

			}


		
			if (climbing) {
				x += dx * (speedboost);
				y += dy;
				if (x < 1)
					x = 1;
				
				if (x > f - width)
					x = f - width;
				return;
			}
			if(!climbing && !falling &!jumping && !onground) falling = true;
			

			if (onground)
				dy = 0;
			if (falling){
				dy = 4;
			}
			if (jumping) {
				if (jumpInfo.get(1) == 1) {
					if (i == 0)
						i = 1;
					dy = -4;
					jumpInfo.put(2, i + 1);
					i = i + 1;
					if (i >= 20) {
						dy = 0;
						if (i >= 25) {
							jumpInfo.put(1, 2);
							jumpInfo.put(2, 0);
							i = 0;
						}
					}
				}
				if (jumpInfo.get(1) == 2) {
					jumping = false;
					falling = true;
				}
			}

			if (getDirection().equals(Direction.LEFT) && left) {
				dx = 0;
			}
			if (getDirection().equals(Direction.RIGHT) && right) {
				dx = 0;
			}
			if (getDirection().equals(Direction.LEFT) && !left && walking) {
				dx = -2;
			}
			if (getDirection().equals(Direction.RIGHT) && !right && walking) {
				dx = 2;
			}
			
		}

		x += dx * speedboost;
		y += dy * jumpboost;

		if (x < 1)
			x = 1;
		if (y < 1)
			y = 1;
		
		if (x > f - width)
			x = f - width;
		
	}

	

	public void addScore(int value) {
		sscore = sscore + value;
		Audio.playSound(Sound.SCORE);
	}
	
	public int getScore(){
		return score + sscore;
	}
	
	public void setScore(int s){
		score = s;
	}

	@Override
	public void kill(DamageReason reason) {
		lives = lives - 1;
		((GameBoard) Bridge.getGame().getBoard()).loadLevel();
		sscore = 0;
		health = maxhealth;
		if (lives <= 0) {
			((GameBoard)Bridge.getGame().getBoard()).gameStatus = "gameover:" + reason.getMessage();
			((GameBoard)Bridge.getGame().getBoard()).ingame = false;
		}
		ready = true;
		
		interact();
	}

	@Override
	public void disable() {
		disabled = true;
	}

	@Override
	public void enable() {
		disabled = false;
	}

	public int getRestingWidth() {
		return rw;
	}
	public int getRestingHeight() {
		return rh;
	}
	public int getWalkingWidth() {
		return ww;
	}
	public int getWalkingHeight() {
		return wh;
	}

	public void toggleJumpBoost() {
		if(jumpboost  == 1){
			jumpboost = 2;
			return;
		} else {
			jumpboost = 1;
		}
	}

	public void mouseClicked(MouseEvent e) {
		if(hasTool() && tool instanceof Bow && shifting){
			tool.use();
		}
	}


	
	
}