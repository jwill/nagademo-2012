package com.html5gamebook.core

import "playn.core.PlayN"

import "playn.core.Game"
import "playn.core.Image"
import "playn.core.ImageLayer"

class MirahExample 
	implements Game
  $Override
  def init:void
    # create and add background image layer
    bgImage = PlayN.assets().getImage("images/bg.png")
    bgLayer = PlayN.graphics().createImageLayer(bgImage)
    PlayN.graphics().rootLayer().add(bgLayer)
  end

  $Override
  def paint(alpha:float):void 
    # the background automatically paints itself, so no need to do anything here!
  end

  $Override
  def update(delta:float):void
  end

  $Override
  def updateRate:int
    return 25
  end
end

