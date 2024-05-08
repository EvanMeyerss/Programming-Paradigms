# Name: Evan Meyers
# Description: Convert Assignment 5 Java Pacman game to Python code
# Date: 4/25/2024

import pygame
import time
import json
import math
import random

from pygame.locals import*
from time import sleep

class Sprite():
	def __init__(self, x, y, w, h):
		self.x = x
		self.y = y
		self.w = w
		self.h = h
		self.isValid = True

	def update(self):
		pass

	def draw(self, yFixed):
		print("No child draw method")
	
	def doesItCollide(self, otherSprite):
		# Right side of self checking
		if self.x >= otherSprite.x + otherSprite.w:
			return False
		# Left side of self checking
		if self.x + self.w <= otherSprite.x:
			return False
		# Bottom of self checking
		if self.y + self.h <= otherSprite.y:
			return False
		# Top of self checking
		if self.y >= otherSprite.y + otherSprite.h:
			return False
		return True
	
	# Checks if sprite exists at specific coordinates
	def spriteExists(self, x1, y1):
		if(self.x == x1 and self.y == y1):
			return True
		return False

	# States if sprite moves
	def isMoving(self):
		return False
	
	##############################
	# Sprite type identification #
	##############################
	def isWall(self):
		return False

	def isPacman(self):
		return False

	def isGhost(self):
		return False

	def isPellet(self):
		return False

	def isFruit(self):
		return False	


class Wall(Sprite):

	wallImage = pygame.image.load("images/wall.png")

	def __init__(self, x, y, w, h):
		super().__init__(x, y, w, h)

	def draw(self, yFixed):
		LOCATION = (self.x, self.y + yFixed)
		SIZE = (self.w, self.h)
		PICTURE = (pygame.transform.scale(self.wallImage, SIZE), LOCATION)
		return PICTURE

	def update(self):
		return self.isValid

	# Wall identification
	def isWall(self):
		return True


class Pacman(Sprite):
	def __init__(self, x, y):
		self.prevX = 0
		self.prevY = 0
		self.direction = 3
		self.yFixed = 0
		self.frameIteration = 1
		self.speed = 10
		self.MAX_IMAGES_PER_DIRECTION = 4
		self.pacmanImages = []
		super().__init__(x, y, 28, 35)
		# Initialize list of Player Images
		self.pacmanImages.append("NULL")
		for i in range(1, self.MAX_IMAGES_PER_DIRECTION * 4 + 1):
			self.pacmanImages.append(pygame.image.load("images/player" + str(i) + ".png"))

	def draw(self, throwAway):
		if (self.y >= 650): 
			self.yFixed = self.y - 300
		elif (self.y <= 50): 
			self.yFixed = self.y + 300
		  
		else: 
			self.yFixed = 350
			## Allows for the player to teleport horizontally
			##########################
			if (self.x > 750): 
				self.x = -50
		  
			elif (self.x < -50):
				self.x = 750
		  
			#########################
		LOCATION = (self.x, self.yFixed)
		SIZE = (self.w, self.h)
		PICTURE = (pygame.transform.scale(self.pacmanImages[self.MAX_IMAGES_PER_DIRECTION * self.direction + self.frameIteration], SIZE), LOCATION)
		return PICTURE

	def update(self):
		return self.isValid
	
	def isMoving(self):
		return True
	
	def isPacman(self):
		return True
	
	def iterateFrame(self):
		self.frameIteration+=1
		if (self.frameIteration > self.MAX_IMAGES_PER_DIRECTION):
			self.frameIteration = 1

	def movePlayer(self, playerDir):
		self.direction = playerDir
		if (self.direction == 0):
			self.x -= self.speed
		elif (self.direction == 1):
			self.y -= self.speed
		elif (self.direction == 2):
			self.x += self.speed
		elif (self.direction == 3):
			self.y += self.speed

	def getOutOfWall(self, sprite):

		# Right
		if (self.x + self.w >= sprite.x and self.prevX + self.w <= sprite.x):
			self.x = sprite.x - self.w

		# Left
		elif (self. x <= sprite.x + sprite.w and self.prevX >= sprite.x + sprite.w):
			self.x = sprite.x + sprite.w

		# Bottom
		elif ((self.y + self.h >= sprite.y and self.y + self.h <= sprite.y + sprite.h) and self.prevY + self.h <= sprite.y):
			self.y =sprite.y - self.h
		
		# Top
		elif ((self.y >= sprite.y and self.y <= sprite.y + sprite.h) and self.prevY >= sprite.y + sprite.h):
			self.y = sprite.y + sprite.h


	def normalSpeed(self):
		self.speed = 10

	def sprintSpeed(self):
		self.speed = 15


class Model():
	# Constructor
	def __init__(self):
		self.sprites = []
		self.queuedSprites = []
		################################
		self.gridDim = 50
		self.pelletDim = 10
		self.ghostDim = 40
		self.fruitDim = 40
		self.spriteFoundAtCursorPos = False
		self.score = 0
		self.loadMap()
		
	# Loads the map
	def loadMap(self):
		self.sprites.clear()
		self.player = Pacman(360, 350)
		self.sprites.append(self.player)
		#open the json map and pull out the individual lists of sprite objects
		with open("map.json") as file:
			data = json.load(file)
			#get the list labeled as "lettuces" from the map.json file
			walls = data["walls"]
			ghosts = data["ghosts"]
			fruits = data["fruits"]
			pellets = data["pellets"]
		file.close()
		
		# Unmarshals the sprites from json file
		for entry in pellets:
			self.sprites.append(Pellet(entry["x"], entry["y"], entry["w"], entry["h"]))
		for entry in walls:
			self.sprites.append(Wall(entry["x"], entry["y"], entry["w"], entry["h"]))
		for entry in fruits:
			self.sprites.append(Fruit(entry["x"], entry["y"], entry["w"], entry["h"], entry["fruitType"]))
		for entry in ghosts:
			self.sprites.append(Ghost(entry["x"], entry["y"], entry["w"], entry["h"], entry["ghostType"]))
		print("\t  ---------------------\n"+ "\t  | -- Map loaded! -- |\n" + "\t  ---------------------\n")	

	# Updates all sprites and removes elements if necessary
	def update(self):	
		# Updates player element within the element ArrayList
		# Updates player element within the element ArrayList
		if len(self.sprites) == 0:
			self.sprites.append(self.player)
		else:
			self.sprites[0] = self.player
		# Adds queued sprites to avoid concurrent modification errors
		self.sprites += self.queuedSprites 
		# Removes all elements from queuedSprites[]
		self.queuedSprites.clear()
		# update sprites, remove if necessary, THEN call collision detection/fixing
		for sprite1 in self.sprites:
			# if not valid, remove from list and continue
			if sprite1.update() == False:
				self.sprites.remove(sprite1)
				continue 

			if sprite1.isMoving():
				for sprite2 in self.sprites:
					if (sprite1 != sprite2 and sprite1.doesItCollide(sprite2)): 
						if(sprite1.isFruit() and sprite2.isWall()):				# Fruit vs. Wall
							sprite1.getOutOfWall(sprite2)
						elif(sprite1.isGhost() and sprite2.isWall()):			# Ghost vs. Wall
							sprite1.getOutOfWall(sprite2)
						elif (sprite1.isPacman() and sprite2.isWall()):			# Pacman vs. Wall
							self.player.getOutOfWall(sprite2)
						elif (sprite1.isPacman() and sprite2.isGhost()):		# Pacman vs. Ghost
							if(sprite2.scorable):
								self.score += 10
							sprite2.eatGhost()
						elif (sprite1.isPacman() and sprite2.isPellet()):		# Pacman vs. Pellet
							(sprite2).eatPellet()
							self.score += 1
						elif (sprite1.isPacman() and sprite2.isFruit()):		# Pacman vs. Fruit
							sprite2.eatFruit()
							self.score += 25
		

	# Add pellets to grid
	def setWallGridClick(self, xClick, yClick): 
		posXClick = math.floor(xClick / self.gridDim) * self.gridDim
		posYClick = math.floor(yClick / self.gridDim) * self.gridDim
		self.spriteAtCursorPos(posXClick, posYClick)		

		if (self.spriteFoundAtCursorPos): 
			for i in range(len(self.sprites)):
				if (self.sprites[i].spriteExists(posXClick, posYClick)):
					self.sprites.pop(i)
					break
		else: 
			sprite = Wall(posXClick, posYClick, self.gridDim, self.gridDim)
			self.queuedSprites.append(sprite)

	# Add ghosts to grid
	def setGhostGridClick(self, xClick, yClick): 
		posXClick = math.floor(xClick / self.gridDim) * self.gridDim
		posYClick = math.floor(yClick / self.gridDim) * self.gridDim
		self.spriteAtCursorPos(posXClick, posYClick)		
		
		if (not self.spriteFoundAtCursorPos): 
			# The "+5" in the coords centers the ghost
			sprite = Ghost(posXClick + 5, posYClick + 5, self.ghostDim, self.ghostDim, 20)
			self.queuedSprites.append(sprite)
		
	# Add pellets to grid
	def setPelletGridClick(self, xClick, yClick): 
		posXClick = math.floor(xClick / self.gridDim) * self.gridDim
		posYClick = math.floor(yClick / self.gridDim) * self.gridDim
		self.spriteAtCursorPos(posXClick, posYClick)		
		
		if (not self.spriteFoundAtCursorPos): 
			# The "+20" in the coords centers the pellet
			sprite = Pellet(posXClick + 20, posYClick + 20, self.pelletDim, self.pelletDim)
			self.queuedSprites.append(sprite)

	# Add fruit to grid
	def setFruitGridClick(self, xClick, yClick): 
		posXClick = math.floor(xClick / self.gridDim) * self.gridDim
		posYClick = math.floor(yClick / self.gridDim) * self.gridDim
		self.spriteAtCursorPos(posXClick, posYClick)		
		
		if (not self.spriteFoundAtCursorPos): 
			# The "+5" in the coords centers the fruit
			sprite = Fruit(posXClick + 5, posYClick + 5, self.fruitDim, self.fruitDim, 10)
			self.queuedSprites.append(sprite)

	# Loops through walls ArrayList and checks if wall exists based on inputted grid coords
	def spriteAtCursorPos(self, x, y):
		for i in range(len(self.sprites)):
			if (self.sprites[i].spriteExists(x, y)):
				self.spriteFoundAtCursorPos = True
				return;
		self.spriteFoundAtCursorPos = False;

	# Lets player sprint
	def sprintSpeed(self): 
		self.player.sprintSpeed()
	
	# Sets player speed back to normal
	def normalSpeed(self): 
		self.player.normalSpeed()
	
	# Following four methods allow for the use of arrow keys in Controller.java
	def changePlayerDirection(self, playerDir): 
		self.player.movePlayer(playerDir)
		self.player.iterateFrame()


class View():
	def __init__(self, model):
		screen_size = (750,750)
		self.screen = pygame.display.set_mode(screen_size, 32)
		self.model = model
		########################
		self.scrollPosYView = 0
		self.mode = "View"
		self.modeType = None
		self.isEdit = False

	def update(self):
		# Moves the map screen while not at one of the vertical wall borders
		# --------------------------------------------------------------------
		for i in range(len(self.model.sprites)):		
			sprite = self.model.sprites[i]
			if (sprite.isPacman()): 
				# Conditionals prevent the camera from going beyond vertical boundaries
				self.scrollPosYView = 350 - sprite.y
				if (self.scrollPosYView < -300): 
					self.scrollPosYView = -300
				if (self.scrollPosYView > 300): 
					self.scrollPosYView = 300
		
		# Changes the background color depending on current mode (View or Edit)
		if self.mode == "View":
			self.screen.fill([255, 186, 99])
		elif self.mode == "Edit":
			self.screen.fill([164, 193, 255])
		for sprite in self.model.sprites:
			self.screen.blit(sprite.draw(self.scrollPosYView)[0], sprite.draw(self.scrollPosYView)[1])
		
		# Score & Edit text
		font_score = pygame.font.SysFont("Calibri", 25, bold=True)
		font_edit = pygame.font.SysFont("Calibri", 20, bold=True)
		BLACK = (0, 0, 0)
		WHITE = (255, 255, 255)

		text = font_score.render("Score: " + str(self.model.score), True, BLACK)
		self.screen.blit(text, (323, 15))
		text = font_score.render("Score: " + str(self.model.score), True, WHITE)
		self.screen.blit(text, (321, 13))

		if self.mode == "Edit":
			text = font_edit.render("Edit: " + self.modeType, True, BLACK)
			self.screen.blit(text, (11, 725))
			text = font_edit.render("Edit: " + self.modeType, True, WHITE)
			self.screen.blit(text, (9, 723))


		pygame.display.flip()


	def setMode(self, mode):
		if(self.mode == "View"):
			self.isEdit = False
			return
		self.isEdit = True

	def setModeType(self, modeType):
		self.modeType = modeType


class Controller():
	def __init__(self, model, view):
		self.model = model
		self.view = view
		self.keep_going = True
		self.mode = "View"
		self.modeType = "Ghosts"
		self.keyE = False 
		self.keyG = False
		self.keyF = False
		self.keyP = False
		self.keyA = False
		self.key_right = False
		self.key_left = False
		self.key_up = False
		self.key_down = False

	def setFalse(self):
		self.keyG = False
		self.keyP = False
		self.keyF = False
		self.keyA = False

	def update(self):
		for event in pygame.event.get():
			if event.type == QUIT:
				self.keep_going = False
			elif event.type == KEYDOWN:
				if event.key == K_ESCAPE or event.key == K_q:
					self.keep_going = False
				elif event.key == K_RIGHT:
					self.key_right = True
				elif event.key == K_LEFT:
					self.key_left = True
				elif event.key == K_UP:
					self.key_up = True
				elif event.key == K_DOWN:
					self.key_down = True
				elif event.key == K_LSHIFT or event.key == K_RSHIFT:
					self.model.sprintSpeed()
			
			elif event.type == pygame.MOUSEBUTTONUP:	
				self.xClick, self.yClick = event.pos
				self.yClick -= self.view.scrollPosYView
				if self.keyE:
					if self.keyG:
						self.model.setGhostGridClick(self.xClick, self.yClick)
					elif self.keyF:
						self.model.setFruitGridClick(self.xClick, self.yClick)
					elif self.keyP:
						self.model.setPelletGridClick(self.xClick, self.yClick)
					elif self.keyA:
						self.model.setWallGridClick(self.xClick, self.yClick)

			elif event.type == pygame.KEYUP: #this is keyReleased!
				if event.key == K_RIGHT:
					self.key_right = False
				elif event.key == K_LEFT:
					self.key_left = False
				elif event.key == K_UP:
					self.key_up = False
				elif event.key == K_DOWN:
					self.key_down = False
				elif event.key == K_LSHIFT or event.key == K_RSHIFT:
					self.model.normalSpeed()
				elif event.key == K_e:
					self.keyE = not self.keyE
					if (self.keyE):
						self.view.mode = "Edit"
						self.view.modeType = "Ghosts"
						self.keyG = True
					else:
						self.view.mode = "View"
				elif event.key == K_g:
					self.view.modeType = "Ghosts"
					self.setFalse()
					self.keyG = True
				elif event.key == K_p:
					self.view.modeType = "Pellets"
					self.setFalse()
					self.keyP = True
				elif event.key == K_f:
					self.view.modeType = "Fruits"
					self.setFalse()
					self.keyF = True
				elif event.key == K_a:
					self.view.modeType = "Walls"
					self.setFalse()
					self.keyA = True
				elif event.key == K_l:
					self.model.loadMap()
				elif event.key == K_c:
					self.model.sprites.clear()
					print("\t  ----------------------\n"+ "\t  | -- Map cleared! -- |\n" + "\t  ----------------------\n")



		# sets player's previous coords for collision fixing
		self.model.player.prevX = self.model.player.x
		self.model.player.prevY = self.model.player.y

		# controls movement for player 
		# elif statements prevents player from going two directions at once
		if(self.key_up): self.model.changePlayerDirection(1)
		elif(self.key_down): self.model.changePlayerDirection(3)
		elif(self.key_left): self.model.changePlayerDirection(0)
		elif(self.key_right): self.model.changePlayerDirection(2)


class Ghost(Sprite):
	def __init__(self, x, y, w, h, ghostType):
		super().__init__(x, y, w, h)
		if ghostType >= 0 and ghostType <= 3:
			self.ghostType = ghostType
		else:
			self.ghostType = random.randint(0,3)
		self.MAX_IMAGES_PER_DIRECTION = 2
		self.MAX_DEATH_IMAGES = 6
		self.frameIteration = 1
		self.direction = random.randint(0,3)
		self.prevX = 0
		self.prevY = 0
		self.speed = 5
		self.isDying = False
		self.deathTimer = 50
		self.scorable = True
		# Looks weird but I used it to initialize a 2D array
		# I tried other methods but they didn't work for me.
		self.ghostImages = [[] for _ in range(4)]
		self.deathImages = []
		
		# Load images into ghostImages[]
		for j in range(4):
			self.ghostImages[j].append(1)
			for i in range (1, self.MAX_IMAGES_PER_DIRECTION * 4 + 1):
				# Fills in the 0 index
				self.ghostImages[j].append(pygame.image.load("images/"+ str(j) + "-" + str(i) + ".png"))
		# Load images into deathImages[]
		for i in range (1, self.MAX_DEATH_IMAGES):
			self.deathImages.append(pygame.image.load("images/ghost" + str(i) + ".png"))

	def update(self):
		if(not self.isDying):
			self.move()
		else:
			self.deathTimer-=1
			if(self.deathTimer <= 0):
				self.isValid = False
		return self.isValid

	def draw(self, yFixed):
		if(self.x > 750):
			self.x = -50
		elif(self.x < -50):
			self.x = 750

		LOCATION = (self.x, self.y + yFixed)
		SIZE = (self.w, self.h)

		if(self.isDying):
			if(self.deathTimer > 40):
					PICTURE = (pygame.transform.scale(self.deathImages[0], SIZE), LOCATION)
			elif(self.deathTimer > 30):
					PICTURE = (pygame.transform.scale(self.deathImages[1], SIZE), LOCATION)
			elif(self.deathTimer > 20):
					PICTURE = (pygame.transform.scale(self.deathImages[2], SIZE), LOCATION)
			elif(self.deathTimer > 10):
					PICTURE = (pygame.transform.scale(self.deathImages[3], SIZE), LOCATION)
			else:
					PICTURE = (pygame.transform.scale(self.deathImages[4], SIZE), LOCATION)
		else:
			PICTURE = (pygame.transform.scale(self.ghostImages[self.ghostType][self.MAX_IMAGES_PER_DIRECTION * self.direction + self.frameIteration], SIZE), LOCATION)
			#########################
		
		return PICTURE

	def eatGhost(self):
		self.isDying = True
		self.scorable = False

	def move(self):
		self.iterateFrame()
		self.prevX = self.x
		self.prevY = self.y

		if(self.direction == 0):
			self.x-=self.speed
		elif(self.direction == 1):
			self.y-=self.speed
		elif(self.direction == 2):
			self.x+=self.speed
		elif(self.direction == 3):
			self.y+=self.speed

	def iterateFrame(self):
		self.frameIteration+=1
		# Sets current frame to 1 if it exceeds 2
		if(self.frameIteration > self.MAX_IMAGES_PER_DIRECTION):
			self.frameIteration = 1

	def getOutOfWall(self, sprite):
		# Right
		if self.x + self.w >= sprite.x and self.prevX + self.w <= sprite.x:
			self.x = sprite.x - self.w
		# Left
		if self.x <= sprite.x + sprite.w and self.prevX >= sprite.x + sprite.w:
			self.x = sprite.x + sprite.w
		# Bottom
		if (self.y + self.h >= sprite.y and self.y + self.h <= sprite.y + sprite.h) and self.prevY + self.h <= sprite.y:
			self.y = sprite.y - self.h
		# Top
		if (self.y >= sprite.y and self.y <= sprite.y + sprite.h) and self.prevY >= sprite.y + sprite.h:
			self.y = sprite.y + sprite.h

		# Randomly change direction
		tempDirection = self.direction
		while (tempDirection == self.direction):
			self.direction = math.floor(random.randint(0,3))

	# Wall identification
	def isGhost(self):
		return True

	def isMoving(self):
		return True


class Fruit(Sprite):
	def __init__(self, x, y, w, h, fruitType):
		
		super().__init__(x, y, w, h)
		self.prevX = 0
		self.prevY = 0
		self.bounceDirection = random.randint(0,3)
		self.numFruit = 7
		self.speed = 5
		if (fruitType >= 0 and fruitType <= 6):
			self.fruitType = fruitType
		else:
			self.fruitType = random.randint(0,6)
		self.fruitImages = []
		for i in range(self.numFruit):
			self.fruitImages.append(pygame.image.load("images/fruit"+ str(i) + ".png"))



	def update(self):
		self.move()
		return self.isValid


	def draw(self, yFixed):
		if(self.x > 750):
			self.x = -50
		elif(self.x < -50):
			self.x = 750

		LOCATION = (self.x, self.y + yFixed)
		SIZE = (self.w, self.h)
		PICTURE = (pygame.transform.scale(self.fruitImages[self.fruitType], SIZE), LOCATION)
		return PICTURE


	def move(self):
		# Previous coords used for collision fixing
		self.prevX = self.x
		self.prevY = self.y

		# Moves fruit depending on bounce direction
		if(self.bounceDirection == 0):
			self.x+=self.speed
		elif(self.bounceDirection == 1):
			self.y-=self.speed
		elif(self.bounceDirection == 2):
			self.x-=self.speed
		elif(self.bounceDirection == 3):
			self.y+=self.speed


	def getOutOfWall(self, sprite):
			# Right
			if self.x + self.w >= sprite.x and self.prevX + self.w <= sprite.x:
				self.x = sprite.x - self.w
			# Left
			if self.x <= sprite.x + sprite.w and self.prevX >= sprite.x + sprite.w:
				self.x = sprite.x + sprite.w
			# Bottom
			if (self.y + self.h >= sprite.y and self.y + self.h <= sprite.y + sprite.h) and self.prevY + self.h <= sprite.y:
				self.y = sprite.y - self.h
			# Top
			if (self.y >= sprite.y and self.y <= sprite.y + sprite.h) and self.prevY >= sprite.y + sprite.h:
				self.y = sprite.y + sprite.h

			# Randomly change direction
			tempDirection = self.bounceDirection
			while (tempDirection == self.bounceDirection):
				self.bounceDirection = math.floor(random.randint(0,3))

	# Fruit identification
	def isFruit(self):
		return True
	
	def isMoving(self):
		return True
	
	def eatFruit(self):
		self.isValid = False


class Pellet(Sprite):

	pelletImage = pygame.image.load("images/pellet.png")

	def __init__(self, x, y, w, h):
		super().__init__(x, y, w, h)

	def draw(self, yFixed):
		LOCATION = (self.x, self.y + yFixed)
		SIZE = (self.w, self.h)
		PICTURE = (pygame.transform.scale(self.pelletImage, SIZE), LOCATION)
		return PICTURE

	def update(self):
		return self.isValid

	# Pellet identification
	def isPellet(self):
		return True
	
	def eatPellet(self):
		self.isValid = False

# Controls menu
print("\n\n               " +"Keybindings:\n" + "-----------------------------------------\n" + "| Q/q\t\t- Exits program         |\n" + "| Escape\t- Exits program         |\n" +  "| Arrow Keys\t- Player movement       |\n" + "| Shift\t\t- Activates sprint      |\n" + "| L/l\t\t- Loads map from save   |\n" + "| E/e\t\t- Toggles editing mode  |\n" +"| A/a\t\t- Add/Remove walls      |\n" + "| G/g\t\t- Add ghosts            |\n" + "| P/p\t\t- Add pellets           |\n" + "| F/f\t\t- Add fruit             |\n" + "| C/c\t\t- Remove all sprites    |\n" + "-----------------------------------------\n")
pygame.init()
m = Model()
v = View(m)
c = Controller(m, v)
while c.keep_going:
	c.update()
	m.update()
	v.update()
	sleep(0.04)