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